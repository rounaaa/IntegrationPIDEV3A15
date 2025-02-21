package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
<<<<<<< HEAD
import tn.esprit.controllers.GestionStation;

import java.io.IOException;

public class MainFX extends Application {

    // Méthode principale qui lance l'application JavaFX
    public static void main(String[] args) {
        System.out.println("Application démarrée!");
=======
import java.io.IOException;
public class MainFX extends Application {

    public static void main(String[] args) {
>>>>>>> origin/may
        launch(args);
    }

    @Override
<<<<<<< HEAD
    public void start(Stage primaryStage) throws IOException {
        // Charger le fichier FXML depuis les ressources
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionStation.fxml"));
=======
    public void start(Stage primaryStage) {

        FXMLLoader loader =new FXMLLoader(getClass().getResource("/gestionEvenement.fxml"));
>>>>>>> origin/may
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
<<<<<<< HEAD
            GestionStation controller = loader.getController();
            controller.initialize(null, null);
            primaryStage.setTitle("---- Gestion Station -----");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Erreur de chargement du fichier FXML : " + e.getMessage());
        }
    }
}
=======
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

>>>>>>> origin/may
