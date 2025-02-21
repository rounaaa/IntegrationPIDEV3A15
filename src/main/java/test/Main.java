package test;

import models.Conteneur;
import services.ServiceConteneur;
import utils.MyDatabase;
public class Main {


    public static void main(String[] args) {
        ServiceConteneur sc = new ServiceConteneur();
        Conteneur c = new Conteneur("bouhnach", Conteneur.Etat.Vide, (float) 58.50, true, (float) 0.25);

        sc.add(c);
        System.out.println(sc.getAll());

        sc.delete("big aafif, Tunis");
        c.setE(Conteneur.Etat.Pleine);
        c.setAvec_capteur(true);
        c.setNiv_remplissage((float) 0.8);
        sc.update(c);
    }

    }

