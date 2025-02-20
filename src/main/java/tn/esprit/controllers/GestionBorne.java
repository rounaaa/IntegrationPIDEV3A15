package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.models.Borne_Pompe;
import tn.esprit.models.Station;
import tn.esprit.models.Utilisateur;
import tn.esprit.models.tarifs;
import tn.esprit.services.ServiceBorne;
import tn.esprit.services.ServiceStation;

import java.util.List;
import java.util.Optional;

public class GestionBorne {

    @FXML
    private TextField tfConnecteur, tfPuissance, tfEnergieConsommee, tfCout, tfTarif;
    @FXML
    private ComboBox<Borne_Pompe.Type> cbType;
    @FXML
    private ComboBox<String> cbEtat;
    @FXML
    private ComboBox<Utilisateur> cbDernierUtilisateur;
    @FXML
    private ComboBox<Station> cbStation;
    @FXML
    private CheckBox cbDisponibilite;
    @FXML
    private ListView<String> listBorne;

    private ServiceBorne serviceBorne = new ServiceBorne();
    private ServiceStation serviceStation = new ServiceStation();
    private Borne_Pompe borneSelectionnee = null;

    public void initialize() {
        List<Utilisateur> utilisateurs = serviceStation.getAllUsers();
        cbDernierUtilisateur.getItems().addAll(utilisateurs);

        cbDernierUtilisateur.setConverter(new javafx.util.StringConverter<Utilisateur>() {
            @Override
            public String toString(Utilisateur user) {
                return (user != null) ? user.getNom() + " - " + user.getId_user() : "";
            }

            @Override
            public Utilisateur fromString(String string) {
                return cbDernierUtilisateur.getItems().stream()
                        .filter(user -> (user.getNom() + " - " + user.getId_user()).equals(string))
                        .findFirst().orElse(null);
            }
        });


        List<Station> stations = serviceStation.getAll();

        cbStation.getItems().addAll(stations);

        cbStation.setConverter(new javafx.util.StringConverter<Station>() {
            @Override
            public String toString(Station station) {
                return (station != null) ? station.getNom_station() + " - " + station.getId_station() : "";
            }

            @Override
            public Station fromString(String string) {
                if (string == null || string.isEmpty()) {
                    return null;
                }
                String[] parts = string.split(" - ");
                if (parts.length != 2) {
                    return null;
                }
                String nomStation = parts[0];
                int idStation = Integer.parseInt(parts[1]);

                return cbStation.getItems().stream()
                        .filter(station -> station.getId_station() == idStation)
                        .findFirst().orElse(null);
            }
        });


        if (cbType != null) {
            cbType.getItems().addAll(Borne_Pompe.Type.values());
        }
        if (cbEtat != null) {

            for (Borne_Pompe.Etat etat : Borne_Pompe.Etat.values()) {
                cbEtat.getItems().add(etat.toString());
            }
        }

        tfEnergieConsommee.textProperty().addListener((obs, oldVal, newVal) -> {
            calculerCout();
        });
        afficherBornes(null);
    }

    @FXML
    public void handleTypeSelection() {
        Borne_Pompe.Type selectedType = cbType.getValue();
        if (selectedType != null) {
            int idTarif = (selectedType == Borne_Pompe.Type.STAN) ? 1 : (selectedType == Borne_Pompe.Type.FAST) ? 2 : 3;
            tarifs tarif = serviceBorne.getTarifById(idTarif);
            if (tarif != null) {
                tfTarif.setText(String.valueOf(tarif.getTarif_par_kwh()));
                calculerCout();
            }
        }
    }

    @FXML
    public void calculerCout() {
        try {
            double energie = Double.parseDouble(tfEnergieConsommee.getText());
            double tarif = Double.parseDouble(tfTarif.getText());
            tfCout.setText(String.valueOf(energie * tarif));
        } catch (NumberFormatException e) {
            tfCout.setText("0");
        }
    }

