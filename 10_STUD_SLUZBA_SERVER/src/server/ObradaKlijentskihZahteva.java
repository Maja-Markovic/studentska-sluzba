/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import controller.Controller;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Angazovanje;
import model.Korisnik;
import model.Predmet;
import model.Profesor;
import operacije.Operacije;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author racunar
 */
public class ObradaKlijentskihZahteva extends Thread {

    private Socket s;

    public ObradaKlijentskihZahteva(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        while (s != null && !s.isClosed()) {
            try {
                KlijentskiZahtev kz = primiZahtev();
                ServerskiOdgovor so = new ServerskiOdgovor();
                switch (kz.getOperacija()) {
                    case Operacije.LOGIN:
                        Korisnik k = Controller.getInstance().login((Korisnik) kz.getParam());
                        so.setOdgovor(k);
                        break;

                    case Operacije.UNESI_PROF:
                        Profesor p = Controller.getInstance().unesiProfesora((Profesor) kz.getParam());
                        so.setOdgovor(p);
                        break;

                    case Operacije.UNESI_PRED:
                        boolean uspesno = Controller.getInstance().unesiPredmet((Predmet) kz.getParam());
                        so.setOdgovor(uspesno);
                        break;

                    case Operacije.UCITAJ_PROF:
                        List<Profesor> lista = Controller.getInstance().ucitajProf();
                        so.setOdgovor(lista);
                        break;

                    case Operacije.UCITAJ_PRED:
                        List<Predmet> pred = Controller.getInstance().ucitajPred();
                        so.setOdgovor(pred);
                        break;

                    case Operacije.UCITAJ_ANG:
                        List<Angazovanje> ang = Controller.getInstance().ucitajAng((Profesor) kz.getParam());
                        so.setOdgovor(ang);
                        break;

                    case Operacije.SACUVAJ_ANG:
                        boolean uspeh = Controller.getInstance().sacuvajAng((List<Angazovanje>) kz.getParam());
                        so.setOdgovor(uspeh);
                        break;

                    default:
                        System.out.println("GRESKA");
                }
                posaljiOdgovor(so);
            } catch (IOException ex) {
                Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private KlijentskiZahtev primiZahtev() throws IOException {
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            return (KlijentskiZahtev) ois.readObject();
        } catch (SocketException ex) {
            s = null;
            s.close();
            System.out.println("KLIJENT SE ODVEZAO");
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void posaljiOdgovor(ServerskiOdgovor so) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(so);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
