package tn.esprit.interfaces;

import tn.esprit.models.Evenement;
import java.util.List;

public interface IEvenementService {
    void add(Evenement evenement);
    List<Evenement> modifierEvenement();
    void supprimerEvenement(int id);
    List<Evenement> getAll();

    void modifierEvenement(Evenement evenement);

    Evenement getById(int id);

 ;
}
