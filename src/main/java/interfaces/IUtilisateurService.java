package interfaces;


import models.Utilisateur;

import java.util.List;

public interface IUtilisateurService {
    void ajouterUtilisateur(Utilisateur u);
    List<Utilisateur> afficherUtilisateurs();
    void modifierUtilisateur(Utilisateur u);
    void supprimerUtilisateur(int id);
}
