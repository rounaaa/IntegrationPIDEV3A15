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

    @FXML private TextField titreField;
    @FXML private ComboBox<String> statusField;
    @FXML private TextField descriptionField;
    @FXML private TextField pathField;
    @FXML private ComboBox<String> categorieField;
    @FXML private TextField auteurField;

    @FXML private ListView<String> categoryListView;
    @FXML private ListView<Document> documentListView;

    @FXML private ComboBox<String> sortComboBox;

    //Instance de connexion
    private final ServiceDocument serviceDocument = new ServiceDocument();

    // Init
    @FXML
    public void initialize() {
        // Load categories
        loadCategories();

        // listener to categoryListView
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

        // Load documents
        handleRefreshList();
    }

    // Load categories
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

    // Init sortComboBox
    private void initializeSortComboBox() {
        sortComboBox.setItems(FXCollections.observableArrayList("Titre", "Statut", "Description", "Type", "Catégorie", "Auteur"));
        sortComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                sortDocuments(newValue);
            }
        });
    }

    // Init statusComboBox
    private void initializeStatusComboBox() {
        statusField.setItems(FXCollections.observableArrayList("En cours de traitement", "Archivé", "Disponible", "Bloqué"));
    }

    // Init categorieComboBox
    private void initializeCategorieComboBox() {
        categorieField.setItems(FXCollections.observableArrayList("Categorie1", "Categorie2", "Categorie3", "Categorie4"));
    }

    // Sort documents
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

    // Ajout
    @FXML
    private void handleAddDocument() {
        if (validateFields()) {
            Document document = new Document(
                    titreField.getText(),
                    statusField.getValue(),
                    descriptionField.getText(),
                    getFileExtension(pathField.getText()),
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
            loadCategories();
        }
    }

    // MAJ
    @FXML
    private void handleUpdateDocument() {
        Document selectedDocument = documentListView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            try {
                // call UpdateDocument.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateDocument.fxml"));
                Parent root = loader.load();

                UpdateDocumentController controller = loader.getController();
                controller.setDocument(selectedDocument);
                controller.setServiceDocument(serviceDocument);

                // New Stage MODIFCATION
                Stage stage = new Stage();
                stage.setTitle("Modifier Document");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Refresh the document list
                handleRefreshList();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Impossible de charger le formulaire de modification.");
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un document à modifier.");
        }
    }


    // Supprimer
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


    // Refresh list documents
    @FXML
    private void handleRefreshList() {
        documentListView.getItems().clear();
        for (Document document : serviceDocument.getAll()) {
            documentListView.getItems().add(document);
        }
    }

    // Browse
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

    //SORT context menu for documentListView
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

    // clear fields
    private void clearFields() {
        titreField.clear();
        statusField.getSelectionModel().clearSelection();
        descriptionField.clear();
        pathField.clear();
        categorieField.getSelectionModel().clearSelection();
        auteurField.clear();
    }

    // Validation input fields
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

    // GET extension
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}