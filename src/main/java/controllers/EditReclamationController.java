package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Reclamation;
import models.ReclamationType;
import services.ReclamationService;

public class EditReclamationController {

    @FXML
    private ComboBox<String> cmbEditType;
    @FXML
    private TextArea txtEditDescription;
    @FXML
    private Button btnSaveEdit, btnCancelEdit;

    private final ReclamationService reclamationService = new ReclamationService();
    private Reclamation selectedReclamation;
    private HistoryCitoyenController historyController;

    @FXML
    public void initialize() {
        cmbEditType.getItems().addAll("TECHNIQUE", "ADMINISTRATIVE", "FINANCIERE", "AUTRE");
    }

    public void setReclamation(Reclamation reclamation, HistoryCitoyenController historyController) {
        this.selectedReclamation = reclamation;
        this.historyController = historyController;
        cmbEditType.setValue(reclamation.getType().name());
        txtEditDescription.setText(reclamation.getDescription());
    }

    @FXML
    private void saveEditedReclamation() {
        if (selectedReclamation != null) {
            selectedReclamation.setType(ReclamationType.valueOf(cmbEditType.getValue()));
            selectedReclamation.setDescription(txtEditDescription.getText().trim());
            reclamationService.updateReclamation(selectedReclamation);

            historyController.loadReclamationHistory();
            closeWindow();
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) btnCancelEdit.getScene().getWindow();
        stage.close();
    }
}
