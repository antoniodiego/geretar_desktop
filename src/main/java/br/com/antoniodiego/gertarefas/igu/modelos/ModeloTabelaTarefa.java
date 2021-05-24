package br.com.antoniodiego.gertarefas.igu.modelos;

import br.com.antoniodiego.gertarefas.pojo.TarefaCoordenada;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Um modelo para exibição e edição de tarefas coordenadas
 *
 * @author Ant?nio Diego- Comp:Ant?nio Diego <your.name at your.org>
 */
public class ModeloTabelaTarefa extends AbstractTableModel {

    //private TarefaComposta tarefa;
    private List<TarefaCoordenada> coords;
    private final boolean exibicao;
    private boolean editando;

    public ModeloTabelaTarefa(boolean  exib){
        this(new ArrayList<TarefaCoordenada>(), exib);
    }
    
    public ModeloTabelaTarefa(List<TarefaCoordenada> tcs, boolean exibicao) {
        // this.tarefa = t;
        this.coords = tcs;
        this.exibicao = exibicao;
    }

    @Override
    public int getRowCount() {
        return coords.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (exibicao) {
            switch (columnIndex) {
                case 0:
                    return Boolean.class;
                case 1:
                    return LocalDateTime.class;
                case 2:
                    return LocalTime.class;
                case 3:
                    return String.class;
                case 4:
                    return String.class;
            }
        } else {
            return String.class;
        }
        return String.class;
    }

    @Override
    //TODO: col coment
    public String getColumnName(int column) {
        if (!exibicao) {
            if (column == 0) {
                return "Descri\u00e7\u00e3o";
            }
        } else {
            switch (column) {
                case 0:
                    return "Feita";
                case 1:
                    return "Data de conclus\u00e3o";
                case 2:
                    return "Hora de conclus\u00e3o";
                case 3:
                    return "Descri\u00e7\u00e3o";
                case 4:
                    return "Coment\u00e1rio";
            }
        }
        return "";
    }

    @Override
    public int getColumnCount() {
        return exibicao ? 5 : 1;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //TODO: Usar ao clic em edit
        return !exibicao || (exibicao && editando);

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //TODO: linha 0 deve ser prin
        //XXX: Parece que pode ser otimiz
        //  TarefaComposta tc = (TarefaComposta) tarefa;

//        if (tc == null) {
//            System.out.println("Sem tar");
//            return null;
//        }
        TarefaCoordenada tf = coords.get(rowIndex);
        if (tf == null) {
         //   System.out.println("Filha li nÃ£o en");
            return null;
        } else {
          //  System.out.println("Tem f");
        }

        Object retorno = null;
        if (!exibicao) {
//Unica col
            retorno = tf.getDescricao();
        } else {
            LocalDateTime dc = tf.getDataConclusao();
          //  System.out.println("dc: " + dc);
            switch (columnIndex) {
                case 0:
                    retorno = tf.isConcluida();
                    break;
                case 1:
                    if (dc == null) {
             //           System.out.println("dc n: " + dc);
                        retorno = null;

                    } else {
                        retorno = dc.toLocalDate();
                    }
                    break;

                case 2:
                    if (dc == null) {
                        System.out.println("dc n: " + dc);
                        retorno = null;

                    } else {
                        retorno = dc.toLocalTime();
                    }

                    break;
                case 3:
                    retorno = tf.getDescricao();
                    break;
                case 4:
                    retorno = tf.getComentario();
                    break;
            }
        }
        return retorno;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex
    ) {
        //OBS: Poderia ser bom salvar no banco durante edi??o
        //     if (tarefa instanceof TarefaComposta) {
        //  TarefaComposta tc = (TarefaComposta) tarefa;
        if (coords.isEmpty()) {
            return;
        }
        TarefaCoordenada f = coords.get(rowIndex);

        if (!exibicao) {
            f.setDescricao((String) aValue);
        } else {
            switch (columnIndex) {
                case 0:
                    f.setConcluida((Boolean) aValue);
                    break;
                case 1:
                    //DeverÃ¡ ser um Date vindo do cell editor
//                        Date nova = (Date) aValue;
//                        Instant t = nova.toInstant();
                    LocalDate ld = (LocalDate) aValue;//t.atZone(ZoneId.systemDefault()).toLocalDate();
                    if (ld == null) {
                        f.setDataConclusao(null);
                        break;
                    }
                    LocalDateTime novaT = f.getDataConclusao() == null ? LocalDateTime.now().with(ld) : f.getDataConclusao().with(ld);
                    f.setDataConclusao(novaT);
                    break;
                case 2:
                    LocalTime te = (LocalTime) aValue;
                    //    Calendar c2 = Calendar.getInstance();
                    // LocalTime elt = ni.atZone(ZoneId.systemDefault()).toLocalTime();
                    if (te == null) {
                        f.setDataConclusao(null);
                        break;
                    }

                    f.setDataConclusao(f.getDataConclusao() == null ? LocalDateTime.from(te) : f.getDataConclusao().with(te));
                    break;
                case 3:
                    f.setDescricao((String) aValue);
                    break;
                case 4:
                    f.setComentario((String) aValue);
                    break;
            }
        }
        fireTableCellUpdated(rowIndex, columnIndex);
        // }
    }

    public List<TarefaCoordenada> getCoords() {
        return coords;
    }

    public void setCoords(List<TarefaCoordenada> coords) {
        this.coords = coords;
        fireTableDataChanged();
    }

//    public void setTarefa(TarefaComposta tarefa) {
//        this.tarefa = tarefa;
//        fireTableDataChanged();
//    }
//
//    public TarefaComposta getTarefa() {
//        return tarefa;
//    }
    public void novaCoordenada() {
        // if (tarefa instanceof TarefaSimples) {
//        if (tarefa.getConteudo() instanceof List) {
//            List tc = (List) tarefa.getConteudo();
//            tc.add(new TarefaSimples());
//        }
        coords.add(new TarefaCoordenada());
        //TODO: simp
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void remove(int idx) {
//        if (tarefa.getTarefasFilhas instanceof List) {
//            List tc = (List) tarefa.getTarefasFilhas();
//            //   tc.add(new TarefaSimples());
            if (coords.isEmpty()) {
                return;
            }
            coords.remove(idx);
            //TODO: Del
            fireTableRowsDeleted(idx, idx);
       // }
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
        //OBS: Achar outra forma cencelar ediçõ 
        fireTableDataChanged();
    }

    public void limpa() {
        this.coords.clear();
        fireTableDataChanged();
    }
}
