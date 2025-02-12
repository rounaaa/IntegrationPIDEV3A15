package tn.esprit.models;

import java.time.LocalDate;

public class Reclamation {
    private int id;
    private String sujet;
    private String status;
    private int id_user;
    private LocalDate date;

    public Reclamation() {}

    public Reclamation(String sujet, String status, int id_user, LocalDate date) {
        this.sujet = sujet;
        this.status = status;
        this.id_user = id_user;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", sujet='" + sujet + '\'' +
                ", status='" + status + '\'' +
                ", id_user=" + id_user +
                ", date=" + date +
                "}\n";
    }
}
