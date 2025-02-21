package services;

import models.Reclamation;
import models.ReclamationStatus;
import models.ReclamationType;
import models.Response;
import models.Utilisateur;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReclamationService {
    private final Connection conn;

    public ReclamationService() {
        conn = MyDatabase.getInstance().getCnx();
    }

    public void ajouterReclamation(Reclamation reclamation) {
        String insertQuery = "INSERT INTO reclamation (type, description, status, image_path, date_creation, id_user) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, reclamation.getType().name());
            stmt.setString(2, reclamation.getDescription());
            stmt.setString(3, reclamation.getStatus().name());
            stmt.setString(4, reclamation.getImagePath());
            stmt.setObject(5, reclamation.getDateCreation());
            stmt.setInt(6, reclamation.getUtilisateur().getIdUser());

            stmt.executeUpdate();
            System.out.println("Réclamation ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scheduleStatusUpdate(reclamation.getId());

    }


    private void scheduleStatusUpdate(int reclamationId) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                modifierStatut(reclamationId, ReclamationStatus.IN_PROGRESS);
            }
        }, 9 * 1000);
    }

    public void modifierStatut(int id, ReclamationStatus newStatus) {
        String updateQuery = "UPDATE reclamation SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, newStatus.name());
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println(" Réclamation mise à jour: " + newStatus);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reclamation> getAllReclamations() {
        List<Reclamation> list = new ArrayList<>();
        String query = """
            SELECT r.*, u.nom, u.prenom, resp.id AS response_id, resp.response_text, resp.date_response
            FROM reclamation r
            JOIN utilisateur u ON r.id_user = u.id_user
            LEFT JOIN response resp ON r.id = resp.id_reclamation
            ORDER BY r.date_creation ASC 
        """;

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int reclamationId = rs.getInt("id");
                Reclamation existingReclamation = list.stream()
                        .filter(r -> r.getId() == reclamationId)
                        .findFirst()
                        .orElse(null);

                if (existingReclamation == null) {
                    Utilisateur utilisateur = new Utilisateur(
                            rs.getInt("id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            "", "", "", "citoyen"
                    );

                    existingReclamation = new Reclamation(
                            ReclamationType.valueOf(rs.getString("type")),
                            rs.getString("description"),
                            rs.getString("image_path"),
                            utilisateur
                    );
                    existingReclamation.setId(reclamationId);
                    existingReclamation.setStatus(ReclamationStatus.valueOf(rs.getString("status")));

                    list.add(existingReclamation);
                }

                if (rs.getInt("response_id") != 0) {
                    Response response = new Response(
                            reclamationId,
                            rs.getString("response_text")
                    );
                    response.setId(rs.getInt("response_id"));
                    response.setDateResponse(rs.getTimestamp("date_response").toLocalDateTime());
                    existingReclamation.getResponses().add(response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void updateReclamationStatus(int id, ReclamationStatus newStatus) {
        String query = "UPDATE reclamation SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus.name());
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Réclamation mise à jour: " + newStatus);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Reclamation> getReclamationsByUserId(int userId) {
        List<Reclamation> list = new ArrayList<>();
        String query = "SELECT r.*, u.nom, u.prenom FROM reclamation r JOIN utilisateur u ON r.id_user = u.id_user WHERE r.id_user = ? ORDER BY r.date_creation DESC";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        "", "", "", "citoyen"
                );

                Reclamation reclamation = new Reclamation(
                        ReclamationType.valueOf(rs.getString("type")),
                        rs.getString("description"),
                        rs.getString("image_path"),
                        utilisateur
                );
                reclamation.setId(rs.getInt("id"));
                reclamation.setStatus(ReclamationStatus.valueOf(rs.getString("status")));

                list.add(reclamation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateReclamation(Reclamation reclamation) {
        String query = "UPDATE reclamation SET type = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, reclamation.getType().name());
            stmt.setString(2, reclamation.getDescription());
            stmt.setInt(3, reclamation.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void supprimerReclamation(int id) {
        String query = "DELETE FROM reclamation WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Réclamation supprimée !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
