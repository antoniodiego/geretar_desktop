package br.com.antoniodiego.gertarefas.model;

import br.com.antoniodiego.gertarefas.persist.daos.DAOTarefa;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import static br.com.antoniodiego.gertarefas.telas.principal.paineis.PainelTabelaTarefas.LOG_PAINEL_T;
import br.com.antoniodiego.gertarefas.util.FuncoesTarefas;
import br.com.antoniodiego.gertarefas.util.Utilid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JTable;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * * Esse modelo é usado para exibir as tarefas na tabela que fica no centro da
 * janela principal
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class ModeloTabelaTarefasLista extends AbstractTableModel {

    private static final Logger LOG_MODELO = LogManager.
            getLogger("log_modelo");

    /**
     * Colunas da tabela
     */
    private static final String[] COLUNAS = new String[]{"ID", "ID Pers",
        "Título",
        "Data de criação", "Prioridade", "Data fazer", "Data modif",
        "Concluída",
        "Posição", "Comentário", "Status"};

    public static final Class[] CLASSES_COLUNAS = new Class[]{Long.class,
        Long.class,
        String.class,
        LocalDate.class, Integer.class, LocalDate.class, LocalDateTime.class,
        Boolean.class, Integer.class, String.class, String.class};

    private List<Tarefa> tarefas;
    //  private final JanelaPrincipalController contr;
    public static final Boolean[] EDITAVEL = new Boolean[]{false, true, true,
        false,
        true, true, false, true, true, true, true};
    private final JTable tabelaTarefas;

    /**
     *
     * @param tabelaTarefas
     */
    public ModeloTabelaTarefasLista(JTable tabelaTarefas) {
        tarefas = new ArrayList<>();
        this.tabelaTarefas = tabelaTarefas;
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
            case 8:
                return tarefaLinha.getPosicao();
            case 9:
                return tarefaLinha.getComentario();
            case 10:
                return tarefaLinha.getStatus();

            default:
                //TODO: poderia ser lan uma excessão
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Tarefa tarefaLinha = tarefas.get(rowIndex);

        LOG_MODELO.debug("Posição: " + tarefaLinha.getPosicao());
//
//        Session s = DAO.getSession();
//
//        s.getTransaction().begin();
//        Tarefa tC = s.get(Tarefa.class, tarefaLinha.getId());
//        s.getTransaction().commit();
//
//        tarefaLinha = tC;
//
//        LOG_MODELO.debug("Posição carregada: " + tarefaLinha.getPosicao());
//
//        this.tarefas.set(rowIndex, tC);
//        fireTableRowsUpdated(rowIndex, rowIndex);
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
                tarefaLinha.setDataModif(LocalDateTime.now());
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
            case 8:
                FuncoesTarefas.alteraPosicao(tarefaLinha, (int) aValue);
                tarefaLinha.setDataModif(LocalDateTime.now());
                //atualizar posições no modelo

                LOG_MODELO.debug("Recarregando tarefas...");
                DAOTarefa daoT = new DAOTarefa();
                daoT.atualiza(tarefaLinha);

                List<Tarefa> todas = daoT.listaTodas();
                setTarefas(todas);
                ordena();

                break;
            case 9:
                tarefaLinha.setComentario((String) aValue);
                tarefaLinha.setDataModif(LocalDateTime.now());
                break;
            case 10:
                tarefaLinha.setStatus((String) aValue);
                tarefaLinha.setDataModif(LocalDateTime.now());
                break;
        }

        LOG_MODELO.debug("Indíce coluna: " + columnIndex);
        LOG_MODELO.debug("Atualizando tarefa no banco...");
        LOG_MODELO.debug("Posição: " + tarefaLinha.getPosicao());

        DAOTarefa daoT = new DAOTarefa();
        daoT.atualiza(tarefaLinha);

        //  int idx = tarefas.indexOf(tarefaLinha);
//        Utilid.persisteInfoTabela(tabelaTarefas);
//       
//
//        Utilid.carregaInfoTabela(tabelaTarefas);
//        /*Nessa chamada de método a alteração do id pode falhar.
//        
//         */
//        contr.getDaoUsuario().flush();
    }

    /**
     * Adiciona uma nova tarefa no modelo da tabela
     *
     * @param tar
     */
    public void adicionaTarefa(Tarefa tar) {
        this.tarefas.add(tar);
        fireTableRowsInserted(tarefas.size() - 1, tarefas.size() - 1);
    }

    public void removeTarefa(int idx) {
        tarefas.remove(idx);
        fireTableRowsInserted(idx, idx);
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
        //  Utilid.persisteInfoTabela(tabelaTarefas);
        fireTableDataChanged();
        //  Utilid.carregaInfoTabela(tabelaTarefas);

    }

    public void ordena(Comparator<Tarefa> comp) {
        this.tarefas.sort(comp);
        fireTableDataChanged();
        //   Utilid.carregaInfoTabela(tabelaTarefas);
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void ordena() {
        Comparator<Tarefa> comp = (Tarefa o1, Tarefa o2) -> {
            if (o1.getPosicao() != null && o2.getPosicao() != null) {

                int resultComp = o1.getPosicao().compareTo(o2.getPosicao());
                return resultComp;
                //LOG_MODELO.traceExit("Compare n n", resultComp);
            }

            if (o1.getPosicao() == null && o2.getPosicao() == null) {
                return 0;//LOG_MODELO.traceExit("2 n", 0);
            } else if (o1.getPosicao() == null) {
                return 1;//LOG_MODELO.traceExit("1 n", 1);
            } else if (o2.getPosicao() == null) {
                return -1;//LOG_MODELO.traceExit("2 n", -1);
            }

            return 0;
        };
        this.tarefas.sort(comp);
        fireTableDataChanged();
        //Utilid.carregaInfoTabela(tabelaTarefas);

    }

    /**
     *
     */
    public void recarregaTarefasBanco() {
        DAOTarefa daoT = new DAOTarefa();

        //TODO: PaginaÃ§Ã£o
        List<Tarefa> tarefasBanco = daoT.listaTodas();
        this.tarefas.clear();
        this.tarefas.addAll(tarefasBanco);
        ordena();
        fireTableDataChanged();
    }

}
