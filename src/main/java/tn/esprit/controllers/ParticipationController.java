package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.models.Evenement;
import tn.esprit.models.Participation;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ParticipationService;
import tn.esprit.services.ServiceEvenement;
import tn.esprit.services.ServiceUtilisateur;

import java.time.LocalDateTime;
import java.util.List;

public class ParticipationController {

    @FXML
    private ListView<String> lvEvenements;
    @FXML
    private TextField tfIdEvenement;
    @FXML
    private TextField tfIdUser;
    @FXML
    private TextField tfMoyenPaiement;

    @FXML
    private Label lblResult;
    @FXML
    private TextField tfMotifAnnulation;


    // Correction du nom de la variable
    private final ParticipationService participationService = new ParticipationService();
    private final ServiceEvenement serviceEvenement = new ServiceEvenement() {
        @Override
        public List<Evenement> modifierEvenement() {
            return List.of();
        }
    };
    private final ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();

    @FXML
    public void initialize() {
        System.out.println("lvEvenements: " + lvEvenements);
        if (lvEvenements != null) {
            afficherEvenements();
        } else {
            System.out.println("ERREUR : lvEvenements est null !");
        }
    }


    public void afficherEvenements() {
        lvEvenements.getItems().clear();
        List<Evenement> evenements = serviceEvenement.getAll();
        ObservableList<String> evenementList = FXCollections.observableArrayList();

        for (Evenement evenement : evenements) {
            String evenementAffichage = evenement.getId_evenement() + " - "
                    + evenement.getNom_evenement() + " | "
                    + evenement.getDate() + " | "
                    + evenement.getLieu();
            evenementList.add(evenementAffichage);
        }

        lvEvenements.setItems(evenementList);
    }

    @FXML
    public void participerEvenement() {
        try {
            int idUser = Integer.parseInt(tfIdUser.getText().trim());
            int idEvenement = Integer.parseInt(tfIdEvenement.getText().trim());
            String moyenPaiement = tfMoyenPaiement.getText().trim();

            Utilisateur utilisateur = serviceUtilisateur.getById(idUser);
            Evenement evenement = serviceEvenement.getById(idEvenement);

            if (utilisateur == null || evenement == null) {
                lblResult.setText("Utilisateur ou événement introuvable !");
                return;
            }

            // Appel à la méthode d'ajout de participation
            participationService.ajouterParticipation(utilisateur, evenement, moyenPaiement);
            lblResult.setText("Participation ajoutée avec succès !");
            afficherEvenements(); // Actualiser la liste
        } catch (NumberFormatException e) {
            lblResult.setText("ID invalide !");
        }
    }

    @FXML
    public void annulerParticipation() {
        String selected = lvEvenements.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // On suppose que l'ID de participation se trouve avant le tiret (" - ")
            int idParticipation = Integer.parseInt(selected.split(" - ")[0]);
            try {
                // Conversion du motif d'annulation en entier (puisque la table attend un int)
                int motifAnnulation = Integer.parseInt(tfMotifAnnulation.getText().trim());
                participationService.annulerParticipation(idParticipation, String.valueOf(motifAnnulation));
                lblResult.setText("Participation annulée !");
                afficherEvenements();
            } catch (NumberFormatException e) {
                lblResult.setText("Motif d'annulation invalide !");
            }
        } else {
            lblResult.setText("Sélectionnez une participation !");
        }
    }
}
