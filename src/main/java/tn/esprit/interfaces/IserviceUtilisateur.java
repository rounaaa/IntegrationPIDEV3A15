package tn.esprit.interfaces;

import tn.esprit.models.Utilisateur;
import java.util.List;

public interface IserviceUtilisateur {

    void add(Utilisateur utilisateur);




    Utilisateur getById(int id);


    List<Utilisateur> getAll();
}
