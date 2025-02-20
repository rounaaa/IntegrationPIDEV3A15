package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Station;
import tn.esprit.models.Utilisateur;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceStation implements IService<Station> {

    private static Connection cnx;

    public ServiceStation() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Station station) {
        String qry = "INSERT INTO `stations`(`nom_station`, `emplacement`, `status`, `Capacite_max`, `id_user`, `heures_ouverture`, `contact`, `latitude`, `longitude`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, station.getNom_station());
            pstm.setString(2, station.getEmplacement());
            pstm.setString(3, station.getStatus().getValue());
            pstm.setInt(4, station.getCapaciteMax());
            pstm.setInt(5, station.getUser().getId_user());
            pstm.setString(6, station.getHeuresOuverture());
            pstm.setString(7, station.getContact());
            pstm.setDouble(8, station.getLatitude());
            pstm.setDouble(9, station.getLongitude());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Utilisateur getUtilisateurById(int id_user) {
        Utilisateur utilisateur = null;
        String qry = "SELECT * FROM utilisateur WHERE id_user = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id_user);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId_user(rs.getInt("id_user"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setMotDePasse(rs.getString("motDePasse"));
                utilisateur.setCin(rs.getInt("cin"));
                utilisateur.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return utilisateur;
    }

    @Override
    public List<Station> getAll() {
        List<Station> stations = new ArrayList<>();
        String qry = "SELECT * FROM `stations`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Station station = new Station();
                station.setId_station(rs.getInt("id_station"));
                station.setNom_station(rs.getString("nom_station"));
                station.setEmplacement(rs.getString("emplacement"));
                station.setStatus(rs.getString("status"));
                station.setCapaciteMax(rs.getInt("capacite_max"));
                Utilisateur utilisateur = getUtilisateurById(rs.getInt("id_user"));
                station.setUser(utilisateur);

                station.setHeuresOuverture(rs.getString("heures_ouverture"));
                station.setContact(rs.getString("contact"));
                station.setLatitude(rs.getDouble("latitude"));
                station.setLongitude(rs.getDouble("longitude"));

                stations.add(station);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return stations;
    }

    @Override
    public void update(Station station) {
        String qry = "UPDATE `stations` SET `nom_station`=?, `emplacement`=?, `status`=?, `Capacite_max`=?, `id_user`=?, `heures_ouverture`=?, `contact`=?, `latitude`=?, `longitude`=? WHERE `id_station`=?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, station.getNom_station());
            pstm.setString(2, station.getEmplacement());
            pstm.setString(3, station.getStatus().getValue());
            pstm.setInt(4, station.getCapaciteMax());
            pstm.setInt(5, station.getUser().getId_user());
            pstm.setString(6, station.getHeuresOuverture());
            pstm.setString(7, station.getContact());
            pstm.setDouble(8, station.getLatitude());
            pstm.setDouble(9, station.getLongitude());
            pstm.setInt(10, station.getId_station());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Station station) {
        String qry = "DELETE FROM `stations` WHERE `id_station` = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, station.getId_station());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteID(int stationId) {
        String qry = "DELETE FROM `stations` WHERE `id_station` = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, stationId);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<Utilisateur> getAllUsers() {
        List<Utilisateur> users = new ArrayList<>();
        String query = "SELECT id_user, nom FROM utilisateur";

        try (PreparedStatement stmt = cnx.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_user");
                String nom = rs.getString("nom");
                users.add(new Utilisateur(id, nom));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public Station getStationById(int id) {
        String query = "SELECT * FROM stations WHERE id_station = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nomStation = rs.getString("nom_station");
                String emplacement = rs.getString("emplacement");
                Station.Status status = Station.Status.fromString(rs.getString("status"));
                int capaciteMax = rs.getInt("capacite_max");
                String heuresOuverture = rs.getString("heures_ouverture");
                String contact = rs.getString("contact");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                int userId = rs.getInt("user_id");

                Utilisateur user = getUtilisateurById(userId);

                return new Station(nomStation, emplacement, status, user, capaciteMax, heuresOuverture, contact, latitude, longitude);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}

