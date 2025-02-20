package tn.esprit.interfaces;

import tn.esprit.models.Participation;
import java.util.List;

public interface IParticipationService {
    void ajouterParticipation(Participation participation);
    void annulerParticipation(int idParticipation, String motifAnnulation);
    List<Participation> getParticipationsParCitoyen(int idUser);
}
