/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.telas.dialogos.editartarefa;

import br.com.antoniodiego.gertarefas.pojo.Relatorio;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class ModeloTabRel extends AbstractTableModel {

    private static final String[] COLUNAS = new String[]{"Data", "Hora", "Relato"};

    private List<Relatorio> relatorios;

    public ModeloTabRel() {
        relatorios = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return relatorios.size();
    }

    @Override
    public int getColumnCount() {
        return COLUNAS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUNAS[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Relatorio refL = relatorios.get(rowIndex);
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
        Relatorio refL = relatorios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                refL.setDataCriacao((LocalDate) aValue);
            case 1:
                refL.setHora((LocalTime) aValue);
            case 2:
                refL.setTexto((String) aValue);
        }
    }

    public void setRelatorios(List<Relatorio> relatorios) {
        this.relatorios = relatorios;
        fireTableDataChanged();
    }

    public List<Relatorio> getRelatorios() {
        return relatorios;
    }

    
     public void notificaRem(int idx) {
         fireTableRowsDeleted(idx, idx);
    }
    public void notificaAdic() {
        int posU =relatorios.size()-1;
        fireTableRowsInserted(posU,posU);
    }
}
