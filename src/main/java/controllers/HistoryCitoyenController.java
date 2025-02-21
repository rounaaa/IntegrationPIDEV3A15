package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Reclamation;
import models.ReclamationStatus;
import services.ReclamationService;

import java.io.IOException;
import java.util.List;

public class HistoryCitoyenController {

    @FXML
    private VBox reclamationContainer;
    @FXML
    private ScrollPane scrollPane;

    private final ReclamationService reclamationService = new ReclamationService();
    private final int citoyenId = 1;

    @FXML
    public void initialize() {
        loadReclamationHistory();
        double screenHeight = javafx.stage.Screen.getPrimary().getBounds().getHeight();
        scrollPane.setPrefHeight(screenHeight * 0.6);
    }

    public void loadReclamationHistory() {
        List<Reclamation> reclamations = reclamationService.getReclamationsByUserId(citoyenId);
        reclamationContainer.getChildren().clear();

        for (Reclamation rec : reclamations) {
            VBox card = createReclamationCard(rec);
            reclamationContainer.getChildren().add(card);
        }
    }

    private VBox createReclamationCard(Reclamation reclamation) {
        VBox card = new VBox();
        card.getStyleClass().add("reclamation-card");

        Label typeLabel = new Label("📌 Type: " + reclamation.getType());
        typeLabel.getStyleClass().add("reclamation-type");

        Label descriptionLabel = new Label("📝 Description: " + reclamation.getDescription());
        descriptionLabel.getStyleClass().add("reclamation-description");

        Label statusLabel = new Label("🚀 Statut: " + reclamation.getStatus());
        statusLabel.getStyleClass().add("reclamation-status");

        Button editButton = new Button("✏️ Modifier");
        editButton.getStyleClass().add("edit-button");
        editButton.setDisable(!(reclamation.getStatus() == ReclamationStatus.PENDING || reclamation.getStatus() == ReclamationStatus.IN_PROGRESS));
        editButton.setOnAction(event -> openEditWindow(reclamation));


        Button deleteButton = new Button("🗑️ Supprimer");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(event -> confirmDelete(reclamation));

        card.getChildren().addAll(typeLabel, descriptionLabel, statusLabel, editButton, deleteButton);
        return card;
    }

    private void openEditWindow(Reclamation reclamation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editReclamation.fxml"));
            Parent root = loader.load();

            EditReclamationController controller = loader.getController();
            controller.setReclamation(reclamation, this);

            Stage stage = new Stage();
            stage.setTitle("Modifier Réclamation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("❌ Error loading EditReclamation.fxml");
            e.printStackTrace();
        }
    }

    private void confirmDelete(Reclamation reclamation) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Supprimer Réclamation");
        alert.setContentText("Voulez-vous vraiment supprimer cette réclamation ?");

        ButtonType confirmButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == confirmButton) {
                reclamationService.supprimerReclamation(reclamation.getId());
                loadReclamationHistory();
                System.out.println("Réclamation supprimée !");
            }
        });
    }
}
