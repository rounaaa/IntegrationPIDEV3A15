package models;

import java.util.Date;

public class Signalement {
    private enum EtatSignalement{
        En_attente,
        Resolu,
        Fraud
    }
    private int id_signalement;
    private Date date_signalement;
    private EtatSignalement etatsignalement;
    private String description;

    public Signalement() {
    }

    public Signalement(int id_signalement, Date date_signalement, EtatSignalement etatsignalement, String description) {
        this.id_signalement = id_signalement;
        this.date_signalement = date_signalement;
        this.etatsignalement = etatsignalement;
        this.description = description;
    }

    public Signalement(Date date_signalement, EtatSignalement etatsignalement, String description) {
        this.date_signalement = date_signalement;
        this.etatsignalement = etatsignalement;
        this.description = description;
    }

    public int getId_signalement() {
        return id_signalement;
    }

    public Date getDate_signalement() {
        return date_signalement;
    }

    public EtatSignalement getEtatsignalement() {
        return etatsignalement;
    }

    public String getDescription() {
        return description;
    }

    public void setId_signalement(int id_signalement) {
        this.id_signalement = id_signalement;
    }

    public void setDate_signalement(Date date_signalement) {
        this.date_signalement = date_signalement;
    }

    public void setEtatsignalement(EtatSignalement etatsignalement) {
        this.etatsignalement = etatsignalement;
    }

    public void setDescription(String description) {
        this.description = description;
    } @Override
    public String toString() {
        return "{Le signalement de date "
                + date_signalement +" indiquant "+description+
        " ,est " + etatsignalement + '\'' +
                "}\n";
    }

}
