package tn.esprit.controllers;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tn.esprit.models.Document;

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

    public DocumentListCell() {
        super();
        imageView = new ImageView();
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        titre = new Text();
        status = new Text();
        description = new Text();
        type = new Text();
        dateCreation = new Text();
        dateModification = new Text();
        path = new Text();
        categorie = new Text();
        auteur = new Text();
        VBox vBox = new VBox(titre, status, description, type, dateCreation, dateModification, path, categorie, auteur);
        content = new HBox(imageView, vBox);
        content.setSpacing(10);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setStyle("-fx-border-color: #16a085; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 10;"); // Add border and padding
    }

    @Override
    protected void updateItem(Document document, boolean empty) {
        super.updateItem(document, empty);
        if (document != null && !empty) {
            // Set the icon based on the document type or other attribute
            Image icon = new Image(getClass().getResourceAsStream("/img/Logo.png"));
            imageView.setImage(icon);
            titre.setText("Titre: " + document.getTitre());
            status.setText("Statut: " + document.getStatus());
            description.setText("Description: " + document.getDescription());
            type.setText("Type: " + document.getType());
            dateCreation.setText("Date de création: " + document.getDateCreation());
            dateModification.setText("Date de modification: " + document.getDateModification());
            path.setText("Chemin: " + document.getPath());
            categorie.setText("Catégorie: " + document.getCategorie());
            auteur.setText("Auteur: " + document.getAuteur());
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}