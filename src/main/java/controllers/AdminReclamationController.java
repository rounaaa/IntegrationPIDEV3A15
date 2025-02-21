package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Callback;
import models.Reclamation;
import models.ReclamationStatus;
import models.Response;
import services.ReclamationService;
import services.ResponseService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AdminReclamationController {
    @FXML
    private ComboBox<String> statusFilter;

    @FXML
    private ListView<Reclamation> listReclamations;
    @FXML
    private TextArea txtReply;
    @FXML
    private Button btnReply, btnRefresh, btnDelete;

    private final ReclamationService reclamationService = new ReclamationService();
    private final ResponseService responseService = new ResponseService();

    @FXML
    public void initialize() {
        statusFilter.setItems(FXCollections.observableArrayList("Tous", "PENDING", "REPLIED", "CLOSED"));
        statusFilter.setValue("Tous"); // Valeur par défaut

        loadReclamations();
        listReclamations.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Reclamation> call(ListView<Reclamation> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Reclamation reclamation, boolean empty) {
                        super.updateItem(reclamation, empty);
                        if (empty || reclamation == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            HBox row = new HBox(10);
                            row.setStyle("-fx-padding: 8px; -fx-border-color: #6fb862; -fx-border-width: 0 0 1 0; -fx-background-color: #aed699;");

                            Label user = new Label(reclamation.getUtilisateur().getNom());
                            user.setPrefWidth(150);

                            Label type = new Label(reclamation.getType().toString());
                            type.setPrefWidth(100);

                            Text description = new Text(reclamation.getDescription());
                            description.setWrappingWidth(250);

                            HBox descBox = new HBox(description);
                            HBox.setHgrow(descBox, Priority.ALWAYS);

                            Label status = new Label(reclamation.getStatus().toString());
                            status.setPrefWidth(100);

                            String responseText = reclamation.getResponses().isEmpty() ? "Non répondu" : reclamation.getResponses().get(0).getResponseText();
                            Label response = new Label(responseText);
                            response.setPrefWidth(200);

                            row.getChildren().addAll( user, type, descBox, status, response);
                            setGraphic(row);
                        }

                    }
                };
            }
        });


    }

    private void loadReclamations() {

        List<Reclamation> reclamations = reclamationService.getAllReclamations();


        ObservableList<Reclamation> observableList = FXCollections.observableArrayList();

        
        for (Reclamation reclamation : reclamations) {
            observableList.add(0, reclamation);  // Ajoute chaque réclamation au début
        }

        listReclamations.setItems(observableList);
    }



    @FXML
    void replyReclamation(ActionEvent event) {
        Reclamation selected = listReclamations.getSelectionModel().getSelectedItem();
        if (selected == null || txtReply.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Sélectionnez une réclamation et entrez une réponse.");
            return;
        }
        scheduleCloseReclamation(selected);
        Response newResponse = new Response(selected.getId(), txtReply.getText().trim());
        responseService.addResponse(newResponse);

        selected.setStatus(ReclamationStatus.REPLIED);
        reclamationService.updateReclamationStatus(selected.getId(), ReclamationStatus.REPLIED);

        scheduleCloseReclamation(selected);

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Réponse envoyée avec succès.");
        txtReply.clear();
        loadReclamations();
    }

    @FXML
    void deleteReply(ActionEvent event) {
        Reclamation selected = listReclamations.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Sélectionnez une réclamation.");
            return;
        }

        responseService.deleteResponseByReclamationId(selected.getId());

        selected.setStatus(ReclamationStatus.PENDING);
        reclamationService.updateReclamationStatus(selected.getId(), ReclamationStatus.PENDING);

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Réponse supprimée.");
        txtReply.clear();
        loadReclamations();

    }


    @FXML
    void refreshList(ActionEvent event) {
        String selectedStatus = statusFilter.getValue();
        List<Reclamation> allReclamations = reclamationService.getAllReclamations();

        List<Reclamation> filteredReclamations = allReclamations.stream()
                .filter(reclamation -> selectedStatus.equals("Tous") ||
                        reclamation.getStatus().toString().equals(selectedStatus))
                .toList();

        ObservableList<Reclamation> observableList = FXCollections.observableArrayList(filteredReclamations);
        listReclamations.setItems(observableList);
    }


    private void scheduleCloseReclamation(Reclamation reclamation) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    reclamation.setStatus(ReclamationStatus.CLOSED);
                    reclamationService.updateReclamationStatus(reclamation.getId(), ReclamationStatus.CLOSED);
                    loadReclamations();
                });
            }
        }, 7 * 1000);
    }




    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
