package tn.esprit.services;

import tn.esprit.models.Utilisateur;
import tn.esprit.utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtilisateur {
    private Connection cnx;

    public ServiceUtilisateur() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    public void add(Utilisateur utilisateur) {
        String qry = "INSERT INTO utilisateur(nom, prenom, email, cin, motDePasse, role) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, utilisateur.getNom());
            pstm.setString(2, utilisateur.getPrenom());
            pstm.setString(3, utilisateur.getEmail());
            pstm.setInt(4, utilisateur.getCin());
            pstm.setString(5, utilisateur.getMotDePasse());
            pstm.setString(6, utilisateur.getRole().name());

            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                utilisateur.setId_user(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    public List<Utilisateur> getAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String qry = "SELECT * FROM utilisateur";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Utilisateur u = new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getInt("cin"),
                        rs.getString("motDePasse"),
                        Utilisateur.Role.valueOf(rs.getString("role"))
                );
                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return utilisateurs;
    }

    public void update(int id_user, String nom, String prenom, String email, int cin, String motDePasse, Utilisateur.Role role) {
        String qry = "UPDATE utilisateur SET nom=?, prenom=?, email=?, cin=?, motDePasse=?, role=? WHERE id_user=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, nom);
            pstm.setString(2, prenom);
            pstm.setString(3, email);
            pstm.setInt(4, cin);
            pstm.setString(5, motDePasse);
            pstm.setString(6, role.name());
            pstm.setInt(7, id_user);

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur mise à jour : " + e.getMessage());
        }
    }

    public void delete(int id_user) {
        String qry = "DELETE FROM utilisateur WHERE id_user=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id_user);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur suppression : " + e.getMessage());
        }
    }
}
