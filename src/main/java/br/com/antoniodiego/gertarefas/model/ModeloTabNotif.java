/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.model;

import br.com.antoniodiego.gertarefas.pojo.Notificacao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class ModeloTabNotif extends AbstractTableModel {

    private List<Notificacao> notif;
    public static final String[] COLUNAS = new String[]{"Tarefa", "Data", "Hora"};

    public ModeloTabNotif() {
        notif = new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        return COLUNAS[column];
    }

    @Override
    public int getRowCount() {
        return notif.size();
    }

    @Override
    public int getColumnCount() {
        return COLUNAS.length;
    }

    public void setNotif(List<Notificacao> notif) {
        this.notif = notif;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Notificacao agL = notif.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return agL.getTarefaMae().getTitulo();
            case 1:
                return agL.getHoraExibicao().toLocalDate();
            case 2:
                return agL.getHoraExibicao().toLocalTime();
            default:
                return null;
        }
    }
}
