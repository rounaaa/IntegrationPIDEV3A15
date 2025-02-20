package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Document;
import tn.esprit.services.ServiceDocument;

import java.io.File;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MyDocsTableView {
/*
    // Champs de formulaire
    @FXML private TextField titreField;
    @FXML private TextField statusField;
    @FXML private TextField descriptionField;
    @FXML private TextField typeField;
    @FXML private TextField pathField;
    @FXML private TextField categorieField;
    @FXML private TextField auteurField;

    // TableView for displaying categories and documents
    @FXML private ListView<String> categoryListView;
    @FXML private TableView<Document> documentTableView;
    @FXML private TableColumn<Document, String> titreColumn;
    @FXML private TableColumn<Document, String> statusColumn;
    @FXML private TableColumn<Document, String> descriptionColumn;
    @FXML private TableColumn<Document, String> typeColumn;
    @FXML private TableColumn<Document, Date> dateCreationColumn;
    @FXML private TableColumn<Document, Date> dateModificationColumn;
    @FXML private TableColumn<Document, String> pathColumn;
    @FXML private TableColumn<Document, String> categorieColumn;
    @FXML private TableColumn<Document, String> auteurColumn;

    // Service pour interagir avec la base de données
    ServiceDocument serviceDocument = new ServiceDocument();

    // Méthode d'initialisation
    @FXML
    public void initialize() {
        loadCategories();
        categoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadDocumentsByCategory(newValue);
            }
        });
        setupDocumentTableView();
    }

    // Load categories into the categoryListView
    private void loadCategories() {
        List<String> categories = serviceDocument.getAllCategories();
        categoryListView.getItems().setAll(categories);
    }

    // Load documents by selected category
    private void loadDocumentsByCategory(String category) {
        List<Document> documents = serviceDocument.getDocumentsByCategory(category);
        documentTableView.getItems().setAll(documents);
    }

    // Setup the documentTableView with columns and sorting
    private void setupDocumentTableView() {
        titreColumn.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        dateCreationColumn.setCellValueFactory(cellData -> cellData.getValue().dateCreationProperty());
        dateModificationColumn.setCellValueFactory(cellData -> cellData.getValue().dateModificationProperty());
        pathColumn.setCellValueFactory(cellData -> cellData.getValue().pathProperty());
        categorieColumn.setCellValueFactory(cellData -> cellData.getValue().categorieProperty());
        auteurColumn.setCellValueFactory(cellData -> cellData.getValue().auteurProperty());

        // Enable sorting for each column
        titreColumn.setSortType(TableColumn.SortType.ASCENDING);
        statusColumn.setSortType(TableColumn.SortType.ASCENDING);
        descriptionColumn.setSortType(TableColumn.SortType.ASCENDING);
        typeColumn.setSortType(TableColumn.SortType.ASCENDING);
        dateCreationColumn.setSortType(TableColumn.SortType.ASCENDING);
        dateModificationColumn.setSortType(TableColumn.SortType.ASCENDING);
        pathColumn.setSortType(TableColumn.SortType.ASCENDING);
        categorieColumn.setSortType(TableColumn.SortType.ASCENDING);
        auteurColumn.setSortType(TableColumn.SortType.ASCENDING);

        documentTableView.getSortOrder().add(titreColumn);
    }

    // Ajouter un document
    @FXML
    private void handleAddDocument() {
        Document document = new Document(
                titreField.getText(),
                statusField.getText(),
                descriptionField.getText(),
                typeField.getText(),
                new Date(), // Date de création
                new Date(), // Date de modification
                pathField.getText(),
                categorieField.getText(),
                auteurField.getText()
        );
        serviceDocument.add(document);
        showAlert("Succès", "Document ajouté avec succès !");
        clearFields();
        handleRefreshList();
    }

    // Mettre à jour un document
    @FXML
    private void handleUpdateDocument() {
        Document selectedDocument = documentTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            selectedDocument.setTitre(titreField.getText());
            selectedDocument.setStatus(statusField.getText());
            selectedDocument.setDescription(descriptionField.getText());
            selectedDocument.setType(typeField.getText());
            selectedDocument.setDateModification(new Date()); // Mettre à jour la date de modification
            selectedDocument.setPath(pathField.getText());
            selectedDocument.setCategorie(categorieField.getText());
            selectedDocument.setAuteur(auteurField.getText());

            serviceDocument.update(selectedDocument);
            showAlert("Succès", "Document mis à jour avec succès !");
            clearFields();
            handleRefreshList();
        }
    }

    // Supprimer un document
    @FXML
    private void handleDeleteDocument() {
        Document selectedDocument = documentTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            serviceDocument.delete(selectedDocument);
            showAlert("Succès", "Document supprimé avec succès !");
            clearFields();
            handleRefreshList();
        }
    }

    // Rafraîchir la liste des documents
    @FXML
    private void handleRefreshList() {
        documentTableView.getItems().clear();
        for (Document document : serviceDocument.getAll()) {
            documentTableView.getItems().add(document);
        }
    }

    // Parcourir un fichier
    @FXML
    private void handleBrowseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            pathField.setText(file.getAbsolutePath());
        }
    }

    // Helper method to show an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    // Helper method to clear all input fields
    private void clearFields() {
        titreField.clear();
        statusField.clear();
        descriptionField.clear();
        typeField.clear();
        pathField.clear();
        categorieField.clear();
        auteurField.clear();
    }*/
}