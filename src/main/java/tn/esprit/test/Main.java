package tn.esprit.test;

import tn.esprit.models.Reclamation;
import tn.esprit.services.ServiceReclamation;
import tn.esprit.models.Reponse;
import tn.esprit.services.ServiceReponse;
import java.time.LocalDate; // Import de LocalDate

public class Main {

    public static void main(String[] args) {
        ServiceReclamation sr = new ServiceReclamation();



        Reclamation rec = new Reclamation("Zebla barcha", "attente", 123, LocalDate.of(2025, 10, 25));
        Reclamation op = new Reclamation("thaww", "traiter", 123, LocalDate.of(2025, 10, 25));
        Reclamation mp = new Reclamation("kayes", "attente", 123, LocalDate.of(2025, 10, 25));

        sr.add(rec);


        System.out.println("************************Liste des réclamations :*********************");
        System.out.println(sr.getAll());


        rec.setId(97688693);
        rec.setStatus("non traiter");
        sr.modifierReclamation(rec);
        System.out.println(sr.getById(97688693));



     sr.supprimerReclamation(97688693);


        ServiceReponse sk = new ServiceReponse();

        Reponse reponse = new Reponse(97688681, 123, "Votre réclamation a été traitée.", LocalDate.now(), "envoyer");
        sk.add(reponse);


        System.out.println("Liste des réponses :");
        System.out.println(sk.getAll());


        reponse.setid_reponse(2);
        reponse.setContenu("bara zamer");
        sk.modifierReponse(reponse);
        System.out.println(sk.getById(2));


       // sk.supprimerReponse(1);



    }
}
