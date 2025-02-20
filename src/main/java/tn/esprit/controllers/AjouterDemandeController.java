package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.models.DemandeDocument;
import tn.esprit.services.ServiceDemandeDocument;

import java.util.Date;

public class AjouterDemandeController {

    @FXML
    private ListView<String> categoryListView;

    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    private ListView<DemandeDocument> demandeListView;

    @FXML
    private TextField titreField;

    @FXML
    private ComboBox<String> statusField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ComboBox<String> categorieField;

    @FXML
    private TextField auteurField;

    @FXML
    private TextField pathField;

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
        sortComboBox.setItems(FXCollections.observableArrayList("Titre", "Statut", "Description", "Type", "Catégorie", "Auteur"));
        sortComboBox.setOnAction(event -> sortDemandes(sortComboBox.getValue()));

        // Set up category list
        categoryListView.setItems(FXCollections.observableArrayList("Category 1", "Category 2", "Category 3"));

        // Set up status field
        statusField.setItems(FXCollections.observableArrayList("En cours de traitement", "Archivé", "Disponible", "Bloqué"));

        // Set up categorie field
        categorieField.setItems(FXCollections.observableArrayList("type1", "type2", "type3", "type4"));

        handleAddDemande.setOnAction(event -> addDemande());
    }

    private void loadAllDemandes() {
        demandeList.setAll(serviceDemandeDocument.getAll());
        demandeListView.setItems(demandeList);
    }

    private void sortDemandes(String sortBy) {
        // Implement sorting logic based on the selected sort criteria
    }

    @FXML
    private void addDemande() {
        String titre = titreField.getText();
        String statut = statusField.getValue();
        String description = descriptionField.getText();
        String categorie = categorieField.getValue();
        String auteur = auteurField.getText();
        String chemin = pathField.getText();

        DemandeDocument demande = new DemandeDocument();
        demande.setTitreDemande(titre);
        demande.setStatus(statut);
        demande.setDescription(description);
        demande.setTypeDocument(categorie);
        demande.setNomDemandeur(auteur);
        demande.setPieceJustif(chemin);
        demande.setDateDemande(new Date());

        serviceDemandeDocument.add(demande);
        loadAllDemandes();
    }

    @FXML
    private void handleBrowseFile() {
        // Implement file browsing logic
    }

    @FXML
    private void handleUpdateDemande() {
        // Implement update demande logic
    }

    @FXML
    private void handleDeleteDemande() {
        DemandeDocument selectedDemande = demandeListView.getSelectionModel().getSelectedItem();
        if (selectedDemande != null) {
            serviceDemandeDocument.delete(selectedDemande);
            loadAllDemandes();
        }
    }
}