/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.forme;


import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Profesor;
import model.Zvanje;

/**
 *
 * @author racunar
 */
public class ModelTabeleZvanje extends AbstractTableModel {

    private Zvanje[] zvanje = Zvanje.values();
    private String[] kolone = {"zvanje", "brojProf"};
    private List<Profesor> lista;

    public ModelTabeleZvanje(List<Profesor> lista) {
        this.lista = lista;
    }
    
    @Override
    public int getRowCount() {
        return zvanje.length;
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
        Zvanje z = zvanje[rowIndex];
        int brojac = 0;
        for (Profesor p : lista) {
            if (z.equals(p.getZvanje())) {
                brojac++;
            }
        }
        if (brojac > 0) {
            switch (columnIndex) {
                case 0:
                    return z;
                case 1:
                    return brojac;
                default:
                    return "NA";
            }
        } else {
            switch (columnIndex) {
                case 0:
                    return null;
                case 1:
                    return null;
                default:
                    return null;

            }

        }
    }
}
