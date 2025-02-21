package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Conteneur;
import services.ServiceConteneur;

public class ModifierConteneurController {

    private Conteneur conteneurActuel;
    private ServiceConteneur serviceConteneur = new ServiceConteneur();
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
    private Button btnUpdate;

    public void setConteneurActuel(Conteneur conteneur) {
        this.conteneurActuel = conteneur;
        // Initialiser les champs
        tfLocalisation.setText(conteneur.getLocalisation());
        tfCapacite.setText(String.valueOf(conteneur.getCapacite()));
        cbEtat.setValue(conteneur.getE());
        chkAvecCapteur.setSelected(conteneur.isAvec_capteur());
        tfNivRemplissage.setText(String.valueOf(conteneur.getNiv_remplissage()));
    }

    @FXML
    public void mettreAJourConteneur(ActionEvent actionEvent){
        // Récupération des nouvelles valeurs
        String nouvelleLocalisation = tfLocalisation.getText();
        float nouvelleCapacite = Float.parseFloat(tfCapacite.getText());
        Conteneur.Etat nouvelEtat = (Conteneur.Etat) cbEtat.getValue();
        boolean nouveauCapteur = chkAvecCapteur.isSelected();
        float nouveauNivRemplissage = Float.parseFloat(tfNivRemplissage.getText());

        // Mise à jour de l'objet
        conteneurActuel.setLocalisation(nouvelleLocalisation);
        conteneurActuel.setCapacite(nouvelleCapacite);
        conteneurActuel.setE(nouvelEtat);
        conteneurActuel.setAvec_capteur(nouveauCapteur);
        conteneurActuel.setNiv_remplissage(nouveauNivRemplissage);

        // Mise à jour dans la base de données
        serviceConteneur.update(conteneurActuel);

        // Fermer la fenêtre après modification
        Stage stage = (Stage) btnUpdate.getScene().getWindow();
        stage.close();
    }


}
