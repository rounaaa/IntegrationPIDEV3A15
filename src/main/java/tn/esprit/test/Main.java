package tn.esprit.test;

import tn.esprit.models.Station;
import tn.esprit.models.Utilisateur;
import tn.esprit.models.tarifs;
import tn.esprit.services.ServiceStation;

public class Main {

    public static void main(String[] args) {
        ServiceStation serviceStation = new ServiceStation();

        Utilisateur user = ServiceStation.getUtilisateurById(3);
        if (user != null) {
            Station station = new Station(
                    "Station 1",
                    "Emplacement 1",
                    Station.Status.OPEN,
                    user,
                    100,
                    "9:00 - 18:00",
                    "123-456-7890",
                    36.8525,
                    10.1630
            );

            serviceStation.add(station);

            System.out.println(serviceStation.getAll());
        } else {
            System.out.println("Utilisateur non trouvé !");
        }
    }
}
