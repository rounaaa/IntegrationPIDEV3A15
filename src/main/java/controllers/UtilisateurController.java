package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Utilisateur;
import models.Citoyen;
import models.Admin;
import services.UtilisateurService;

public class UtilisateurController {
    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCin;

    @FXML
    private TextField txtMotDePasse;

    @FXML
    private Button btnAjouterUtilisateur;

    private final UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    void ajouterUtilisateur(ActionEvent event) {
        // Retrieve user input
        String nom = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();
        String email = txtEmail.getText().trim();
        String cin = txtCin.getText().trim();
        String motDePasse = txtMotDePasse.getText().trim();

        // Validate input fields
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || cin.isEmpty() || motDePasse.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        // Validate email format
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer une adresse email valide !");
            return;
        }

        // Validate CIN (Assuming CIN is numeric and has 8 digits)
        if (!cin.matches("\\d{8}")) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le CIN doit contenir exactement 8 chiffres !");
            return;
        }

        // Check if the email already exists
        if (utilisateurService.emailExists(email)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Cet email est déjà utilisé !");
            return;
        }
        String role = "citoyen";

        Utilisateur newUser = new Citoyen(0, nom, prenom, email, cin, motDePasse, role, 0);

        utilisateurService.ajouterUtilisateur(newUser);

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur citoyen ajouté avec succès !");

        clearFields();
    }

    private void clearFields() {
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
        txtCin.clear();
        txtMotDePasse.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
