package tn.esprit.services;

import tn.esprit.models.Utilisateur;
import tn.esprit.models.Admin;
import tn.esprit.models.Citoyen;
import tn.esprit.interfaces.IService;
import tn.esprit.utils.MyDatabase;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtilisateur implements IService<Utilisateur> {
    private Connection cnx;

    public ServiceUtilisateur() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    public void add(Utilisateur utilisateur) {
        if (!isValidCIN(utilisateur.getCin())) {
            throw new IllegalArgumentException("Le CIN doit contenir exactement 8 chiffres !");
        }

        if (existsByCIN(utilisateur.getCin())) {
            throw new IllegalArgumentException("Ce CIN est déjà utilisé !");
        }

        if (!isValidEmail(utilisateur.getEmail())) {
            throw new IllegalArgumentException("Format d'email invalide !");
        }

        // Vérifier si l'email existe déjà
        if (existsByEmail(utilisateur.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé !");
        }

        String qry = "INSERT INTO utilisateur(nom, prenom, email, cin, motDePasse, role) VALUES (?,?,?,?,?,?)";
        try {
            String hashedPassword = BCrypt.hashpw(utilisateur.getMotDePasse(), BCrypt.gensalt(12));

            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, utilisateur.getNom());
            pstm.setString(2, utilisateur.getPrenom());
            pstm.setString(3, utilisateur.getEmail());
            pstm.setInt(4, utilisateur.getCin());
            pstm.setString(5, hashedPassword);
            pstm.setString(6, utilisateur.getRole());
            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                utilisateur.setId_user(rs.getInt(1));
            }

            if ("ADMIN".equalsIgnoreCase(utilisateur.getRole())) {
                String qryAdmin = "INSERT INTO admins(id_user) VALUES (?)";
                PreparedStatement pstmAdmin = cnx.prepareStatement(qryAdmin);
                pstmAdmin.setInt(1, utilisateur.getId_user());
                pstmAdmin.executeUpdate();
            } else {
                String qryCitoyen = "INSERT INTO citoyens(id_user) VALUES (?)";
                PreparedStatement pstmCitoyen = cnx.prepareStatement(qryCitoyen);
                pstmCitoyen.setInt(1, utilisateur.getId_user());
                pstmCitoyen.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    public boolean existsByCIN(int cin) {
        String query = "SELECT COUNT(*) FROM utilisateur WHERE cin = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, cin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByEmail(String email) {
        String query = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isValidCIN(int cin) {
        return String.valueOf(cin).matches("\\d{8}");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

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

    public void update(Utilisateur utilisateur) {
        try {
            String oldPassword = getPasswordById(utilisateur.getId_user());
            String newPassword = utilisateur.getMotDePasse();
            String hashedPassword = oldPassword;

            if (!newPassword.equals(oldPassword)) {
                hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
            }

            String qry = "UPDATE utilisateur SET nom=?, prenom=?, email=?, cin=?, motDePasse=?, role=? WHERE id_user=?";
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, utilisateur.getNom());
            pstm.setString(2, utilisateur.getPrenom());
            pstm.setString(3, utilisateur.getEmail());
            pstm.setInt(4, utilisateur.getCin());
            pstm.setString(5, hashedPassword);
            pstm.setString(6, utilisateur.getRole());
            pstm.setInt(7, utilisateur.getId_user());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur mise à jour : " + e.getMessage());
        }
    }

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

    public boolean verifyPassword(String enteredPassword, String storedHashedPassword) {
        return BCrypt.checkpw(enteredPassword, storedHashedPassword);
    }

    @Override
    public void delete(Utilisateur utilisateur) {
        try {
            String qry = "DELETE FROM utilisateur WHERE id_user=?";
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, utilisateur.getId_user());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur suppression : " + e.getMessage());
        }
    }
}
