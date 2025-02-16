package tn.esprit.models;

public class Citoyen extends Utilisateur {

    public Citoyen() {
        super();
        this.setRole("CITOYEN");
    }

    public Citoyen(int id_user, String nom, String prenom, String email, int cin, String motDePasse) {
        super(id_user, nom, prenom, email, cin, motDePasse, "CITOYEN");
    }

    public Citoyen(String nom, String prenom, String email, int cin, String motDePasse) {
        super(nom, prenom, email, cin, motDePasse);
        this.setRole("CITOYEN");
    }
}
