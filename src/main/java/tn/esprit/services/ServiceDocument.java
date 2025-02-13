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
        String qry = "INSERT INTO `document`(`Titre`, `Description`, `Type_Document`, `Date_Creation`, `Date_Modification`, `Status`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, document.getTitre());
            pstm.setString(2, document.getDescription());
            pstm.setString(3, document.getType_document());
            pstm.setDate(4, new java.sql.Date(document.getDate_creation().getTime()));
            pstm.setDate(5, new java.sql.Date(document.getDate_modification().getTime()));
            pstm.setString(6, document.getStatus());

            pstm.executeUpdate();

            // Récupérer l'ID généré
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                document.setId(rs.getInt(1)); // Mettre à jour l'ID du document
            }
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
                d.setId(rs.getInt("id_Document"));
                d.setTitre(rs.getString("Titre"));
                d.setDescription(rs.getString("Description"));
                d.setType_document(rs.getString("Type_Document"));
                d.setDate_creation(rs.getDate("Date_Creation"));
                d.setDate_modification(rs.getDate("Date_Modification"));
                d.setStatus(rs.getString("Status"));

                documents.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return documents;
    }

    @Override
    public void update(Document document) {
        String qry = "UPDATE `document` SET `Titre`=?, `Description`=?, `Type_Document`=?, `Date_Creation`=?, `Date_Modification`=?, `Status`=? WHERE `id_Document`=?";
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
        String qry = "DELETE FROM `document` WHERE `id_Document`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, document.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}