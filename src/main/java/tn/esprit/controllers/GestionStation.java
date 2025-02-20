package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.models.Station;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceStation;
import tn.esprit.models.Station.Status;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GestionStation {
    public Label stationLabel;
    @FXML
    private ListView<String> liststation;

    @FXML
    private TextField tfNomStation, tfEmplacement, tfCapaciteMax, tfHeuresOuverture, tfContact, tfLatitude, tfLongitude;

    @FXML
    private ComboBox<Status> cbStatus;

    @FXML
    private ComboBox<Utilisateur> cbUserId;

    private ServiceStation serviceStation = new ServiceStation();
    private Station stationSelectionnee = null;

    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("🔍 Initialisation du contrôleur GestionStation...");


        List<Utilisateur> utilisateurs = serviceStation.getAllUsers();
        cbUserId.getItems().addAll(utilisateurs);

        cbUserId.setConverter(new javafx.util.StringConverter<Utilisateur>() {
            @Override
            public String toString(Utilisateur user) {
                return (user != null) ? user.getNom() + " - " + user.getId_user() : "";
            }

            @Override
            public Utilisateur fromString(String string) {
                return cbUserId.getItems().stream()
                        .filter(user -> (user.getNom() + " - " + user.getId_user()).equals(string))
                        .findFirst().orElse(null);
            }

        });


        cbStatus.getItems().addAll(Status.values());
        cbStatus.setValue(Status.CLOSED);
        afficherStations(null);

    }



    @FXML
    public void ajouterStation(ActionEvent actionEvent) {
        try {
            if (tfNomStation.getText().isEmpty() || tfEmplacement.getText().isEmpty() || tfCapaciteMax.getText().isEmpty()) {
                liststation.getItems().clear();
                liststation.getItems().add("❌ Erreur : Veuillez remplir tous les champs !");
                return;
            }

            String nomStation = tfNomStation.getText();
            String emplacement = tfEmplacement.getText();
            Status status = cbStatus.getValue();
            int capaciteMax = Integer.parseInt(tfCapaciteMax.getText());
            String heuresOuverture = tfHeuresOuverture.getText();
            String contact = tfContact.getText();
            double latitude = Double.parseDouble(tfLatitude.getText());
            double longitude = Double.parseDouble(tfLongitude.getText());

            Utilisateur user = cbUserId.getValue();
            if (user == null) {
                liststation.getItems().clear();
                liststation.getItems().add("❌ Erreur : Veuillez sélectionner un utilisateur !");
                return;
            }

            Station station = new Station(nomStation, emplacement, status, user, capaciteMax, heuresOuverture, contact, latitude, longitude);
            serviceStation.add(station);

            liststation.getItems().clear();
            liststation.getItems().add("✅ Station ajoutée avec succès !");
            afficherStations(null);
        } catch (NumberFormatException e) {
            liststation.getItems().clear();
            liststation.getItems().add("❌ Erreur : Vérifiez les champs numériques !");
        } catch (Exception e) {
            liststation.getItems().clear();
            liststation.getItems().add("❌ Erreur : " + e.getMessage());
        }
        resetFields();
    }
    private void resetFields() {
        tfNomStation.clear();
        tfEmplacement.clear();
        cbStatus.setValue(null);
        cbUserId.setValue(null);
        tfCapaciteMax.clear();
        tfHeuresOuverture.clear();
        tfContact.clear();
        tfLatitude.clear();
        tfLongitude.clear();
    }

    @FXML
    public void miseajourStation(ActionEvent actionEvent) {
        if (stationSelectionnee == null) {
            liststation.getItems().clear();
            liststation.getItems().add("❌ Erreur : Aucune station sélectionnée !");
            return;
        }

        try {
            stationSelectionnee.setNom_station(tfNomStation.getText());
            stationSelectionnee.setEmplacement(tfEmplacement.getText());
            stationSelectionnee.setStatus(cbStatus.getValue().getValue());
            stationSelectionnee.setCapaciteMax(Integer.parseInt(tfCapaciteMax.getText()));
            stationSelectionnee.setHeuresOuverture(tfHeuresOuverture.getText());
            stationSelectionnee.setContact(tfContact.getText());
            stationSelectionnee.setLatitude(Double.parseDouble(tfLatitude.getText()));
            stationSelectionnee.setLongitude(Double.parseDouble(tfLongitude.getText()));

            Utilisateur selectedUser = cbUserId.getValue();
            if (selectedUser != null) {
                stationSelectionnee.setUser(selectedUser);
            } else {
                liststation.getItems().clear();
                liststation.getItems().add("❌ Erreur : Veuillez sélectionner un utilisateur !");
                return;
            }

            serviceStation.update(stationSelectionnee);
            liststation.getItems().clear();
            liststation.getItems().add("✅ Station mise à jour avec succès !");
            afficherStations(null);

            stationSelectionnee = null;
        } catch (NumberFormatException e) {
            liststation.getItems().clear();
            liststation.getItems().add("❌ Erreur : Vérifiez les champs numériques !");
        } catch (Exception e) {
            liststation.getItems().clear();
            liststation.getItems().add("❌ Erreur : " + e.getMessage());
        }
        resetFields();
    }


    @FXML
    public void afficherStations(ActionEvent actionEvent) {
        liststation.getItems().clear();

        List<Station> stations = serviceStation.getAll();

        for (Station station : stations) {
            String affichage = "🏠 " + station.getNom_station() + " - " + station.getStatus() +
                    "\n📍 Emplacement: " + station.getEmplacement() +
                    "\n👤 Responsable: " + station.getUser().getNom() + " (ID: " + station.getUser().getId_user() + ")" +
                    "\n📊 Capacité: " + station.getCapaciteMax() +
                    "\n📞 Contact: " + station.getContact() +
                    "\n🕒 Horaires: " + station.getHeuresOuverture() +
                    "\n🌍 Position: [" + station.getLatitude() + ", " + station.getLongitude() + "]" ;

            liststation.getItems().add(affichage);
        }

        liststation.setOnMouseClicked(event -> {
            int index = liststation.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                Station station = stations.get(index);
                remplirChampsPourModification(station);
            }
        });
    }

    private void remplirChampsPourModification(Station station) {
        stationSelectionnee = station;
        tfNomStation.setText(station.getNom_station());
        tfEmplacement.setText(station.getEmplacement());
        cbStatus.setValue(station.getStatus());
        tfCapaciteMax.setText(String.valueOf(station.getCapaciteMax()));
        tfHeuresOuverture.setText(station.getHeuresOuverture());
        tfContact.setText(station.getContact());
        tfLatitude.setText(String.valueOf(station.getLatitude()));
        tfLongitude.setText(String.valueOf(station.getLongitude()));

        Utilisateur user = station.getUser();
        if (user != null) {
            for (Utilisateur u : cbUserId.getItems()) {
                if (u.getId_user() == user.getId_user()) {
                    cbUserId.setValue(u);
                    break;
                }
            }
        } else {
            cbUserId.setValue(null);
        }
    }

    public void SupprimerStation(ActionEvent actionEvent) {
        if (stationSelectionnee == null) {
            liststation.getItems().clear();
            liststation.getItems().add("❌ Erreur : Aucune station sélectionnée !");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer la station ?");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette station ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            serviceStation.deleteID(stationSelectionnee.getId_station());
            liststation.getItems().clear();
            liststation.getItems().add("✅ Station supprimée avec succès !");
            afficherStations(null);
            stationSelectionnee = null;
        }
        resetFields();
    }

    public void OuvrirGestionBorne(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionBorne.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("--------Gestion des Bornes-----------");
            stage.setScene(scene);

            stage.show();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
