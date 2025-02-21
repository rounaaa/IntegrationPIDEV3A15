package services;

import models.Response;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResponseService {
    private final Connection conn;

    public ResponseService() {
        conn = MyDatabase.getInstance().getCnx();
    }

    public void addResponse(Response response) {
        String query = "INSERT INTO response (id_reclamation, response_text) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, response.getIdReclamation());
            stmt.setString(2, response.getResponseText());
            stmt.executeUpdate();
            System.out.println("✅ Réponse ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteResponseByReclamationId(int reclamationId) {
        String query = "DELETE FROM response WHERE id_reclamation = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, reclamationId);
            stmt.executeUpdate();
            System.out.println("✅ Réponse supprimée !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
