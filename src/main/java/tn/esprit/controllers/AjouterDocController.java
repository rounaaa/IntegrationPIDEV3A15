package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Document;
import tn.esprit.services.ServiceDocument;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class AjouterDocController {

    // Champs de formulaire
    @FXML private TextField titreField;
    @FXML private ComboBox<String> statusField;
    @FXML private TextField descriptionField;
    @FXML private TextField pathField;
    @FXML private ComboBox<String> categorieField;
    @FXML private TextField auteurField;

    // ListView for displaying categories and documents
    @FXML private ListView<String> categoryListView;
    @FXML private ListView<Document> documentListView;

    // ComboBox for sorting
    @FXML private ComboBox<String> sortComboBox;

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
        documentListView.setCellFactory(param -> new DocumentListCell());
        addContextMenuToDocumentListView();
        initializeSortComboBox();
        initializeStatusComboBox();
        initializeCategorieComboBox();
    }

    // Load categories into the categoryListView
    private void loadCategories() {
        List<String> categories = serviceDocument.getAllCategories();
        ObservableList<String> sortedCategories = FXCollections.observableArrayList(categories);
        sortedCategories.sort(Comparator.naturalOrder());
        categoryListView.setItems(sortedCategories);
    }

    // Load documents by selected category
    private void loadDocumentsByCategory(String category) {
        List<Document> documents = serviceDocument.getDocumentsByCategory(category);
        documentListView.getItems().setAll(documents);
    }

    // Initialize the sortComboBox with sorting options
    private void initializeSortComboBox() {
        sortComboBox.setItems(FXCollections.observableArrayList("Titre", "Statut", "Description", "Type", "Catégorie", "Auteur"));
        sortComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                sortDocuments(newValue);
            }
        });
    }

    // Initialize the statusComboBox with status options
    private void initializeStatusComboBox() {
        statusField.setItems(FXCollections.observableArrayList("En cours de traitement", "Archivé", "Disponible", "Bloqué"));
    }

    // Initialize the categorieComboBox with category options
    private void initializeCategorieComboBox() {
        categorieField.setItems(FXCollections.observableArrayList("Categorie1", "Categorie2", "Categorie3", "Categorie4"));
    }

    // Sort documents based on the selected attribute
    private void sortDocuments(String attribute) {
        Comparator<Document> comparator;
        switch (attribute) {
            case "Titre":
                comparator = Comparator.comparing(Document::getTitre);
                break;
            case "Statut":
                comparator = Comparator.comparing(Document::getStatus);
                break;
            case "Description":
                comparator = Comparator.comparing(Document::getDescription);
                break;
            case "Type":
                comparator = Comparator.comparing(Document::getType);
                break;
            case "Catégorie":
                comparator = Comparator.comparing(Document::getCategorie);
                break;
            case "Auteur":
                comparator = Comparator.comparing(Document::getAuteur);
                break;
            default:
                return;
        }
        documentListView.getItems().sort(comparator);
    }

    // Ajouter un document
    @FXML
    private void handleAddDocument() {
        if (validateFields()) {
            Document document = new Document(
                    titreField.getText(),
                    statusField.getValue(),
                    descriptionField.getText(),
                    getFileExtension(pathField.getText()), // Auto-generate type from file extension
                    new Date(), // Date de création
                    new Date(), // Date de modification
                    pathField.getText(),
                    categorieField.getValue(),
                    auteurField.getText()
            );
            serviceDocument.add(document);
            showAlert("Succès", "Document ajouté avec succès !");
            clearFields();
            handleRefreshList();
            loadCategories(); // Refresh categories after adding a document
        }
    }

    @FXML
    private void handleUpdateDocument() {
        Document selectedDocument = documentListView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateDocument.fxml"));
                Parent root = loader.load();

                UpdateDocumentController controller = loader.getController();
                controller.setDocument(selectedDocument);
                controller.setServiceDocument(serviceDocument);

                Stage stage = new Stage();
                stage.setTitle("Modifier Document");
                stage.setScene(new Scene(root));
                controller.setStage(stage);
                stage.showAndWait();

                handleRefreshList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Supprimer un document
    @FXML
    private void handleDeleteDocument() {
        Document selectedDocument = documentListView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            serviceDocument.delete(selectedDocument);
            showAlert("Succès", "Document supprimé avec succès !");
            clearFields();
            handleRefreshList();
            loadCategories(); // Refresh categories after deleting a document
        }
    }

    // Rafraîchir la liste des documents
    @FXML
    private void handleRefreshList() {
        documentListView.getItems().clear();
        for (Document document : serviceDocument.getAll()) {
            documentListView.getItems().add(document);
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
            String fileType = getFileExtension(file.getName());
            // Handle the file type internally
            System.out.println("File type: " + fileType);
        }
    }
    // Add context menu to documentListView for sorting
    private void addContextMenuToDocumentListView() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem sortByName = new MenuItem("Sort by Name");
        sortByName.setOnAction(event -> sortDocuments("Titre"));

        MenuItem sortByDate = new MenuItem("Sort by Date");
        sortByDate.setOnAction(event -> sortDocuments("Date"));

        MenuItem sortByStatus = new MenuItem("Sort by Status");
        sortByStatus.setOnAction(event -> sortDocuments("Statut"));

        contextMenu.getItems().addAll(sortByName, sortByDate, sortByStatus);

        documentListView.setContextMenu(contextMenu);
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
        statusField.getSelectionModel().clearSelection();
        descriptionField.clear();
        pathField.clear();
        categorieField.getSelectionModel().clearSelection();
        auteurField.clear();
    }

    // Helper method to validate input fields
    private boolean validateFields() {
        Pattern pattern = Pattern.compile("^[A-Za-z].*");
        if (!pattern.matcher(titreField.getText()).matches()) {
            showAlert("Erreur", "Le titre doit commencer par une lettre.");
            return false;
        }
        if (auteurField.getText().isEmpty()) {
            showAlert("Erreur", "Le champ Auteur ne doit pas être vide.");
            return false;
        }
        return true;
    }

    // Helper method to get file extension
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}