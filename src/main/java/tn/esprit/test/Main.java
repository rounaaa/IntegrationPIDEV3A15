package tn.esprit.test;

import tn.esprit.models.Utilisateur;
import tn.esprit.models.Document;
import tn.esprit.models.DemandeDocument;
import tn.esprit.services.ServiceUtilisateur;
import tn.esprit.services.ServiceDocument;
import tn.esprit.services.ServiceDemandeDocument;

import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServiceUtilisateur su = new ServiceUtilisateur();
        ServiceDocument sd = new ServiceDocument();
        ServiceDemandeDocument sdd = new ServiceDemandeDocument();

        Utilisateur utilisateur = new Utilisateur("Doe", "John", "john.doe@example.com", "password123", "User");
        su.add(utilisateur);

        Document document = new Document("Rapport Annuel", "Rapport financier pour l'année 2023", "PDF", new Date(), new Date(), "valider");
        sd.add(document);

        DemandeDocument demande = new DemandeDocument(document.getId(), "en cours", new Date(), utilisateur.getId_user(), "Besoin urgent");
        sdd.add(demande);

        System.out.println("Utilisateurs : " + su.getAll());
        System.out.println("Documents : " + sd.getAll());
        System.out.println("Demandes de document : " + sdd.getAll());

        scanner.close();
    }
}