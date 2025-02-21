package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Conteneur;
import services.ServiceConteneur;


import java.io.IOException;
import java.util.List;

public class ListViewConteneurController {
    @FXML
    private ListView<Conteneur> listViewConteneurs;
    @FXML
    private Button btnSupprimer;

    private ServiceConteneur serviceConteneur = new ServiceConteneur();
    private ObservableList<Conteneur> conteneursList;

    @FXML
    public void initialize() {
        List<Conteneur> conteneurs = serviceConteneur.getAll();
        conteneursList = FXCollections.observableArrayList(conteneurs);

        // Utiliser une cellule personnalisée pour ajouter le bouton Modifier
        listViewConteneurs.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Conteneur conteneur, boolean empty) {
                super.updateItem(conteneur, empty);
                if (empty || conteneur == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Texte affiché dans la ListView
                    String formattedText = "ID: " + conteneur.getId_conteneurs() +
                            " | Localisation: " + conteneur.getLocalisation() +
                            " | État: " + conteneur.getE()+
                            " | Capacité: " + conteneur.getCapacite()+
                             " | Capteur: " + conteneur.isAvec_capteur()+
                             " | Niveau_Remplissage: " + conteneur.getNiv_remplissage();

                    Label label = new Label(formattedText);

                    // Création du bouton Modifier
                    Button btnModifier = new Button("Modifier");
                    btnModifier.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
                    btnModifier.setOnAction(event -> goToModifierConteneur(conteneur));

                    // Ajout du label et du bouton dans un HBox
                    HBox hbox = new HBox(10, label, btnModifier);
                    setGraphic(hbox);
                }
            }
        });

        listViewConteneurs.setItems(conteneursList);
    }
    @FXML
    public void supprimerConteneur() {
        Conteneur selected = listViewConteneurs.getSelectionModel().getSelectedItem();
        if (selected != null) {
            serviceConteneur.delete(selected.getLocalisation());
            conteneursList.remove(selected);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Suppression");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un conteneur à supprimer !");
            alert.showAndWait();
        }
    }

    // Méthode pour rediriger vers l'interface de modification
    private void goToModifierConteneur(Conteneur conteneur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateConteneur.fxml"));
            Parent root = loader.load();

            // Passer les données du conteneur au contrôleur de modification
            ModifierConteneurController controller = loader.getController();
            controller.setConteneurActuel(conteneur);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Conteneur");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@FXML
    public void supprimerConteneur(ActionEvent actionEvent) {
    }*/


}
