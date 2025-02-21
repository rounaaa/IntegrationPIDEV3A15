package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reclamation {
    private int id;
    private ReclamationType type;
    private String description;
    private ReclamationStatus status;
    private String imagePath;
    private LocalDateTime dateCreation;
    private Utilisateur utilisateur;
    private List<Response> responses;


    public Reclamation(ReclamationType type, String description, String imagePath, Utilisateur utilisateur) {
        this.type = type;
        this.description = description;
        this.status = ReclamationStatus.PENDING;
        this.imagePath = imagePath;
        this.dateCreation = LocalDateTime.now();
        this.utilisateur = utilisateur;
        this.responses = new ArrayList<>();
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public ReclamationType getType() { return type; }
    public void setType(ReclamationType type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ReclamationStatus getStatus() { return status; }
    public void setStatus(ReclamationStatus status) { this.status = status; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    public List<Response> getResponses() { return responses; }
    public void setResponses(List<Response> responses) { this.responses = responses; }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", imagePath='" + imagePath + '\'' +
                ", dateCreation=" + dateCreation +
                ", utilisateur=" + utilisateur.getNom() + " " + utilisateur.getPrenom() +
                ", responses=" + responses +
                '}';
    }
}
