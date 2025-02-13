package tn.esprit.models;

public class Utilisateur {
    private int id_user;
    private String nom;
    private String prenom;
    private String email;
    private String Mdp;
    private String Role;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String email, String Mdp, String Role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.Mdp = Mdp;
        this.Role = Role;
    }

    public Utilisateur(int id_user, String nom, String prenom, String email, String Mdp, String Role) {
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.Mdp = Mdp;
        this.Role = Role;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return Mdp;
    }

    public void setMdp(String Mdp) {
        this.Mdp = Mdp;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id_user=" + id_user +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", Mdp='" + Mdp + '\'' +
                ", Role='" + Role + '\'' +
                "}\n";
    }
}