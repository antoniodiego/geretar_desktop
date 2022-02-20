package br.com.antoniodiego.gertarefas.controller;

import br.com.antoniodiego.gertarefas.telas.dialdet.DialogoVerTarefa;
import br.com.antoniodiego.gertarefas.igu.EditorData;
import br.com.antoniodiego.gertarefas.telas.JanelaExibTabela;
import br.com.antoniodiego.gertarefas.telas.principal.JanelaPrincipal;
import br.com.antoniodiego.gertarefas.model.ModeloTabelaTarefasLista;
import br.com.antoniodiego.gertarefas.telas.TelaLogin;
import br.com.antoniodiego.gertarefas.telas.TelaOpc;
import br.com.antoniodiego.gertarefas.telas.TelaSobre;
import br.com.antoniodiego.gertarefas.igu.TransfXMLT;
import br.com.antoniodiego.gertarefas.igu.modelos.ModeloArvore;
import br.com.antoniodiego.gertarefas.igu.modelos.ModeloData;
import br.com.antoniodiego.gertarefas.igu.modelos.ModeloTabelaTarefa;
import br.com.antoniodiego.gertarefas.igu.renderers.DeseData;
import br.com.antoniodiego.gertarefas.model.ModeloTabAgend;
import br.com.antoniodiego.gertarefas.model.ModeloTabNotif;
import br.com.antoniodiego.gertarefas.Constantes;
import br.com.antoniodiego.gertarefas.persist.daos.DAOAgendamentos;
import br.com.antoniodiego.gertarefas.persist.daos.DAOGrupos;
import br.com.antoniodiego.gertarefas.persist.daos.DAONotifcacao;
import br.com.antoniodiego.gertarefas.persist.daos.DAOTarefa;
import br.com.antoniodiego.gertarefas.persist.daos.DAOUsuario;
import br.com.antoniodiego.gertarefas.pojo.Agendamento;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Notificacao;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import br.com.antoniodiego.gertarefas.pojo.TarefaCoordenada;
import br.com.antoniodiego.gertarefas.pojo.TipoVoto;
import br.com.antoniodiego.gertarefas.pojo.Usuario;
import br.com.antoniodiego.gertarefas.pojo.Voto;
import br.com.antoniodiego.gertarefas.util.ConversXML;
import br.com.antoniodiego.gertarefas.util.ConversXMLD;
import br.com.antoniodiego.gertarefas.util.HibernateUtil;
import br.com.antoniodiego.gertarefas.telas.DialogoConfirmarExcTudo;
import br.com.antoniodiego.gertarefas.telas.DialogoNovaTarView;
import br.com.antoniodiego.gertarefas.telas.principal.DialogoAgend;
import br.com.antoniodiego.gertarefas.telas.principal.PainelListaTarefas;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SpinnerNumberModel;
import javax.swing.TransferHandler;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.TreePath;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.hsqldb.HsqlException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.DOMException;

/**
 *
 * @author Ant�noio Diego <antoniodiegoluz at gmail.com>
 */
public class JanelaPrincipalController {

    private Usuario usuario;
    private DAOGrupos gerg;
    private DAOUsuario daoUsuario;
    /**
     * Tarefa selecionada.
     */
    private Tarefa tarefaAEditar;
    private File arquiP;
    private Properties proprie;
    /**
     * Grupo selecionado. Da tarefa atual. Essa váriável guarda o grupo selecionado na árvore se houver um. Se não tiver
     * um grupo, mas houver uma tarefa selecionada o pai dela é guardado no campo. Se um nó ramo que não for grupo for
     * selecionado ela deve guardar uma ref para o nó raiz
     */
    private GrupoTarefas grupoDaAtu;
    private Tarefa tarefaExibida;
    private ModeloArvore modeloArv;
    private AcaoNovaTarefa acaoNovaTar;
    private AcaoEditarTarefa acaoEditar;
    /**
     * Obs: Atualmente (26/07/18) persiste no banco mas deveria pesistir no objeto
     */
    private AcaoSalvarTarefa acaoSalvar;
    private JanelaPrincipal view;
    private JTree arvoreTarefas;
    public static final Logger LOG_CONTR_PRINC = LogManager.getLogger("Controller_Principal");
    private AcaoBackup acaoBackup;
    private AcaoExcluirGrupo acaoExG;
    private AcaoRecortar acaoRec;
  //  private AcaoCopiar acaoCop;
    private AcaoColar acaoColar;

    private AdicCam acaoAdic;
    private RemovCam acaoRem;
  //  private JButton botaRem;
    private AcaoReiniciar acaoReiniciarBanco;
    private AcaoExcluirTudo acaoExT;
    private AcaoNovoGr acaoNovG;
    private AcaoAtalho acaoCriAt;

    private SpinnerNumberModel modeloCampoPro;

    private ModeloTabelaTarefa modeloTabela;
    private TrayIcon iconeGeretar;
    private int totalFaz;
    private final JFileChooser seletorArquivos;
    private final FileNameExtensionFilter filtroNome;

    private ModeloTabNotif modTabNotif;

    private void configuraIconeBandeja() {
        if (SystemTray.isSupported()) {
            SystemTray st = SystemTray.getSystemTray();
            ImageIcon imageIcGer = new ImageIcon(JanelaPrincipal.class.getResource("/imagens/icone lapis.png"));

            iconeGeretar = new TrayIcon(imageIcGer.getImage(), "Gerenciador de tarefas " + Constantes.VERS);

            PopupMenu menuPopUp = new PopupMenu();
            MenuItem sair = new MenuItem("Sair");
            sair.addActionListener((ActionEvent e) -> {
                System.exit(0);
            });

            menuPopUp.add(sair);
            try {
                st.add(iconeGeretar);
            } catch (AWTException ex) {
                LOG_CONTR_PRINC.catching(ex);
            }

            iconeGeretar.setPopupMenu(menuPopUp);
            iconeGeretar.addActionListener(acIconeBand);
        }

    }

    /**
     *
     */
    public void conf() {
        arvoreTarefas = view.getArvoreTarefas();
        arvoreTarefas.addTreeSelectionListener(ouvSelArv);

        view.getArvoreTarefas().setTransferHandler(new TransfHandArv(this));

        modeloArv = new ModeloArvore();

        modeloArv.addTreeModelListener(listMod);
        view.getArvoreTarefas().setModel(modeloArv);
        view.getArvoreTarefas().addMouseListener(new AdaptadorArvore());
        /* 
        menuContArv = view.getMenuContextoArvore(); */

        this.arquiP = new File("conf.properties");
        proprie = new Properties();
        try {
            if (arquiP.exists()) {
                proprie.load(new FileInputStream(arquiP));
            }
        } catch (IOException ex) {
            LOG_CONTR_PRINC.catching(ex);
        }

        configuraIconeBandeja();

        // Daemon para o prog fechar quan só hou ela
        timerAlarme = new Timer(true);

        view.addWindowListener(ouvJane);
        view.addWindowStateListener(ouvJane);
        acaoAdic = new AdicCam();
        view.getBotaoAdic().setAction(acaoAdic);

        view.getItemExibirPorPri().setAction(new AcaoExibirPorPrioridade());

        acaoRem = new RemovCam();

        acaoRem.setEnabled(false);

        acaoAdic.setEnabled(false);

        // Cria uma instância da ação de criação de nova tarefa
        acaoNovaTar = new AcaoNovaTarefa(view.getIconeNovaTarefa());
        // Defini a acão do menu nova como sendo a AçãoNovaTarefa
        view.getMenuNovaTarefa().setAction(acaoNovaTar);

        /*
         * Cria atalho para a ação nova tarefa e o configura no menu homônimo
         * 
         */
        KeyStroke combNovaTarefa = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
        view.getMenuNovaTarefa().setAccelerator(combNovaTarefa);

        acaoNovG = new AcaoNovoGr(iconeAdicionarGrupo);

        KeyStroke combNovoGrupo = KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK);
        view.getMenuNovoGrupo().setAccelerator(combNovoGrupo);

        view.getMenuRecorta().setAction(acaoExG);

        modeloCampoPro = new SpinnerNumberModel(5, 1, 100, 2);
        view.getPainelFunLadoDire().getCampoPrioridade().setModel(modeloCampoPro);

        modeloTabela = new ModeloTabelaTarefa(true);
        // Obs: Com cell edit tamb fun- 07/07/18
        // TODO: Por data atual auto em dta conclusa tar coo após marc con - Feito
        // tabelaExibT.getCellEditor(0, 0).addCellEditorListener(this);
        modeloTabela.addTableModelListener(ouvMTab);

        view.getTabelaExibT().setModel(modeloTabela);
        view.getTabelaExibT().setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        view.getTabelaExibT().setFillsViewportHeight(true);
        view.getTabelaExibT().putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        acaoEditar = new AcaoEditarTarefa();
        acaoSalvar = new AcaoSalvarTarefa();

        view.getMenuNovoGrupo().setAction(acaoNovG);

        acaoExT = new AcaoExcluirTudo(view.getIconeExcluirTudo());

        view.getMenuExcluirTudo().setAction(acaoExT);

        // acaoExG = new AcaoExcluirGrupo(view.g);
        acaoReiniciarBanco = new AcaoReiniciar();
        acaoBackup = new AcaoBackup();

        acaoBackup = new AcaoBackup();

        view.getMenuExcluirGrupo().setAction(acaoExG);

        view.getMenuTrocarUsuario().addActionListener(acGere);
        view.getMenuSair().addActionListener(acGere);
        view.getItemMenuExcluirTarefa().addActionListener(acGere);
        view.getItemFazerBackup().setAction(acaoBackup);
        view.getPainelFunLadoDire().getCampoPrioridade().setModel(modeloCampoPro);
        view.getItemOpcoes().addActionListener(acGere);

        view.getBotaoExcluirTarefa().addActionListener(acGere);

        view.getItemCriarAta().setAction(acaoCriAt);
        view.getItemSalvarComoTexto().addActionListener(acGere);
        view.getItemExportarXML().addActionListener(acGere);
        view.getItemImportarXML().addActionListener(acGere);

        acaoRec = new AcaoRecortar();

     //   acaoCop = new AcaoCopiar();

