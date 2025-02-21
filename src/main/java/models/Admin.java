package models;

public class Admin extends Utilisateur {
    private int idAdmin;

    public Admin(int idUser, String nom, String prenom, String email, String cin, String motDePasse, String role, int idAdmin) {
        super(idUser, nom, prenom, email, cin, motDePasse, role);
        this.idAdmin = idAdmin;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    @Override
    public String toString() {
        return "Admin{" + super.toString() + ", idAdmin=" + idAdmin + '}';
    }
}
