package tn.esprit.models;

import java.util.Date;

public class Document {
    private int id_document;
    private String titre,status,description,type_document;
    private Date date_creation;
    private Date date_modification;
    public Document() {
    }

    public Document(String titre, String description, String type_document, Date date_creation, Date date_modification, String status) {
        this.titre = titre;
        this.description = description;
        this.type_document = type_document;
        this.date_creation = date_creation;
        this.date_modification = date_modification;
        this.status = status;
    }

    public Document(int id_document, String titre, String description, String type_document, Date date_creation, Date date_modification, String status) {
        this.id_document = id_document;
        this.titre = titre;
        this.description = description;
        this.type_document = type_document;
        this.date_creation = date_creation;
        this.date_modification = date_modification;
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType_document() {
        return type_document;
    }

    public void setType_document(String type_document) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id_document=" + id_document +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", type_document='" + type_document + '\'' +
                ", date_creation=" + date_creation +
                ", date_modification=" + date_modification +
                ", status='" + status + '\'' +
                "}\n";
    }
}