/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.telas.dialdet;

import br.com.antoniodiego.gertarefas.pojo.Reflexao;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class ModeloTabRefl extends AbstractTableModel {

    private static final String[] COLUNAS = new String[]{"Data", "Hora", "Reflexão"};

    private List<Reflexao> ref;

    public ModeloTabRefl() {
        ref = new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        return COLUNAS[column];
    }

    @Override
    public int getRowCount() {
        return ref.size();
    }

    @Override
    public int getColumnCount() {
        return COLUNAS.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        System.out.println("getVAt");
        Reflexao refL = ref.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return refL.getDataCriacao();
            case 1:
                return refL.getHora();
            case 2:
                return refL.getTexto();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Reflexao refL = ref.get(rowIndex);
        switch (columnIndex) {
            case 0:
                refL.setDataCriacao((LocalDate) aValue);
            case 1:
                refL.setHora((LocalTime) aValue);
            case 2:
                refL.setTexto((String) aValue);
        }
    }

    public void setRef(List<Reflexao> ref) {
        this.ref = ref;
        fireTableDataChanged();
    }

    public void notificaAdic() {
        int posU =ref.size()-1;
        fireTableRowsInserted(posU,posU);
    }

     public void notificaRem(int idx) {
         fireTableRowsDeleted(idx, idx);
    }
     
    public List<Reflexao> getRef() {
        return ref;
    }

}
