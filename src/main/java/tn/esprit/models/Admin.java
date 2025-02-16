package tn.esprit.models;

public class Admin extends Utilisateur {

    public Admin() {
        super();
        this.setRole("ADMIN");
    }

    public Admin(int id_user, String nom, String prenom, String email, int cin, String motDePasse) {
        super(id_user, nom, prenom, email, cin, motDePasse, "ADMIN");
    }

    public Admin(String nom, String prenom, String email, int cin, String motDePasse) {
        super(nom, prenom, email, cin, motDePasse);
        this.setRole("ADMIN");
    }
}
