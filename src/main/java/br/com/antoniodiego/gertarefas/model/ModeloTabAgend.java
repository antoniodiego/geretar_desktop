/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.model;

import br.com.antoniodiego.gertarefas.pojo.Agendamento;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class ModeloTabAgend extends AbstractTableModel {

    private List<Agendamento> ag;
    public static final String[] COLUNAS = new String[]{"Tarefa", "Data", "Hora"};

    public ModeloTabAgend() {
        ag = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return ag.size();
    }

    @Override
    public String getColumnName(int column) {
        return COLUNAS[column];
    }

    
    @Override
    public int getColumnCount() {
        return COLUNAS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Agendamento agL = ag.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return agL.getTarefa().getTitulo();
            case 1:
                return agL.getDataAgendamento();
            case 2:
                return agL.getHora();
            default:
                return null;
        }
    }

    public void setAg(List<Agendamento> ag) {
        this.ag = ag;
        fireTableDataChanged();
    }

    public void adicAg(Agendamento age) {
        this.ag.add(age);
        fireTableRowsInserted(ag.size() - 1, ag.size() - 1);
    }
}
