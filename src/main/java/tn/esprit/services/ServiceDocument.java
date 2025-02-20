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
        String qry = "INSERT INTO `document`(`Titre`, `Status`, `Description`, `Type_Document`, `Date_Creation`, `Date_Modification`, `Path`, `Categorie`, `Auteur`) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, document.getTitre());
            pstm.setString(2, document.getStatus());
            pstm.setString(3, document.getDescription());
            pstm.setString(4, document.getType());
            pstm.setDate(5, new java.sql.Date(document.getDate_creation().getTime()));
            pstm.setDate(6, new java.sql.Date(document.getDate_modification().getTime()));
            pstm.setString(7, document.getPath());
            pstm.setString(8, document.getCategorie());
            pstm.setString(9, document.getAuteur());

            pstm.executeUpdate();

            // Retrieve the generated ID
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                document.setId(rs.getInt(1)); // Update the document ID
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
                d.setId(rs.getInt("id_document"));
                d.setTitre(rs.getString("Titre"));
                d.setStatus(rs.getString("Status"));
                d.setDescription(rs.getString("Description"));
                d.setType(rs.getString("Type_Document"));
                d.setDate_creation(rs.getDate("Date_Creation"));
                d.setDate_modification(rs.getDate("Date_Modification"));
                d.setPath(rs.getString("Path"));
                d.setCategorie(rs.getString("Categorie"));
                d.setAuteur(rs.getString("Auteur"));

                documents.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return documents;
    }

    @Override
    public void update(Document document) {
        String qry = "UPDATE `document` SET `Titre`=?, `Status`=?, `Description`=?, `Type_Document`=?, `Date_Creation`=?, `Date_Modification`=?, `Path`=?, `Categorie`=?, `Auteur`=? WHERE `id_document`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, document.getTitre());
            pstm.setString(2, document.getStatus());
            pstm.setString(3, document.getDescription());
            pstm.setString(4, document.getType());
            pstm.setDate(5, new java.sql.Date(document.getDate_creation().getTime()));
            pstm.setDate(6, new java.sql.Date(document.getDate_modification().getTime()));
            pstm.setString(7, document.getPath());
            pstm.setString(8, document.getCategorie());
            pstm.setString(9, document.getAuteur());
            pstm.setInt(10, document.getId());

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

    // Additional methods for filtering or sorting
    public List<Document> getDocumentsByCategory(String category) {
        List<Document> documents = new ArrayList<>();
        String qry = "SELECT * FROM `document` WHERE `Categorie`=?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, category);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Document d = new Document();
                d.setId(rs.getInt("id_document"));
                d.setTitre(rs.getString("Titre"));
                d.setStatus(rs.getString("Status"));
                d.setDescription(rs.getString("Description"));
                d.setType(rs.getString("Type_Document"));
                d.setDate_creation(rs.getDate("Date_Creation"));
                d.setDate_modification(rs.getDate("Date_Modification"));
                d.setPath(rs.getString("Path"));
                d.setCategorie(rs.getString("Categorie"));
                d.setAuteur(rs.getString("Auteur"));

                documents.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return documents;
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String qry = "SELECT DISTINCT `Categorie` FROM `document`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                categories.add(rs.getString("Categorie"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categories;
    }
}