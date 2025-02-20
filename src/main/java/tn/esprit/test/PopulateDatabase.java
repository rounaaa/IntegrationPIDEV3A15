/*package tn.esprit.test;

import com.github.javafaker.Faker;
import tn.esprit.models.DemandeDocument;
import tn.esprit.services.ServiceDemandeDocument;

import java.util.Date;
import java.util.Random;

public class PopulateDatabase {
    public static void main(String[] args) {
        ServiceDemandeDocument service = new ServiceDemandeDocument();
        Faker faker = new Faker();
        Random random = new Random();

        String[] typesDocuments = {"Passport", "Carte d'identité", "Extrait de naissance", "Permis de conduire", "Certificat de résidence"};
        String[] statuses = {"accepté", "rejeté", "en cours de traitement"};

        for (int i = 0; i < 20; i++) {
            String typeDocument = typesDocuments[random.nextInt(typesDocuments.length)];
            String description = faker.lorem().sentence();
            String emailDemandeur = faker.internet().emailAddress();
            int idUtilisateur = random.nextInt(100) + 1; // ID aléatoire entre 1 et 100
            String nomDemandeur = faker.name().fullName();
            String titreDemande = "Demande de " + typeDocument;
            String status = statuses[random.nextInt(statuses.length)];
            Date dateDemande = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS);
            String motifDemande = faker.lorem().sentence();
            String pieceJustif = "piece_justif_" + (i + 1) + ".pdf";

            DemandeDocument demande = new DemandeDocument(
                    typeDocument,
                    description,
                    emailDemandeur,
                    idUtilisateur,
                    nomDemandeur,
                    titreDemande,
                    status,
                    dateDemande,
                    motifDemande,
                    pieceJustif
            );

            service.add(demande);
        }

        System.out.println("20 demandes ont été ajoutées avec succès.");
    }
}
*/