/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Angazovanje;

/**
 *
 * @author racunar
 */
public class ModelTabeleAngazovanja extends AbstractTableModel {

    String[] kolone = {"datum", "predmet"};
    List<Angazovanje> lista;

    public ModelTabeleAngazovanja(List<Angazovanje> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Angazovanje a = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return a.getDatum();
            case 1:
                return a.getPredmet().getNaziv();
            default:
                return "NA";
        }
    }

    public List<Angazovanje> getLista() {
        return lista;
    }

    public void dodaj(Angazovanje a) {
        lista.add(a);
        fireTableDataChanged();
    }

    public void obrisi(int selektovaniRed) {
        lista.remove(selektovaniRed);
        fireTableDataChanged();
    }

    public void osveziTabelu() {
        fireTableDataChanged();
    }

    public void azuriraj(Angazovanje a) {
        for (Angazovanje ang : lista) {
            if(ang.getPredmet().equals(a.getPredmet()) && 
                    ang.getProfesor().equals(a.getProfesor())){
                ang.setDatum(a.getDatum());
            }
        }
        fireTableDataChanged();
    }

    
    

}
