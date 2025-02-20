package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Document;
import tn.esprit.services.ServiceDocument;

import java.io.File;

public class UpdateDocumentController {

    @FXML private TextField titreField;
    @FXML private ComboBox<String> statusField;
    @FXML private TextField descriptionField;
    @FXML private TextField pathField;
    @FXML private TextField auteurField;

    private Document document;
    private ServiceDocument serviceDocument;
    private Stage stage;

    // Set the document to be updated
    public void setDocument(Document document) {
        this.document = document;
        populateForm();
    }

    public void setServiceDocument(ServiceDocument serviceDocument) {
        this.serviceDocument = serviceDocument;
    }

    // Set stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Populate the form with the selected document data
    private void populateForm() {
        if (document != null) {
            titreField.setText(document.getTitre());
            statusField.setValue(document.getStatus());
            descriptionField.setText(document.getDescription());
            pathField.setText(document.getPath());
            auteurField.setText(document.getAuteur());
        }
    }

    // Handle updating the document
    @FXML
    private void handleUpdateDocument() {
        if (document != null) {
            // NEW VALUES
            document.setTitre(titreField.getText());
            document.setStatus(statusField.getValue());
            document.setDescription(descriptionField.getText());
            document.setPath(pathField.getText());
            document.setAuteur(auteurField.getText());

            // UPDATE DOC IN DATABASE
            serviceDocument.update(document);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Document mis à jour avec succès !");
            alert.showAndWait();

            // Close the update form
            if (stage != null) {
                stage.close();
            }
        }
    }

    // Handle browsing for a file
    @FXML
    private void handleBrowseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            pathField.setText(file.getAbsolutePath());
        }
    }
}