package models;

import javafx.beans.property.*;

public class Utilisateur {
    protected IntegerProperty idUser;
    protected StringProperty nom;
    protected StringProperty prenom;
    protected StringProperty email;
    protected StringProperty cin;
    protected StringProperty motDePasse;
    protected StringProperty role;

    public Utilisateur() {}

    public Utilisateur(int idUser, String nom, String prenom, String email, String cin, String motDePasse, String role) {
        this.idUser = new SimpleIntegerProperty(idUser);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.email = new SimpleStringProperty(email);
        this.cin = new SimpleStringProperty(cin);
        this.motDePasse = new SimpleStringProperty(motDePasse);
        this.role = new SimpleStringProperty(role);
    }

    public int getIdUser() { return idUser.get(); }
    public void setIdUser(int idUser) { this.idUser.set(idUser); }
    public IntegerProperty idUserProperty() { return idUser; }

    public String getNom() { return nom.get(); }
    public void setNom(String nom) { this.nom.set(nom); }
    public StringProperty nomProperty() { return nom; }

    public String getPrenom() { return prenom.get(); }
    public void setPrenom(String prenom) { this.prenom.set(prenom); }
    public StringProperty prenomProperty() { return prenom; }

    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }
    public StringProperty emailProperty() { return email; }

    public String getCin() { return cin.get(); }
    public void setCin(String cin) { this.cin.set(cin); }
    public StringProperty cinProperty() { return cin; }

    public String getMotDePasse() { return motDePasse.get(); }
    public void setMotDePasse(String motDePasse) { this.motDePasse.set(motDePasse); }
    public StringProperty motDePasseProperty() { return motDePasse; }

    public String getRole() { return role.get(); }
    public void setRole(String role) { this.role.set(role); }
    public StringProperty roleProperty() { return role; }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUser=" + idUser.get() +
                ", nom='" + nom.get() + '\'' +
                ", prenom='" + prenom.get() + '\'' +
                ", email='" + email.get() + '\'' +
                ", cin='" + cin.get() + '\'' +
                ", motDePasse='" + motDePasse.get() + '\'' +
                ", role='" + role.get() + '\'' +
                '}';
    }
}
