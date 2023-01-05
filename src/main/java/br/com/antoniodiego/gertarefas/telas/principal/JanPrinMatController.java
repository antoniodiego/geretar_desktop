package br.com.antoniodiego.gertarefas.telas.principal;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.TransferHandler;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.TreePath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.hsqldb.HsqlException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.DOMException;

import br.com.antoniodiego.gertarefas.Constantes;
import br.com.antoniodiego.gertarefas.exportacao_backup.TransfXMLT;
import br.com.antoniodiego.gertarefas.model.ModeloTabAgend;
import br.com.antoniodiego.gertarefas.model.ModeloTabNotif;
import br.com.antoniodiego.gertarefas.model.ModeloTabelaTarefasLista;
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
import br.com.antoniodiego.gertarefas.pojo.TipoVoto;
import br.com.antoniodiego.gertarefas.pojo.Usuario;
import br.com.antoniodiego.gertarefas.pojo.Voto;
import br.com.antoniodiego.gertarefas.telas.confirmacoes.DialogoConfirmarExcTudo;
import br.com.antoniodiego.gertarefas.telas.login.TelaLogin;
import br.com.antoniodiego.gertarefas.telas.modelos.ModeloArvore;
import br.com.antoniodiego.gertarefas.telas.modelos.ModeloData;
import br.com.antoniodiego.gertarefas.telas.modelos.ModeloTabelaTarefa;
import br.com.antoniodiego.gertarefas.telas.novatarefa.DialogoNovaTarView;

import br.com.antoniodiego.gertarefas.telas.principal.paineis.PainelListaTarefas;
import br.com.antoniodiego.gertarefas.util.ConversXML;
import br.com.antoniodiego.gertarefas.util.ConversXMLD;
import br.com.antoniodiego.gertarefas.util.HibernateUtil;

/**
 *
 * @author Ant�noio Diego <antoniodiegoluz at gmail.com>
 */
public class JanPrinMatController {

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

    private AcaoEditarTarefa acaoEditar;

    private JanelaPrincipalMatisse view;
    private JTree arvoreTarefas;
    public static final Logger LOG_CONTR_PRINC = LogManager.getLogger("Controller_Principal");
    private AcaoBackup acaoBackup;
    private AcaoExcluirGrupo acaoExG;
    private AcaoRecortar acaoRec;
    private AcaoCopiar acaoCop;
    private AcaoColar acaoColar;

    private AdicCam acaoAdic;

    private JButton botaRem;
    private AcaoReiniciar acaoReiniciarBanco;
    private AcaoExcluirTudo acaoExT;

    private AcaoAtalho acaoCriAt;

    private SpinnerNumberModel modeloCampoPro;

    private ModeloTabelaTarefa modeloTabela;
    private TrayIcon iconeGeretar;
    private int totalFaz;

    private ModeloTabNotif modTabNotif;
    JanelaPrincipalMatisse princ;

    public void instanciaJanelaPrincipal() {
        princ = new JanelaPrincipalMatisse();
    }

    public void exibeJanelaPrincipal() {

        EventQueue.invokeLater(() -> {
            // Boas práticas
            /*Exibe a janela na EDT
             */

            LOG_CONTR_PRINC.traceEntry();
            LOG_CONTR_PRINC.trace("Em run invoke later");
            LOG_CONTR_PRINC.trace("Antes setVisible");

          
            princ.setVisible(true);
        });
    }

    public void inicializaSistema() {
        //Excuta tarefa de inicialização em Thread
        new Thread(new TarefaInicia()).start();
    }

    /**
     * Deve fazer a inicialização do programa
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

            /* Nesse ponto o sist já dev estar inc
           * A partir daqui já deve ser possível fazer consulta do banco de dados.
            Seria interessante poder fazer isso mexendo apenas nos models.
             */
            DAOTarefa daoTarefa = new DAOTarefa();
            List<Tarefa> tarefas = daoTarefa.listaTodas();

            princ.getPainelTarefas().getModeloTabela().setTarefas(tarefas);
            princ.getPainelTarefas().getModeloTabela().ordena();
            //  princ.getPainelTarefas().getRs().sort();

            LOG_CONTR_PRINC.trace(tarefas.size() + " Tarefas carregadas no modelo da tabela");