    @FXML
    public void ajouterBorne(ActionEvent event) {
        try {
            if (cbType.getValue() == null || cbEtat.getValue() == null || cbDernierUtilisateur.getValue() == null || cbStation.getValue() == null) {
                listBorne.getItems().add(" Erreur: Tous les champs doivent être remplis!");
                return;
            }

            Borne_Pompe.Type type = cbType.getValue();
            Borne_Pompe.Etat etat = Borne_Pompe.Etat.valueOf(cbEtat.getValue());
            String connecteur = tfConnecteur.getText();
            double puissance = Double.parseDouble(tfPuissance.getText());
            boolean disponibilite = cbDisponibilite.isSelected();
            double energieConsommee = Double.parseDouble(tfEnergieConsommee.getText());
            double cout = Double.parseDouble(tfCout.getText());
            Utilisateur user = cbDernierUtilisateur.getValue();
            Station station = cbStation.getValue();
            tarifs tarif = serviceBorne.getTarifById(type == Borne_Pompe.Type.STAN ? 1 : (type == Borne_Pompe.Type.FAST ? 2 : 3));

            Borne_Pompe borne = new Borne_Pompe(type, puissance, etat, connecteur, disponibilite, energieConsommee, user, station, tarif, cout);
            serviceBorne.add(borne);
            listBorne.getItems().add(" Borne ajoutée avec succès!");
        } catch (NumberFormatException e) {
            listBorne.getItems().add(" Erreur: Vérifiez les champs numériques!");
        } catch (Exception e) {
            listBorne.getItems().add(" Erreur inconnue: " + e.getMessage());
        }
        resetFields();
    }
    public void SupprimerBorne(ActionEvent actionEvent) {
        if (borneSelectionnee == null) {
            listBorne.getItems().clear();
            listBorne.getItems().add(" Erreur : Aucune station sélectionnée !");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer la borne ?");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette borne ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            serviceStation.deleteID(borneSelectionnee.getId_borne());
            listBorne.getItems().clear();
            listBorne.getItems().add(" Station supprimée avec succès !");
            afficherBornes(null);
            borneSelectionnee = null;
        }
        resetFields();
    }
    @FXML
    public void afficherBornes(ActionEvent actionEvent) {
        listBorne.getItems().clear();

        List<Borne_Pompe> bornes = serviceBorne.getAll();

        for (Borne_Pompe borne : bornes) {
            String affichage =
                    "\nType: " + borne.getType() + " - " + borne.getEtat() +
                            "\nConnecteur: " + borne.getConnecteur_type() +
                            "\nDisponibilité: " + (borne.isDisponible() ? "Disponible" : "Indisponible") +
                            "\nÉnergie consommée: " + borne.getEnergie_consommee() + " kWh" +
                            "\nDernier utilisateur: " + borne.getDernier_utilisateur().getId_user() + ")" +
                            "\n Station: " + borne.getStation().getId_station() +
                            "\nTarif: " + borne.getTarif().getTarif_par_kwh() + " DNT" +
                            "\nCoût: " + borne.getCout() + " DNT";

            listBorne.getItems().add(affichage);
        }

        listBorne.setOnMouseClicked(event -> {
            int index = listBorne.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                System.out.println("Borne sélectionnée : " + bornes.get(index).getId_borne());
                borneSelectionnee = bornes.get(index);
                remplirChampsPourModification(borneSelectionnee);
            } else {
                System.out.println("Aucune borne sélectionnée");
            }
        });

    }

    private void remplirChampsPourModification(Borne_Pompe borne) {
        if (borne == null) return;

        cbType.setValue(borne.getType());

        tfConnecteur.setText(borne.getConnecteur_type());
        tfPuissance.setText(String.valueOf(borne.getPuissance_kW()));
        cbEtat.setValue(borne.getEtat().toString());
        cbDisponibilite.setSelected(borne.isDisponible());
        tfEnergieConsommee.setText(String.valueOf(borne.getEnergie_consommee()));
        tfCout.setText(String.valueOf(borne.getCout()));

        if (borne.getTarif() != null) {
            tfTarif.setText(String.valueOf(borne.getTarif().getTarif_par_kwh()));
        }


        cbDernierUtilisateur.setValue(borne.getDernier_utilisateur());
        cbStation.setValue(borne.getStation());
    }

    private void resetFields() {
        tfTarif.clear();
        tfCout.clear();
        cbType.setValue(null);
        cbEtat.setValue(null);
        tfEnergieConsommee.clear();
        tfConnecteur.clear();
        cbDisponibilite.setSelected(false);
        tfPuissance.clear();
        cbStation.setValue(null);
    }

    @FXML
    public void miseajourBorne(ActionEvent actionEvent) {
        if (borneSelectionnee == null) {
            listBorne.getItems().add("❌ Erreur: Aucune borne sélectionnée !");
            System.out.println("Aucune borne sélectionnée pour mise à jour");
            return;
        }
        borneSelectionnee.setType(cbType.getValue());
        borneSelectionnee.setEtat(Borne_Pompe.Etat.valueOf(cbEtat.getValue()));
        borneSelectionnee.setConnecteur_type(tfConnecteur.getText());
        borneSelectionnee.setPuissance_kW(Double.parseDouble(tfPuissance.getText()));
        borneSelectionnee.setDisponibilile(cbDisponibilite.isSelected());
        borneSelectionnee.setEnergie_consommee(Double.parseDouble(tfEnergieConsommee.getText()));
        borneSelectionnee.setCout(Double.parseDouble(tfCout.getText()));
        borneSelectionnee.setDernier_utilisateur(cbDernierUtilisateur.getValue());
        borneSelectionnee.setStation(cbStation.getValue());

        serviceBorne.update(borneSelectionnee);

        listBorne.getItems().add("✅ Borne mise à jour avec succès !");
        afficherBornes(actionEvent);
    }
}
