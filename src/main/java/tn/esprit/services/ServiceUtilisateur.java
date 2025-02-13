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
        String qry = "INSERT INTO `utilisateur`(`nom`, `prenom`, `email`, `motDePasse`, `role`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, utilisateur.getNom());
            pstm.setString(2, utilisateur.getPrenom());
            pstm.setString(3, utilisateur.getEmail());
            pstm.setString(4, utilisateur.getMotDePasse());
            pstm.setString(5, utilisateur.getRole().name());

            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                utilisateur.setId_user(rs.getInt(1));
            }

            System.out.println("Utilisateur ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    public List<Utilisateur> getAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String qry = "SELECT * FROM `utilisateur`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Utilisateur u = new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
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

    public void update(int id_user, String nom, String prenom, String email, String motDePasse, Utilisateur.Role role) {
        String qry = "UPDATE `utilisateur` SET `nom`=?, `prenom`=?, `email`=?, `motDePasse`=?, `role`=? WHERE `id_user`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, nom);
            pstm.setString(2, prenom);
            pstm.setString(3, email);
            pstm.setString(4, motDePasse);
            pstm.setString(5, role.name());
            pstm.setInt(6, id_user);

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Utilisateur " + id_user + " mis à jour !");
            } else {
                System.out.println("Aucun utilisateur trouvé avec ID " + id_user);
            }
        } catch (SQLException e) {
            System.out.println("Erreur mise à jour : " + e.getMessage());
        }
    }

    public void delete(int id_user) {
        String qry = "DELETE FROM `utilisateur` WHERE `id_user`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id_user);

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Utilisateur " + id_user + " supprimé !");
            } else {
                System.out.println("Aucun utilisateur trouvé avec ID " + id_user);
            }
        } catch (SQLException e) {
            System.out.println("Erreur suppression : " + e.getMessage());
        }
    }
}
