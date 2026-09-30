/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import baza.DBBroker;
import java.util.ArrayList;
import java.util.List;
import model.Angazovanje;
import model.Korisnik;
import model.Predmet;
import model.Profesor;

/**
 *
 * @author racunar
 */
public class Controller {

    private List<Korisnik> korisnici = new ArrayList<>();
    private static Controller instance;
    private DBBroker dbb;

    private Controller() {

        Korisnik k1 = new Korisnik(1, "Vanja", "Vizi", "vanja@gmail.com", "vanja");
        Korisnik k2 = new Korisnik(2, "Maja", "Markovic", "maja@gmail.com", "maja");
        Korisnik k3 = new Korisnik(3, "Dunja", "Nesic", "dunja@gmail.com", "dunja");
        Korisnik k4 = new Korisnik(4, "Marko", "Sljivic", "marko@gmail.com", "marko");
        Korisnik k5 = new Korisnik(5, "Rasta", "Djelic", "rasta@gmail.com", "rasta");

        korisnici.add(k1);
        korisnici.add(k2);
        korisnici.add(k3);
        korisnici.add(k4);
        korisnici.add(k5);

        dbb = new DBBroker();

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public Korisnik login(Korisnik korisnik) {
        for (Korisnik k : korisnici) {
            if (k.equals(korisnik)) {
                return k;
            }

        }
        return null;
    }

    public Profesor unesiProfesora(Profesor profesor) {
        return dbb.unesiProfesora(profesor);
    }

    public boolean unesiPredmet(Predmet predmet) {
        return dbb.unesiPredmet(predmet);
    }

    public List<Profesor> ucitajProf() {
        return dbb.ucitajProf();
    }

    public List<Predmet> ucitajPred() {
        return dbb.ucitajPred();
    }

    public List<Angazovanje> ucitajAng(Profesor profesor) {
        return dbb.ucitajAng(profesor);
    }

    public boolean sacuvajAng(List<Angazovanje> lista) {
        return dbb.sacuvajAng(lista);
    }

}
