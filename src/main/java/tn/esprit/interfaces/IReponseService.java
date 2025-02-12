package tn.esprit.interfaces;

import tn.esprit.models.Reponse;

import java.util.List;

public interface IReponseService<R> {
    void add(Reponse reponse);
    void modifierReponse(Reponse reponse);
    void supprimerReponse(int id);
    List<Reponse> getAll();
    Reponse getById(int id);
}