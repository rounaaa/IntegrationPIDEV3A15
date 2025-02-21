package controllers;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import models.Conteneur;
import services.ServiceConteneur;

public class GestionConteneur {
    @FXML
    private CheckBox chkAvecCapteur;
    @FXML
    private TextField tfNivRemplissage;
    @FXML
    private TextField tfCapacite;
    @FXML
    private ComboBox cbEtat;
    @FXML
    private TextField tfLocalisation;
    @FXML
    private Button btnAjouter;
    @FXML
    private Label lbConteneurs;

    private ServiceConteneur sc = new ServiceConteneur();

        @FXML
        public void initialize() {
            // Initialisation de la ComboBox avec les valeurs "Vide" et "Pleine"
            cbEtat.setItems(FXCollections.observableArrayList("Vide", "Pleine"));
        }

    @FXML
    public void ajouterConteneur(ActionEvent actionEvent) {
        try {
            String localisation = tfLocalisation.getText();
            float capacite = Float.parseFloat(tfCapacite.getText());
            Conteneur.Etat etat = Conteneur.Etat.valueOf(cbEtat.getValue().toString());
            boolean avec_capteur = chkAvecCapteur.isSelected();
            float niv_remplissage = Float.parseFloat(tfNivRemplissage.getText());

            Conteneur c = new Conteneur(localisation, etat, capacite, avec_capteur, niv_remplissage);

            sc.add(c);
            lbConteneurs.setText("Conteneur ajouté avec succès !");
        } catch (Exception e) {
            lbConteneurs.setText("Erreur : " + e.getMessage());
        }
    }
}

