package test;

import com.sun.javafx.stage.EmbeddedWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("/GestionListView.fxml"));
        primaryStage.setTitle("afficher un conteneur");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.setProperty("javafx.platform", "Desktop");
        System.setProperty("prism.order", "sw");
        System.setProperty("java.library.path", "path/to/javafx/lib");
        System.setProperty("jdk.module.path", "path/to/javafx/modules");
        launch(args);
    }
}