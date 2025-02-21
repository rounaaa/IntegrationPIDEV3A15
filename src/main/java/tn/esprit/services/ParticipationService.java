package tn.esprit.services;

import tn.esprit.utils.MyDatabase;
import tn.esprit.models.Participation;
import tn.esprit.models.Utilisateur;
import tn.esprit.models.Evenement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipationService {

    private final Connection connection;

    public ParticipationService() {
        this.connection = MyDatabase.getInstance().getCnx();
        if (connection == null) {
            System.err.println("❌ Erreur : Impossible d'obtenir la connexion à la base de données !");
        }
    }

    /**
     * Récupère toutes les participations avec les informations de l'utilisateur et de l'événement.
     */
    public List<Participation> getAll() {
        List<Participation> participations = new ArrayList<>();
        String query = "SELECT p.id_participation, " +
                "u.id AS id_utilisateur, u.nom AS nom_utilisateur, " +
                "e.id_evenement, e.nom_evenement, " +
                "p.date_inscription, p.moyen_paiement, p.motif_annulation " +
                "FROM participation p " +
                "JOIN utilisateur u ON p.id_utilisateur = u.id " +
                "JOIN evenement e ON p.id_evenement = e.id_evenement";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int idParticipation = resultSet.getInt("id_participation");

                // Création de l'objet Utilisateur
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setid_user(resultSet.getInt("id_utilisateur"));
                utilisateur.setNom(resultSet.getString("nom_utilisateur"));

                // Création de l'objet Evenement
                Evenement evenement = new Evenement();
                evenement.setId_evenement(resultSet.getInt("id_evenement"));
                evenement.setNom_evenement(resultSet.getString("nom_evenement"));

                // Création de l'objet Participation
                Participation participation = new Participation(idParticipation, utilisateur, evenement);
                // Vous pouvez compléter l'objet Participation avec date_inscription, moyen_paiement, motif_annulation si nécessaire

                participations.add(participation);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des participations : " + e.getMessage());
            e.printStackTrace();
        }
        return participations;
    }

    /**
     * Ajoute une participation dans la base de données.
     * La date d'inscription est définie par défaut en base (current_timestamp)
     * et motif_annulation est NULL lors de l'ajout.
     *
     * @param utilisateur   l'utilisateur qui participe
     * @param evenement     l'événement auquel l'utilisateur participe
     * @param moyenPaiement le mode de paiement (par exemple "gratuit", "carte bancaire", "especes", "virement bancaire")
     */
    public void ajouterParticipation(Utilisateur utilisateur, Evenement evenement, String moyenPaiement) {
        String query = "INSERT INTO participation (id_utilisateur, id_evenement, moyen_paiement, motif_annulation) VALUES (?, ?, ?, NULL)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, utilisateur.getid_user());
            ps.setInt(2, evenement.getId_evenement());
            // On suppose ici que le mode de paiement est en minuscules pour correspondre à la valeur en base
            ps.setString(3, moyenPaiement.toLowerCase());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Participation ajoutée avec succès !");
            } else {
                System.out.println("Erreur lors de l'ajout de la participation.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout de la participation : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Annule une participation en mettant à jour le champ motif_annulation.
     *
     * @param idParticipation L'ID de la participation à annuler
     * @param motifAnnulation Le code du motif d'annulation (tel que défini dans ta logique)
     */
    public void annulerParticipation(int idParticipation, String motifAnnulation) {
        String query = "UPDATE participation SET motif_annulation = ? WHERE id_participation = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, Integer.parseInt(motifAnnulation));
            ps.setInt(2, idParticipation);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Participation annulée avec succès.");
            } else {
                System.out.println("Aucune participation trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'annulation de la participation : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
