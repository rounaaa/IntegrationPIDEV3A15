package services;

import models.Utilisateur;
import models.Citoyen;
import models.Admin;
import utils.MyDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurService {
    private final Connection conn;

    public UtilisateurService() {
        conn = MyDatabase.getInstance().getCnx();
    }

    // Method to check if email exists
    public boolean emailExists(String email) {
        String checkEmailQuery = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(checkEmailQuery)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void ajouterUtilisateur(Utilisateur user) {
        // Check if email already exists
        if (emailExists(user.getEmail())) {
            System.out.println("Erreur: Cet email est déjà utilisé !");
            return;
        }

        String insertUserQuery = "INSERT INTO utilisateur (nom, prenom, email, cin, mot_de_passe, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertUserQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getCin());
            stmt.setString(5, user.getMotDePasse());
            stmt.setString(6, user.getRole());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insertion de l'utilisateur échouée !");
            }

            var generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);

                if (user instanceof Citoyen) {
                    ajouterCitoyen(userId);
                } else if (user instanceof Admin) {
                    ajouterAdmin(userId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ajouterCitoyen(int userId) throws SQLException {
        String insertCitoyenQuery = "INSERT INTO citoyen (id_user) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertCitoyenQuery)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    private void ajouterAdmin(int userId) throws SQLException {
        String insertAdminQuery = "INSERT INTO admin (id_user) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertAdminQuery)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public Utilisateur getUtilisateurById(int id) {
        String query = "SELECT * FROM utilisateur WHERE id_user = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                if ("citoyen".equals(role)) {
                    return new Citoyen(
                            rs.getInt("id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("cin"),
                            rs.getString("mot_de_passe"),
                            role,
                            getCitoyenId(rs.getInt("id_user"))
                    );
                } else if ("admin".equals(role)) {
                    return new Admin(
                            rs.getInt("id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("cin"),
                            rs.getString("mot_de_passe"),
                            role,
                            getAdminId(rs.getInt("id_user"))
                    );
                } else {
                    return new Utilisateur(
                            rs.getInt("id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("cin"),
                            rs.getString("mot_de_passe"),
                            role
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private int getCitoyenId(int userId) {
        String query = "SELECT id_citoyen FROM citoyen WHERE id_user = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_citoyen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getAdminId(int userId) {
        String query = "SELECT id_admin FROM admin WHERE id_user = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_admin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
