package tn.esprit.models;

import java.util.List;

public class Utilisateur {

    private int id_user;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private int cin;
    private String role;
    private List<Station> stations;  // Liste des stations appartenant à l'utilisateur

    // Constructeur par défaut
    public Utilisateur() {}

    // Nouveau constructeur qui accepte seulement l'ID
    public Utilisateur(int id_user,String nom) {
        this.id_user = id_user;
        this.nom = nom;
    }

    // Constructeur complet
    public Utilisateur(int id_user, String nom, String prenom, String email, int cin, String motDePasse, String role) {
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.cin = cin;
        this.role = role;
    }

    // Getters et Setters
    public int getId_user() { return id_user; }
    public void setId_user(int id_user) { this.id_user = id_user; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public int getCin() { return cin; }
    public void setCin(int cin) { this.cin = cin; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<Station> getStations() { return stations; }
    public void setStations(List<Station> stations) { this.stations = stations; }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id_user=" + id_user +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", cin=" + cin +
                ", role='" + role + '\'' +
                '}';
    }
}