            /*
             * Aqui deve ser bom se com com o serv de sinc
             */ //            LOG_CONTR_PRINC.trace("Inici pro de sincro...");
            //
            //            RestTemplate templ = new RestTemplate();
            //
            //            URI uriInfo = null;
            //
            //            try {
            //                uriInfo = new URI("http://localhost:8015/sinc/info");
            //            } catch (URISyntaxException ex) {
            //                java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            //            }
            //
            //            LocalDateTime dataUlSincServ = null;
            //            try {
            //                dataUlSincServ = templ.getForObject(uriInfo, LocalDateTime.class);
            //            } catch (ResourceAccessException ex) {
            //
            //                Throwable cause = ex.getCause();
            //                if (cause instanceof ConnectException) {
            //                    // Provavelmente o serv está offline
            //                    // Obs: talvez fosse bom apenas igonorar
            //                    // JOptionPane.showMessageDialog(view, "Houve uma falha de comunicão com o
            //                    // servidor");
            //                    LOG_CONTR_PRINC.info("Houve uma falha de comunicão com o servidor");
            //                    return;
            //                }
            //            }
            //
            //            LOG_CONTR_PRINC.debug("Data ul atu rec " + dataUlSincServ);
            //
            //            if (dataUlSincServ == null) {
            //                // Primeiro cl a se con. Enviar dados
            //
            //                HttpHeaders head = new HttpHeaders();
            //                head.add("Accept", MediaType.APPLICATION_XML_VALUE);
            //                head.setContentType(MediaType.APPLICATION_XML);
            //
            //                RestTemplate reT = new RestTemplate();
            //                //    GrupoTarefas gr = usuario.getGrupoRaiz();
            //
            //                List<GrupoTarefas> subG = gr.getSubgrupos();
            //                LOG_CONTR_PRINC.trace("Qaunt g: " + subG.size());
            //                GrupoTarefas g1 = subG.get(0);
            //                LOG_CONTR_PRINC.debug("Envi: " + g1);
            //                br.com.antoniodiego.gertarefas.pojo.Tarefa tar1 = g1.get(0);
            //                LocalDate data = tar1.getDataCriacao();
            //
            //                HttpEntity<GrupoTarefas> reB = new HttpEntity<>(g1, head);
            //
            //                URI uriEnviaGrupo = null;
            //
            //                try {
            //                    uriEnviaGrupo = new URI("http://localhost:8015/grupo/");
            //                } catch (URISyntaxException ex) {
            //                    java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            //                }
            //
            //                try {
            //                    LOG_CONTR_PRINC.trace("Fazendo requi...");
            //                    GrupoTarefas gt = reT.postForObject(uriEnviaGrupo, reB, GrupoTarefas.class);
            //
            //                    if (gt != null) {
            //                        LOG_CONTR_PRINC.info("Grupo enviado +");
            //                    } else {
            //                        LOG_CONTR_PRINC.error("Falha no envio");
            //                    }
            //                } catch (RestClientException ex) {
            //                    ex.printStackTrace();
            //
            //                    if (ex instanceof HttpClientErrorException) {
            //                        HttpClientErrorException hce = (HttpClientErrorException) ex;
            //                        LOG_CONTR_PRINC.error("Corpo resp: " + hce.getResponseBodyAsString());
            //                    }
            //                }
            //            }
        }
    }

    private void configuraIconeBandeja() {
        if (SystemTray.isSupported()) {
            SystemTray st = SystemTray.getSystemTray();
            ImageIcon imageIcGer = new ImageIcon(JanelaPrincipalMatisse.class.getResource("/imagens/icone lapis.png"));

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
        confPainelAg();
        confPainNotif();

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
//                atualizaExibicaoTarefa(null);
                acaoEditar.setEnabled(false);
                return;
            }

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
//                    atualizaExibicaoTarefa(t);
                    acaoEditar.setEnabled(true);
                } else if (sel instanceof GrupoTarefas) {
                    LOG_CONTR_PRINC.debug("Grupo folha!");
                    // noGrupo = sel;
                    grupoDaAtu = (GrupoTarefas) sel;
//                    atualizaExibicaoTarefa(null);
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
//                    atualizaExibicaoTarefa(null);
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

//            atualizaEstadoDosMenusBotoes();
        }
    };

    private TreeModelListener listMod = new TreeModelListener() {
        @Override
        public void treeNodesChanged(TreeModelEvent e) {
            Object[] filAlt = e.getChildren();
            Object fi1 = filAlt[0];
            if (fi1 == tarefaExibida) {
                // Alterada aponta para a mesma que está send exib
//                atualizaExibicaoTarefa((Tarefa) fi1);
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

    private Icon iconeAdicionarGrupo;
    private TableRowSorter ordenadorTabelaLista;
    private ModeloData modeloCData;
    private ModeloData modeloCDataFaz;
    private ModeloData modeloCDataAl;

    private ModeloTabAgend modAg;
    private List<Notificacao> nots;
    private String arquivoEs;

    /**
     * O id da próxima tarefa a ser registrada
     */
    private Long proximoId;

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

    }

   // private PainelAgController contrPA;

    private void confPainelAg() {
        modAg = new ModeloTabAgend();

    }

    private void confPainNotif() {
        modTabNotif = new ModeloTabNotif();

    }

    private TableColumn colunaHora;
    private TableColumn colD;

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

//    /**
//     * Faz a conexao e carregamento dos dados do banco
//     */
//    public void inicializa() {
//        LOG_CONTR_PRINC.traceEntry("Inicando Thread de inic");
//        Thread th = new Thread(new TarefaInicia());
//       th.start();
//    }
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
//                java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

            LocalDateTime dataUlSincServ = templ.getForObject(uriInfo, LocalDateTime.class);

            LOG_CONTR_PRINC.debug("Data ul atu rec " + dataUlSincServ);

        }
    }
    // ====== Fim das acões====== //

    public Properties getProp() {
        return this.proprie;
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

    private class AdicCam extends AbstractAction {

        public AdicCam() {
            super("Adicionar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            modeloTabela.novaCoordenada();
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

    private GrupoTarefas importaGrupoRaiz(File f) {
        ObjectMapper map = new XmlMapper();
        map.registerModule(new JavaTimeModule());
        map.registerModule(new Hibernate5Module().configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true));

        GrupoTarefas grupoRaiz;

        try {
            grupoRaiz = map.readValue(f, GrupoTarefas.class);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JanPrinMatController.class.getName()).log(Level.SEVERE, null, ex);

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

    private void fazMigraçãoBanco() {

    }

    private class AcaoDiminuiPrio extends AbstractAction {

        public AcaoDiminuiPrio() {
            super("Diminui prio");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            int linhaSel = view.getPainelLista().getTabelaTarefas().getSelectedRow();
//            if (linhaSel == -1) {
//                return;
//            }
//            int idxMod = view.getPainelLista().getTabelaTarefas().convertRowIndexToModel(linhaSel);
//            Tarefa tarSel = modeloTab.getTarefas().get(idxMod);
//            tarSel.setPrioridade(tarSel.getPrioridade() - 1);
//            ordenadorTabelaLista.sort();
        }
    }

    private class AcaoAumentaPrio extends AbstractAction {

        public AcaoAumentaPrio() {
            super("Aumenta prio");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            int linhaSel = view.getPainelLista().getTabelaTarefas().getSelectedRow();
//            if (linhaSel == -1) {
//                return;
//            }
//            int idxMod = view.getPainelLista().getTabelaTarefas().convertRowIndexToModel(linhaSel);
//            Tarefa tarSel = modeloTab.getTarefas().get(idxMod);
//            tarSel.setPrioridade(tarSel.getPrioridade() + 1);
//            tarSel.setDataModif(LocalDateTime.now());
//            ordenadorTabelaLista.sort();
        }
    }

    private class AcaoBuscar extends AbstractAction {

        public AcaoBuscar() {
            super("Buscar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            PainelListaTarefas painelLista = view.getPainelLista();
//            String termo = painelLista.getCampoTextoBusca().getText();
//            filtraTarefasLPorTit(termo, false);
        }
    }

    private class AcaoFiltrarTarefas extends AbstractAction {

        public AcaoFiltrarTarefas() {
            super("Filtrar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            PainelListaTarefas painLT = null;// = view.getPainelLista();

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
