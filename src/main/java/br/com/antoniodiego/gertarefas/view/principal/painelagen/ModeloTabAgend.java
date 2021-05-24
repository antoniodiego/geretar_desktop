/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.view.principal.painelagen;

import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class ModeloTabAgend extends AbstractTableModel {

    private static final String[] COLUNAS = new String[]{"Tí­tulo", "Data de criação"};

    private List<Tarefa> tarefas;

    public ModeloTabAgend() {
        tarefas = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return tarefas.size();
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
        Tarefa tarefaLinha = tarefas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tarefaLinha.getTitulo();
            case 1:
                return tarefaLinha.getDataCriacao();
            default:
                //TODO: poderia ser lan uma excessÃ£o
                return null;
        }
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
        fireTableDataChanged();
    }

    public void ordena(Comparator<Tarefa> comp) {
        this.tarefas.sort(comp);
        fireTableDataChanged();
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }
    
}
