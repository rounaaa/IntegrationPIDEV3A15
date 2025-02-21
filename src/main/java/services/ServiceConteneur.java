package services;
import interfaces.IConteneur;
import models.Conteneur;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class ServiceConteneur implements IConteneur<Conteneur> {
        private Connection cnx ;

        public ServiceConteneur(){
            cnx = MyDatabase.getInstance().getCnx();
        }

        public boolean doesLocalisationExist(String localisation) {
            String qry = "SELECT COUNT(*) FROM conteneur WHERE localisation = ?";
            try {
                PreparedStatement pstm = cnx.prepareStatement(qry);
                pstm.setString(1, localisation);
                ResultSet rs = pstm.executeQuery();

                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Si le résultat est supérieur à 0, la localisation existe déjà.
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la vérification de la localisation : " + e.getMessage());
            }
            return false; // La localisation n'existe pas
        }


        @Override
        public void add(Conteneur conteneur) {
            //create Qry SQL
            //execute Qry
            if (doesLocalisationExist(conteneur.getLocalisation())) {
                System.out.println("La localisation " + conteneur.getLocalisation() + " existe déjà !");
                return;
            }

            String qry ="INSERT INTO `conteneur`(localisation, etat , capacite, avec_capteur , niv_remplissage) VALUES (?,?,?,?,?)";
            try {
                PreparedStatement pstm = cnx.prepareStatement(qry);
                pstm.setString(1,conteneur.getLocalisation());
                pstm.setString(2, conteneur.getE().name());
                pstm.setFloat(3, conteneur.getCapacite());
                pstm.setBoolean(4, conteneur.isAvec_capteur());
                pstm.setFloat(5, conteneur.getNiv_remplissage());

                pstm.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

        @Override
        public List<Conteneur> getAll() {
            //create Qry sql
            //execution
            //Mapping data


            List<Conteneur> conteneurs = new ArrayList<>();
            String qry ="SELECT * FROM `conteneur`";

            try {
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(qry);

                while (rs.next()){
                    Conteneur c = new Conteneur();
                    c.setId_conteneurs(rs.getInt("id_conteneur"));
                    c.setLocalisation(rs.getString("localisation"));
                    c.setE(Conteneur.Etat.valueOf(rs.getString("etat")));
                    c.setCapacite(rs.getFloat("capacite"));
                    c.setAvec_capteur(rs.getBoolean("avec_capteur"));
                    c.setNiv_remplissage(rs.getFloat("niv_remplissage"));


                    conteneurs.add(c);
                }



            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return conteneurs;
        }
        @Override
        public void update(Conteneur conteneur) {
            String qry = "UPDATE conteneur SET etat = ?, avec_capteur = ?, niv_remplissage = ? WHERE localisation = ?";

            try {
                PreparedStatement pstm = cnx.prepareStatement(qry);
                pstm.setString(1, conteneur.getE().name());
                pstm.setBoolean(2, conteneur.isAvec_capteur());
                pstm.setFloat(3, conteneur.getNiv_remplissage());
                pstm.setString(4, conteneur.getLocalisation());

                int rowsUpdated = pstm.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Conteneur mis à jour avec succès !");
                } else {
                    System.out.println("Aucun conteneur trouvé avec cette localisation.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            }
        }

        @Override
        public void delete(String localisation) {
            String qry = "DELETE FROM conteneur WHERE localisation = ?";

            try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
                pstm.setString(1, localisation);
                if (pstm.executeUpdate() > 0) {
                    System.out.println("****************conteneur supprimé avec succès****************");
                } else {
                    System.out.println("**************Aucun conteneur trouvée avec cette localisation**************");
                }
            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            }
        }

    }


