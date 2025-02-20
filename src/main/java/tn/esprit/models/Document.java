package tn.esprit.models;

import java.util.Date;

public class Document {
    private int id_document;
    private String titre;
    private String status;
    private String description;
    private String type_document;
    private Date date_creation;
    private Date date_modification;
    private String path;
    private String categorie;
    private String auteur;

    public Document() {
    }

    public Document(String titre, String status, String description, String type_document, Date date_creation, Date date_modification, String path, String categorie, String auteur) {
        this.titre = titre;
        this.status = status;
        this.description = description;
        this.type_document = type_document;
        this.date_creation = date_creation;
        this.date_modification = date_modification;
        this.path = path;
        this.categorie = categorie;
        this.auteur = auteur;
    }

    public Document(int id_document, String titre, String status, String description, String type_document, Date date_creation, Date date_modification, String path, String categorie, String auteur) {
        this.id_document = id_document;
        this.titre = titre;
        this.status = status;
        this.description = description;
        this.type_document = type_document;
        this.date_creation = date_creation;
        this.date_modification = date_modification;
        this.path = path;
        this.categorie = categorie;
        this.auteur = auteur;
    }

    public int getId() {
        return id_document;
    }

    public void setId(int id_document) {
        this.id_document = id_document;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type_document;
    }

    public void setType(String type_document) {
        this.type_document = type_document;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public Date getDate_modification() {
        return date_modification;
    }

    public void setDate_modification(Date date_modification) {
        this.date_modification = date_modification;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id_document=" + id_document +
                ", titre='" + titre + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", type_document='" + type_document + '\'' +
                ", date_creation=" + date_creation +
                ", date_modification=" + date_modification +
                ", path='" + path + '\'' +
                ", categorie='" + categorie + '\'' +
                ", auteur='" + auteur + '\'' +
                "}\n";
    }
}