package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Start.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setMinWidth(1280);
            primaryStage.setMinHeight(720);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BlediSmart");

            // icon
            Image icon = new Image(getClass().getResourceAsStream("/img/Logo.png"));
            primaryStage.getIcons().add(icon);

            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            primaryStage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}