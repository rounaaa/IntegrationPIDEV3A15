package tn.esprit.services;

import tn.esprit.interfaces.IEvenementService;
import tn.esprit.models.Evenement;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvenement implements IEvenementService {
    private Connection cnx;

    public ServiceEvenement() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Evenement evenement) {
        String qry = "INSERT INTO evenement (nom_evenement, description, date_debut, date_fin, lieu, statut, capacite_max, image, id_user) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);

            pstm.setString(1, evenement.getNom_evenement());
            pstm.setString(2, evenement.getDescription());
            pstm.setTimestamp(3, Timestamp.valueOf(evenement.getDate_debut())); // Conversion LocalDateTime → SQL Timestamp
            pstm.setTimestamp(4, Timestamp.valueOf(evenement.getDate_fin()));
            pstm.setString(5, evenement.getLieu());
            pstm.setString(6, evenement.getStatut());
            pstm.setInt(7, evenement.getCapacite_max());
            pstm.setString(8, evenement.getImage());
            pstm.setInt(9, evenement.getId_user());

            pstm.executeUpdate();
            System.out.println("****************Événement ajouté avec succès *******************");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Evenement> getAll() {
        List<Evenement> evenements = new ArrayList<>();
        String qry = "SELECT * FROM evenement";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Evenement e = new Evenement();
                e.setId_evenement(rs.getInt("id_evenement"));
                e.setNom_evenement(rs.getString("nom_evenement"));
                e.setDescription(rs.getString("description"));
                e.setDate_debut(rs.getTimestamp("date_debut").toLocalDateTime()); // Conversion SQL Timestamp → LocalDateTime
                e.setDate_fin(rs.getTimestamp("date_fin").toLocalDateTime());
                e.setLieu(rs.getString("lieu"));
                e.setStatut(rs.getString("statut"));
                e.setCapacite_max(rs.getInt("capacite_max"));
                e.setImage(rs.getString("image"));
                e.setId_user(rs.getInt("id_user"));

                evenements.add(e);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return evenements;
    }

    @Override
    public void modifierEvenement(Evenement evenement) {
        String qry = "UPDATE evenement SET nom_evenement = ?, description = ?, date_debut = ?, date_fin = ?, lieu = ?, statut = ?, capacite_max = ?, image = ? WHERE id_evenement = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, evenement.getNom_evenement());
            pstm.setString(2, evenement.getDescription());
            pstm.setTimestamp(3, Timestamp.valueOf(evenement.getDate_debut()));
            pstm.setTimestamp(4, Timestamp.valueOf(evenement.getDate_fin()));
            pstm.setString(5, evenement.getLieu());
            pstm.setString(6, evenement.getStatut());
            pstm.setInt(7, evenement.getCapacite_max());
            pstm.setString(8, evenement.getImage());
            pstm.setInt(9, evenement.getId_evenement());

            pstm.executeUpdate();
            System.out.println("*Événement mis à jour **");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Evenement getById(int id) {
        String qry = "SELECT * FROM evenement WHERE id_evenement = ?";
        Evenement evenement = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                evenement = new Evenement();
                evenement.setId_evenement(rs.getInt("id_evenement"));
                evenement.setNom_evenement(rs.getString("nom_evenement"));
                evenement.setDescription(rs.getString("description"));
                evenement.setDate_debut(rs.getTimestamp("date_debut").toLocalDateTime());
                evenement.setDate_fin(rs.getTimestamp("date_fin").toLocalDateTime());
                evenement.setLieu(rs.getString("lieu"));
                evenement.setStatut(rs.getString("statut"));
                evenement.setCapacite_max(rs.getInt("capacite_max"));
                evenement.setImage(rs.getString("image"));
                evenement.setId_user(rs.getInt("id_user"));
            }
        } catch (SQLException e) {
            System.out.println("*Erreur lors de la récupération de l'événement  *" + e.getMessage());
        }

        return evenement;
    }

    @Override
    public void supprimerEvenement(int id) {
        String qry = "DELETE FROM evenement WHERE id_evenement = ?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id);
            if (pstm.executeUpdate() > 0) {
                System.out.println("*Événement supprimé avec succès*");
            } else {
                System.out.println("*Aucun événement trouvé avec cet id**");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }
}
