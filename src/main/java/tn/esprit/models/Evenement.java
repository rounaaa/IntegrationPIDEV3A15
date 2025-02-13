package tn.esprit.models;

import java.time.LocalDateTime;

public class Evenement {
    private int id_evenement;
    private String nom_evenement;
    private String description;
    private LocalDateTime date_debut;
    private LocalDateTime date_fin;
    private String lieu;
    private String statut; // Peut être "actif", "annulé" ou "terminé"
    private int capacite_max;
    private String image;
    private int id_user; // Référence à l'utilisateur organisateur

    public Evenement() {}

    public Evenement(String nom_evenement, String description, LocalDateTime date_debut, LocalDateTime date_fin,
                     String lieu, String statut, int capacite_max, String image, int id_user) {
        this.nom_evenement = nom_evenement;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.lieu = lieu;
        this.statut = statut;
        this.capacite_max = capacite_max;
        this.image = image;
        this.id_user = id_user;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public String getNom_evenement() {
        return nom_evenement;
    }

    public void setNom_evenement(String nom_evenement) {
        this.nom_evenement = nom_evenement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDateTime date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getCapacite_max() {
        return capacite_max;
    }

    public void setCapacite_max(int capacite_max) {
        this.capacite_max = capacite_max;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id_evenement=" + id_evenement +
                ", nom_evenement='" + nom_evenement + '\'' +
                ", description='" + description + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", lieu='" + lieu + '\'' +
                ", statut='" + statut + '\'' +
                ", capacite_max=" + capacite_max +
                ", image='" + image + '\'' +
                ", id_user=" + id_user +
                "}\n";
    }
}
