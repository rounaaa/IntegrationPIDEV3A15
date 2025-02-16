package tn.esprit.services;

import tn.esprit.models.Utilisateur;
import tn.esprit.models.Admin;
import tn.esprit.models.Citoyen;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtilisateur {
    private Connection cnx;

    public ServiceUtilisateur() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    // Méthode pour ajouter un utilisateur
    public void add(Utilisateur utilisateur) {
        String qry = "INSERT INTO utilisateur(nom, prenom, email, cin, motDePasse, role) VALUES (?,?,?,?,?,?)";
        try {
            // Insertion dans la table utilisateur
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, utilisateur.getNom());
            pstm.setString(2, utilisateur.getPrenom());
            pstm.setString(3, utilisateur.getEmail());
            pstm.setInt(4, utilisateur.getCin());
            pstm.setString(5, utilisateur.getMotDePasse());
            pstm.setString(6, utilisateur.getRole()); // Ajout du champ 'role'

            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                utilisateur.setId_user(rs.getInt(1)); // Récupérer l'ID généré
            }

            // Ajouter dans la table appropriée (admins ou citoyens) en fonction du rôle
            if (utilisateur.getRole().equalsIgnoreCase("admin")) {
                String qryAdmin = "INSERT INTO admins(id_user) VALUES (?)";
                PreparedStatement pstmAdmin = cnx.prepareStatement(qryAdmin);
                pstmAdmin.setInt(1, utilisateur.getId_user()); // Insertion de l'ID de l'utilisateur dans la table admins
                pstmAdmin.executeUpdate();
            } else if (utilisateur.getRole().equalsIgnoreCase("citoyen")) {
                String qryCitoyen = "INSERT INTO citoyens(id_user) VALUES (?)";
                PreparedStatement pstmCitoyen = cnx.prepareStatement(qryCitoyen);
                pstmCitoyen.setInt(1, utilisateur.getId_user()); // Insertion de l'ID de l'utilisateur dans la table citoyens
                pstmCitoyen.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    // Méthode pour récupérer tous les utilisateurs
    public List<Utilisateur> getAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String qry = "SELECT * FROM utilisateur";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Utilisateur u;
                String role = rs.getString("role");
                if ("ADMIN".equalsIgnoreCase(role)) {
                    u = new Admin(
                            rs.getInt("id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getInt("cin"),
                            rs.getString("motDePasse")
                    );
                    utilisateurs.add(u);
                } else if ("CITOYEN".equalsIgnoreCase(role)) {
                    u = new Citoyen(
                            rs.getInt("id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getInt("cin"),
                            rs.getString("motDePasse")
                    );
                    utilisateurs.add(u);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return utilisateurs;
    }

    // Méthode pour mettre à jour un utilisateur
    public void update(Utilisateur utilisateur) {
        String qry = "UPDATE utilisateur SET nom=?, prenom=?, email=?, cin=?, motDePasse=?, role=? WHERE id_user=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, utilisateur.getNom());
            pstm.setString(2, utilisateur.getPrenom());
            pstm.setString(3, utilisateur.getEmail());
            pstm.setInt(4, utilisateur.getCin());
            pstm.setString(5, utilisateur.getMotDePasse());
            pstm.setString(6, utilisateur.getRole());  // Mise à jour du rôle
            pstm.setInt(7, utilisateur.getId_user());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur mise à jour : " + e.getMessage());
        }
    }

    // Méthode pour supprimer un utilisateur
    public void delete(int id_user) {
        try {
            String qry = "DELETE FROM utilisateur WHERE id_user=?";
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id_user);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur suppression : " + e.getMessage());
        }
    }

}
