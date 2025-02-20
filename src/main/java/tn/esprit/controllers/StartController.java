package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;

public class StartController {

    @FXML
    private void handleAdminButton(ActionEvent event) throws IOException {
        Parent adminView = FXMLLoader.load(getClass().getResource("/AjouterDoc.fxml"));
        Scene adminScene = new Scene(adminView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.show();
    }

    @FXML
    private void handleUserButton(ActionEvent event) {
        try {
            // CALL UserDashboard.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserDashboard.fxml"));
            Parent root = loader.load();

            // SET stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set scene
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}