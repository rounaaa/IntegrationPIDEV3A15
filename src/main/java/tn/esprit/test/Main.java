package tn.esprit.test;

import tn.esprit.models.Utilisateur;
import tn.esprit.services.ServiceUtilisateur;

public class Main {
    public static void main(String[] args) {
        ServiceUtilisateur service = new ServiceUtilisateur();

        Utilisateur admin = new Utilisateur("Admin", "System", "admin@example.com", "password", Utilisateur.Role.ADMIN);
        service.add(admin);

        Utilisateur citoyen = new Utilisateur("Jean", "Dupont", "jean@example.com", "password", Utilisateur.Role.CITOYEN);
        service.add(citoyen);

        System.out.println("Liste des utilisateurs après ajout :");
        for (Utilisateur u : service.getAll()) {
            System.out.println(u);
        }

        service.update(admin.getId_user(), "Admin", "System", "admin@example.com", "newpassword", Utilisateur.Role.ADMIN);

        System.out.println("\nListe après modification :");
        for (Utilisateur u : service.getAll()) {
            System.out.println(u);
        }

        service.delete(admin.getId_user());

        System.out.println("\nListe après suppression :");
        for (Utilisateur u : service.getAll()) {
            System.out.println(u);
        }
    }
}
