package interfaces;

import java.util.List;

public interface IReclamationService<Reclamation> {
    void add(Reclamation reclamation);
    void modifierReclamation(Reclamation reclamation);
    void supprimerReclamation(int id);
    List<Reclamation> getAll();
    Reclamation getById(int id);


}