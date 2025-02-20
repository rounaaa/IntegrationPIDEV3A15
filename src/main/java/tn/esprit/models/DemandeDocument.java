package tn.esprit.models;

import java.util.Date;

public class DemandeDocument {
    private int id_Demande;
    private int id_Document;
    private int id_user;
    private String status;
    private String Commentaire;
    private Date Date_Demande;
    private String titreDemande;
    private String description;
    private String typeDocument;
    private String nomDemandeur;
    private String pieceJustif;

    public DemandeDocument() {
    }

    public DemandeDocument(int id_Document, String status, Date Date_Demande, int id_user, String Commentaire, String titreDemande, String description, String typeDocument, String nomDemandeur, String pieceJustif) {
        this.id_Document = id_Document;
        this.status = status;
        this.Date_Demande = Date_Demande;
        this.id_user = id_user;
        this.Commentaire = Commentaire;
        this.titreDemande = titreDemande;
        this.description = description;
        this.typeDocument = typeDocument;
        this.nomDemandeur = nomDemandeur;
        this.pieceJustif = pieceJustif;
    }

    public DemandeDocument(int id_Demande, int id_Document, String status, Date Date_Demande, int id_user, String Commentaire, String titreDemande, String description, String typeDocument, String nomDemandeur, String pieceJustif) {
        this.id_Demande = id_Demande;
        this.id_Document = id_Document;
        this.status = status;
        this.Date_Demande = Date_Demande;
        this.id_user = id_user;
        this.Commentaire = Commentaire;
        this.titreDemande = titreDemande;
        this.description = description;
        this.typeDocument = typeDocument;
        this.nomDemandeur = nomDemandeur;
        this.pieceJustif = pieceJustif;
    }

    // Getters and Setters
    public int getId_Demande() {
        return id_Demande;
    }

    public void setId_Demande(int id_Demande) {
        this.id_Demande = id_Demande;
    }

    public int getId_Document() {
        return id_Document;
    }

    public void setId_Document(int id_Document) {
        this.id_Document = id_Document;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommentaire() {
        return Commentaire;
    }

    public void setCommentaire(String Commentaire) {
        this.Commentaire = Commentaire;
    }

    public Date getDate_Demande() {
        return Date_Demande;
    }

    public void setDate_Demande(Date Date_Demande) {
        this.Date_Demande = Date_Demande;
    }

    public String getTitreDemande() {
        return titreDemande;
    }

    public void setTitreDemande(String titreDemande) {
        this.titreDemande = titreDemande;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getNomDemandeur() {
        return nomDemandeur;
    }

    public void setNomDemandeur(String nomDemandeur) {
        this.nomDemandeur = nomDemandeur;
    }

    public String getPieceJustif() {
        return pieceJustif;
    }

    public void setPieceJustif(String pieceJustif) {
        this.pieceJustif = pieceJustif;
    }

    @Override
    public String toString() {
        return "DemandeDocument{" +
                "id_Demande=" + id_Demande +
                ", id_Document=" + id_Document +
                ", id_user=" + id_user +
                ", status='" + status + '\'' +
                ", Commentaire='" + Commentaire + '\'' +
                ", Date_Demande=" + Date_Demande +
                ", titreDemande='" + titreDemande + '\'' +
                ", description='" + description + '\'' +
                ", typeDocument='" + typeDocument + '\'' +
                ", nomDemandeur='" + nomDemandeur + '\'' +
                ", pieceJustif='" + pieceJustif + '\'' +
                "}\n";
    }
}