package tn.esprit.test;

import tn.esprit.models.Evenement;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

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
    }
}
