package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.DemandeDocument;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDemandeDocument implements IService<DemandeDocument> {
    private Connection cnx;

    public ServiceDemandeDocument() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(DemandeDocument demandeDocument) {
        String qry = "INSERT INTO `demande_document`(`id_Document`, `status`, `Date_Demande`, `id_user`, `Commentaire`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, demandeDocument.getId_Document());
            pstm.setString(2, demandeDocument.getStatus());
            pstm.setDate(3, new java.sql.Date(demandeDocument.getDate_Demande().getTime()));
            pstm.setInt(4, demandeDocument.getId_user());
            pstm.setString(5, demandeDocument.getCommentaire());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<DemandeDocument> getAll() {
        List<DemandeDocument> demandes = new ArrayList<>();
        String qry = "SELECT * FROM `demande_document`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                DemandeDocument d = new DemandeDocument();
                d.setId_Demande(rs.getInt("id_Demande"));
                d.setId_Document(rs.getInt("id_Document"));
                d.setStatus(rs.getString("status"));
                d.setDate_Demande(rs.getDate("Date_Demande"));
                d.setId_user(rs.getInt("id_user"));
                d.setCommentaire(rs.getString("Commentaire"));

                demandes.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return demandes;
    }

    @Override
    public void update(DemandeDocument demandeDocument) {
        String qry = "UPDATE `demande_document` SET `id_Document`=?, `status`=?, `Date_Demande`=?, `id_user`=?, `Commentaire`=? WHERE `id_Demande`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, demandeDocument.getId_Document());
            pstm.setString(2, demandeDocument.getStatus());
            pstm.setDate(3, new java.sql.Date(demandeDocument.getDate_Demande().getTime()));
            pstm.setInt(4, demandeDocument.getId_user());
            pstm.setString(5, demandeDocument.getCommentaire());
            pstm.setInt(6, demandeDocument.getId_Demande());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(DemandeDocument demandeDocument) {
        String qry = "DELETE FROM `demande_document` WHERE `id_Demande`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, demandeDocument.getId_Demande());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}