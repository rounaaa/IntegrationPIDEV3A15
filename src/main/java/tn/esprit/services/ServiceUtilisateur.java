package tn.esprit.services;

import tn.esprit.models.Utilisateur;
import tn.esprit.utils.MyDatabase; // Assurez-vous d'avoir une classe pour gérer la connexion DB

import java.sql.*;

public class ServiceUtilisateur {

    private Connection cnx;

    public ServiceUtilisateur() {
        // Connexion à la base de données via singleton
        this.cnx = MyDatabase.getInstance().getCnx();
    }

    // Méthode pour récupérer un utilisateur par son ID
    public Utilisateur getById(int id) {
        Utilisateur utilisateur = null;
        String query = "SELECT * FROM utilisateur WHERE id_user = ?"; // Assure-toi que la colonne s'appelle bien 'id_user'

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Création d'une instance de l'utilisateur avec les données récupérées
                    utilisateur = new Utilisateur();
                    utilisateur.setid_user(resultSet.getInt("id_user"));


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }
}
