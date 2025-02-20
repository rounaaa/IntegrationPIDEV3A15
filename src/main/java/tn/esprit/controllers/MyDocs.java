package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;

public class MyDocs {

    @FXML
    private ListView<String> menuListView;

    @FXML
    private VBox ajouterDemandeForm;

    @FXML
    private VBox consulterDemandesSection;

    @FXML
    private ComboBox<String> typeDocumentComboBox;

    @FXML
    private TextField emailDemandeurField;

    @FXML
    private TextField nomDemandeurField;

    @FXML
    private ListView<String> demandesListView;

    private ObservableList<String> demandesList;

    @FXML
    public void initialize() {
        // Init menu list view
        menuListView.setItems(FXCollections.observableArrayList("Ajouter une Demande", "Consulter mes demandes"));
        menuListView.setOnMouseClicked(this::handleMenuClick);

        // Init type document combo box
        typeDocumentComboBox.setItems(FXCollections.observableArrayList("Type1", "Type2", "Type3"));

        // Init demandes list
        demandesList = FXCollections.observableArrayList();
        demandesListView.setItems(demandesList);

        // Show  Ajouter Demande form by default
        showAjouterDemandeForm();
    }

    private void handleMenuClick(MouseEvent event) {
        String selectedItem = menuListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            switch (selectedItem) {
                case "Ajouter une Demande":
                    showAjouterDemandeForm();
                    break;
                case "Consulter mes demandes":
                    showConsulterDemandesSection();
                    break;
            }
        }
    }

    private void showAjouterDemandeForm() {
        ajouterDemandeForm.setVisible(true);
        consulterDemandesSection.setVisible(false);
    }

    private void showConsulterDemandesSection() {
        ajouterDemandeForm.setVisible(false);
        consulterDemandesSection.setVisible(true);
    }

    @FXML
    private void handleAddDemande() {
        String typeDocument = typeDocumentComboBox.getValue();
        String emailDemandeur = emailDemandeurField.getText();
        String nomDemandeur = nomDemandeurField.getText();

        if (typeDocument != null && !emailDemandeur.isEmpty() && !nomDemandeur.isEmpty()) {
            String demande = "Type: " + typeDocument + ", Email: " + emailDemandeur + ", Nom: " + nomDemandeur;
            demandesList.add(demande);

            // Clear fields
            typeDocumentComboBox.setValue(null);
            emailDemandeurField.clear();
            nomDemandeurField.clear();
        }
    }
}