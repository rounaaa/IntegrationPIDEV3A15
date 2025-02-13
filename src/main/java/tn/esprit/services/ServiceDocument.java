package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Document;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDocument implements IService<Document> {
    private Connection cnx;

    public ServiceDocument() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Document document) {
        String qry = "INSERT INTO `document`(`titre`, `description`, `type_document`, `date_creation`, `date_modification`, `status`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, document.getTitre());
            pstm.setString(2, document.getDescription());
            pstm.setString(3, document.getType_document());
            pstm.setDate(4, new java.sql.Date(document.getDate_creation().getTime()));
            pstm.setDate(5, new java.sql.Date(document.getDate_modification().getTime()));
            pstm.setString(6, document.getStatus());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Document> getAll() {
        List<Document> documents = new ArrayList<>();
        String qry = "SELECT * FROM `document`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Document d = new Document();
                d.setId(rs.getInt("id_document"));
                d.setTitre(rs.getString("titre"));
                d.setDescription(rs.getString("description"));
                d.setType_document(rs.getString("type_document"));
                d.setDate_creation(rs.getDate("date_creation"));
                d.setDate_modification(rs.getDate("date_modification"));
                d.setStatus(rs.getString("status"));

                documents.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return documents;
    }

    @Override
    public void update(Document document) {
        String qry = "UPDATE `document` SET `titre`=?, `description`=?, `type_document`=?, `date_creation`=?, `date_modification`=?, `status`=? WHERE `id_document`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, document.getTitre());
            pstm.setString(2, document.getDescription());
            pstm.setString(3, document.getType_document());
            pstm.setDate(4, new java.sql.Date(document.getDate_creation().getTime()));
            pstm.setDate(5, new java.sql.Date(document.getDate_modification().getTime()));
            pstm.setString(6, document.getStatus());
            pstm.setInt(7, document.getId());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Document document) {
        String qry = "DELETE FROM `document` WHERE `id_document`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, document.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}