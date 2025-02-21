package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SidebarController {

    @FXML private Button btnAddreclamation;
    @FXML private Button btnListreclamation;
    @FXML private Button btnHistorique;



    @FXML
    private void initialize() {

        btnAddreclamation.setOnAction(event -> loadView("/AjouterReclamation.fxml"));
        btnListreclamation.setOnAction(event -> loadView("/AdminReclamation.fxml"));
        btnHistorique.setOnAction(event -> loadView("/historyreclamation.fxml"));

    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (loader.getLocation() == null) {
                throw new IOException("FXML file not found: " + fxmlPath);
            }
            Parent root = loader.load();
            Stage stage = (Stage) btnAddreclamation.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("❌ Error loading FXML file: " + fxmlPath);
            e.printStackTrace();
        }
    }

}
