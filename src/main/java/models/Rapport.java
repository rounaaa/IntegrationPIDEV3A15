package models;

import java.util.Date;

public class Rapport {
    private int id_rap;
    private Date date_rapport;
    private String conclusion;
    private boolean penaliteapp;
    private float montant;

    public Rapport() {
    }

    public Rapport(int id_rap, Date date_rapport, String conclusion, boolean penaliteapp, float montant) {
        this.id_rap = id_rap;
        this.date_rapport = date_rapport;
        this.conclusion = conclusion;
        this.penaliteapp = penaliteapp;
        this.montant = montant;
    }

    public Rapport(Date date_rapport, String conclusion, boolean penaliteapp, float montant) {
        this.date_rapport = date_rapport;
        this.conclusion = conclusion;
        this.penaliteapp = penaliteapp;
        this.montant = montant;
    }

    public int getId_rap() {
        return id_rap;
    }

    public Date getDate_rapport() {
        return date_rapport;
    }

    public String getConclusion() {
        return conclusion;
    }

    public boolean isPenaliteapp() {
        return penaliteapp;
    }

    public float getMontant() {
        return montant;
    }

    public void setId_rap(int id_rap) {
        this.id_rap = id_rap;
    }

    public void setDate_rapport(Date date_rapport) {
        this.date_rapport = date_rapport;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public void setPenaliteapp(boolean penaliteapp) {
        this.penaliteapp = penaliteapp;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

}
