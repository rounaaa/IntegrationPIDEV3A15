package tn.esprit.test;

<<<<<<< HEAD
import tn.esprit.models.Station;
import tn.esprit.models.Utilisateur;
import tn.esprit.models.tarifs;
import tn.esprit.services.ServiceStation;
=======
import tn.esprit.models.Evenement;
import java.time.LocalDateTime;
import java.time.LocalDate;
>>>>>>> origin/may

public class Main {

    public static void main(String[] args) {
<<<<<<< HEAD
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
=======

        String nom = "Concert Rock";
        String description = "Un concert de rock exceptionnel";
        LocalDateTime date = LocalDate.now().atStartOfDay();
        String lieu = "Stade de France";
        String statut = "actif";
        int capacite = 5000;
        String image = "concert.jpg";
        int idUser = 1;


        Evenement evenement = new Evenement(nom, description, date, lieu, statut, capacite, image, idUser);


        System.out.println(evenement);
>>>>>>> origin/may
    }
}
