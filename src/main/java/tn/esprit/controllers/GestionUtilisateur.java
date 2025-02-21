package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceUtilisateur;
import javafx.scene.control.ButtonType;

public class GestionUtilisateur {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfCIN;
    @FXML
    private TextField tfMDP;
    @FXML
    private GridPane usersCardContainer;

    private ServiceUtilisateur su = new ServiceUtilisateur();
    private Utilisateur utilisateurSelectionne;

    @FXML
    public void inscrireUtilisateur(ActionEvent actionEvent) {
        // Inscription de l'utilisateur
        if (tfNom.getText().trim().isEmpty() || tfPrenom.getText().trim().isEmpty() || tfEmail.getText().trim().isEmpty()
                || tfCIN.getText().trim().isEmpty() || tfMDP.getText().trim().isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis !", AlertType.ERROR);
            return;
        }

        try {
            int cin = Integer.parseInt(tfCIN.getText().trim());
            if (!isValidCIN(cin)) {
                showAlert("Erreur", "Le CIN doit contenir exactement 8 chiffres !", AlertType.ERROR);
                return;
            }

            if (su.existsByCIN(cin)) {
                showAlert("Erreur", "Ce CIN est déjà utilisé !", AlertType.ERROR);
                return;
            }

            String email = tfEmail.getText().trim();
            if (!isValidEmail(email)) {
                showAlert("Erreur", "Format d'email invalide !", AlertType.ERROR);
                return;
            }

            if (su.existsByEmail(email)) {
                showAlert("Erreur", "Cet email est déjà utilisé !", AlertType.ERROR);
                return;
            }

            Utilisateur u = new Utilisateur();
            u.setNom(tfNom.getText().trim());
            u.setPrenom(tfPrenom.getText().trim());
            u.setEmail(email);
            u.setCin(cin);
            u.setMotDePasse(tfMDP.getText().trim());
            u.setRole("CITOYEN");

            su.add(u);

            // Affichage d'un message de succès
            showAlert("Succès", "Citoyen ajouté avec succès.", AlertType.INFORMATION);
            resetFields();
            afficherUtilisateurs(null); // Rafraîchir la liste après ajout
        } catch (NumberFormatException e) {
            showAlert("Erreur", "CIN doit être un nombre valide !", AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue : " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void afficherUtilisateurs(ActionEvent actionEvent) {
        try {
            var utilisateurs = su.getAll();
            usersCardContainer.getChildren().clear();  // Clear existing children in the grid

            int row = 0;
            for (Utilisateur utilisateur : utilisateurs) {
                HBox userCard = new HBox(10);
                userCard.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5px; -fx-padding: 10px;");

                userCard.getChildren().add(new Text("Nom: " + utilisateur.getNom()));
                userCard.getChildren().add(new Text("Prénom: " + utilisateur.getPrenom()));
                userCard.getChildren().add(new Text("Email: " + utilisateur.getEmail()));
                userCard.getChildren().add(new Text("CIN: " + utilisateur.getCin()));
                userCard.getChildren().add(new Text("Role: " + utilisateur.getRole()));

                // Bouton Modifier
                Button btnModifier = new Button("Modifier");
                btnModifier.setOnAction(e -> remplirChampsPourModification(utilisateur));
                userCard.getChildren().add(btnModifier);

                // Bouton Supprimer
                Button btnSupprimer = new Button("Supprimer");
                btnSupprimer.setOnAction(e -> supprimerUtilisateur(utilisateur));
                userCard.getChildren().add(btnSupprimer);

                // Ajouter chaque carte utilisateur dans la grille à une position spécifique
                usersCardContainer.add(userCard, 0, row++);
            }
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de l'affichage des utilisateurs : " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void remplirChampsPourModification(Utilisateur utilisateur) {
        utilisateurSelectionne = utilisateur;
        tfNom.setText(utilisateur.getNom());
        tfPrenom.setText(utilisateur.getPrenom());
        tfEmail.setText(utilisateur.getEmail());
        tfCIN.setText(String.valueOf(utilisateur.getCin()));
        tfMDP.setText(utilisateur.getMotDePasse());
    }

    @FXML
    public void updateUtilisateur(ActionEvent actionEvent) {
        if (utilisateurSelectionne != null) {
            try {
                int cin = Integer.parseInt(tfCIN.getText().trim());
                if (!isValidCIN(cin)) {
                    showAlert("Erreur", "Le CIN doit contenir exactement 8 chiffres !", AlertType.ERROR);
                    return;
                }

                String email = tfEmail.getText().trim();
                if (!isValidEmail(email)) {
                    showAlert("Erreur", "Format d'email invalide !", AlertType.ERROR);
                    return;
                }

                utilisateurSelectionne.setNom(tfNom.getText().trim());
                utilisateurSelectionne.setPrenom(tfPrenom.getText().trim());
                utilisateurSelectionne.setEmail(email);
                utilisateurSelectionne.setCin(cin);
                utilisateurSelectionne.setMotDePasse(tfMDP.getText().trim());

                su.update(utilisateurSelectionne);
                showAlert("Succès", "Utilisateur mis à jour avec succès !", AlertType.INFORMATION);
                resetFields();
                afficherUtilisateurs(null); // Rafraîchir la liste après mise à jour
            } catch (Exception e) {
                showAlert("Erreur", "Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage(), AlertType.ERROR);
            }
        }
    }

    private void supprimerUtilisateur(Utilisateur utilisateur) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
        alert.setContentText(utilisateur.getNom() + " " + utilisateur.getPrenom());
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                su.delete(utilisateur);
                showAlert("Succès", "Utilisateur supprimé avec succès !", AlertType.INFORMATION);
                afficherUtilisateurs(null); // Rafraîchir la liste après suppression
            }
        });
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    private boolean isValidCIN(int cin) {
        return String.valueOf(cin).length() == 8;
    }

    private void resetFields() {
        tfNom.clear();
        tfPrenom.clear();
        tfEmail.clear();
        tfCIN.clear();
        tfMDP.clear();
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
