package br.com.antoniodiego.gertarefas.controller;

import java.awt.EventQueue;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.antoniodiego.gertarefas.model.ModeloTabAgend;
import br.com.antoniodiego.gertarefas.model.ModeloTabNotif;
import br.com.antoniodiego.gertarefas.model.ModeloTabelaTarefasLista;
import br.com.antoniodiego.gertarefas.persist.DAOGrupos;
import br.com.antoniodiego.gertarefas.persist.DAOUsuario;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Notificacao;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.Usuario;
import br.com.antoniodiego.gertarefas.service.InicializacaoService;
import br.com.antoniodiego.gertarefas.service.LoginService;
import br.com.antoniodiego.gertarefas.service.TarefaService;
import br.com.antoniodiego.gertarefas.ui.confirmacoes.DialogoConfirmarExcTudo;
import br.com.antoniodiego.gertarefas.ui.login.TelaLogin;
import br.com.antoniodiego.gertarefas.ui.modelos.ModeloArvore;
import br.com.antoniodiego.gertarefas.ui.modelos.ModeloData;
import br.com.antoniodiego.gertarefas.ui.modelos.ModeloTabelaTarefa;
import br.com.antoniodiego.gertarefas.ui.novatarefa.DialogoNovaTarView;
import br.com.antoniodiego.gertarefas.ui.principal.JanelaPrincipalMatisse;

/**
 *
 * @author Ant�noio Diego <antoniodiegoluz at gmail.com>
 */
public class PrincipalController {

    private Usuario usuario;    

    private JanelaPrincipalMatisse view;
    private JTree arvoreTarefas;
    public static final Logger LOG_CONTR_PRINC = LogManager.getLogger("Controller_Principal");
    private JButton botaAdd;
    private JButton botaRem;
  

    private AcaoAtalho acaoCriAt;

    private SpinnerNumberModel modeloCampoPro;

 
    private TrayIcon iconeGeretar;
    private int totalFaz;

    private ModeloTabNotif modTabNotif;
    JanelaPrincipalMatisse princ;

   
    /**
     * Janela nova tarefa
     */
    private DialogoNovaTarView dialogoNovaTarefa;
    private TelaLogin dialogoLogin;

    private ModeloData modeloCDataConc;

    private Icon iconeAdicionarGrupo;
    private ModeloData modeloCData;
    private ModeloData modeloCDataFaz;
    private ModeloData modeloCDataAl;

    private List<Notificacao> nots;
    private String arquivoEs;

    /**
     * O id da próxima tarefa a ser registrada
     */
    private Long proximoId;

   

    public void instanciaJanelaPrincipal() {
        princ = new JanelaPrincipalMatisse();
        princ.setController(this);
       
        carregaPropriedades();
    }

    private void carregaPropriedades() {
      

    }

  

    public void exibeJanelaPrincipal() {

        EventQueue.invokeLater(() -> {
            // Boas práticas
            /*
             * Exibe a janela na EDT
             */

            LOG_CONTR_PRINC.traceEntry();
            LOG_CONTR_PRINC.trace("Em run invoke later");
            LOG_CONTR_PRINC.trace("Antes setVisible");

            princ.setVisible(true);
        });
    }


    public void inicializaSistema() {
        // Excuta tarefa de inicialização em Thread
        new Thread(new TarefaInicia()).start();
    }

   

    /**
     * Deve fazer a inicialização do programa
     */
    private class TarefaInicia implements Runnable {

        @Override
        public void run() {
            LOG_CONTR_PRINC.traceEntry();

            InicializacaoService inicializacaoService = new InicializacaoService();
            inicializacaoService.inicializar();
            carregaTarefas();
        }
    }

    /**
     * Carrega tarefas do banco e exibe na tela
     */
    public void carregaTarefas() {
        List<Tarefa> tarefas = carregaTarefasNaoConcluidas();
        atualizaTabelaTarefas(tarefas);
    }

    private void atualizaTabelaTarefas(List<Tarefa> tarefas) {
        princ.getPainelTarefas().getModeloTabela().setTarefas(tarefas);
        princ.getPainelTarefas().getModeloTabela().ordena();
        LOG_CONTR_PRINC.trace(tarefas.size() + " Tarefas carregadas no"
                + " modelo da tabela");
    }

    private List<Tarefa> carregaTarefasNaoConcluidas() {
        TarefaService tarefaService = new TarefaService();
        return tarefaService.getTarefasNaoConcluidas();
    }

    /**
     *
     * @param antiga     Versão antiga [atual no modelo]
     * @param versaoNova
     */
    public void atualizaTarefa(Tarefa antiga, Tarefa versaoNova) {
        // Faz tarefa ser recarregada

        ModeloTabelaTarefasLista modelo = princ.getPainelTarefas()
                .getModeloTabela();
        int idx = modelo.getTarefas().indexOf(antiga);

        modelo.getTarefas().set(idx, versaoNova);

        modelo.fireTableRowsUpdated(idx, idx);

    }

    private void configuraIconeBandeja() {
       
    }

    /**
     *
     */
    public void conf() {
        confPainelAg();
        confPainNotif();
    }

    private void confPainelAg() {
      
    }

    private void confPainelTarefasLista() {
        /*
         * Modelo da tabela para exib tarefas em form de lista
         */

    }

  

    private void confPainNotif() {
        modTabNotif = new ModeloTabNotif();

    }

    private TableColumn colunaHora;
    private TableColumn colD;



    // =========== Ações ============
   

  


  
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


    /**
     * Exibe a tela de login
     */
    private void permiss\u00e3oECarrega() {
        dialogoLogin.setVisible(true);
    }
   

    private void fazMigraçãoBanco() {

    }
}
