 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import model.Profesor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Angazovanje;
import model.Predmet;
import model.Zvanje;

/**
 *
 * @author racunar
 */
public class DBBroker {

    public Profesor unesiProfesora(Profesor profesor) {
        try {
            String upit = "INSERT INTO profesor(ime, prezime, zvanje, email) values (?,?,?,?)";
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, profesor.getIme());
            ps.setString(2, profesor.getPrezime());
            ps.setString(3, profesor.getZvanje().toString());
            ps.setString(4, profesor.getEmail());

            ps.executeUpdate();
            Konekcija.getInstance().getConnection().commit();

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                int noviId = generatedKeys.getInt(1);
                profesor.setId(noviId);
                return profesor;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean unesiPredmet(Predmet predmet) {
        try {
            String upit = "INSERT INTO predmet(naziv, kod, esp) values (?,?,?)";
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);

            ps.setString(1, predmet.getNaziv());
            ps.setString(2, predmet.getKod());
            ps.setInt(3, predmet.getEsp());

            int brojRedova = ps.executeUpdate();
            Konekcija.getInstance().getConnection().commit();
            return brojRedova > 0;

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public List<Profesor> ucitajProf() {
        List<Profesor> lista = new ArrayList<>();

        try {
            String upit = "SELECT * FROM profesor";
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Profesor p = new Profesor();

                p.setId(rs.getInt("id"));
                p.setIme(rs.getString("ime"));
                p.setPrezime(rs.getString("prezime"));
                p.setZvanje(Zvanje.valueOf(rs.getString("zvanje")));
                p.setEmail(rs.getString("email"));

                lista.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    public List<Predmet> ucitajPred() {
        List<Predmet> lista = new ArrayList<>();

        try {
            String upit = "SELECT * FROM predmet";
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Predmet p = new Predmet();

                p.setSifra(rs.getInt("id"));
                p.setNaziv(rs.getString("naziv"));
                p.setKod(rs.getString("kod"));
                p.setEsp(rs.getInt("esp"));

                lista.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    public List<Angazovanje> ucitajAng(Profesor profesor) {
        List<Angazovanje> lista = new ArrayList<>();

        try {
            String upit = "SELECT * FROM angazovanje a JOIN predmet pred ON a.predmet=pred.id WHERE a.profesor=" + profesor.getId();
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Predmet pred = new Predmet();

                pred.setSifra(rs.getInt("id"));
                pred.setNaziv(rs.getString("naziv"));
                pred.setKod(rs.getString("kod"));
                pred.setEsp(rs.getInt("esp"));

                Angazovanje a = new Angazovanje();
                a.setId(rs.getInt("id"));

                java.sql.Date datumSQL = rs.getDate("datum");
                java.util.Date datumUtil = new java.util.Date(datumSQL.getTime());

                a.setDatum(datumUtil);
                a.setPredmet(pred);
                a.setProfesor(profesor);
                a.setEmail(rs.getString("email"));

                lista.add(a);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    public boolean sacuvajAng(List<Angazovanje> lista) {
        //svejedno ciji id uzmemo jer ovo radimo samo za jednog istog prof
        Profesor p = lista.get(0).getProfesor();

        try {
            String upit1 = "DELETE FROM angazovanje WHERE profesor=" + p.getId();
            Statement st1 = Konekcija.getInstance().getConnection().createStatement();
            st1.executeUpdate(upit1);

            String upit2 = "INSERT INTO angazovanje(datum, profesor, predmet, email) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit2);

            for (Angazovanje a : lista) {
                java.util.Date datumUtil = a.getDatum();
                java.sql.Date datumSQL = new java.sql.Date(datumUtil.getTime());
                ps.setDate(1, datumSQL);
                ps.setInt(2, p.getId());
                ps.setInt(3, a.getPredmet().getSifra());
                ps.setString(4, a.getEmail());
                
                ps.addBatch();
            }
            
            ps.executeBatch();
            
            Konekcija.getInstance().getConnection().commit();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
