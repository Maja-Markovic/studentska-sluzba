/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Profesor;

/**
 *
 * @author racunar
 */
public class ModelTabeleProfesor extends AbstractTableModel {

    String[] kolone = {"id", "ime", "prezime", "zvanje", "email"};
    List<Profesor> lista;

    public ModelTabeleProfesor() {
    }

    public ModelTabeleProfesor(List<Profesor> lista) {
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
        Profesor p = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getId();
            case 1:
                return p.getIme();
            case 2:
                return p.getPrezime();
            case 3:
                return p.getZvanje().toString();
            case 4:
                return p.getEmail();
            default:
                return "NA";
        }
    }

    public List<Profesor> getLista() {
        return lista;
    }
    
    

}
