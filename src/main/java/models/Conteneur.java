package models;

public class Conteneur {

        public enum Etat{
            Vide,
            Pleine
        }
        private int id_conteneurs;
        private String localisation;
        private float capacite;
        private Etat etat;
        private boolean avec_capteur;
        private float niv_remplissage;

        public Conteneur() {
        }

        public Conteneur(int id_conteneurs, String localisation,  Etat etat, float capacite, boolean avec_capteur, float niv_remplissage) {
            this.id_conteneurs = id_conteneurs;
            this.localisation = localisation;
            this.etat = etat;
            this.capacite = capacite;
            this.avec_capteur = avec_capteur;
            this.niv_remplissage = niv_remplissage;
        }

        public Conteneur(String localisation, Etat etat, float capacite, boolean avec_capteur, float niv_remplissage) {
            this.localisation = localisation;
            this.etat = (etat != null) ? etat : Etat.Vide;
            this.capacite = capacite;
            this.avec_capteur = avec_capteur;
            this.niv_remplissage = niv_remplissage;
        }

        public int getId_conteneurs() {
            return id_conteneurs;
        }

        public String getLocalisation() {
            return localisation;
        }

        public float getCapacite() {
            return capacite;
        }

        public Etat getE() {
            return etat;
        }

        public boolean isAvec_capteur() {
            return avec_capteur;
        }

        public float getNiv_remplissage() {
            return niv_remplissage;
        }

        public void setId_conteneurs(int id_conteneurs) {
            this.id_conteneurs = id_conteneurs;
        }

        public void setLocalisation(String localisation) {
            this.localisation = localisation;
        }

        public void setCapacite(float capacite) {
            this.capacite = capacite;
        }

        public void setE(Etat etat) {
            this.etat = etat;
        }

        public void setAvec_capteur(boolean avec_capteur) {
            this.avec_capteur = avec_capteur;
        }

        public void setNiv_remplissage(float niv_remplissage) {
            this.niv_remplissage = niv_remplissage;
        }

        @Override
        public String toString() {
            return "{Le conteneur qui se trouve dans "
                    + localisation +
                    " ,est " + etat + '\'' +
                    "}\n";
        }
    }



