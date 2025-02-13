package tn.esprit.interfaces;

import tn.esprit.models.Evenement;
import java.util.List;

public interface IEvenementService {
    void add(Evenement evenement);
    void modifierEvenement(Evenement evenement);
    void supprimerEvenement(int id);
    List<Evenement> getAll();
    Evenement getById(int id);
}
