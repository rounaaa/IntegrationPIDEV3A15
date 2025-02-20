package tn.esprit.models;

import java.time.LocalDateTime;

public class Evenement {
    private int id_evenement;
    private String nom_evenement;
    private String description;
    private LocalDateTime date;
    private String lieu;
    private String statut; // Peut être "actif", "annulé" ou "terminé"
    private int capacite_max;
    private String image;
    private Utilisateur utilisateur; // Référence à l'utilisateur organisateur (remplace 'User' par 'Utilisateur')

    public Evenement() {}

    // Constructeur modifié pour accepter une instance de Utilisateur
    public Evenement(String nom_evenement, String description, LocalDateTime date,
                     String lieu, String statut, int capacite_max, String image, Utilisateur utilisateur) {
        this.nom_evenement = nom_evenement;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.statut = statut;
        this.capacite_max = capacite_max;
        this.image = image;
        this.utilisateur = utilisateur; // Passer l'instance de Utilisateur
    }

    public Evenement(String nom, String description, LocalDateTime date, String lieu, String statut, int capacite, String image, int idUser) {

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public Utilisateur getUtilisateur() {
        return utilisateur; // Retourner l'instance de Utilisateur
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur; // Assigner l'instance de Utilisateur
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id_evenement=" + id_evenement +
                ", nom_evenement='" + nom_evenement + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                ", statut='" + statut + '\'' +
                ", capacite_max=" + capacite_max +
                ", image='" + image + '\'' +
                ", utilisateur=" + utilisateur + // Afficher l'utilisateur
                "}\n";
    }
}
