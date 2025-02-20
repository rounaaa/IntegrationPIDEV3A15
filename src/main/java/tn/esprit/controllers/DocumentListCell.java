package tn.esprit.controllers;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tn.esprit.models.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DocumentListCell extends ListCell<Document> {
    private HBox content;
    private ImageView imageView;
    private Text titre;
    private Text status;
    private Text description;
    private Text type;
    private Text dateCreation;
    private Text dateModification;
    private Text path;
    private Text categorie;
    private Text auteur;

    // Date formatter for displaying dates
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public DocumentListCell() {
        super();

        imageView = new ImageView();
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        // Initialize text fields
        titre = new Text();
        status = new Text();
        description = new Text();
        type = new Text();
        dateCreation = new Text();
        dateModification = new Text();
        path = new Text();
        categorie = new Text();
        auteur = new Text();


        description.setWrappingWidth(300); // Adjust width as needed
        path.setWrappingWidth(300);


        VBox vBox = new VBox(
                titre, status, description, type, dateCreation, dateModification, path, categorie, auteur
        );
        vBox.setSpacing(5);

        // Create an HBox to hold the image and text fields
        content = new HBox(imageView, vBox);
        content.setSpacing(10);
        content.setAlignment(Pos.CENTER_LEFT);

        // Add styling to the cell
        content.setStyle(
                "-fx-border-color: #16a085; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 5; " +
                        "-fx-padding: 10; " +
                        "-fx-background-color: #f0f0f0;"
        );
    }

    @Override
    protected void updateItem(Document document, boolean empty) {
        super.updateItem(document, empty);
        if (document != null && !empty) {
            try {
                Image icon = new Image(getClass().getResourceAsStream("/img/Logo.png"));
                imageView.setImage(icon);
            } catch (Exception e) {
                imageView.setImage(null);
            }

            // Set text fields with document data
            titre.setText("Titre: " + document.getTitre());
            status.setText("Statut: " + document.getStatus());
            description.setText("Description: " + document.getDescription());
            type.setText("Type: " + document.getType());
            dateCreation.setText("Date de création: " + formatDate(document.getDate_creation()));
            dateModification.setText("Date de modification: " + formatDate(document.getDate_modification()));
            path.setText("Chemin: " + document.getPath());
            categorie.setText("Catégorie: " + document.getCategorie());
            auteur.setText("Auteur: " + document.getAuteur());


            setGraphic(content);
        } else {

            setGraphic(null);
        }
    }

    private String formatDate(Date date) {
        if (date != null) {
            return dateFormatter.format(date);
        }
        return "N/A";
    }
}