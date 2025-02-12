package tn.esprit.services;

import tn.esprit.interfaces.IReponseService;
import tn.esprit.models.Reponse;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceReponse implements IReponseService<Reponse> {
    private Connection cnx;

    public ServiceReponse() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Reponse reponse) {
        String qry = "INSERT INTO reponse (id_reclamation, id_user, contenu, Date_Reponse, status) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, reponse.getId_reclamation());
            pstm.setInt(2, reponse.getId_user());
            pstm.setString(3, reponse.getContenu());
            pstm.setDate(4, Date.valueOf(LocalDate.now())); // Conversion LocalDate → SQL Date
            pstm.setString(5, reponse.getStatus());

            pstm.executeUpdate();
            System.out.println("****************Réponse ajoutée avec succès *******************");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reponse> getAll() {
        List<Reponse> reponses = new ArrayList<>();
        String qry = "SELECT * FROM reponse";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Reponse r = new Reponse();
                r.setid_reponse(rs.getInt("id_reponse"));
                r.setId_reclamation(rs.getInt("id_reclamation"));
                r.setId_user(rs.getInt("id_user"));
                r.setContenu(rs.getString("contenu"));
                r.setDate_reponse(rs.getDate("Date_Reponse").toLocalDate()); // Conversion SQL Date → LocalDate
                r.setStatus(rs.getString("status"));

                reponses.add(r);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reponses;
    }

    @Override
    public void modifierReponse(Reponse reponse) {
        String qry = "UPDATE reponse SET contenu = ?, status = ?, Date_Reponse = ? WHERE id_reponse = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, reponse.getContenu());
            pstm.setString(2, reponse.getStatus());
            pstm.setDate(3, Date.valueOf(reponse.getDate_reponse())); // Mise à jour de la date
            pstm.setInt(4, reponse.getid_reponse());

            pstm.executeUpdate();
            System.out.println("************************Réponse mise à jour ************************");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Reponse getById(int id) {
        String qry = "SELECT * FROM reponse WHERE id_reponse = ?";
        Reponse reponse = null;

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                reponse = new Reponse();
                reponse.setid_reponse(rs.getInt("id_reponse"));
                reponse.setId_reclamation(rs.getInt("id_reclamation"));
                reponse.setId_user(rs.getInt("id_user"));
                reponse.setContenu(rs.getString("contenu"));
                reponse.setDate_reponse(rs.getDate("Date_Reponse").toLocalDate());
                reponse.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("******Erreur lors de la récupération de la réponse  ****" + e.getMessage());
        }

        return reponse;  // Retourne la réponse trouvée, ou null si non trouvée
    }

    @Override
    public void supprimerReponse(int id) {
        String qry = "DELETE FROM reponse WHERE id_reponse = ?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id);
            if (pstm.executeUpdate() > 0) {
                System.out.println("****************Réponse supprimée avec succès****************");
            } else {
                System.out.println("**************Aucune réponse trouvée avec cet id**************");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }
}
