package tn.esprit.test;

import tn.esprit.models.Admin;
import tn.esprit.models.Citoyen;
import tn.esprit.services.ServiceUtilisateur;
import tn.esprit.models.Utilisateur;

public class Main {
    public static void main(String[] args) {
        ServiceUtilisateur service = new ServiceUtilisateur();

        // Test d'ajout d'un Admin :
        Admin admin = new Admin("John", "Doe", "john.doe@example.com", 123456, "admin123");
        admin.setRole("ADMIN");
        service.add(admin);
        System.out.println("Admin ajouté : " + admin);

        // Test d'ajout d'un Citoyen :
        Citoyen citoyen = new Citoyen("Jane", "Doe", "jane.doe@example.com", 654321, "citoyen123");
        citoyen.setRole("CITOYEN");
        service.add(citoyen);
        System.out.println("Citoyen ajouté : " + citoyen);

        // Affichage de tous les utilisateurs
        System.out.println("\nListe des utilisateurs après ajout :");
        for (Utilisateur utilisateur : service.getAll()) {
            System.out.println(utilisateur);
        }

        // Test de la mise à jour d'un Admin :
        admin.setEmail("john.updated@example.com"); // Mise à jour de l'email
        service.update(admin);
        System.out.println("Admin mis à jour : " + admin);

        // Test de la mise à jour d'un Citoyen :
        citoyen.setEmail("jane.updated@example.com"); // Mise à jour de l'email
        service.update(citoyen);
        System.out.println("Citoyen mis à jour : " + citoyen);
        // Affichage de tous les utilisateurs
        System.out.println("\nListe des utilisateurs après mise à jour :");
        for (Utilisateur utilisateur : service.getAll()) {
            System.out.println(utilisateur);
        }
        // Test de la suppression d'un Admin :
        service.delete(admin.getId_user());
        System.out.println("Admin supprimé : " + admin);

        // Test de la suppression d'un Citoyen :
        service.delete(citoyen.getId_user());
        System.out.println("Citoyen supprimé : " + citoyen);

        // Affichage de tous les utilisateurs
        System.out.println("\nListe des utilisateurs après la suppression :");
        for (Utilisateur utilisateur : service.getAll()) {
            System.out.println(utilisateur);
        }

    }
}
