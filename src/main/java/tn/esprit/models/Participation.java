package tn.esprit.models;

import java.time.LocalDateTime;

public class Participation {
    private int id_participation;
    private Utilisateur utilisateur;   // Remplacé Citoyen par Utilisateur
    private Evenement evenement;         // Instance d'Evenement
    private LocalDateTime dateInscription;
    private String motifAnnulation;
    private String moyenPaiement;        // Stocké sous forme de String

    /**
     * Constructeur complet pour la lecture depuis la base de données.
     *
     * @param id_participation l'identifiant de la participation
     * @param utilisateur      l'utilisateur participant
     * @param evenement        l'événement auquel il participe
     */
    public Participation(int id_participation, Utilisateur utilisateur, Evenement evenement) {
        this.id_participation = id_participation;
        this.utilisateur = utilisateur;
        this.evenement = evenement;
        this.dateInscription = dateInscription;
        this.motifAnnulation = motifAnnulation;
        this.moyenPaiement = moyenPaiement;
    }

    /**
     * Constructeur pour ajouter une nouvelle participation.
     * Le champ motifAnnulation est initialisé à null.
     *
     * @param utilisateur     l'utilisateur participant
     * @param evenement       l'événement auquel il participe
     * @param dateInscription la date d'inscription
     * @param moyenPaiement   le mode de paiement utilisé
     */
    public Participation(Utilisateur utilisateur, Evenement evenement,
                         LocalDateTime dateInscription, String moyenPaiement) {
        this.utilisateur = utilisateur;
        this.evenement = evenement;
        this.dateInscription = dateInscription;
        this.motifAnnulation = null;
        this.moyenPaiement = moyenPaiement;
    }

    public int getId_participation() {
        return id_participation;
    }

    public void setId_participation(int id_participation) {
        this.id_participation = id_participation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getMotifAnnulation() {
        return motifAnnulation;
    }

    public void setMotifAnnulation(String motifAnnulation) {
        this.motifAnnulation = motifAnnulation;
    }

    public String getMoyenPaiement() {
        return moyenPaiement;
    }

    public void setMoyenPaiement(String moyenPaiement) {
        this.moyenPaiement = moyenPaiement;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "id_participation=" + id_participation +
                ", utilisateur=" + (utilisateur != null ? utilisateur.getNom() : "null") +
                ", evenement=" + (evenement != null ? evenement.getId_evenement() : "null") +
                ", dateInscription=" + dateInscription +
                ", motifAnnulation='" + motifAnnulation + '\'' +
                ", moyenPaiement='" + moyenPaiement + '\'' +
                '}';
    }
}
