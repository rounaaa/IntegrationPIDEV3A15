package tn.esprit.test;
import tn.esprit.models.Document;
import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceDocument;
import tn.esprit.models.DemandeDocument;
import tn.esprit.services.ServiceDemandeDocument;
import tn.esprit.services.ServiceUtilisateur;

import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /*ServiceDocument sd = new ServiceDocument();
        ServiceDemandeDocument sdd = new ServiceDemandeDocument();

        DemandeDocument demande = new DemandeDocument(1, "accepter", new Date(), 1, "Besoin urgent");
        sdd.add(demande);

        System.out.println(sdd.getAll());

        demande.setStatus("Approuvé");
        sdd.update(demande);

        sdd.delete(demande);
        Document docToUpdate = null;
        for (Document doc : sd.getAll()) {
            if (doc.getId() == 26) {
                docToUpdate = doc;
                break;
            }
        }

        if (docToUpdate != null) {
            docToUpdate.setTitre("Nouveau Titre");

            sd.update(docToUpdate);
            System.out.println("titre document modifié.");
        } else {
            System.out.println("Aucun document trouvé.");
        }

        int idDocumentToDelete = 25;

        Document docToDelete = null;
        for (Document doc : sd.getAll()) {
            if (doc.getId() == idDocumentToDelete) {
                docToDelete = doc;
                break;
            }
        }

        if (docToDelete != null) {
            sd.delete(docToDelete);
            System.out.println("Document " + idDocumentToDelete + " supprimé ");
        } else {
            System.out.println("Aucun document trouvé");
        }*/


        Scanner scanner = new Scanner(System.in);
        ServiceUtilisateur su = new ServiceUtilisateur();
        ServiceDemandeDocument sdd = new ServiceDemandeDocument();
        ServiceDocument sd = new ServiceDocument();

        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();
        System.out.print("Rôle : ");
        String role = scanner.nextLine();

        Utilisateur utilisateur = new Utilisateur(nom, prenom, email, mdp, role);
        su.add(utilisateur);

        System.out.println("Utilisateur créé");

        System.out.print("créer une demande de document ? (oui/non) : ");
        String reponse = scanner.nextLine();

        if (reponse.equalsIgnoreCase("oui")) {
            Document document = new Document("Titre1", "Description1", "PDF", new Date(), new Date(), "Actif");
            sd.add(document);

            int idDocument = document.getId();

            System.out.print("type de document : ");
            String typeDocument = scanner.nextLine();

            System.out.print(" commentaire : ");
            String commentaire = scanner.nextLine();

            DemandeDocument demande = new DemandeDocument(29, "accepter", new Date(), 5, commentaire);
            sdd.add(demande);

            System.out.println("Demande de document créée ");
        } else {
            System.out.println("demande de document non créée.");
        }

        scanner.close();

    }
}

