package tn.esprit.test;

import tn.esprit.models.Borne_Pompe;
import tn.esprit.models.Station;

import tn.esprit.services.ServiceStation;
import tn.esprit.services.ServiceBorne;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import tn.esprit.models.Borne_Pompe;
import tn.esprit.models.Station;
import tn.esprit.models.Utilisateur;
import tn.esprit.models.tarifs;
import tn.esprit.models.Borne_Pompe.Etat;
import tn.esprit.models.Borne_Pompe.Type;
import tn.esprit.models.Station.Status;
import tn.esprit.services.ServiceBorne;
import tn.esprit.services.ServiceStation;

public class Main1 {
    public Main1() {
    }

    public static void main(String[] args) {
        ServiceStation s = new ServiceStation();
        Utilisateur user = new Utilisateur(1, "raouen", "maknine ", "raouen@gmail.com", "125478");
        s.add(new Station("RRR", "Ariana", Status.CLOSED, user, 100, "8h", "55680729", 36.258, 10.182));
        Station station = new Station();
        station.setId_station(9);
        ServiceBorne serviceBorne = new ServiceBorne();
        tarifs tarifStandard = serviceBorne.getTarifById(3);
        Borne_Pompe borne = new Borne_Pompe();
        borne.setType(Type.FAST);
        borne.setEtat(Etat.ACTV);
        borne.setPuissance_kW(22.5);
        borne.setConnecteur_type("Type1");
        borne.setDisponibilile(true);
        borne.setEnergie_consommee(30.0);
        borne.setDernier_utilisateur(user);
        borne.setStation(station);
        borne.setTarif(tarifStandard);
        borne.calculerCout();
        serviceBorne.add(borne);
        System.out.println("Coût total : " + borne.getCout() + " TND");
    }
}

