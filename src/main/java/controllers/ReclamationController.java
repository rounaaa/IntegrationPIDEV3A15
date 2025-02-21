package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.Reclamation;
import models.ReclamationType;
import models.Utilisateur;
import services.ReclamationService;
import services.UtilisateurService;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class ReclamationController {
    @FXML
    private TextField txtCite;


    @FXML
    private Label lblCite;


    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private Label lblDate, lblTime;

    @FXML
    private ImageView imagePreview;

    @FXML
    private TextArea txtDescription;



    @FXML
    private Button btnChooseImage, btnAjouter;

    private final ReclamationService reclamationService = new ReclamationService();
    private final UtilisateurService utilisateurService = new UtilisateurService();
    private Utilisateur loggedInUser;
    private File selectedFile;

    @FXML

    public void initialize() {

        cmbType.setItems(FXCollections.observableArrayList("TECHNIQUE", "ADMINISTRATIVE", "FINANCIERE", "AUTRE"));
        loggedInUser = utilisateurService.getUtilisateurById(1);
        updateDateTime();


        txtCite.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s]*") || newValue.length() > 6) {
                txtCite.setText(oldValue);


            }
        });
    }



    private void updateDateTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    LocalDateTime now = LocalDateTime.now();
                    lblDate.setText(now.format(dateFormatter));
                    lblTime.setText(now.format(timeFormatter));
                });
            }
        }, 0, 1000);
    }

    @FXML
    void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionnez une image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePreview.setImage(new Image(selectedFile.toURI().toString()));
        }
    }


    @FXML
    public void validateDescription() {
        String text = txtDescription.getText(); // Retirez le .trim() ici

        String[] bannedWords = {"badword", "test", "bip"};
        for (String bannedWord : bannedWords) {
            text = text.replace(bannedWord, "**********");
        }

        txtDescription.setText(text);
        txtDescription.positionCaret(text.length());
    }

    @FXML
    void ajouterReclamation(ActionEvent event) {
        String typeStr = cmbType.getValue();
        String description = txtDescription.getText().trim();
        String imagePath = selectedFile != null ? selectedFile.getAbsolutePath() : "";


        if (typeStr == null || description.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs !");
            return;}


        if (description.split("\\s+").length > 4) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Votre description doit être de 20 mots maximum !");
            return;

        }
        if(description.split("\\s+").length < 1) {
            showAlert(Alert.AlertType.ERROR,"Erreur", "Veuillez remplir tous les champs Votre description doit être de 2 mots  minimum !");
        return;
        }

        ReclamationType type = ReclamationType.valueOf(typeStr);
        Reclamation reclamation = new Reclamation(type, description, imagePath, loggedInUser);
        reclamationService.ajouterReclamation(reclamation);

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation ajoutée avec succès !");
        clearFields();
        clear();
    }

    private void clearFields() {
        cmbType.getSelectionModel().clearSelection();
        txtDescription.clear();
        imagePreview.setImage(null);
        selectedFile = null;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void clear() {
        txtCite.clear();
    }

}
