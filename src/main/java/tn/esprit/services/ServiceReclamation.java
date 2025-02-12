package tn.esprit.services;

import tn.esprit.interfaces.IReclamationService;
import tn.esprit.models.Reclamation;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements IReclamationService<Reclamation> {
    private Connection cnx;

    public ServiceReclamation() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Reclamation reclamation) {
        String qry = "INSERT INTO reclamation (sujet, status, id_user, date) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, reclamation.getSujet());
            pstm.setString(2, reclamation.getStatus());
            pstm.setInt(3, reclamation.getId_user());
            pstm.setDate(4, Date.valueOf(LocalDate.now())); // Conversion LocalDate → SQL Date

            pstm.executeUpdate();
            System.out.println("****************Réclamation ajoutée avec succès *******************");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





    @Override
    public List<Reclamation> getAll() {
        List<Reclamation> reclamations = new ArrayList<>();
        String qry = "SELECT * FROM reclamation";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt("id"));
                r.setSujet(rs.getString("sujet"));
                r.setStatus(rs.getString("status"));
                r.setId_user(rs.getInt("id_user"));
                r.setDate(rs.getDate("date").toLocalDate()); // Conversion SQL Date → LocalDate

                reclamations.add(r);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reclamations;
    }





    @Override
    public void modifierReclamation(Reclamation reclamation) {
        String qry = "UPDATE reclamation SET sujet = ?, status = ?, date = ? WHERE id = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, reclamation.getSujet());
            pstm.setString(2, reclamation.getStatus());
            pstm.setDate(3, Date.valueOf(reclamation.getDate())); // Mise à jour de la date
            pstm.setInt(4, reclamation.getId());

            pstm.executeUpdate();
            System.out.println("************************Réclamation mise à jour ************************");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }






    @Override
    public Reclamation getById(int id) {
        String qry = "SELECT * FROM reclamation WHERE id = ?";
        Reclamation reclamation = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);  // Utilisation de l'id pour sélectionner la réclamation
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                // Récupérer les valeurs de la réclamation
                reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setSujet(rs.getString("sujet"));
                reclamation.setStatus(rs.getString("status"));
                reclamation.setDate(rs.getDate("date").toLocalDate());// Conversion de Date SQL à LocalDate
                reclamation.setId_user(rs.getInt("id_user"));
            }
        } catch (SQLException e) {
            System.out.println("******Erreur lors de la récupération de la réclamation  ****" + e.getMessage());
        }

        return reclamation;  // Retourne la réclamation trouvée, ou null si non trouvée
    }


    @Override
    public void supprimerReclamation(int id) {
        String qry = "DELETE FROM reclamation WHERE id = ?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id);
            if (pstm.executeUpdate() > 0) {
                System.out.println("****************Réclamation supprimée avec succès****************");
            } else {
                System.out.println("**************Aucune réclamation trouvée avec cet id**************");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }


}

