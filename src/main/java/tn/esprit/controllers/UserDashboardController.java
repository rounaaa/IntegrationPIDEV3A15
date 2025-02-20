package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.models.DemandeDocument;
import tn.esprit.services.ServiceDemandeDocument;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UserDashboardController {

    @FXML
    private Button ajouterDemandeButton;

    @FXML
    private ListView<DemandeDocument> documentListView;

    @FXML
    private TextField searchField;

    private ServiceDemandeDocument serviceDemandeDocument;
    private ObservableList<DemandeDocument> documentList;

    @FXML
    private void initialize() {
        serviceDemandeDocument = new ServiceDemandeDocument();
        documentList = FXCollections.observableArrayList();

        // Load all documents initially
        loadAllDocuments();

        // Set up search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterDocuments(newValue));

        // Set custom cell factory
        documentListView.setCellFactory(param -> new DemandeDocumentListCell());

        ajouterDemandeButton.setOnAction(event -> handleAjouterDemande());
    }

    private void loadAllDocuments() {
        List<DemandeDocument> documents = serviceDemandeDocument.getAll();
        documentList.setAll(documents);
        documentListView.setItems(documentList);
    }

    private void filterDocuments(String query) {
        List<DemandeDocument> documents = serviceDemandeDocument.getAll();
        List<DemandeDocument> filteredDocuments = documents.stream()
                .filter(document -> document.getTitreDemande().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        documentList.setAll(filteredDocuments);
    }

    private void handleAjouterDemande() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterDemande.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ajouterDemandeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println("Error loading AjouterDemande.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Custom ListCell
    private static class DemandeDocumentListCell extends ListCell<DemandeDocument> {
        @Override
        protected void updateItem(DemandeDocument demande, boolean empty) {
            super.updateItem(demande, empty);
            if (empty || demande == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(demande.getTitreDemande() + " - " + demande.getStatus());
            }
        }
    }
}