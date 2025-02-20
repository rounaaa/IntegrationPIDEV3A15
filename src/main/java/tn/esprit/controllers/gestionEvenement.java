package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.models.Evenement;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceEvenement;
import tn.esprit.services.ServiceUtilisateur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

public class gestionEvenement {

    @FXML
    private TextField tfNomEvenement;
    @FXML
    private TextArea tfDescription;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextField tfLieu;
    @FXML
    private ComboBox<String> cbStatut;
    @FXML
    private TextField tfCapacite;
    @FXML
    private TextField tfImage;
    @FXML
    private TextField tfIdUser;
    @FXML
    private Label lblResult;
    @FXML
    private ListView<String> lvEvenements;
    @FXML
    private Button participerButton;
    private final ServiceEvenement serviceEvenement = new ServiceEvenement() {
        @Override
        public List<Evenement> modifierEvenement() {
            return List.of();
        }
    };
    private final ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();

    @FXML
    public void initialize() {
        cbStatut.getItems().addAll("actif", "annulé", "terminé");
        afficherEvenements();

        lvEvenements.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    int id = Integer.parseInt(newValue.split(" - ")[0]);
                    Evenement evenement = serviceEvenement.getById(id);
                    if (evenement != null) {
                        tfNomEvenement.setText(evenement.getNom_evenement());
                        tfDescription.setText(evenement.getDescription());
                        dpDate.setValue(evenement.getDate().toLocalDate());
                        tfLieu.setText(evenement.getLieu());
                        cbStatut.setValue(evenement.getStatut());
                        tfCapacite.setText(String.valueOf(evenement.getCapacite_max()));
                        tfImage.setText(evenement.getImage());
                        tfIdUser.setText(String.valueOf(evenement.getUtilisateur().getid_user()));
                    }
                } catch (Exception e) {
                    lblResult.setText("Erreur de sélection de l'événement.");
                }
            }
        });
    }

    @FXML
    public void ajouterEvenement(ActionEvent actionEvent) {
        try {

            String nom = tfNomEvenement.getText().trim();
            String description = tfDescription.getText().trim();
            LocalDateTime date = dpDate.getValue() != null ? dpDate.getValue().atStartOfDay() : null;

            String lieu = tfLieu.getText().trim();
            String statut = cbStatut.getValue();
            String capaciteText = tfCapacite.getText().trim();
            String image = tfImage.getText().trim();
            String idUserText = tfIdUser.getText().trim();

            // Vérification des champs obligatoires
            if (nom.isEmpty() || description.isEmpty() || lieu.isEmpty() || statut == null || capaciteText.isEmpty() || image.isEmpty() || idUserText.isEmpty()) {
                lblResult.setText("Veuillez remplir tous les champs !");
                return;
            }


            if (date == null) {
                lblResult.setText("Dates invalides !");
                return;
            }


            int capacite;
            try {
                capacite = Integer.parseInt(capaciteText);
            } catch (NumberFormatException e) {
                lblResult.setText("Capacité invalide !");
                return;
            }

            int idUser;
            try {
                idUser = Integer.parseInt(idUserText);
            } catch (NumberFormatException e) {
                lblResult.setText("ID utilisateur invalide !");
                return;
            }


            Utilisateur utilisateur = serviceUtilisateur.getById(idUser); // Obtenir l'utilisateur

            if (utilisateur == null) {
                lblResult.setText("Utilisateur non trouvé !");
                return;
            }


            Evenement evenement = new Evenement(nom, description, date, lieu, statut, capacite, image, utilisateur);
            serviceEvenement.add(evenement);


            lblResult.setText("Événement ajouté !");
            afficherEvenements();
            resetFields();
        } catch (Exception e) {
            lblResult.setText("Erreur lors de l'ajout !");
        }
    }

    @FXML
    public void afficherEvenements() {
        lvEvenements.getItems().clear();
        List<Evenement> evenements = serviceEvenement.getAll();
        for (Evenement e : evenements) {
            lvEvenements.getItems().add(e.getId_evenement() + " - " + e.getNom_evenement());
        }
    }

    private void resetFields() {
        tfNomEvenement.clear();
        tfDescription.clear();
        dpDate.setValue(null);
        tfLieu.clear();
        cbStatut.setValue(null);
        tfCapacite.clear();
        tfImage.clear();
        tfIdUser.clear();
    }

    @FXML
    public void modifierEvenement(ActionEvent actionEvent) {
        String selected = lvEvenements.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                int id = Integer.parseInt(selected.split(" - ")[0]);
                Evenement evenement = serviceEvenement.getById(id); // Récupérer l'événement via la méthode d'instance
                if (evenement != null) {
                    String nom = tfNomEvenement.getText().trim();
                    String description = tfDescription.getText().trim();
                    LocalDateTime date = dpDate.getValue() != null ? dpDate.getValue().atStartOfDay() : null;

                    String lieu = tfLieu.getText().trim();
                    String statut = cbStatut.getValue();
                    String capaciteText = tfCapacite.getText().trim();
                    String image = tfImage.getText().trim();

                    // Vérification des champs obligatoires
                    if (nom.isEmpty() || description.isEmpty() || lieu.isEmpty() || statut == null || capaciteText.isEmpty() || image.isEmpty()) {
                        lblResult.setText("Veuillez remplir tous les champs !");
                        return;
                    }


                    if (date == null) {
                        lblResult.setText("Dates invalides !");
                        return;
                    }


                    int capacite;
                    try {
                        capacite = Integer.parseInt(capaciteText);
                    } catch (NumberFormatException e) {
                        lblResult.setText("Capacité invalide !");
                        return;
                    }


                    evenement.setNom_evenement(nom);
                    evenement.setDescription(description);
                    evenement.setDate(date);
                    evenement.setLieu(lieu);
                    evenement.setStatut(statut);
                    evenement.setCapacite_max(capacite);
                    evenement.setImage(image);

                    serviceEvenement.modifierEvenement();
                    lblResult.setText("Événement modifié !");
                    afficherEvenements();
                    resetFields();

                }
            } catch (Exception e) {
                lblResult.setText("Erreur lors de la modification !");
            }
        } else {
            lblResult.setText("⚠ Sélectionnez un événement !");
        }
    }

    @FXML
    public void supprimerEvenement(ActionEvent actionEvent) {
        String selected = lvEvenements.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                int id = Integer.parseInt(selected.split(" - ")[0]);
                serviceEvenement.supprimerEvenement(id);
                lblResult.setText(" Événement supprimé !");
                afficherEvenements();
                resetFields(); // Réinitialiser les champs
            } catch (NumberFormatException e) {
                lblResult.setText(" Erreur de sélection !");
            }
        } else {
            lblResult.setText(" Sélectionnez un événement !");
        }
    }
    @FXML
    private void ouvrirParticipation(ActionEvent event) {
        Stage secondStage = new Stage(); // Créer une nouvelle fenêtre (Stage)
        setSecondStage(secondStage);       // Charger et afficher la fenêtre
    }

    private void setSecondStage(Stage secondStage) {
        try {
            // Vérifie que le fichier FXML est bien trouvé
            URL fxmlUrl = getClass().getResource("/participation.fxml");
            if (fxmlUrl == null) {
                System.err.println("Erreur : participation.fxml introuvable. Vérifiez son emplacement.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            secondStage.setScene(scene);
            secondStage.setTitle("---- Gestion participation -----");
            secondStage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de participation.fxml : " + e.getMessage());
            e.printStackTrace();
        }
    }


}



