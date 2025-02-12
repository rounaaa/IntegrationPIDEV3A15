package tn.esprit.models;

import java.time.LocalDate;

public class Reponse {
    private int id_reponse;
    private int id_reclamation;
    private int id_user;
    private String contenu;
    private LocalDate date_reponse;
    private String status;

    public Reponse() {}

    public Reponse(int id_reclamation, int id_user, String contenu, LocalDate date_reponse, String status) {
        this.id_reclamation = id_reclamation;
        this.id_user = id_user;
        this.contenu = contenu;
        this.date_reponse = date_reponse;
        this.status = status;
    }

    public int getid_reponse() {
        return id_reponse;
    }

    public void setid_reponse(int id_reponse) {
        this.id_reponse = id_reponse;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDate getDate_reponse() {
        return date_reponse;
    }

    public void setDate_reponse(LocalDate date_reponse) {
        this.date_reponse = date_reponse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "id_reponse=" + id_reponse +
                ", id_reclamation=" + id_reclamation +
                ", id_user=" + id_user +
                ", contenu='" + contenu + '\'' +
                ", date_reponse=" + date_reponse +
                ", status='" + status + '\'' +
                '}';
    }
}