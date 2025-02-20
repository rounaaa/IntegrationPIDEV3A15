package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import tn.esprit.models.DemandeDocument;
import tn.esprit.services.ServiceDemandeDocument;

import java.io.File;
import java.util.Comparator;
import java.util.Date;

public class AjouterDemandeController {

    @FXML
    private ListView<String> categoryListView;

    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    private ListView<DemandeDocument> demandeListView;

    @FXML
    private TextField titreDemandeField;

    @FXML
    private ComboBox<String> statusField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ComboBox<String> typeDocumentField;

    @FXML
    private TextField nomDemandeurField;

    @FXML
    private TextField pieceJustifField;

    @FXML
    private Button handleAddDemande;

    private ServiceDemandeDocument serviceDemandeDocument;
    private ObservableList<DemandeDocument> demandeList;

    @FXML
    private void initialize() {
        serviceDemandeDocument = new ServiceDemandeDocument();
        demandeList = FXCollections.observableArrayList();

        // Load all demandes initially
        loadAllDemandes();

        // Set up sort functionality
        sortComboBox.setItems(FXCollections.observableArrayList("Titre", "Statut", "Description", "Type", "Nom Demandeur", "Pièce Justificative"));
        sortComboBox.setOnAction(event -> sortDemandes(sortComboBox.getValue()));

        // Set up category list
        categoryListView.setItems(FXCollections.observableArrayList("Category 1", "Category 2", "Category 3"));

        // Set up status field
        statusField.setItems(FXCollections.observableArrayList("En cours de traitement", "Archivé", "Disponible", "Bloqué"));

        // Set up type document field
        typeDocumentField.setItems(FXCollections.observableArrayList("PDF", "Word", "Excel", "Autre"));

        handleAddDemande.setOnAction(event -> addDemande());
    }

    private void loadAllDemandes() {
        demandeList.setAll(serviceDemandeDocument.getAll());
        demandeListView.setItems(demandeList);
    }

    private void sortDemandes(String sortBy) {
        switch (sortBy) {
            case "Titre":
                demandeList.sort(Comparator.comparing(DemandeDocument::getTitreDemande));
                break;
            case "Statut":
                demandeList.sort(Comparator.comparing(DemandeDocument::getStatus));
                break;
            case "Description":
                demandeList.sort(Comparator.comparing(DemandeDocument::getDescription));
                break;
            case "Type":
                demandeList.sort(Comparator.comparing(DemandeDocument::getTypeDocument));
                break;
            case "Nom Demandeur":
                demandeList.sort(Comparator.comparing(DemandeDocument::getNomDemandeur));
                break;
            case "Pièce Justificative":
                demandeList.sort(Comparator.comparing(DemandeDocument::getPieceJustif));
                break;
        }
    }

    @FXML
    private void addDemande() {
        String titreDemande = titreDemandeField.getText();
        String statut = statusField.getValue();
        String description = descriptionField.getText();
        String typeDocument = typeDocumentField.getValue();
        String nomDemandeur = nomDemandeurField.getText();
        String pieceJustif = pieceJustifField.getText();

        // Input validation
        if (titreDemande.isEmpty() || statut == null || description.isEmpty() || typeDocument == null || nomDemandeur.isEmpty() || pieceJustif.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        DemandeDocument demande = new DemandeDocument();
        demande.setTitreDemande(titreDemande);
        demande.setStatus(statut);
        demande.setDescription(description);
        demande.setTypeDocument(typeDocument);
        demande.setNomDemandeur(nomDemandeur);
        demande.setPieceJustif(pieceJustif);
        demande.setDate_Demande(new Date());

        serviceDemandeDocument.add(demande);
        loadAllDemandes();
        clearForm();
    }

    @FXML
    private void handleBrowseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une pièce justificative");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            pieceJustifField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleUpdateDemande() {
        DemandeDocument selectedDemande = demandeListView.getSelectionModel().getSelectedItem();
        if (selectedDemande != null) {
            String titreDemande = titreDemandeField.getText();
            String statut = statusField.getValue();
            String description = descriptionField.getText();
            String typeDocument = typeDocumentField.getValue();
            String nomDemandeur = nomDemandeurField.getText();
            String pieceJustif = pieceJustifField.getText();

            // Input validation
            if (titreDemande.isEmpty() || statut == null || description.isEmpty() || typeDocument == null || nomDemandeur.isEmpty() || pieceJustif.isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
                return;
            }

            selectedDemande.setTitreDemande(titreDemande);
            selectedDemande.setStatus(statut);
            selectedDemande.setDescription(description);
            selectedDemande.setTypeDocument(typeDocument);
            selectedDemande.setNomDemandeur(nomDemandeur);
            selectedDemande.setPieceJustif(pieceJustif);

            serviceDemandeDocument.update(selectedDemande);
            loadAllDemandes();
            clearForm();
        } else {
            showAlert("Erreur", "Veuillez sélectionner une demande à modifier.");
        }
    }

    @FXML
    private void handleDeleteDemande() {
        DemandeDocument selectedDemande = demandeListView.getSelectionModel().getSelectedItem();
        if (selectedDemande != null) {
            serviceDemandeDocument.delete(selectedDemande);
            loadAllDemandes();
        } else {
            showAlert("Erreur", "Veuillez sélectionner une demande à supprimer.");
        }
    }

    private void clearForm() {
        titreDemandeField.clear();
        statusField.setValue(null);
        descriptionField.clear();
        typeDocumentField.setValue(null);
        nomDemandeurField.clear();
        pieceJustifField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}