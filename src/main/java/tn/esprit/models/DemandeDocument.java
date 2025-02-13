package tn.esprit.models;

import java.util.Date;

public class DemandeDocument {
    private int id_Demande,id_Document,id_user;
    private String status,Commentaire;
    private Date Date_Demande;

    public DemandeDocument() {
    }

    public DemandeDocument(int id_Document, String status, Date Date_Demande, int id_user, String Commentaire) {
        this.id_Document = id_Document;
        this.status = status;
        this.Date_Demande = Date_Demande;
        this.id_user = id_user;
        this.Commentaire = Commentaire;
    }

    public DemandeDocument(int id_Demande, int id_Document, String status, Date Date_Demande, int id_user, String Commentaire) {
        this.id_Demande = id_Demande;
        this.id_Document = id_Document;
        this.status = status;
        this.Date_Demande = Date_Demande;
        this.id_user = id_user;
        this.Commentaire = Commentaire;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate_Demande() {
        return Date_Demande;
    }

    public void setDate_Demande(Date Date_Demande) {
        this.Date_Demande = Date_Demande;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getCommentaire() {
        return Commentaire;
    }

    public void setCommentaire(String Commentaire) {
        this.Commentaire = Commentaire;
    }

    @Override
    public String toString() {
        return "DemandeDocument{" +
                "id_Demande=" + id_Demande +
                ", id_Document=" + id_Document +
                ", status='" + status + '\'' +
                ", Date_Demande=" + Date_Demande +
                ", id_user=" + id_user +
                ", Commentaire='" + Commentaire + '\'' +
                "}\n";
    }
}