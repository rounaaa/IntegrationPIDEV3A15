package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Document;
import tn.esprit.services.ServiceDocument;

import java.io.File;
import java.util.Date;

public class UpdateDocumentController {

    @FXML private TextField titreField;
    @FXML private ComboBox<String> statusField;
    @FXML private TextField descriptionField;
    @FXML private TextField pathField;
    @FXML private TextField auteurField;

    private Document document;
    private ServiceDocument serviceDocument;
    private Stage stage;

    public void setDocument(Document document) {
        this.document = document;
        titreField.setText(document.getTitre());
        statusField.setValue(document.getStatus());
        descriptionField.setText(document.getDescription());
        pathField.setText(document.getPath());
        auteurField.setText(document.getAuteur());
    }

    public void setServiceDocument(ServiceDocument serviceDocument) {
        this.serviceDocument = serviceDocument;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleUpdateDocument() {
        document.setTitre(titreField.getText());
        document.setStatus(statusField.getValue());
        document.setDescription(descriptionField.getText());
        document.setPath(pathField.getText());
        document.setAuteur(auteurField.getText());
        document.setDateModification(new Date());

        serviceDocument.update(document);
        stage.close();
    }

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