        KeyStroke combCop = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK);

        KeyStroke combRec = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK);

        KeyStroke combCol = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK);

        acaoExG = new AcaoExcluirGrupo(view.getIconeExcluirGrupo());

        acaoColar = new AcaoColar();

        // view.getMe
        view.getCaixaMarcarF().setEnabled(false);
        view.getCaixaMarcarF().addActionListener(acGere);

        view.getMenuRecorta().setAction(acaoRec);
        view.getMenuRecorta().setAccelerator(combRec);

        view.getMiCol().setAction(acaoColar);
        view.getMiCol().setAccelerator(combCol);

        view.getMiCop().setAction(TransferHandler.getCopyAction());

        view.getMiCop().setAccelerator(combCop);

        view.getBotaoNovoGrupo().setAction(acaoNovG);
        view.getBotaoExcluirGrupo().setAction(acaoExG);
        view.getBotaoExcluirTudo().setAction(acaoExT);
        view.getItemSobre().addActionListener(acGere);

        view.getMenuNovTAr().setAction(acaoNovaTar);
        view.getMenuNovTAr().setAccelerator(combNovaTarefa);

        view.getMenuNovGAr().setAction(acaoNovG);
        view.getMenuNovGAr().setAccelerator(combNovoGrupo);

        view.getMenuRec().setAction(acaoRec);
        view.getMenuRec().setAccelerator(combRec);

        view.getMenuCopiaG().setAction(TransferHandler.getCopyAction());
        view.getMenuCopiaG().setAccelerator(combCop);

        view.getMenuColar().setAction(acaoColar);
        view.getMenuColar().setAccelerator(combCol);

        view.getPainelFunLadoDire().getBotaoEditarTarefa().setAction(acaoEditar);
        view.getPainelFunLadoDire().getBotaoCancelarEditar().addActionListener(acGere);

        view.getPainelFunLadoDire().getBotaoExcluirTarefa2().addActionListener(acGere);
        view.getPainelFunLadoDire().getBotaoCopiaConteudo().addActionListener(acGere);

        view.getPainelFunLadoDire().getCampoPrioridade().setModel(modeloCampoPro);

        view.getPainelFunLadoDire().getBtVotarLembrei().setAction(new AcaoVotoLembrei());
        view.getPainelFunLadoDire().getBtVotarProclast().setAction(new AcaoVotProc());

        view.getBotaRem().setAction(acaoRem);

        acaoVerDet = new AcaoVerDetDaTar();
        view.getBtVerDetTarefa().setAction(acaoVerDet);

        dialogoNovaTarefa = new DialogoNovaTarView(view, null);

        confPainelTarefasLista();

        view.getBotaoNovaTarefa().setAction(acaoNovaTar);

        acaoAg = new AcaoAgendar();

        view.getMenuAgendar().setAction(acaoAg);

        confColTab();

        view.getPainelFunLadoDire().getBtDiminuiPrio().setAction(new AcaoDiminuiPrio());
        view.getPainelFunLadoDire().getBtAumentaPrio().setAction(new AcaoAumentaPrio());

        ordenadorTabelaLista = new TableRowSorter<>(modeloTab);

        // RowSorter.SortKey skSta = new RowSorter.SortKey(4, SortOrder.ASCENDING);
        // RowSorter.SortKey skDataF = new RowSorter.SortKey(3, SortOrder.DESCENDING);
        // RowSorter.SortKey sk = new RowSorter.SortKey(2, SortOrder.DESCENDING);
        // Por data de modif
        RowSorter.SortKey chaveOrdDataModif = new RowSorter.SortKey(6, SortOrder.DESCENDING);

        ordenadorTabelaLista.setSortKeys(Arrays.asList(chaveOrdDataModif));

        view.getPainelLista().getTabelaTarefas().setRowSorter(ordenadorTabelaLista);

        modeloCDataConc = (ModeloData) view.getCampoDataConc().getModel();
        modeloCData = (ModeloData) view.getPainelFunLadoDire().getCampoData().getModel();
        modeloCDataFaz = (ModeloData) view.getPainelFunLadoDire().getCampoDataFazer().getModel();
        modeloCDataAl = (ModeloData) view.getPainelFunLadoDire().getCampoDataAl().getModel();

        view.getMenuDetalhes().setAction(acaoVerDet);
        view.getMenuDetalhes().setMnemonic(KeyEvent.VK_D);
        view.getMenuDetalhes().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));

        view.getMenuNovaTarefa().setAccelerator(combNovaTarefa);
        view.getMenuNovoGrupo().setAccelerator(combNovoGrupo);

        view.getMenuVerDetBM().setAction(acaoVerDet);
        view.getMenuVerDetBM().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));

        view.getMenuAgendarBM().setAction(acaoAg);
        view.getMenuAgendarBM().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));

        view.getMenuAgendar().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));

        confPainelAg();
        confPainNotif();

        confColTabLista();

        acaoVerDet.setEnabled(false);
    }

    private ActionListener acIconeBand = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(true);

            buscaNotifPerdidas();
        }

        private void buscaNotifPerdidas() {
            LOG_CONTR_PRINC.traceEntry("Buscando notif perd");

            try {
                // TODO: Corrigir exc aqui
                List<Notificacao> notifPerd = nots.stream()
                        .filter(notif -> (notif.getHoraExibicao() != null
                        && notif.getHoraExibicao().isBefore(LocalDateTime.now())))
                        .filter(notif -> !notif.isFoiExibida()).collect(Collectors.toList());

                modTabNotif.setNotif(notifPerd);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private WindowAdapter ouvJane = new WindowAdapter() {
        @Override
        public void windowIconified(WindowEvent e) {
            // Janela deve ter sido min
            view.setVisible(false);
        }

        @Override
        public void windowStateChanged(WindowEvent e) {
            LOG_CONTR_PRINC.trace("Nov est: " + e.getNewState());

        }
    };
    /**
     *
     */
    private Timer timerAlarme;
    /**
     * Janela nova tarefa
     */
    private DialogoNovaTarView dialogoNovaTarefa;
    private TelaLogin dialogoLogin;
    private ModeloTabelaTarefasLista modeloTab;

    private TreeSelectionListener ouvSelArv = new TreeSelectionListener() {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            LOG_CONTR_PRINC.debug("Evento de seleção");
            // DefaultMutableTreeNode sel = (DefaultMutableTreeNode) arvoreTarefas.
            // getLastSelectedPathComponent();

            Object sel = arvoreTarefas.getLastSelectedPathComponent();
            if (sel == null) {
                LOG_CONTR_PRINC.debug("Seleção nula");
                grupoDaAtu = null;
                tarefaExibida = null;
                atualizaExibicaoTarefa(null);
                acaoEditar.setEnabled(false);
                return;
            }

            /*
             * Se chegou aqui algum nó da árvore foi selecionado
             */
            // XXX: Chece e salva. perde edições
            checaESalva();

            // Verifica se o nó selecionado é folha ou não
            if (modeloArv.isLeaf(sel)) {
                // Aqui um nó folha foi selecionado

                LOG_CONTR_PRINC.debug("No folha");
                // Tarefa ou grupo vazio
                if (sel instanceof Tarefa) {
                    LOG_CONTR_PRINC.debug("Folha tarefa");
                    Tarefa t = (Tarefa) sel;// (Tarefa) sel.getUserObject();
                    LOG_CONTR_PRINC.debug("Tit: " + t.getTitulo());
                    // Pela organ deve ser GrupTa

                    // OBS: Linhas poderia estar em um método
                    GrupoTarefas g = (GrupoTarefas) t.getPai();
                    grupoDaAtu = g;
                    // } else {
                    // grupoDaAtu = null;
                    // }
                    // this.noGrupo = grupoPai;
                    tarefaExibida = t;
                    atualizaExibicaoTarefa(t);
                    acaoEditar.setEnabled(true);
                } else if (sel instanceof GrupoTarefas) {
                    LOG_CONTR_PRINC.debug("Grupo folha!");
                    // noGrupo = sel;
                    grupoDaAtu = (GrupoTarefas) sel;
                    atualizaExibicaoTarefa(null);
                    LOG_CONTR_PRINC.debug("Nome: " + grupoDaAtu);
                } else {
                    LOG_CONTR_PRINC.debug("N? folha n?o GrupoTarefas nem Tar");
                    // XXX: Obs: pode ser no raiz
                }
            } else {
                // Não foi um nó folha que foi escolhido.
                // Deve ser g

                if (sel instanceof GrupoTarefas) {
                    // System.out.println("Sel grupo ramo: " + sel);

                    grupoDaAtu = (GrupoTarefas) sel;
                    atualizaExibicaoTarefa(null);
                } else {
                    /*
                     * Neste ponto um nó ramo foi selecionado, mas que não foi um grupo
                     * 
                     */
                    System.out.println("N? ramo n?o GrupoTarefas nem no Princ");
                    // XXX: Obs: pode ser no raiz
                    grupoDaAtu = usuario.getGrupoRaiz();
                }
            }

            atualizaEstadoDosMenusBotoes();
        }
    };

    private TreeModelListener listMod = new TreeModelListener() {
        @Override
        public void treeNodesChanged(TreeModelEvent e) {
            Object[] filAlt = e.getChildren();
            Object fi1 = filAlt[0];
            if (fi1 == tarefaExibida) {
                // Alterada aponta para a mesma que está send exib
                atualizaExibicaoTarefa((Tarefa) fi1);
            }
        }

        @Override
        public void treeNodesInserted(TreeModelEvent e) {

        }

        @Override
        public void treeNodesRemoved(TreeModelEvent e) {

        }

        @Override
        public void treeStructureChanged(TreeModelEvent e) {

        }
    };
    private ModeloData modeloCDataConc;

    private TableModelListener ouvMTab = new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            switch (e.getType()) {
                case TableModelEvent.INSERT:
                    acaoAdic.setEnabled(modeloTabela.getRowCount() < 5 && modeloTabela.isEditando());
                    break;
                case TableModelEvent.DELETE:
                    acaoRem.setEnabled(modeloTabela.getRowCount() > 1 && modeloTabela.isEditando());
                    break;
                case TableModelEvent.UPDATE:
                    if (e.getColumn() == 0) {
                        Boolean mudouFeita = (Boolean) modeloTabela.getValueAt(e.getFirstRow(), 0);
                        System.out.println("mud col 0. Val: " + mudouFeita);
                        if (mudouFeita) {
                            modeloTabela.setValueAt(LocalDate.now(), e.getFirstRow(), 1);
                            modeloTabela.setValueAt(LocalTime.now(), e.getFirstRow(), 2);
                            int numL = modeloTabela.getRowCount();
                            boolean todasCooCon = false;
                            for (int i = 0; i < numL; i++) {
                                Boolean vC = (Boolean) modeloTabela.getValueAt(i, 0);
                                todasCooCon = vC;
                            }
                            if (todasCooCon) {
                                view.getCaixaMarcarF().setSelected(true);
                                modeloCDataConc.setValue(LocalDate.now());
                                // view.getCampoDataConc().getModel().setValue(LocalDate.now());
                                view.getCampoHoraCon().setValue(LocalTime.now());
                            }
                        } else {
                            modeloTabela.setValueAt(null, e.getFirstRow(), 1);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private AcaoVerDetDaTar acaoVerDet;
    private ListSelectionListener ouviModeloTabList = new ListSelectionListener() {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int lTareSel = view.getPainelLista().getTabelaTarefas().getSelectedRow();

            if (lTareSel != -1) {
                int indexTarSel = view.getPainelLista().getTabelaTarefas().convertRowIndexToModel(lTareSel);

                Tarefa tarS = modeloTab.getTarefas().get(indexTarSel);

                System.out.println("Tit: " + tarS.getTitulo());
                // Pela organ deve ser GrupTa

                // OBS: Linhas poderia estar em um método
                GrupoTarefas g = (GrupoTarefas) tarS.getPai();
                grupoDaAtu = g;

                tarefaExibida = tarS;
                atualizaExibicaoTarefa(tarS);
                acaoEditar.setEnabled(true);
                acaoVerDet.setEnabled(true);
                atualizaEstadoDosMenusBotoes();
            } else {
                LOG_CONTR_PRINC.debug("Nada selecionado na tabela");

                grupoDaAtu = null;
                tarefaExibida = null;
                atualizaExibicaoTarefa(null);
                acaoEditar.setEnabled(false);
                acaoVerDet.setEnabled(false);

            }

        }
    };

    private final ActionListener acGere = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            final String cmd = e.getActionCommand();
            Object origem = e.getSource();

            // 03/07/18~~09:38 - JTable j? salva com clique fora
            if (origem == view.getItemOpcoes()) {
                TelaOpc opc = new TelaOpc(view, false);
                opc.setVisible(true);
            } else if (origem == view.getItemExportarXML()) {
                exportaXml();
            } else if (origem == view.getItemImportarXML()) {
                importaXML();
            } else if (cmd.equals("deleta_tarefa")) {
                TreePath[] sel = arvoreTarefas.getSelectionPaths();
                if (mostraConfirma\u00e7\u00e3o("Excluir tarefa" + (sel.length > 1 ? "s" : ""), "Tu tens certeza?")) {
                    GrupoTarefas grupo = grupoDaAtu;
                    // DefaultMutableTreeNode possNoTa;
                    Object o;
                    for (TreePath cm : sel) {
                        o = cm.getLastPathComponent();
                        // Object o = possNoTa.getUserObject();

                        if (o instanceof Tarefa) {
                            Tarefa ta = (Tarefa) o;
                            System.out.println("Deleta inst Tarefa: " + ta.getTitulo() + " de " + grupo.getTitulo());
                            boolean fe = ta.isConcluida();
                            // System.out.println("");
                            // grupo.getTarefas().remove(ta);
                            // Obs: Remoe mas nÃ£o apaga nÃ³
                            modeloArv.remove(grupo, ta);
                            // Dever? causar orphao remov
                            // TODO: Nofi mode arv
                            // modeloArv.removeNodeFromParent(possNoTa);
                            if (!fe) {
                                totalFaz--;
                            }
                        } else {
                            System.out.println("Delet nÃ£o tar");
                        }
                    }

                    /*
                     * Atualiza [[pai ]pois ofphanRemov não dispara. 9;15 a corri pai nõ
                     */
 /*
                     * Obs: 09:23 - funcionou mas tive que consertar erro de não mudar pai da tarefa
                     * pra null antes. Isso //que causa o orphan removal
                     */
                    daoUsuario.flush();
                    view.getLbQTF().setText(String.valueOf(totalFaz));
                }
            } else if (cmd.equals("cancel_new_task")) {
                dialogoNovaTarefa.dispose();
            }

            if (cmd.equals("copy_task")) {
                TransferHandler.getCopyAction()
                        .actionPerformed(new ActionEvent(arvoreTarefas, ActionEvent.ACTION_PERFORMED, "copy"));
            } else if (cmd.equals("paste")) {
                TransferHandler.getPasteAction()
                        .actionPerformed(new ActionEvent(arvoreTarefas, ActionEvent.ACTION_PERFORMED, "paste"));
            }

            if (cmd.equals("paste")) {
                TransferHandler.getPasteAction()
                        .actionPerformed(new ActionEvent(arvoreTarefas, ActionEvent.ACTION_PERFORMED, "paste"));
            } else if (origem == view.getMenuColar()) {
                TransferHandler.getPasteAction()
                        .actionPerformed(new ActionEvent(arvoreTarefas, ActionEvent.ACTION_PERFORMED, "paste"));
            } else if (cmd.equals("cadastrar")) {
                Usuario novo = dialogoLogin.getUsuario();
                daoUsuario.salva(novo);
                // bancoDadosUsu?rio.cadastra(novo);
                dialogoLogin.atualizaBot\u00f5es();

                exibeGrupos();
            } else if (cmd.equals("exit")) {
                // Fecha ? sess?o
                // PENDING: Definir camop padr?o
                usuario = new Usuario(Constantes.NOME_USR_PADR, Constantes.SENHA_PADR.toCharArray());
                setUsuario(usuario);

                // PENDIG: Dados usu deveria ser carregado - Talvez Lazy
                // gerg.setUsu(usuario);
                proprie.setProperty("manter", "false");
                proprie.setProperty("hash", "");
                try {
                    gravaProp();

                } catch (IOException ex) {
                    LOG_CONTR_PRINC.catching(ex);
                }
                exibeGrupos();
                JOptionPane.showMessageDialog(view, "Tu te desconectaste!");
                // System.exit(0);
            } else if (cmd.equals("relogin")) {
                dialogoLogin.setModal(false);
                permiss\u00e3oECarrega();
            } else if (cmd.equals("delete_user")) {
                if (mostraConfirma\u00e7\u00e3o("Excluir usu\u00e1rio", "Tens certeza?")) {
                    Usuario del = dialogoLogin.getUsuario();
                    daoUsuario.dele(del);
                    // PENDING: Verficiar
                    gerg.deletaTudo(del.getNome());
                    dialogoLogin.atualizaBot\u00f5es();
                    // TODO: Voltar para padr
                }
            } else if (cmd.equals("cancel_edit")) {
                // Apertou botão cancelar
                alteraEditLote(false);
                // campoT\u00edtuloTarefa.setEditable(false);
                // modeloTabela.setEditando(false);
                // botaoCancelarEditar.setEnabled(false);
                view.getPainelFunLadoDire().getBotaoEditarTarefa().setAction(acaoEditar);
                atualizaEstadoDosMenusBotoes();
                atualizaExibicaoTarefa(tarefaAEditar);
            } else if (cmd.equals("copy_content")) {
                Clipboard areaTransferencia = view.getToolkit().getSystemClipboard();
                // POENDING:Corrigir
                // Copiar desc de tarefa cood sel
                int ls = view.getTabelaExibT().getSelectedRow();
                if (ls < 0) {
                    return;
                }
                int idxm = view.getTabelaExibT().convertRowIndexToModel(ls);
                TarefaCoordenada tc = modeloTabela.getCoords().get(idxm);
                // OBS: Analizar para clipboard owner
                StringSelection conteudoCopia = new StringSelection(tc.getDescricao());
                areaTransferencia.setContents(conteudoCopia, conteudoCopia);
                JOptionPane.showMessageDialog(view, "Descri\u00e7\u00e3o da" + " tarefa copiada!", "Conclu\u00eddo!",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (cmd.equals("sobre")) {
                TelaSobre telSobre = new TelaSobre(view);
                telSobre.setVisible(true);
            } else if (origem == view.getItemSalvarComoTexto()) {
                // TODO: pasta documentos.

                getSeletorArquivos().setFileFilter(getFiltroNome());
                getSeletorArquivos().setSelectedFile(new File(".txt"));
                int resultado = getSeletorArquivos().showSaveDialog(view);
                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File arquivoEscolhido = getSeletorArquivos().getSelectedFile();
                    FileOutputStream saidaArquivo;
                    try {
                        saidaArquivo = new FileOutputStream(arquivoEscolhido);
                        try ( DataOutputStream saidaDados = new DataOutputStream(saidaArquivo)) {
                            saidaDados.writeUTF("# Tarefas de usu?rio: " + usuario.getNome() + "\r\n");
                            List<GrupoTarefas> gruposL = usuario.getGrupoRaiz().getSubgrupos();// gerg.receG();
                            // db.getGrupos();
                            for (int c = 0; c < gruposL.size(); c++) {
                                GrupoTarefas grupo = gruposL.get(c);
                                saidaDados.writeUTF("# Grupo: " + grupo.getTitulo() + "\r\n");
                                List<Tarefa> tarefasGrupo = grupo.getTarefas();

                                for (int cb = 0; cb < tarefasGrupo.size(); cb++) {
                                    TarefaComposta tarefa = (TarefaComposta) tarefasGrupo.get(cb);
                                    saidaDados.writeUTF("# Tarefa: " + tarefa.getTitulo() + "\r\n");
                                    tarefa.getTarefasFilhas().forEach((tc) -> {
                                        try {
                                            saidaDados.writeUTF("" + tc.isConcluida() + " "
                                                    + Constantes.FORMATADOR_DATA_H_BR_T.format(tc.getDataConclusao())
                                                    + " " + tc.getDescricao() + "\r\n");

                                        } catch (IOException ex) {
                                            LOG_CONTR_PRINC.catching(ex);
                                        }
                                    });
                                    // saidaDados.writeUTF(tarefa.getConteudo() + "\r\n\r\n");
                                    // TODO: Escrever cada tar coord
                                }
                            }
                            // TODO: Tarefs
                        }
                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(view,
                                "Falha ao exportar. Arquivo não encontrado. " + e1.getLocalizedMessage());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(view, "Falha ao exportar. " + e1.getLocalizedMessage());
                    }

                }
            } else if (origem == view.getCaixaMarcarF()) {
                // Date datH = new Date();
                // campoDataConc.setText(mFormat.format(datH));
                if (view.getCaixaMarcarF().isSelected()) {
                    modeloCDataConc.setValue(LocalDate.now());
                    // view.getCampoDataConc().setValue(LocalDate.now());
                    view.getCampoHoraCon().setValue(LocalTime.now());
                } else {
                    modeloCDataConc.setValue(null);
                    // view.getCampoDataConc().setValue(null);
                    view.getCampoHoraCon().setValue(null);
                }

            }

        }
    };
    private Icon iconeAdicionarGrupo;
    private TableRowSorter<ModeloTabelaTarefasLista> ordenadorTabelaLista;
    private ModeloData modeloCData;
    private ModeloData modeloCDataFaz;
    private ModeloData modeloCDataAl;
    private AcaoAgendar acaoAg;
    private ModeloTabAgend modAg;
    private List<Notificacao> nots;
    private String arquivoEs;

    /**
     * O id da próxima tarefa a ser registrada
     */
    private Long proximoId;

    // ---- Fim dos campos ---- //
    public JanelaPrincipalController(JanelaPrincipal view) {
        this.view = view;

        // TODO: binding: deletar tarefas
        seletorArquivos = new JFileChooser();
        filtroNome = new FileNameExtensionFilter("Arquivo de texto", "txt");
        // seletorSalvar.setFileFilter(filtroNome);
        nots = new ArrayList<>();

    }

    public JanelaPrincipal getView() {
        return this.view;
    }

    public AcaoReiniciar getAcaoReiniciarBanco() {
        if (acaoReiniciarBanco == null) {
            acaoReiniciarBanco = new AcaoReiniciar();
        }
        return acaoReiniciarBanco;
    }

    private class AcaoVotoLembrei extends AbstractAction {

        public AcaoVotoLembrei() {
            super("Lembrei");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            tarefaExibida.aumentaPrio();
            tarefaExibida.setDataModif(LocalDateTime.now());
            ordenadorTabelaLista.sort();
        }
    }

    private void confPainelTarefasLista() {
        /*
         * Modelo da tabela para exib tarefas em form de lista
         */
        modeloTab = new ModeloTabelaTarefasLista();
        view.getPainelLista().getTabelaTarefas().setModel(modeloTab);
        view.getPainelLista().getTabelaTarefas().getSelectionModel().addListSelectionListener(ouviModeloTabList);

        view.getPainelLista().getTabelaTarefas().addMouseListener(new AdaptadorMouseLista());
        view.getPainelLista().getBtFiltrar().setAction(new AcaoFiltrarTarefas());

        PainelListaTarefas painelLista = view.getPainelLista();

        painelLista.getBtBuscar().setAction(new AcaoBuscar());
        painelLista.getBtVerDetalhes().setAction(acaoVerDet);

        painelLista.getCampoTextoBusca().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG_CONTR_PRINC.debug("Ac {} no TF", e);
                new AcaoBuscar().actionPerformed(e);
                // painelLista.getBtBuscar().doClick();
            }
        });
    }

  //  private PainelAgController contrPA;

    private void confPainelAg() {
        modAg = new ModeloTabAgend();
        view.getPainelAgendamentos().getTabelaAgendamentos().setModel(modAg);
     //   contrPA = new PainelAgController(this, view.getPainelAgendamentos());
    }

    private void confPainNotif() {
        modTabNotif = new ModeloTabNotif();

        view.getPainelNotif().getTabelaNotif().setModel(modTabNotif);
    }

    private TableColumn colunaHora;
    private TableColumn colD;

    public void confColTab() {
        TableColumnModel modC = view.getTabelaExibT().getColumnModel();

        // Coluna feita
        TableColumn colunaFeita = modC.getColumn(0);
        colunaFeita.setPreferredWidth(50);
        colunaFeita.setMaxWidth(50);
        colunaFeita.setMinWidth(50);

        // Poderi falhar se col reorganiz
        // Colunda data
        colD = modC.getColumn(1);
        colD.setPreferredWidth(100);
        colD.setMaxWidth(100);
        colD.setMinWidth(80);

        colD.setCellEditor(new EditorData(false));
        colD.setCellRenderer(new DeseData());

        // Coluna hora
        colunaHora = modC.getColumn(2);
        colunaHora.setPreferredWidth(80);
        colunaHora.setMaxWidth(80);
        colunaHora.setMinWidth(40);
        colunaHora.setCellEditor(new EditorData(true));

        TableColumn colunaDesc = modC.getColumn(3);
        colunaDesc.setPreferredWidth(256);
        colunaDesc.setMaxWidth(Integer.MAX_VALUE);
        colunaDesc.setMinWidth(200);

        TableColumn colunaComent = modC.getColumn(4);
        colunaComent.setPreferredWidth(230);
        colunaComent.setMaxWidth(800);
        colunaComent.setMinWidth(100);

        colunaFeita.sizeWidthToFit();
    }

    /**
     * Configura as colunas da tabela de exi de tar em forma de lista
     */
    public void confColTabLista() {
        JTable tabeTar = view.getPainelLista().getTabelaTarefas();
        TableColumnModel modC = tabeTar.getColumnModel();

        TableColumn colunaID = modC.getColumn(0);
        colunaID.setPreferredWidth(50);
        colunaID.setMaxWidth(50);

        colunaID.setMinWidth(50);

        TableColumn colunaPers = modC.getColumn(1);
        colunaPers.setPreferredWidth(50);
        colunaPers.setMaxWidth(50);

        colunaPers.setMinWidth(50);

        TableColumn colunaTitulo = tabeTar.getColumn("Título");

        // modC.getColumn(0);
        colunaTitulo.setPreferredWidth(300);
        // colunaTitulo.setMaxWidth(200);
        LOG_CONTR_PRINC.debug("Rs {}", colunaTitulo.getResizable());
        colunaTitulo.setResizable(true);

        colunaTitulo.setMinWidth(200);

        // Poderi falhar se col reorganiz
        // Colunda data
        TableColumn colDC = tabeTar.getColumn("Data de criação");
        colDC.setPreferredWidth(80);
        colDC.setMaxWidth(80);
        colDC.setMinWidth(80);

        colDC.setCellRenderer(new DeseData());

        // Coluna hora
        TableColumn colunaPrioridade = tabeTar.getColumn("Prioridade");
        colunaPrioridade.setPreferredWidth(80);
        colunaPrioridade.setMaxWidth(80);
        colunaPrioridade.setMinWidth(20);

        TableColumn colunaDataF = tabeTar.getColumn("Data fazer");
        colunaDataF.setPreferredWidth(80);
        colunaDataF.setMaxWidth(80);
        colunaDataF.setMinWidth(80);

        TableColumn colunaConc = tabeTar.getColumn("Concluída");
        colunaConc.setPreferredWidth(60);
        colunaConc.setMaxWidth(80);
        colunaConc.setMinWidth(40);
    }

    private class AcaoVotProc extends AbstractAction {

        public AcaoVotProc() {
            super("Proclastinei");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (tarefaExibida != null) {
                Voto votoPro = new Voto(TipoVoto.Proclastinei);
                tarefaExibida.getVotos().add(votoPro);
                tarefaExibida.aumentaPrio();
                ordenadorTabelaLista.sort();
                daoUsuario.flush();
            }
        }
    }

    public TableColumn getColunaHora() {
        return colunaHora;
    }

    public TableColumn getColD() {
        return colD;
    }

    private void ordenaGrupos() {
        LOG_CONTR_PRINC.traceEntry();
        GrupoTarefas gRaiz = usuario.getGrupoRaiz();

        // Ordena grupos
        ordenaGrupo(gRaiz);
        // List<Tarefa> lT = gRaiz.getTarefas();
        // ComparaTarPrio comparadorPrio = new ComparaTarPrio();
        // lT.sort(comparadorPrio);
        //
        // List<GrupoTarefas> subGrupos = gRaiz.getSubgrupos();
        // subGrupos.forEach((grupo) -> {
        // grupo.getTarefas().sort(comparadorPrio);
        //
        // });;
    }

    // =========== Ações ============
    public class AcaoReiniciar extends AbstractAction {

        public AcaoReiniciar() {
            super("Reiniciar banco");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                daoUsuario.reinicia();
                carregaUsuarioEDados();
                // TODO: Atualizar tab. rec usu
            } catch (SQLException ex) {
                LOG_CONTR_PRINC.catching(ex);
            }
            System.out.println("Reiniciou banco!");
            // TODO: Exib JOpP
        }
    }

    public ModeloArvore getModeloArv() {
        return modeloArv;
    }

    public ModeloTabelaTarefa getModeloTabela() {
        return modeloTabela;
    }

    public ModeloTabelaTarefasLista getModeloTab() {
        return modeloTab;
    }

    public ActionListener getAcGere() {
        return acGere;
    }

    private class AcaoEditarTarefa extends AbstractAction {

        public AcaoEditarTarefa() {
            super("Editar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (tarefaExibida == null) {
                return;
            }
            tarefaAEditar = tarefaExibida;
            // TODO: Btch estdo campos
            modeloTabela.setEditando(true);
            // PENDING: Desabilitar outros se for nec
            view.getCampoTituloTarefa().setEditable(true);
            view.getPainelFunLadoDire().getCampoData().setTextEditable(true);
            view.getPainelFunLadoDire().getCampoDataFazer().setTextEditable(true);
            view.getCampoDataConc().setTextEditable(true);
            view.getCampoHoraCon().setEditable(true);
            view.getPainelFunLadoDire().getBotaoEditarTarefa().setAction(acaoSalvar);
            view.getCaixaMarcarF().setEnabled(true);
            acaoAdic.setEnabled(true);
            acaoRem.setEnabled(true);
            view.getPainelFunLadoDire().getBotaoCancelarEditar().setEnabled(true);
            view.getPainelFunLadoDire().getCampoPrioridade().setEnabled(true);
            view.getPainelFunLadoDire().getCampoDataAl().setTextEditable(true);
            // view.getPainelFunLadoDire().getCampoDataAl().setEnabled(true);
            view.getPainelFunLadoDire().getCampoHoraAl().setEditable(true);
            view.getPainelFunLadoDire().getCampoHoraAl().setEnabled(true);
        }
    }

    private class AcaoSalvarTarefa extends AbstractAction {

        public AcaoSalvarTarefa() {
            super("Salvar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG_CONTR_PRINC.traceEntry("Salvando: quant co: " + modeloTabela.getCoords().size());
            // tarefaAEditar.setConteudo(conteudoTarefa.getText());
            tarefaAEditar.setTitulo(view.getCampoTituloTarefa().getText());

            LocalDate l = (LocalDate) view.getPainelFunLadoDire().getCampoData().getModel()
                    .getValue();/*
                                 * insC. atZone(ZoneId.systemDefault()).toLocalDate();
             */

            tarefaAEditar.setDataCriacao(l);
            LOG_CONTR_PRINC.trace("Conf data fazer");
            tarefaAEditar.setDataFazer((LocalDate) view.getPainelFunLadoDire().getCampoDataFazer().getModel()
                    .getValue());/*
                                  * ins.atZone(ZoneId.systemDefault()).toLocalDate());
             */
            LOG_CONTR_PRINC.trace("Config");
            tarefaAEditar.setPrioridade(modeloCampoPro.getNumber().intValue());

            boolean estavaCon = tarefaAEditar.isConcluida();
            tarefaAEditar.setConcluida(view.getCaixaMarcarF().isSelected());
            // if (caixaMarcarF.isSelected()) {
            if (tarefaAEditar.isConcluida()) {
                // TODO: Melhor
                LocalDate dac = (LocalDate) view.getCampoDataConc().getModel().getValue();
                LocalTime hc = (LocalTime) view.getCampoHoraCon().getValue();

                tarefaAEditar.setDataConclusao(
                        dac == null ? null : LocalDateTime.of(dac, hc == null ? LocalTime.now() : hc));
                /*
                 * FIXME: Est? contando mesmo sem haver altera??o em caixaMarcarF
                 */

                if (!estavaCon) {
                    totalFaz--;
                }
                view.getLbQTF().setText(String.valueOf(totalFaz));
            } else {
                tarefaAEditar.setDataConclusao(null);
                if (estavaCon) {
                    totalFaz++;
                }
                view.getLbQTF().setText(String.valueOf(totalFaz));
            }

            // Salva edições da tabela
            if (tarefaAEditar instanceof TarefaComposta) {
                TarefaComposta ic = (TarefaComposta) tarefaAEditar;
                // Apenas ad para não ter ref mesma lista pois outra sofre alt
                /*
                 * Obs: depois disso as tar coo da tabela serao mesmas da tare comp. [Talvez
                 * seja bom] chamada de atualizaExib parece mudar isso
                 */
                ic.getTarefasFilhas().clear();
                ic.getTarefasFilhas().addAll(modeloTabela.getCoords());
            }

            LocalDate dataAl = (LocalDate) view.getPainelFunLadoDire().getCampoDataAl().getModel().getValue();

            LocalTime horaAl = (LocalTime) view.getPainelFunLadoDire().getCampoHoraAl().getValue();

            if (dataAl != null && horaAl != null) {
                LOG_CONTR_PRINC.debug("Data pega do campo data al: " + dataAl.getDayOfMonth());

                LocalDateTime instAl = LocalDateTime.of(dataAl, horaAl);
                tarefaAEditar.setDataHoraLembrete(instAl);

                Notificacao notif = new Notificacao(instAl, tarefaAEditar);
                /**
                 * Penso que talvez fosse bom já gravar a notificação aqui usando um dao dela para evitar muito proces-
                 * samento com flush
                 */
                tarefaAEditar.setNotificacao(notif);

                // TODO: Reconfi lembrete
            } else {
                System.out.println(
                        "br.com.antoniodiego.gertarefas.igu.DialogoNovaTarefa.AcaoSalvaTarefa.actionPerformed()" + " da"
                        + dataAl + " ha " + horaAl);
            }
            alteraEditLote(false);
            // Cancela edição em andamento [edição em tabela]
            TableCellEditor ediT = view.getTabelaExibT().getCellEditor();
            if (ediT != null) {
                System.out.println("Estava editando ao salvar!");
                ediT.cancelCellEditing();
            }
            view.getPainelFunLadoDire().getBotaoEditarTarefa().setAction(acaoEditar);
            acaoEditar.setEnabled(true);
            atualizaEstadoDosMenusBotoes();
            // Persistir tarefa especifica.
            // gerg.atuG(grupoDaAtu);
            // Obs: Funciona -pers tarefa especifica-03/07/18 ~09;45 a

            daoUsuario.flush();
            // daoUsuario.atualiza((TarefaComposta) tarefaExibida);
            // Notific muda ao mod para mud ico
            modeloArv.notifAt(tarefaAEditar.getPai(), tarefaAEditar);
            atualizaExibicaoTarefa(tarefaExibida);
        }
    }

    /**
     *
     */
    private void carregaUsuarioEDados() {
        LOG_CONTR_PRINC.traceEntry("Carr dados");
        // Verfiica sessoes salvas
        if (proprie.getProperty("manter", "false").equalsIgnoreCase("true")) {
            // Sess?o salva
            LOG_CONTR_PRINC.trace("Manter estava em prop!");

            String nomeUsuarioSalvo = proprie.getProperty("usuario");
            String hash = proprie.getProperty("hash");

            usuario = daoUsuario.receUPorH(nomeUsuarioSalvo, hash);

            gerg.setUsu(usuario);

            exibeGrupos();
        } else if (proprie.getProperty("exibirin", "false").equalsIgnoreCase("true")) {
            // N?o tem sess?o e atividao login no in?cio.
            LOG_CONTR_PRINC.trace("Exibindo tela login");
            dialogoLogin.setModal(true);
            permiss\u00e3oECarrega();
        } else {
            // N?o tem sess?o e nem atividao login no in?cio.
            LOG_CONTR_PRINC.trace("dir carr");
            // Procura usu?rio padr?o
            // Obs: Parece que apenas o nome bastaria (id)
            // OBS: anal load e get e refresh
            LOG_CONTR_PRINC.trace("Procurando usu pad");
            Usuario padr\u00e3o = daoUsuario.receU(Constantes.NOME_USR_PADR, Constantes.SENHA_PADR.toCharArray());
            LOG_CONTR_PRINC.trace("Busca conc");

            // Salva usu?rio pad?ro se n?o existir
            if (padr\u00e3o == null) {
                // gereu.receU(padr\u00e3o.getNome(), padr\u00e3o.getSenha()) == null) {
                // if (!bl.setVisible(true)
                /* ;ancoDadosUsu?rio.temUsu?rio(padr?o)) { */
                LOG_CONTR_PRINC.trace("N?o encontrou usu?rio padr?o!." + " Salvando...");
                padr\u00e3o = new Usuario(Constantes.NOME_USR_PADR, Constantes.SENHA_PADR.toCharArray());
                daoUsuario.salva(padr\u00e3o);
            } else {
                // XXX: Tem us mesmo após reiniciar banco
                LOG_CONTR_PRINC.trace("Tem usr padrao.");
            }

            setUsuario(padr\u00e3o);
            /*
             * At? aqui os grupos (e tarefas) do usu?rio padr?o j? dever?o ter sido
             * carregadas
             */

            carregaNotificacoes();

            carregaAgendamentos();

            exibeGrupos();
        }

    }

    private void carregaNotificacoes() {
        LOG_CONTR_PRINC.traceEntry();
        DAONotifcacao daoN = new DAONotifcacao();

        nots = daoN.getAll();

        LOG_CONTR_PRINC.debug("Enc: " + nots.size());

        nots.forEach((not) -> {
            confDespNot(not);
        });

        LOG_CONTR_PRINC.traceExit();
    }

    private void confDespNot(Notificacao not) {
        LocalDateTime horaN = not.getHoraExibicao();

        LOG_CONTR_PRINC.debug("Hora enc: " + horaN);

        LocalDateTime agora = LocalDateTime.now();

        if (horaN.isAfter(agora)) {
            LOG_CONTR_PRINC.debug("É depois de ag");
            Instant ins = horaN.toInstant(ZoneOffset.of("-3"));

            Date dataNot = Date.from(ins);

            TimerTask tarefaAl = new TimerTask() {
                @Override
                public void run() {
                    LOG_CONTR_PRINC.trace("Exib mens");
                    iconeGeretar.displayMessage("Tarefa", not.getTarefaMae().getTitulo(), TrayIcon.MessageType.WARNING);

                    JOptionPane.showMessageDialog(view, not.getTarefaMae().getTitulo(), "Tarefa",
                            JOptionPane.INFORMATION_MESSAGE);

                    not.setFoiExibida(true);
                }
            };

            SimpleDateFormat formData = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
            System.out.println(formData.format(dataNot));
            timerAlarme.schedule(tarefaAl, dataNot);
            LOG_CONTR_PRINC.debug("Da c: " + dataNot);
            LOG_CONTR_PRINC.debug("Da conf: " + horaN);
            LOG_CONTR_PRINC.trace("Tar age! ");
        }
    }

    private void carregaAgendamentos() {
        DAOAgendamentos daAg = new DAOAgendamentos();
        List<Agendamento> agen = daAg.getAll();
        modAg.setAg(agen);
    }

    /**
     * Pode ser criada sem grupo
     */
    private class AcaoNovaTarefa extends AbstractAction {

        public AcaoNovaTarefa(Icon icone) {
            super("Nova tarefa", icone);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Aqui deveria ser bom passar o grupo de destino
            int idxSel = view.getPainelLadoEsq().getSelectedIndex();
            if (idxSel == 0) {
                // List
            }

            //    dialogoNovaTarefa.getContro().nova();
            DAOTarefa daoT = new DAOTarefa();
            Long ultId = daoT.getMaiorIDPers();
            dialogoNovaTarefa.getCampoId().setText(String.valueOf(ultId + 1));
        }
    }

    /**
     * Faz a conexao e carregamento dos dados do banco
     */
    public void inicializa() {
        LOG_CONTR_PRINC.traceEntry("Inicando Thread de inic");
        Thread th = new Thread(new TarefaInicia());
        th.start();
    }

    /**
     * Sincroniza com backend
     */
    private class AcaoSincronizar extends AbstractAction {

        private final CloseableHttpClient cliente = HttpClients.createDefault();
        private static final String URL_POST = "http://localhost:8006/grupos/";

        public AcaoSincronizar() {
            super("Sincronizar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG_CONTR_PRINC.traceEntry();
            LOG_CONTR_PRINC.trace("Inici pro de sincro...");

            RestTemplate templ = new RestTemplate();

            URI uriInfo = null;

            try {
                uriInfo = new URI("http://localhost:8015/sinc/info");
            } catch (URISyntaxException ex) {
                java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

            LocalDateTime dataUlSincServ = templ.getForObject(uriInfo, LocalDateTime.class);

            LOG_CONTR_PRINC.debug("Data ul atu rec " + dataUlSincServ);

        }
    }
    // ====== Fim das acões====== //

    private void alteraEditLote(boolean ed) {

        view.getCampoTituloTarefa().setEditable(ed);
        view.getPainelFunLadoDire().getCampoData().setTextEditable(ed);
        view.getPainelFunLadoDire().getCampoDataFazer().setTextEditable(ed);
        view.getCampoDataConc().setTextEditable(ed);
        view.getCampoHoraCon().setEditable(ed);
        // XXX: Parece ser importante salvar edi??es
        modeloTabela.setEditando(ed);
        acaoAdic.setEnabled(ed);
        acaoRem.setEnabled(ed);
        view.getPainelFunLadoDire().getBotaoCancelarEditar().setEnabled(ed);
        view.getPainelFunLadoDire().getCampoPrioridade().setEnabled(ed);
        view.getPainelFunLadoDire().getCampoDataAl().setTextEditable(ed);
        view.getPainelFunLadoDire().getCampoHoraAl().setEditable(ed);
        view.getPainelFunLadoDire().getCampoHoraAl().setEnabled(ed);
    }

    public Properties getProp() {
        return this.proprie;
    }

    public JFileChooser getSeletorArquivos() {
        return seletorArquivos;
    }

    public FileNameExtensionFilter getFiltroNome() {
        return filtroNome;
    }

    public DAOUsuario getDaoUsuario() {
        return daoUsuario;
    }

    public Timer getTimerAlarme() {
        return timerAlarme;
    }

    public void ordenaGrupo(GrupoTarefas grupo) {
        /*
         * Primeiro deve ser bom ordenar as tarefas do grupo e dos subgrupos. Depois
         * disso os subgrupos poderão ser orde- nados
         */

        List<Tarefa> lT = grupo.getTarefas();
        ComparaTarPrio comparadorPrio = new ComparaTarPrio();
        lT.sort(comparadorPrio);

        List<GrupoTarefas> subGrupos = grupo.getSubgrupos();
        // for(gf:subGrupos){
        //
        // }
        subGrupos.forEach((grupoF) -> {
            grupoF.getTarefas().sort(comparadorPrio);

            // Quando o grupo não tiver mais subgrupos esse método não será invocado
            ordenaGrupo(grupoF);
        });

        // Nesse ponto deve poder ser admitido que os subgrupos deste deve estar com as
        // tarefas e a list de sub ord
        subGrupos.sort(new ComparaGruposPrio());
    }

    /**
     * Ordena tarefas do grupo e de seus subgrupos
     *
     * Por que esse método é importante ?
     *
     * @param grupo
     */
    public void ordenaTarefas(GrupoTarefas grupo) {
        List<Tarefa> lT = grupo.getTarefas();
        ComparaTarPrio comparadorPrio = new ComparaTarPrio();
        lT.sort(comparadorPrio);

        List<GrupoTarefas> subGrupos = grupo.getSubgrupos();
        subGrupos.forEach((grupoF) -> {
            grupoF.getTarefas().sort(comparadorPrio);

            ordenaGrupo(grupo);
        });

    }

    /**
     *
     */
    public void iniciaGrupoRaiz() {
        GrupoTarefas gr = usuario.getGrupoRaiz();
        if (gr == null) {
            System.out.println("Não tem gru ra");
            usuario.setGrupoRaiz(new GrupoTarefas("Tarefas"));
            gerg.salvaG(usuario.getGrupoRaiz());
        }
    }

    /**
     * Atualiza conte\u00eddo da janela de acordo com os grupos e tarefas existentes no banco.
     */
    public void exibeGrupos() {
        iniciaGrupoRaiz();
        atualizaBarraDeStatus();
        atualizaEstadoDosMenusBotoes();
    }

    public void ordenaTarefas() {
        LOG_CONTR_PRINC.traceEntry();
        GrupoTarefas gRaiz = usuario.getGrupoRaiz();

        // Ordena tarefas
        List<Tarefa> lT = gRaiz.getTarefas();
        ComparaTarPrio comparadorPrio = new ComparaTarPrio();
        lT.sort(comparadorPrio);

        List<GrupoTarefas> subGrupos = gRaiz.getSubgrupos();
        subGrupos.forEach((grupo) -> {
            grupo.getTarefas().sort(comparadorPrio);
        });
    }

    /**
     * Aqui o usuário que fez login é definido no sistema. Nesse momento os grupos e tarefas dele são exi na árvore
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        LOG_CONTR_PRINC.traceEntry();
        this.usuario = usuario;
        // Impor disp fir strut chan depois
        ordenaTarefas();
        ordenaGrupos();
        passaDadosParaArv(usuario);

        preencheATabela();

        this.gerg.setUsu(usuario);

        DAOTarefa daoT = new DAOTarefa();
        Long maiorId = daoT.getMaiorIDPers();

        this.proximoId = maiorId++;

        LOG_CONTR_PRINC.debug("Próx id: " + proximoId);

        LOG_CONTR_PRINC.traceExit();
    }

    /**
     * Nesse método as tarefas são carregadas do banco
     */
    private void preencheATabela() {
        // Aqui deve ser bom dar com de preench tab

        List<Tarefa> todasAsTarefas = obtemTodasAsTar();
        LOG_CONTR_PRINC.info("Qaunt de tarefas ob: " + todasAsTarefas.size());
        todasAsTarefas.sort(new ComparaTarPrio());
        modeloTab.setTarefas(todasAsTarefas);
    }

    private List<Tarefa> obtemTodasAsTar() {
        List<Tarefa> todasAsTarefas = new ArrayList<>();
        GrupoTarefas gRaiz = usuario.getGrupoRaiz();
        copiaTarefas(gRaiz, todasAsTarefas);

        return todasAsTarefas;
    }

    private void passaDadosParaArv(Usuario usu) {
        LOG_CONTR_PRINC.traceEntry();
        modeloArv.setUsu(usu);
        LOG_CONTR_PRINC.traceExit();
    }

    public void gravaProp() throws FileNotFoundException, IOException {
        try ( FileOutputStream sai = new FileOutputStream(arquiP)) {
            this.proprie.store(sai, "arqu conf");
        }
    }

    private class GereTransLista extends TransferHandler {

        private static final long serialVersionUID = -7220352246768596588L;

        /**
         * Cont?m os grupos que foram arrastados
         */
        private TreePath[] selecoesMovi;

        @Override
        public int getSourceActions(JComponent c) {
            return COPY_OR_MOVE;
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport t) {
            System.out.println("Importando...");

            Transferable tr = t.getTransferable();
            JTree.DropLocation ld;
            TreePath caminhoD;
            GrupoTarefas grupInserir;
            // DefaultMutableTreeNode noGrin = null;

            if (t.isDrop()) {
                System.out.println("É soltar");
                // ? um ato de soltar
                ld = (JTree.DropLocation) t.getDropLocation();
                // Cam sel
                caminhoD = ld.getPath();
            } else {
                // Acho que seria colar
                // TODO: Implementar colar
                System.out.println("Não é soltar");
                // Prim sel
                caminhoD = arvoreTarefas.getSelectionPath();
            }

            Object sel = caminhoD.getLastPathComponent();// PathComponent(indF);
            // System.out.println("No ult cam: " + noIn);
            // Object obU = noIn.getUserObject();
            System.out.println("Locl drop: " + sel);

            if (sel instanceof GrupoTarefas) {
                System.out.println("Soltar em GrupoTarefas. Correto!");
                grupInserir = (GrupoTarefas) sel;
                // noGrin = noIn;
            } // else if (noIn.equals(noPrinc)) {
            // System.out.println("Soltando em raiz");
            // grupInserir = null;
            // }
            else {
                System.out.println("Não esta soltand em grupo");
                return false;
            }

            if (tr.isDataFlavorSupported(Tarefa.TAREFA_FLAVOR)) {
                System.out.println("Imp Tarefa fla");
                Tarefa im = null;
                try {
                    im = (Tarefa) t.getTransferable().getTransferData(Tarefa.TAREFA_FLAVOR);
                } catch (UnsupportedFlavorException | IOException e1) {

                }
                if (im != null && t.isDrop()) {
                    // if (grupInserir != null) {
                    grupInserir.add(im);
                    /// daoUsuario.flush();
                    // }
                }
                // TODO: Recuperar apagada
            } else if (tr.isDataFlavorSupported(br.com.antoniodiego.gertarefas.Tarefa.SABOR_TAREFA_AN)) {
                // System.out.println("Antiga");
                // TODO: Estudar retrocompatbi e DnD (Falvor)
                br.com.antoniodiego.gertarefas.Tarefa tarA;
                try {
                    tarA = (br.com.antoniodiego.gertarefas.Tarefa) tr
                            .getTransferData(br.com.antoniodiego.gertarefas.Tarefa.SABOR_TAREFA_AN);
                    TarefaComposta tareN = new TarefaComposta();
                    tareN.setTitulo(tarA.getTitulo());
                    tareN.getTarefasFilhas();
                    tareN.setConcluida(tarA.isConcluida());

                    tareN.setDataCriacao(convertData(tarA.getData()));
                    tareN.setDataFazer(convertData(tarA.getDataFazer()));
                    if (t.isDrop()) {
                        // if (grupInserir != null) {
                        grupInserir.add(tareN);
                        // } else {
                        // TODO: Raiz
                        // }
                    }
                } catch (UnsupportedFlavorException | IOException ex) {
                    LOG_CONTR_PRINC.catching(ex);
                }
            } else if (tr.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                System.out.println("Recebeu string transf");
                // Recebeu uma string que pode ser xml
                String strX;

                // FIXME: Alterar xml causa incompatibilidade
                try {
                    strX = (String) tr.getTransferData(DataFlavor.stringFlavor);
                    System.out.println("Importand XML Tar. Cont: " + strX);

                    ConversXML cd = new ConversXMLD();
                    List<Object> gt = cd.leGrupoETars(new ByteArrayInputStream(strX.trim().getBytes()));
                    System.out.println("Gr ou T lidos: " + gt.size());
                    // TODO: Melhorar
                    if (t.isDrop()) {
                        // JList.DropLocation ld = (JList.DropLocation) t.getDropLocation();
                        // int idx = ld.getIndex();
                        // System.out.println("Iserir: " + ld.isInsert());
                        // if (!ld.) {
                        // GrupoTarefas gi = modGt.getElementAt(idx);
                        System.out.println("Adicionando em grupo ins");
                        // grupInserir.add(tarX);
                        // modeloArv.insertNodeInto(new DefaultMutableTreeNode(tarX), noGrin, 0);
                        // gerg.atuG(gi);
                        // }
                    }

                    // selec.add(tarX);
                    gt.forEach((o) -> {
                        modeloArv.insere(grupInserir, o);
                    });
                } catch (UnsupportedFlavorException | IOException | DOMException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Problema ao soltar");

                }
            }

            // System.out.println("Atualizando grupo inserir.");
            System.out.println("Flushando após importar");
            // OBS: Flush pode falahar mas dados vao para a tela.
            daoUsuario.flush();

            System.out.println("Após flush");
            // gerg.atuG(grupInserir)
            // XXX OBS Parece que não estava retornando true por causa de erro em flush e
            // não estv chamndo exp done e
            // removendo.

            return true;
        }

        /**
         * Dever? verificar se a ?rvore poder? importar uma coisa arrastada.
         *
         * @param support
         * @return
         */
        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            System.out.println("pode imp?");
            boolean podeIm = false;
            // A ?rvore pode importar uma tarefa
            if (support.isDataFlavorSupported(
                    Tarefa.TAREFA_FLAVOR) /*
                                           * || support.isDataFlavorSupported(br.diego.gertarefas.core.Tarefa.
                                           * SABOR_TAREFA_AN)
                     */) {
                podeIm = true;
            } else if (support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                podeIm = true;
            }
            return podeIm;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            if (action == MOVE) {
                System.out.println("Export mov con");
                if (selecoesMovi != null) {
                    for (TreePath mov : selecoesMovi) {
                        System.out.println("removendo objeto movido");

                        Object o = mov.getLastPathComponent();// .getUserObject();
                        GrupoTarefas gp;
                        if (o instanceof GrupoTarefas) {
                            GrupoTarefas instG = (GrupoTarefas) o;
                            gp = instG.getPai();
                            if (gp != null) {
                                // instG.setDono(null);
                                modeloArv.remove(gp, instG);
                                // instG.getPai().remove(instG);
                                // Dever? causar remo? orf?o.
                                // TODO: Remover da ?rvore.
                                // Obs: parece bom criar um modelo arv com grupos para remover junt
                            }
                        } else if (o instanceof TarefaComposta) {
                            /// DefaultMutableTreeNode noP = (DefaultMutableTreeNode) no.getParent();
                            // if (noP.equals(noPrinc)) {
                            // usuario.getGrupoRaiz().getTarefas().remove((TarefaComposta) o);
                            // } else {
                            gp = ((TarefaComposta) o).getPai();// noP.getUserObject();
                            if (gp instanceof GrupoTarefas) {
                                // GrupoTarefas grP = (GrupoTarefas) gP;
                                modeloArv.remove(gp, o);
                            }
                            // }
                        }
                    }
                    daoUsuario.flush();
                }
            } else {
                System.out.println("Não foi mover");
            }
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            // TODO: Adaptar para arrastar grupos e tarefas tamb?m
            // GrupoTarefas g = listaGrupos.getSelectedValue();
            TreePath[] selecoes = arvoreTarefas.getSelectionPaths();
            // TODO: Obter

            List<GrupoTarefas> gruposS = new ArrayList<>();

            // TODO: Expo tarefa tamb
            List<Tarefa> tarefas = new ArrayList<>();
            for (TreePath ca : selecoes) {
                // ul = (DefaultMutableTreeNode) ca.getLastPathComponent();
                Object ou = ca.getLastPathComponent();// ul.getUserObject();
                LOG_CONTR_PRINC.debug("Obje exp:" + ou);
                if (ou instanceof GrupoTarefas) {
                    gruposS.add((GrupoTarefas) ou);
                } else if (ou instanceof Tarefa) {
                    LOG_CONTR_PRINC.debug("Tarefa exp");
                    tarefas.add((Tarefa) ou);
                }
            }

            // XXX: ideia criar metodos de convers?o/leitura de grupos e tarefas de xml
            TransfXMLT trsX = new TransfXMLT(DataFlavor.stringFlavor);

            ConversXML cx = new ConversXMLD();
            String xml = cx.geraXML(gruposS, tarefas);// saiB.toString();

            trsX.setDadTransXML(xml);
            this.selecoesMovi = selecoes;// .movidos = gruposS;

            return trsX;
        }
    }

    /**
     * Para ordenar tarefas por ord de prio
     */
    public static class ComparaTarPrio implements Comparator<Tarefa> {

        @Override
        public int compare(Tarefa tar1, Tarefa tar2) {

            // System.out.println("comp: " + tar1.getTitulo() + " " + tar1.getPrioridade() +
            // " " + tar2.getTitulo() + " " + tar2.getPrioridade());

            /*
             * Os itens considerados maiores ficam no fim da lista e os menores no início,
             * por isso deve ser bom que os de maiores prioridades devem ser considerados
             * menores
             */
            return tar2.getPrioridade().compareTo(tar1.getPrioridade());
        }
    }

    /**
     * Para ordenar tarefas por ord de prio
     */
    public static class ComparaGruposPrio implements Comparator<GrupoTarefas> {

        @Override
        public int compare(GrupoTarefas g1, GrupoTarefas g2) {
            System.out.println("comp: " + g1.getTitulo() + " " + g1.getTitulo());
            /*
             * Quais devem ser as estratégias para efetuar a ordenação dos grupos de acordo
             * com prioridades? Quais são meus desejos? Desejo que os na parte de cima
             * fiquem grupos que contenham tarefas de maior prioridade, mas parece que seria
             * ainda mais eficiente se os gupos cujas tarefas mais prio tive mesmo valor,
             * ficasse no topo os que tivesse mais de uma dela.
             */
 /*
             * Os itens considerados maiores ficam no fim da lista e os menores no início,
             * por isso deve ser bom que os de maiores prioridades devem ser considerados
             * menores
             */
            int retorno = 0;

            // Deve ser necessário comparar as tarefas de cada grupo
            List<Tarefa> tarefaG1 = g1.getTarefas();
            List<Tarefa> tarefaG2 = g2.getTarefas();

            Tarefa prioG1 = null;
            Tarefa prioG2 = null;

            if (tarefaG1.isEmpty() && tarefaG2.isEmpty()) {
                LOG_CONTR_PRINC.info("Dois grupos sem tarefas.");
                LOG_CONTR_PRINC.trace("Proc nos filhos.");

                /*
                 * Percebi que retornar zero aqui pode ser ruim. Parece que o certo seria ver se
                 * tem tarefa em algum sub -grupo do grupo, e assim por diante, em cada grupo o
                 * valor dele seria corr à tarefa de maior prio que fosse encontrada.
                 */

 /*
                 * Aqui parece que, se os subgrupos tivessem ordenados esse algo funcionaria
                 * melhor, pois as de maior pri est nos prim grupos
                 */
                List<GrupoTarefas> gruposG1 = g1.getSubgrupos();
                List<GrupoTarefas> gruposG2 = g1.getSubgrupos();

                if (!gruposG1.isEmpty() && !gruposG2.isEmpty()) {
                    // Busca

                    for (GrupoTarefas grupoG1 : gruposG1) {
                        if (!grupoG1.getTarefas().isEmpty()) {
                            prioG1 = grupoG1.getTarefas().get(0);
                            break;
                        } else {
                            prioG1 = procuraPrimTarefa(grupoG1);
                            break;
                        }
                    }

                    for (GrupoTarefas grupoG2 : gruposG2) {
                        if (!grupoG2.getTarefas().isEmpty()) {
                            prioG2 = grupoG2.getTarefas().get(0);
                            break;
                        } else {
                            prioG2 = procuraPrimTarefa(grupoG2);
                            break;
                        }
                    }

                    /*
                     * Busca por taref de sub term
                     */
                }

            }

            if (tarefaG1.isEmpty()) {
                LOG_CONTR_PRINC.info("Prim sem tarefas");
                // Aqui dev ser proc nos filh

                prioG1 = procuraPrimTarefa(g1);
            } else if (tarefaG2.isEmpty()) {
                prioG2 = procuraPrimTarefa(g2);
            } else {
                // Deve ser necessário obter-se as tarefas mais prioritarias de cada um.
                // Inicialmente prentendo supor que elas já estão ordena por essa qual
                prioG1 = tarefaG1.get(0);
                prioG2 = tarefaG2.get(0);
            }

            if (prioG1 == null && prioG2 == null) {
                return 0;
            }

            if (prioG1 != null && prioG2 == null) {

                return -1;
            }

            if (prioG1 == null && prioG2 != null) {
                return 1;
            }

            if (prioG1.getPrioridade() > prioG2.getPrioridade()) {
                // Grupo 1 deve ser menor
                return -1;
            } else if (prioG1.getPrioridade() < prioG2.getPrioridade()) {
                // Grupo 1 deve ser menor
                retorno = 1;
            } else {
                retorno = 0;
            }

            return retorno;
        }

        /**
         *
         * @param grupo
         * @return
         */
        private Tarefa procuraPrimTarefa(GrupoTarefas grupo) {
            LOG_CONTR_PRINC.info("Proc tar em " + grupo.getTitulo());
            Tarefa tarEnc = null;
            if (!grupo.getTarefas().isEmpty()) {
                tarEnc = grupo.getTarefas().get(0);
            } else {
                // Será nec proc em subg
                List<GrupoTarefas> grupos = grupo.getSubgrupos();
                for (GrupoTarefas sG : grupos) {
                    tarEnc = procuraPrimTarefa(sG);
                    break;
                }
            }
            LOG_CONTR_PRINC.info("Tar enc em " + grupo.getTitulo() + " : " + tarEnc);
            return tarEnc;
        }

    }

    public LocalDate convertData(Date d) {
        Instant t = d.toInstant();
        return t.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private class AcaoExcluirGrupo extends AbstractAction {

        public AcaoExcluirGrupo(Icon icone) {
            super("Excluir grupo", icone);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (grupoDaAtu != null) {
                // DefaultMutableTreeNode ngP = (DefaultMutableTreeNode) noGrupo.getParent();
                // Remove grupo da atu do pai
                GrupoTarefas grupoP = grupoDaAtu.getPai();
                System.out.println("clic em exc gr");
                if (grupoP != null) {// Subgrupo
                    System.out.println("Exc grupo sub: " + grupoDaAtu + " de " + grupoP + "...");

                    modeloArv.remove(grupoP, grupoDaAtu);
                    // Obs: atu g após del de grupo func mas de tar não. 06/07/18 ~09;18
                    daoUsuario.flush();
                } else {
                    // Da raiz

                    // Orphan rem
                    System.out.println("Exc grupo sem pai");
                    // grupoDaAtu.setDono(null);
                    if (!(grupoDaAtu.equals(usuario.getGrupoRaiz()))) {
                        gerg.deleta(grupoDaAtu);
                    } else {
                        System.out.println("Tentou excluir grupo raiz");
                    }

                }
                // OBs-29/07/18-09:00: flush deve remover grupo sem pai
            }
        }
    }

    private class AcaoNovoGr extends AbstractAction {

        public AcaoNovoGr(Icon icone) {
            super("Novo grupo", icone);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // XXX: Anot: Funcionou
            // Anot: 21/06/18 - Est? salvando um grupo no gripo filho rai e outro em raiz
            // o subgrupo com tarefa vai para a raiz e o sem continua no lugar
            String titulo = JOptionPane.showInputDialog(view, "T\u00edtulo do grupo:", "Novo grupo",
                    JOptionPane.QUESTION_MESSAGE);
            if (titulo != null & !"".equals(titulo)) {
                GrupoTarefas novoGrupo = new GrupoTarefas(titulo);
                if (grupoDaAtu != null) {
                    // [Grupo nao raiz]
                    // grupoDaAtu.add(novoGrupo);
                    modeloArv.insere(grupoDaAtu, novoGrupo);
                    // TODO: Fazer com model para atu arv
                    System.out.println("Novo grupo ad em: " + grupoDaAtu);
                    // noGrupo.add(new DefaultMutableTreeNode(novoGrupo));
                    // No modelo para atualizar!
                    // modeloArv.insertNodeInto(new DefaultMutableTreeNode(novoGrupo), noGrupo, 0);
                    // Atualiza o grupo pai
                    // Obs; quando era salvar dava err
                    // 04/07/18 ~8:35 a
                    // Apenas salva o novo jÃ¡ o pai deve estar s
                    // gerg.salva(novoGrupo);
                    // daoUsuario.salvaG(novoGrupo);
                    // gerg.atuG(grupoDaAtu);//salvaG(grupoDaAtu);
                    daoUsuario.flush();
                } else {
                    // Cert raiz
                    // Grupos da raiz apenas precisam de dono
                    // novoGrupo.setDono(usuario);
                    modeloArv.insere(usuario.getGrupoRaiz(), novoGrupo);
                    // usuario.getGrupoRaiz().add(novoGrupo);
                    daoUsuario.flush();// atu(usuario);
                    // gerg.salvaG(novoGrupo);
                    // modeloArv.insertNodeInto(new DefaultMutableTreeNode(novoGrupo), noPrinc, 0);
                }
                // Obs: salvar Tarefa estÃ¡ alterando o grupo pai
                System.out.println("Salva g...");
                // daoUsuario.salvaG(novoGrupo);
                atualizaEstadoDosMenusBotoes();
            }
        }
    }

    private class AcaoAtalho extends AbstractAction {

        public AcaoAtalho() {
            super("Criar atalho");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // int reto = seletorArquivos.showSaveDialog(JanelaPrincipal.this);
            String home = System.getProperty("user.home");
            System.out.println("Home: " + home);
            // PENDING: Terminar
            // OBS: Area t
        }

    }

    public class AcaoExcluirTudo extends AbstractAction {

        public AcaoExcluirTudo(Icon ic) {
            super("Excluir tudo", ic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DialogoConfirmarExcTudo dialogoConf = new DialogoConfirmarExcTudo(view);

            dialogoConf.setVisible(true);

            int st = dialogoConf.getReturnStatus();

            if (st == DialogoConfirmarExcTudo.RET_OK) {
                if (dialogoConf.getCheckSim().isSelected()) {
                    if (dialogoConf.getCheckBackup().isSelected()) {
                        daoUsuario.fazBackupB();
                    }
                    modeloArv.removeTudo();
                    daoUsuario.flush();
                    atualizaEstadoDosMenusBotoes();
                    atualizaBarraDeStatus();
                }
            }

        }
    }

    private class AcaoRecortar extends AbstractAction {

        public AcaoRecortar() {
            super("Recortar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            TransferHandler.getCutAction()
                    .actionPerformed(new ActionEvent(arvoreTarefas, ActionEvent.ACTION_PERFORMED, "cut"));
        }

    }

    private class AcaoColar extends AbstractAction {

        public AcaoColar() {
            super("Colar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            TransferHandler.getPasteAction()
                    .actionPerformed(new ActionEvent(arvoreTarefas, ActionEvent.ACTION_PERFORMED, "paste"));
        }

    }

    private class AcaoCopiar extends AbstractAction {

        public AcaoCopiar() {
            super("Copiar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            TransferHandler.getCopyAction()
                    .actionPerformed(new ActionEvent(arvoreTarefas, ActionEvent.ACTION_PERFORMED, "copy"));
        }

    }

    private class AcaoAgendar extends AbstractAction {

        public AcaoAgendar() {
            super("Agendar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DialogoAgend dA = new DialogoAgend(view, tarefaExibida);
            dA.setVisible(true);
        }

    }

    private class AdicCam extends AbstractAction {

        public AdicCam() {
            super("Adicionar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            modeloTabela.novaCoordenada();
        }
    }

    private class RemovCam extends AbstractAction {

        public RemovCam() {
            super("Remover");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int idx = view.getTabelaExibT().getSelectedRow();
            modeloTabela.remove(idx);
        }
    }

    /**
     *
     */
    private class AcaoBackup extends AbstractAction {

        public AcaoBackup() {
            super("Fazer backup banco");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Executar sql bakup com execupdate
            daoUsuario.fazBackupB();
        }

    }

    /**
     *
     */
    private class AcaoExibirPorPrioridade extends AbstractAction {

        public AcaoExibirPorPrioridade() {
            super("Tarefas prioritárias");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Executar sql bakup com execupdate
            JanelaExibTabela exibT = new JanelaExibTabela(view);
            exibT.setVisible(true);

            // Aqui deve ser bom dar com de preench tab
            List<Tarefa> todasAsTarefas = new ArrayList<>();
            GrupoTarefas gRaiz = usuario.getGrupoRaiz();
            copiaTarefas(gRaiz, todasAsTarefas);
            LOG_CONTR_PRINC.info("Qaunt de tarefas ob: " + todasAsTarefas.size());

            exibT.preenche(todasAsTarefas);
        }

    }

    private class AcaoVerDetDaTar extends AbstractAction {

        public AcaoVerDetDaTar() {
            super("Ver detalhes");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (tarefaExibida != null) {
                /*
                 * TODO: Talvez fosse bom desab a ação caso não houvesse uma tar sel
                 *
                 */
                DialogoVerTarefa diaVerTar = new DialogoVerTarefa(tarefaExibida, view);
                diaVerTar.setVisible(true);
            }
        }

    }
    // Fim ações//

    /**
     *
     * @param grupo
     * @param destino
     */
    private void copiaTarefas(GrupoTarefas grupo, List<Tarefa> destino) {
        // Adiciona tarefas nele
        destino.addAll(grupo.getTarefas());

        // Adiciona de filhos
        List<GrupoTarefas> subGr = grupo.getSubgrupos();
        subGr.forEach((gr) -> {
            copiaTarefas(gr, destino);
        });
    }

    // ID: Procurar saber mais se ? melhor fazer class anonima ou aninhada
    private class AdaptadorArvore extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {
            // Verificando se o bot?o direito do mouse foi clicado
            if (me.getButton() == MouseEvent.BUTTON3) {
                // listaGrupos.setSelectedIndex(listaGrupos.locationToIndex(me.getPoint()));
                // Verifica a area de transfer?ncia
                Clipboard clp = view.getToolkit().getSystemClipboard();

                boolean podeIm = false;
                if (clp.isDataFlavorAvailable(Tarefa.TAREFA_FLAVOR)
                        || clp.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                    podeIm = true;
                    LOG_CONTR_PRINC.debug("Pode importar area tran");
                }
                // XXX: EstÃ¡ sempre ativ
                // TODO: Analisar. Est? estanho. Parece dev ser me cola
                acaoColar.setEnabled(podeIm);// clp.isDataFlavorAvailable(Tarefa.TAREFA_FLAVOR));
                view.getMenuContextoArvore().show(arvoreTarefas, me.getX(), me.getY());
            }
        }
    }

    private class AdaptadorMouseLista extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {
            // LOG_CONTR_PRINC.trace("Clique mouse");
            // Verificando se o bot?o direito do mouse foi clicado
            if ((me.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
                // listaGrupos.setSelectedIndex(listaGrupos.locationToIndex(me.getPoint()));
                // Verifica a area de transfer?ncia
                Clipboard clp = view.getToolkit().getSystemClipboard();

                boolean podeIm = false;
                if (clp.isDataFlavorAvailable(Tarefa.TAREFA_FLAVOR)
                        || clp.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                    podeIm = true;
                    System.out.println("Pode importar area tran");
                }
                // XXX: EstÃ¡ sempre ativ
                // TODO: Analisar. Est? estanho. Parece dev ser me cola
                acaoColar.setEnabled(podeIm);// clp.isDataFlavorAvailable(Tarefa.TAREFA_FLAVOR));
                view.getMenuContextoArvore().show(view.getPainelLista().getTabelaTarefas(), me.getX(), me.getY());
            }
        }
    }

    /**
     * Ceca se estava editando e salva
     */
    private void checaESalva() {
        if (view.getPainelFunLadoDire().getBotaoEditarTarefa().getAction().equals(acaoSalvar)) {
            view.getPainelFunLadoDire().getBotaoEditarTarefa().doClick();
        }
    }

    private boolean mostraConfirma\u00e7\u00e3o(String titulo, String mensagem) {
        return JOptionPane.showConfirmDialog(view, mensagem, titulo, JOptionPane.YES_NO_OPTION) == 0;
    }

    /**
     * Exibe a tela de login
     */
    private void permiss\u00e3oECarrega() {
        dialogoLogin.setVisible(true);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    /**
     *
     * @param novaTarefa
     */
    public void adicTarefa(Tarefa novaTarefa) {

        LOG_CONTR_PRINC.traceEntry();

        /**
         * Obs que esse método está bloq
         */
        /*
         * Obtem o grupo selecionado
         */
        GrupoTarefas selec = grupoDaAtu;

        LOG_CONTR_PRINC.debug("Grupo sel det: " + selec);

        int idxAbaSel = view.getPainelLadoEsq().getSelectedIndex();
        if (selec == null || idxAbaSel == 0) {
            LOG_CONTR_PRINC.debug("Nao tem grupo sel ou primeira aba esc");

            modeloArv.insere(usuario.getGrupoRaiz(), novaTarefa);

            // Obs.
            // XXX: 05/07/18 ~13:11 - funcionou
            // XXX: Parece ser bom ter como marcar todo como feito
        } else {
            modeloArv.insere(selec, novaTarefa);

            // atualizaExibi\u00e7\u00e3oTarefas();
            // listaTarefas.setSelectedIndex(selec.recebeConte\u00fado().size() - 1);
            // FIXME: quant linha exp 1 retorna 0
        }

        modeloTab.adicionaTarefa(novaTarefa);

        totalFaz++;
        view.getLbQTF().setText(String.valueOf(totalFaz));
        // XXX: 05/07/18 ~13:11 - funcionou
        // OBS: 281218 12:59 - Apenas seleciona, não exibe
        arvoreTarefas.addSelectionPath(modeloArv.geraCam(novaTarefa));

        this.tarefaExibida = novaTarefa;

        acaoEditar.setEnabled(true);

        if (novaTarefa.getDataHoraLembrete() != null) {
            confDespNot(novaTarefa.getNotificacao());

            // LocalDateTime dataHL = novaTarefa.getDataHoraLembrete();
            // Instant ins = dataHL.toInstant(ZoneOffset.of("-3"));
            //
            // Date dataNot = Date.from(ins);
            //
            // TimerTask tarefaAl = new TimerTask() {
            // @Override
            // public void run() {
            // iconeGeretar.displayMessage("Tarefa", novaTarefa.getTitulo(),
            // TrayIcon.MessageType.WARNING);
            //
            // JOptionPane.showMessageDialog(view, novaTarefa.getTitulo(), "Tarefa",
            // JOptionPane.INFORMATION_MESSAGE);
            // }
            // };
            //
            // SimpleDateFormat formData = new SimpleDateFormat("dd/MM/yy kk:mm:ss");
            // System.out.println(formData.format(dataNot));
            // timerAlarme.schedule(tarefaAl, dataNot);
            // LOG_CONTR_PRINC.debug("Da c: " + dataNot);
            // LOG_CONTR_PRINC.debug("Da conf: " + dataHL);
            // LOG_CONTR_PRINC.trace("Tar age! ");
        }

        atualizaExibicaoTarefa(novaTarefa);
        // 13:03
        daoUsuario.flush();

        LOG_CONTR_PRINC.traceExit();
    }

    private void agendaAl(Tarefa t) {
        LocalDateTime dataHL = t.getDataHoraLembrete();
        Instant ins = dataHL.toInstant(ZoneOffset.of("-3"));

        Date dataNot = Date.from(ins);

        TimerTask tarefaAl = new TimerTask() {
            @Override
            public void run() {
                iconeGeretar.displayMessage("Tarefa", t.getTitulo(), TrayIcon.MessageType.WARNING);

                JOptionPane.showMessageDialog(view, t.getTitulo(), "Tarefa", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        SimpleDateFormat formData = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        System.out.println(formData.format(dataNot));
        timerAlarme.schedule(tarefaAl, dataNot);
        LOG_CONTR_PRINC.debug("Da c: " + dataNot);
        LOG_CONTR_PRINC.debug("Da conf: " + dataHL);
        LOG_CONTR_PRINC.trace("Tar age! ");
    }

    private void exportaXml() {
        getSeletorArquivos().setFileFilter(new FileNameExtensionFilter("XML", "xml"));
        getSeletorArquivos().setSelectedFile(new File("ExportTarefas" + Constantes.VERS + recebeStringData() + ".xml"));
        int res = getSeletorArquivos().showSaveDialog(view);
        if (res == JFileChooser.APPROVE_OPTION) {
            File arquivoEs = getSeletorArquivos().getSelectedFile();
            try {
                try ( FileOutputStream s = new FileOutputStream(arquivoEs)) {
                    exportaXMLParaS(s);
                }
            } catch (FileNotFoundException ex) {
                LOG_CONTR_PRINC.catching(ex);
            } catch (IOException ex) {
                LOG_CONTR_PRINC.catching(ex);
            }
        }
    }

    /**
     * Gera dados XML contendo todos os grupos e tarefas e o envia para o c?rrego especificado.
     *
     * @param saida
     */
    // XXX:Perguntar sobrescre
    private void exportaXMLParaS(OutputStream saida) throws IOException {
        ObjectMapper map = new XmlMapper();
        map.registerModule(new JavaTimeModule());
        map.registerModule(new Hibernate5Module().configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true));

        map.writeValue(saida, usuario.getGrupoRaiz());

        // String tarefasComoXML = converteTodasAsTarefasParaXML();
        // LOG_CONTR_PRINC.debug("XML Gerado trans s: {} ", tarefasComoXML);
        // OutputStreamWriter writer = new OutputStreamWriter(saida,
        // proprie.getProperty("encoding-exporta",
        // "UTF-8"));//"UTF-8");
        // try ( //saida.write(xml.getBytes());
        // BufferedWriter ea = new BufferedWriter(writer)) {
        // ea.write(xml);
        // ea.flush();
        // }
    }

    private String converteTodasAsTarefasParaXML() {
        ConversXMLD c = new ConversXMLD();
        c.setCharsetSaida(proprie.getProperty("encoding-exporta", "UTF-8"));
        String xml = c.geraXML(usuario.getGrupoRaiz().getSubgrupos(), usuario.getGrupoRaiz().getTarefas());

        return xml;
    }

    /**
     * Importa grupos tarefas de um InputStrem com arquivo XML (Do formato Geretar). Salva e exibe
     *
     * @param in
     */
    // FIXME: N?o atualiza contador tarefas fazer
    private void importaXML() {
        getSeletorArquivos().setFileFilter(new FileNameExtensionFilter("XML", "xml"));
        int res = getSeletorArquivos().showOpenDialog(view);
        if (res == JFileChooser.APPROVE_OPTION) {
            File arquivoEs = getSeletorArquivos().getSelectedFile();

            GrupoTarefas grupoLido = importaGrupoRaiz(arquivoEs);

            if (grupoLido == null) {
                LOG_CONTR_PRINC.warn("Grupo lido nulo");
                return;
            }

            grupoLido.getSubgrupos().forEach((GrupoTarefas gl) -> {
                modeloArv.insere(usuario.getGrupoRaiz(), gl);
            });

            grupoLido.getTarefas().forEach((t) -> {
                LOG_CONTR_PRINC.debug("Taref enc im");
                // Add de GrupT para alterar pai
                modeloArv.insere(usuario.getGrupoRaiz(), t);
            });

            atualizaBarraDeStatus();
            // Expande raiz
            arvoreTarefas.expandPath(new TreePath(usuario.getGrupoRaiz()));
            // Obs: NÃ£o exibiu
            System.out.println("Persistindo...");
            daoUsuario.flush();

        }
    }

    private GrupoTarefas importaGrupoRaiz(File f) {
        ObjectMapper map = new XmlMapper();
        map.registerModule(new JavaTimeModule());
        map.registerModule(new Hibernate5Module().configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true));

        GrupoTarefas grupoRaiz;

        try {
            grupoRaiz = map.readValue(f, GrupoTarefas.class);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }

        return grupoRaiz;
    }

    private void salvaDadosLidosUsandoDOM(File ar) {
        List<Object> gt = importaXMLComDOM(ar);

        LOG_CONTR_PRINC.debug("Adicionando no usu {} itens lidos...", gt.size());

        gt.forEach((Object o) -> {
            if (o instanceof GrupoTarefas) {
                GrupoTarefas gl = (GrupoTarefas) o;
                // Add para alterar pai
                modeloArv.insere(usuario.getGrupoRaiz(), gl);
            } else if (o instanceof Tarefa) {
                System.out.println("Taref enc im");
                // Add de GrupT para alterar pai
                modeloArv.insere(usuario.getGrupoRaiz(), o);
            }
        });
    }

    /**
     *
     * @param f
     * @return
     */
    private List<Object> importaXMLComDOM(File f) {
        ConversXMLD cx = new ConversXMLD();
        cx.setCharsetEntrada(proprie.getProperty("encoding-importacao", "UTF-8"));
        FileInputStream en = null;
        try {
            en = new FileInputStream(arquivoEs);
        } catch (FileNotFoundException ex) {
            LOG_CONTR_PRINC.catching(ex);
        }
        System.out.println("Interpretando...");
        List<Object> gt = cx.leGrupoETars(en);
        return gt;
    }

    private String recebeStringData() {
        Calendar ca = Calendar.getInstance();

        StringBuilder controiDataAtual = new StringBuilder();
        controiDataAtual.append(ca.get(Calendar.DAY_OF_MONTH));
        controiDataAtual.append(ca.get(Calendar.MONTH));
        controiDataAtual.append(ca.get(Calendar.YEAR));
        controiDataAtual.append("-").append(ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MINUTE));

        return controiDataAtual.toString();
    }

    /**
     *
     * @param mantem
     * @throws java.io.IOException
     */
    public void fazLogin(boolean mantem) throws IOException {
        LOG_CONTR_PRINC.traceEntry();
        // Combina??o de nome e senha
        usuario = dialogoLogin.getUsuario();

        // N?o permite login se n?o tem usu?rio
        if (daoUsuario.receUPorH(usuario.getNome(), usuario.getEmb()) == null) {
            LOG_CONTR_PRINC.error("Usu?rio n?o encontrado");
            return;
        }

        gerg.setUsu(usuario);

        // db = new BancoDadosTarefas(usuario);
        if (mantem) {
            // Mant?m login
            this.proprie.setProperty("manter", "true");
            proprie.setProperty("usuario", usuario.getNome());
            proprie.setProperty("hash", usuario.getEmb());
            gravaProp();
        } else {
            this.proprie.setProperty("manter", "false");
            proprie.setProperty("hash", "");
            gravaProp();
        }
        dialogoLogin.limpa();
        dialogoLogin.dispose();

        LOG_CONTR_PRINC.trace("Exibindo grupos...");
        exibeGrupos();

        LOG_CONTR_PRINC.traceExit();
    }

    /**
     * Atualiza estado(habilitado) dos menus e bot?es de acordo com a interface.
     */
    public void atualizaEstadoDosMenusBotoes() {
        // TreePath selec = tarefaExibida;//arvoreTarefas.getSelectionPath();
        Object sel = tarefaExibida;// selec.getLastPathComponent();

        final boolean haGrupoSelecionado = sel instanceof GrupoTarefas;
        // (grupoDaAtu != null);// listaGrupos.getSelectedIndex() > -1;
        final boolean h\u00e1TarefaSelecionada = sel instanceof Tarefa;
        // tarefaExibida != null;//listaTarefas.getSelectedIndex() > -1;

        if (usuario != null) {
            GrupoTarefas gr = usuario.getGrupoRaiz();
            final boolean temGruposOuTars = !gr.getSubgrupos().isEmpty() || !gr.isEmpty();

            view.getBotaoExcluirTudo().setEnabled(temGruposOuTars);
            view.getMenuExcluirTudo().setEnabled(temGruposOuTars);
        } else {
            LOG_CONTR_PRINC.warn("Usuario não def ainda");
        }

        // grupos != null && grupos.size() > 0;//modGt.getSize() > 0;
        // System.out.println("Grupo sel: " + haGrupoSelecionado);
        // System.out.println("Grupo se: " + listaGrupos.getSelectedIndex());
        // System.out.println("Grupo sel: " + haGrupoSelecionado);
        // System.out.println("Grupo se: " + listaGrupos.getSelectedIndex());
        view.getPainelFunLadoDire().getBotaoEditarTarefa().setEnabled(h\u00e1TarefaSelecionada);
        // botaoCancelarEditar.setEnabled(botaoEditarTarefa.getActionCommand().
        // equals("save"));
        view.getPainelFunLadoDire().getBotaoCopiaConteudo().setEnabled(h\u00e1TarefaSelecionada);
        view.getPainelFunLadoDire().getBotaoExcluirTarefa2().setEnabled(h\u00e1TarefaSelecionada);

        // acaoNovaTar.setEnabled(haGrupoSelecionado);
        // botaoNovaTarefa.setEnabled(haGrupoSelecionado);
        view.getBotaoExcluirTarefa().setEnabled(h\u00e1TarefaSelecionada);
        view.getBotaoExcluirGrupo().setEnabled(haGrupoSelecionado);
        // && !padraoSelecionado);

        view.getMenuExcluirGrupo().setEnabled(haGrupoSelecionado);
        // && !padraoSelecionado);
        // menuNovaTarefa.setEnabled(haGrupoSelecionado);
        view.getItemMenuExcluirTarefa().setEnabled(h\u00e1TarefaSelecionada);

        // TODO: Melhorar. Editando.
        view.getCaixaMarcarF()
                .setEnabled(view.getPainelFunLadoDire().getBotaoEditarTarefa().getActionCommand().equals("save"));

    }

    /**
     * Atualiza a barra de estado
     */
    public void atualizaBarraDeStatus() {
        // Ler apenas o raiz - Feito
        GrupoTarefas gr = usuario.getGrupoRaiz();
        int totFaz = contaTotalFazer(gr);
        view.getLbQTF().setText(String.valueOf(totFaz));
        totalFaz = totFaz;
    }

    private int contaTotalFazer(GrupoTarefas gr) {
        List<GrupoTarefas> subg = gr.getSubgrupos();
        List<Tarefa> grupTars = gr.getTarefas();
        int totF = 0;

        if (subg != null) {
            // TODO: Por em metodo sep
            for (GrupoTarefas g : subg) {
                // TODO: sub e filhas
                List<GrupoTarefas> subgB = g.getSubgrupos();
                totF = subgB.stream().map((gf) -> contaTotalFazer(gf)).reduce(totF, Integer::sum);
                List<Tarefa> liT = g.getTarefas();
                totF = liT.stream().filter((t) -> (!t.isConcluida())).map((_item) -> 1).reduce(totF, Integer::sum);
            }
        }

        // Conta tarefas
        grupTars.stream().filter((ta) -> (ta.isConcluida())).map((ite) -> 1).reduce(totF, Integer::sum);
        return totF;
    }

    /**
     * Atualiza a janela de acordo com a tarefa selecionada.
     */
    // TODO: Cncelr ediçao
    private void atualizaExibicaoTarefa(Tarefa tselec) {
        // System.out.println("Atua exib tar: " + tselec.getClass());
        // Se existir tarefa selecionada.
        // final Tarefa tselec = null;//; = (Tarefa) listaTarefas.getSelectedValue();
        if (tselec != null) {
            if (tselec instanceof TarefaComposta) {
                // TODO: Adapat para taref simp
                // Exibir clones
                List<TarefaCoordenada> clones = new ArrayList<>();

                List<TarefaCoordenada> orig = ((TarefaComposta) tselec).getTarefasFilhas();
                System.out.println("Exib. Quant orig: " + orig.size());
                orig.forEach((TarefaCoordenada tco) -> {
                    clones.add(tco.recebeClone());
                });
                modeloTabela.setCoords(clones);// Tarefa((TarefaComposta) tselec);
            }

            // //Atualiza o t?tulo
            view.getCampoTituloTarefa().setText(tselec.getTitulo());
            view.getCaixaMarcarF().setSelected(tselec.isConcluida());
            LocalDateTime dc = tselec.getDataConclusao();
            modeloCDataConc.setValue(dc == null ? null : dc.toLocalDate());
            // view.getCampoDataConc().setValue(dc == null ? dc : dc.toLocalDate());
            view.getCampoHoraCon().setValue(dc == null ? dc : dc.toLocalTime());
            modeloCData.setValue(tselec.getDataCriacao());
            // view.getPainelFunLadoDire().getCampoData().getModel().setValue(tselec.getDataCriacao());
            modeloCDataFaz.setValue(tselec.getDataFazer());
            // view.getPainelFunLadoDire().getCampoDataFazer().setValue(tselec.getDataFazer());
            view.getPainelFunLadoDire().getCampoPrioridade().setValue(tselec.getPrioridade());
            if (tselec.getNotificacao() != null) {
                modeloCDataAl.setValue(tselec.getNotificacao().getHoraExibicao().toLocalDate());
                // view.getPainelFunLadoDire().getCampoDataAl().setValue(tselec.getNotificacao().getHoraExibicao().toLocalDate());
                view.getPainelFunLadoDire().getCampoHoraAl()
                        .setValue(tselec.getNotificacao().getHoraExibicao().toLocalTime());
            } else {
                modeloCDataAl.setValue(null);
                // view.getPainelFunLadoDire().getCampoDataAl().setValue(tselec.getNotificacao().getHoraExibicao().toLocalDate());
                view.getPainelFunLadoDire().getCampoHoraAl().setValue(null);
            }
            // adiciCampoDes();
            // filhas.forEach((TarefaSimples t) -> {
            // System.out.println(t.getConteudo());
            // });
            // adiciCampoDes();
            // filhas.forEach((TarefaSimples t) -> {
            // System.out.println(t.getConteudo());
            // });
            // 21/06/18 20:33 - Persiste e rec funcionou!
        } else {
            // TODO: Limpar campos
            view.getCampoTituloTarefa().setText("");
            view.getCaixaMarcarF().setSelected(false);
            view.getCampoDataConc().getModel().setValue(null);
            view.getCampoHoraCon().setValue(null);
            view.getPainelFunLadoDire().getCampoData().getModel().setValue(null);
            view.getPainelFunLadoDire().getCampoDataFazer().getModel().setValue(null);
            view.getPainelFunLadoDire().getCampoDataAl().getModel().setValue(null);
            view.getPainelFunLadoDire().getCampoHoraAl().setValue(null);
            modeloTabela.limpa();// set//Tarefa(null);
            view.getPainelFunLadoDire().getCampoPrioridade().setValue(0);

        }
    }

    /**
     *
     */
    private class TarefaInicia implements Runnable {

        @Override
        public void run() {
            LOG_CONTR_PRINC.traceEntry();

            /*
             * Faz migração do banco
             * 
             */
            Flyway fw = Flyway.configure().baselineOnMigrate(true).baselineVersion("0")
                    .dataSource(HibernateUtil.determinaURIBanco(), "SA", "").load();

            try {
                fw.migrate();
            } catch (FlywayException ex) {
                if (ex.getCause() instanceof SQLException) {
                    SQLException excSQL = (SQLException) ex.getCause();
                    if (excSQL.getCause() instanceof HsqlException) {
                        HsqlException excHSQL = (HsqlException) excSQL.getCause();
                        LOG_CONTR_PRINC.trace(excHSQL.getErrorCode());
                        LOG_CONTR_PRINC.trace(excHSQL.getLevel());
                        LOG_CONTR_PRINC.trace(excHSQL.getMessage());
                        LOG_CONTR_PRINC.trace(excHSQL.getStatementCode());
                        LOG_CONTR_PRINC.trace(excHSQL.info);
                    }
                }
                LOG_CONTR_PRINC.catching(ex);
                try {
                    fw.repair();
                } catch (FlywayException ex2) {

                }
            }

            /*
             * Faz o bootstrap do Hibernate
             *
             */
            HibernateUtil.getInstance().inicia();

            dialogoLogin = new TelaLogin(view, true);
            gerg = new DAOGrupos(HibernateUtil.getInstance());
            daoUsuario = new DAOUsuario(HibernateUtil.getInstance());
            modeloArv.iniciaGer(daoUsuario);
            daoUsuario.abresSS();

            carregaUsuarioEDados();

            // Nesse ponto o sist já dev estar inc
            /*
             * Aqui deve ser bom se com com o serv de sinc
             */
            LOG_CONTR_PRINC.trace("Inici pro de sincro...");

            RestTemplate templ = new RestTemplate();

            URI uriInfo = null;

            try {
                uriInfo = new URI("http://localhost:8015/sinc/info");
            } catch (URISyntaxException ex) {
                java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

            LocalDateTime dataUlSincServ = null;
            try {
                dataUlSincServ = templ.getForObject(uriInfo, LocalDateTime.class);
            } catch (ResourceAccessException ex) {

                Throwable cause = ex.getCause();
                if (cause instanceof ConnectException) {
                    // Provavelmente o serv está offline
                    // Obs: talvez fosse bom apenas igonorar
                    // JOptionPane.showMessageDialog(view, "Houve uma falha de comunicão com o
                    // servidor");
                    LOG_CONTR_PRINC.info("Houve uma falha de comunicão com o servidor");
                    return;
                }
            }

            LOG_CONTR_PRINC.debug("Data ul atu rec " + dataUlSincServ);

            if (dataUlSincServ == null) {
                // Primeiro cl a se con. Enviar dados

                HttpHeaders head = new HttpHeaders();
                head.add("Accept", MediaType.APPLICATION_XML_VALUE);
                head.setContentType(MediaType.APPLICATION_XML);

                RestTemplate reT = new RestTemplate();
                GrupoTarefas gr = usuario.getGrupoRaiz();

                List<GrupoTarefas> subG = gr.getSubgrupos();
                LOG_CONTR_PRINC.trace("Qaunt g: " + subG.size());
                GrupoTarefas g1 = subG.get(0);
                LOG_CONTR_PRINC.debug("Envi: " + g1);
                Tarefa tar1 = g1.get(0);
                LocalDate data = tar1.getDataCriacao();

                HttpEntity<GrupoTarefas> reB = new HttpEntity<>(g1, head);

                URI uriEnviaGrupo = null;

                try {
                    uriEnviaGrupo = new URI("http://localhost:8015/grupo/");
                } catch (URISyntaxException ex) {
                    java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    LOG_CONTR_PRINC.trace("Fazendo requi...");
                    GrupoTarefas gt = reT.postForObject(uriEnviaGrupo, reB, GrupoTarefas.class);

                    if (gt != null) {
                        LOG_CONTR_PRINC.info("Grupo enviado +");
                    } else {
                        LOG_CONTR_PRINC.error("Falha no envio");
                    }
                } catch (RestClientException ex) {
                    ex.printStackTrace();

                    if (ex instanceof HttpClientErrorException) {
                        HttpClientErrorException hce = (HttpClientErrorException) ex;
                        LOG_CONTR_PRINC.error("Corpo resp: " + hce.getResponseBodyAsString());
                    }
                }
            }
        }
    }

    private void fazMigraçãoBanco() {

    }

    private class AcaoDiminuiPrio extends AbstractAction {

        public AcaoDiminuiPrio() {
            super("Diminui prio");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int linhaSel = view.getPainelLista().getTabelaTarefas().getSelectedRow();
            if (linhaSel == -1) {
                return;
            }
            int idxMod = view.getPainelLista().getTabelaTarefas().convertRowIndexToModel(linhaSel);
            Tarefa tarSel = modeloTab.getTarefas().get(idxMod);
            tarSel.setPrioridade(tarSel.getPrioridade() - 1);
            ordenadorTabelaLista.sort();
        }
    }

    private class AcaoAumentaPrio extends AbstractAction {

        public AcaoAumentaPrio() {
            super("Aumenta prio");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int linhaSel = view.getPainelLista().getTabelaTarefas().getSelectedRow();
            if (linhaSel == -1) {
                return;
            }
            int idxMod = view.getPainelLista().getTabelaTarefas().convertRowIndexToModel(linhaSel);
            Tarefa tarSel = modeloTab.getTarefas().get(idxMod);
            tarSel.setPrioridade(tarSel.getPrioridade() + 1);
            tarSel.setDataModif(LocalDateTime.now());
            ordenadorTabelaLista.sort();
        }
    }

    private class AcaoBuscar extends AbstractAction {

        public AcaoBuscar() {
            super("Buscar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            PainelListaTarefas painelLista = view.getPainelLista();
            String termo = painelLista.getCampoTextoBusca().getText();
            filtraTarefasLPorTit(termo, false);
        }
    }

    private class AcaoFiltrarTarefas extends AbstractAction {

        public AcaoFiltrarTarefas() {
            super("Filtrar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            PainelListaTarefas painLT = view.getPainelLista();

            LocalDate dataAg = null;
            if (painLT.getCheckDataAgend().isSelected()) {
                if (painLT.getRbHoje().isSelected()) {
                    dataAg = LocalDate.now();
                } else if (painLT.getRbFiltroAma().isSelected()) {
                    dataAg = LocalDate.now().plusDays(1);
                } else if (painLT.getRbDataEsp().isSelected()) {
                    dataAg = painLT.getCampoDataAgFil().getModeloDef().getValue();
                }

                // TODO: Adic campo
            }

            boolean apVenc = painLT.getCheckApenasVencidas().isSelected();

            filtraTarefasL(dataAg, apVenc);
        }
    }

    private void filtraTarefasL(LocalDate dataAg, boolean apVenc) {
        // TODO OBS Esse proc parece lento
        List<Tarefa> todasAsTar = obtemTodasAsTar();

        Stream<Tarefa> st = todasAsTar.stream();

        if (dataAg != null) {
            st = st.filter(t -> (t.getDataFazer() != null) && t.getDataFazer().equals(dataAg));
        }

        if (apVenc) {
            st = st.filter(t -> t.getDataFazer() != null && t.getDataFazer().isBefore(LocalDate.now()));
        }

        modeloTab.setTarefas(st.collect(Collectors.toList()));

        ordenadorTabelaLista.sort();
    }

    /**
     *
     * @param termo
     * @param apVenc
     */
    private void filtraTarefasLPorTit(String termo, boolean naoFeitas) {
        // TODO OBS Esse proc parece lento
        List<Tarefa> todasAsTar = obtemTodasAsTar();

        Stream<Tarefa> st = todasAsTar.stream();

        st = st.filter(t -> t.getTitulo().toLowerCase().contains(termo.toLowerCase()));

        if (naoFeitas) {
            st = st.filter(t -> t.isConcluida() == false);
        }

        modeloTab.setTarefas(st.collect(Collectors.toList()));

        ordenadorTabelaLista.sort();
    }

    public ModeloTabAgend getModAg() {
        return modAg;
    }

    public TableRowSorter getOrdenadorTabelaLista() {
        return ordenadorTabelaLista;
    }

}
