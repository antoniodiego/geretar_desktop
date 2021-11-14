package br.com.antoniodiego.gertarefas.model;

import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class ModeloTabelaTarefasLista extends AbstractTableModel {

    /**
     * Colunas da tabela
     */
    private static final String[] COLUNAS = new String[]{"ID", "ID Pers",
        "Título",
        "Data de criação", "Prioridade", "Data fazer", "Data modif",
        "Concluída"};

    public static final Class[] CLASSES_COLUNAS = new Class[]{Long.class,
        Long.class,
        String.class,
        LocalDate.class, Integer.class, LocalDate.class, LocalDateTime.class, Boolean.class};
    private List<Tarefa> tarefas;
    //  private final JanelaPrincipalController contr;
    public static final Boolean[] EDITAVEL = new Boolean[]{false, true, true,
        false,
        true, true, false, true};

    /**
     *
     * @param controller
     */
    public ModeloTabelaTarefasLista() {
        tarefas = new ArrayList<>();
//        //  this.contr = controller;
    }

    @Override
    public int getRowCount() {
        return tarefas.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return CLASSES_COLUNAS[columnIndex];
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
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return EDITAVEL[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tarefa tarefaLinha = tarefas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tarefaLinha.getId();
            case 1:
                return tarefaLinha.getIdPers();
            case 2:
                return tarefaLinha.getTitulo();
            case 3:
                return tarefaLinha.getDataCriacao();
            //  case 3:
            // return tarefaLinha.getDataCriacao();
            case 4:
                return tarefaLinha.getPrioridade();
            case 5:
                return tarefaLinha.getDataFazer();
            case 6:
                return tarefaLinha.getDataModif();
            case 7:
                return tarefaLinha.isConcluida();
            default:
                //TODO: poderia ser lan uma excessão
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Tarefa tarefaLinha = tarefas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                //tarefaLinha.setId((Long) aValue);
                break;
            case 1:
                tarefaLinha.setIdPers((Long) aValue);
                tarefaLinha.setDataModif(LocalDateTime.now());
                break;
            case 2:
                tarefaLinha.setTitulo((String) aValue);
                tarefaLinha.setDataModif(LocalDateTime.now());
                break;
            case 3:

                break;
            case 4:
                tarefaLinha.setPrioridade((Integer) aValue);
                break;
            case 5:
                tarefaLinha.setDataFazer((LocalDate) aValue);
                tarefaLinha.setDataModif(LocalDateTime.now());
                break;
            case 6:
                break;
            case 7:
                tarefaLinha.setConcluida((boolean) aValue);
                tarefaLinha.setDataModif(LocalDateTime.now());
                break;
        }

//        /*Nessa chamada de método a alteração do id pode falhar.
//        
//         */
//        contr.getDaoUsuario().flush();
    }

    public void adicionaTarefa(Tarefa tar) {
        this.tarefas.add(tar);
        fireTableRowsInserted(tarefas.size() - 1, tarefas.size() - 1);
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
