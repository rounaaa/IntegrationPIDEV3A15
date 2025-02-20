package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loader =new FXMLLoader(getClass().getResource("/gestionEvenement.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("---- Gestion evenement-----");
            primaryStage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void setSecondStage(Stage secondStage) {

        FXMLLoader loader =new FXMLLoader(getClass().getResource("/participation.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            secondStage.setScene(scene);
           secondStage.setTitle("---- Gestion participation-----");
            secondStage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

