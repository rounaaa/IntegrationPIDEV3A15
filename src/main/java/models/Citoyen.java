package models;

public class Citoyen extends Utilisateur {
    private int idCitoyen;

    public Citoyen(int idUser, String nom, String prenom, String email, String cin, String motDePasse, String role, int idCitoyen) {
        super(idUser, nom, prenom, email, cin, motDePasse, role);
        this.idCitoyen = idCitoyen;
    }

    public int getIdCitoyen() {
        return idCitoyen;
    }

    public void setIdCitoyen(int idCitoyen) {
        this.idCitoyen = idCitoyen;
    }

    @Override
    public String toString() {
        return "Citoyen{" + super.toString() + ", idCitoyen=" + idCitoyen + '}';
    }
}
