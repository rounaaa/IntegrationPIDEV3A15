package tn.esprit.services;

import tn.esprit.models.Utilisateur;
import tn.esprit.models.Admin;
import tn.esprit.models.Citoyen;
import tn.esprit.utils.MyDatabase;
import org.mindrot.jbcrypt.BCrypt; // Ajout pour le hachage du mot de passe

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtilisateur {
    private Connection cnx;

    public ServiceUtilisateur() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    // Méthode pour ajouter un utilisateur avec hachage du mot de passe
    public void add(Utilisateur utilisateur) {
        String qry = "INSERT INTO utilisateur(nom, prenom, email, cin, motDePasse, role) VALUES (?,?,?,?,?,?)";
        try {
            // Hachage du mot de passe avec BCrypt
            String hashedPassword = BCrypt.hashpw(utilisateur.getMotDePasse(), BCrypt.gensalt(12));

            // Insertion dans la table utilisateur
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, utilisateur.getNom());
            pstm.setString(2, utilisateur.getPrenom());
            pstm.setString(3, utilisateur.getEmail());
            pstm.setInt(4, utilisateur.getCin());
            pstm.setString(5, hashedPassword); // Mot de passe haché
            pstm.setString(6, utilisateur.getRole());

            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                utilisateur.setId_user(rs.getInt(1)); // Récupérer l'ID généré
            }

            // Ajouter dans la table appropriée (admins ou citoyens) en fonction du rôle
            if (utilisateur.getRole().equalsIgnoreCase("admin")) {
                String qryAdmin = "INSERT INTO admins(id_user) VALUES (?)";
                PreparedStatement pstmAdmin = cnx.prepareStatement(qryAdmin);
                pstmAdmin.setInt(1, utilisateur.getId_user());
                pstmAdmin.executeUpdate();
            } else if (utilisateur.getRole().equalsIgnoreCase("citoyen")) {
                String qryCitoyen = "INSERT INTO citoyens(id_user) VALUES (?)";
                PreparedStatement pstmCitoyen = cnx.prepareStatement(qryCitoyen);
                pstmCitoyen.setInt(1, utilisateur.getId_user());
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
                } else {
                    u = new Citoyen(
                            rs.getInt("id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getInt("cin"),
                            rs.getString("motDePasse")
                    );
                }
                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return utilisateurs;
    }

    // Méthode pour mettre à jour un utilisateur avec hachage conditionnel
    public void update(Utilisateur utilisateur) {
        try {
            // Récupérer l'ancien mot de passe
            String oldPassword = getPasswordById(utilisateur.getId_user());
            String newPassword = utilisateur.getMotDePasse();
            String hashedPassword = oldPassword;

            // Si le mot de passe est modifié, on le hache avant la mise à jour
            if (!newPassword.equals(oldPassword)) {
                hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
            }

            String qry = "UPDATE utilisateur SET nom=?, prenom=?, email=?, cin=?, motDePasse=?, role=? WHERE id_user=?";
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, utilisateur.getNom());
            pstm.setString(2, utilisateur.getPrenom());
            pstm.setString(3, utilisateur.getEmail());
            pstm.setInt(4, utilisateur.getCin());
            pstm.setString(5, hashedPassword); // Mot de passe (haché si modifié)
            pstm.setString(6, utilisateur.getRole());
            pstm.setInt(7, utilisateur.getId_user());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur mise à jour : " + e.getMessage());
        }
    }

    // Méthode pour récupérer le mot de passe d'un utilisateur par son ID
    public String getPasswordById(int id_user) {
        String qry = "SELECT motDePasse FROM utilisateur WHERE id_user=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id_user);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getString("motDePasse");
            }
        } catch (SQLException e) {
            System.out.println("Erreur récupération mot de passe : " + e.getMessage());
        }
        return null;
    }

    // Vérifier un mot de passe (ex: lors de la connexion)
    public boolean verifyPassword(String enteredPassword, String storedHashedPassword) {
        return BCrypt.checkpw(enteredPassword, storedHashedPassword);
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
