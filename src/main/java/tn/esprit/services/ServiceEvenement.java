package tn.esprit.services;

import tn.esprit.interfaces.IEvenementService;
import tn.esprit.models.Evenement;
import tn.esprit.models.Utilisateur;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ServiceEvenement implements IEvenementService {
    private Connection cnx;

    public ServiceEvenement() {
        try {
            cnx = MyDatabase.getInstance().getCnx();
            if (cnx == null || cnx.isClosed()) {
                throw new SQLException("La connexion à la base de données a échoué.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'établissement de la connexion : " + e.getMessage());
        }
    }

    @Override
    public void add(Evenement evenement) {
        String qry = "INSERT INTO evenement (nom_evenement, description, date, lieu, statut, capacite_max, image, id_user) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, evenement.getNom_evenement());
            pstm.setString(2, evenement.getDescription());
            pstm.setTimestamp(3, Timestamp.valueOf(evenement.getDate())); // Conversion LocalDateTime → SQL Timestamp
            pstm.setString(4, evenement.getLieu());
            pstm.setString(5, evenement.getStatut());
            pstm.setInt(6, evenement.getCapacite_max());
            pstm.setString(7, evenement.getImage());
            pstm.setInt(8, evenement.getUtilisateur().getid_user()); // Liaison avec l'ID de l'utilisateur

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int lastInsertedId = generatedKeys.getInt(1);
                        System.out.println("Événement ajouté avec succès, ID généré : " + lastInsertedId);
                    }
                }
            }

            // Commit si le mode auto-commit est désactivé
            if (!cnx.getAutoCommit()) {
                cnx.commit();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'événement : " + e.getMessage());
            try {
                if (!cnx.getAutoCommit()) {
                    cnx.rollback();
                }
            } catch (SQLException rollbackException) {
                System.out.println("Erreur lors du rollback : " + rollbackException.getMessage());
            }
        }
    }

    @Override
    public List<Evenement> getAll() {
        List<Evenement> evenements = new ArrayList<>();
        String qry = "SELECT * FROM evenement";

        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(qry)) {

            while (rs.next()) {
                Evenement e = new Evenement();
                e.setId_evenement(rs.getInt("id_evenement"));
                e.setNom_evenement(rs.getString("nom_evenement"));
                e.setDescription(rs.getString("description"));
                e.setDate(rs.getTimestamp("date").toLocalDateTime()); // Conversion SQL Timestamp → LocalDateTime
                e.setLieu(rs.getString("lieu"));
                e.setStatut(rs.getString("statut"));
                e.setCapacite_max(rs.getInt("capacite_max"));
                e.setImage(rs.getString("image"));

                // Récupérer l'ID utilisateur et construire l'objet Utilisateur
                int idUser = rs.getInt("id_user");
                Utilisateur utilisateur = getUtilisateurById(idUser);
                e.setUtilisateur(utilisateur);

                evenements.add(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return evenements;
    }

    // Méthode privée pour récupérer un Utilisateur à partir de son ID
    private Utilisateur getUtilisateurById(int idUser) {
        Utilisateur utilisateur = null;
        String qry = "SELECT * FROM utilisateur WHERE id_user = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, idUser);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    utilisateur = new Utilisateur();
                    utilisateur.setid_user(rs.getInt("id_user"));
                    // Vous pouvez compléter avec d'autres attributs si nécessaire
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return utilisateur;
    }

    @Override
    public void modifierEvenement(Evenement evenement) {
        String qry = "UPDATE evenement SET nom_evenement = ?, description = ?, date = ?, lieu = ?, statut = ?, capacite_max = ?, image = ?, id_user = ? " +
                "WHERE id_evenement = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, evenement.getNom_evenement());
            pstm.setString(2, evenement.getDescription());
            pstm.setTimestamp(3, Timestamp.valueOf(evenement.getDate()));
            pstm.setString(4, evenement.getLieu());
            pstm.setString(5, evenement.getStatut());
            pstm.setInt(6, evenement.getCapacite_max());
            pstm.setString(7, evenement.getImage());
            pstm.setInt(8, evenement.getUtilisateur().getid_user());
            pstm.setInt(9, evenement.getId_evenement());
            pstm.executeUpdate();
            System.out.println("Événement mis à jour avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'événement : " + e.getMessage());
        }
    }

    @Override
    public Evenement getById(int id) {
        String qry = "SELECT * FROM evenement WHERE id_evenement = ?";
        Evenement evenement = null;
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    evenement = new Evenement();
                    evenement.setId_evenement(rs.getInt("id_evenement"));
                    evenement.setNom_evenement(rs.getString("nom_evenement"));
                    evenement.setDescription(rs.getString("description"));
                    evenement.setDate(rs.getTimestamp("date").toLocalDateTime());
                    evenement.setLieu(rs.getString("lieu"));
                    evenement.setStatut(rs.getString("statut"));
                    evenement.setCapacite_max(rs.getInt("capacite_max"));
                    evenement.setImage(rs.getString("image"));
                    int idUser = rs.getInt("id_user");
                    Utilisateur utilisateur = getUtilisateurById(idUser);
                    evenement.setUtilisateur(utilisateur);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'événement : " + e.getMessage());
        }
        return evenement;
    }

    @Override
    public void supprimerEvenement(int id) {
        String qry = "DELETE FROM evenement WHERE id_evenement = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Événement supprimé avec succès");
            } else {
                System.out.println("Aucun événement trouvé avec cet id");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }
}
