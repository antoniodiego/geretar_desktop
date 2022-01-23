package br.com.antoniodiego.gertarefas.telas.principal;

import br.com.antoniodiego.gertarefas.controller.JanelaPrincipalController;
import br.com.antoniodiego.gertarefas.igu.FormatadorJTime;
import br.com.antoniodiego.gertarefas.igu.modelos.FormatDatePick;
import br.com.antoniodiego.gertarefas.igu.modelos.ModeloData;

import br.com.antoniodiego.gertarefas.igu.renderers.RenderizadorGT;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.Properties;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.TreeCellRenderer;

import javax.swing.tree.TreeSelectionModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Classe que repredenta o JFrame da janela principal.
 *
 * @author Ant\u00f4nio Diego.
 */
//TODO: Facilitar login
//TODO: Op??o classificar em arvoreTar
//XXX: Parte da janela est? ficando branca enquanto o Hibernate inicia.
//[XXX: Melhorar icones. P?r ?cones novos nas tarefas e grupos do JTree]
//TODO: P?r menu enviar tarefas como xml por email.
//TODO: Por menu que cria um atalho e o envia para a area de trabalho
/*TODO: Por funcao backup(banco inteiro) com anota??o da data para checar se
backupo est? em dia ou houve modif*/
 /*TODO: Ideia: Por arquivos gerais sobre todos os ramos do projeto geretar: 
anota??es, melhorias , plan, bib, etc,
num reposit?rio - Feito*/
//TODO: Usar Actions nos menus e botoes - Fiz com alguns - 15/07/18 ~20 36
//TODO: Por sincronização com front 22/12/19 1059
//TODO: Aumentar largura inicial da árvore tarefas
public class JanelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG_PRINC = LogManager.
            getLogger(JanelaPrincipal.class);

    // Bot?es
    private JButton botaoExcluirGrupo;
    private JButton botaoExcluirTarefa;

    private JButton botaoExcluirTudo;
    private JButton botaoNovaTarefa;
    private JButton botaoNovoGrupo;
    // Menus
    private JMenuItem menuExcluirGrupo;
    private JMenuItem menuExcluirTudo;
    private JMenuItem itemMenuExcluirTarefa;
    private JMenuItem menuNovaTarefa;
    /**
     * ?rvore que exibe tarefas
     */
    private JTree arvoreTarefas;
    ;
    private JTextField campoTituloTarefa;
    private JMenuItem itemSalvarComoTexto;
    private JCheckBox caixaMarcarF;
    private JMenuItem itemOpcoes;
    private JMenuItem itemExportarXML;
    private JMenuItem itemImportarXML;
    private JMenuItem menuColarTar;
    private JMenuItem menuRecorta;
    private JMenuItem menuCopiaG;
    private JLabel lbQTF;
    private JDatePickerImpl campoDataConc;
    private JMenuItem itemFazerBackup;
    private JMenuItem itemCriarAta;
    private JPopupMenu menuContextoArvore;
    private JMenuItem menuColar;
    private JTable tabelaExibT;
    private JButton botaoAdic;
    private JFormattedTextField campoHoraCon;
    private JMenuItem itemExibirPorPri;
    private final JanelaPrincipalController control;
    private JMenuItem miCol;
    private JMenu menuArquivo;
    private ImageIcon iconeNovaTarefa;
    private JMenuItem menuNovoGrupo;
    private JMenuItem menuTrocarUsuario;
    private JMenuItem menuSair;
    private ImageIcon iconeExcluirTudo;
    private JSplitPane divisorArvTab;
    private ImageIcon iconeAdicionarGrupo;
    private JMenuItem menuSinc;
    private JMenuItem miCop;
    private JMenuItem itemSobre;
    private Icon acaoNovaTar;
    private JMenuItem menuNovTAr;
    private JMenuItem menuNovGAr;
    private JMenuItem menuRec;
    private JButton botaRem;
    private JButton btVerDetTarefa;
    private ImageIcon iconeExcluirGrupo;
    private JTabbedPane tabPaneLadoEsq;
    //private JTable tabelaTarefas;
    private JTabbedPane tabPaneLadoDireito;
    private ImageIcon iconeExcluirTarefa;
    private ImageIcon iconeTrocarUsuario;
    private ImageIcon iconeSair;
    private PainelAgendamentosMat painelAge;
    private JMenuItem menuAgendar;
    private PainelFunc painelFunLa;
    private JPopupMenu menuContextoTabTar;
    private JMenuItem menuDetalhes;
    private JMenuItem menuAgendarBM;
    private JMenuItem menuVerDetBM;
    private PainelListaTarefas painelLista;
    private PainelNotificacoesM painelNotif;

    /**
     * Constr?i o programa.
     */
    //Cria??o tela login inic hibernate( demora) - cert corrigdo
    public JanelaPrincipal() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        control = new JanelaPrincipalController(this);
        constroiJanela();
        control.conf();
    }

    public static void main(String[] args) {
        JanelaPrincipal jP = new JanelaPrincipal();
        jP.setVisible(true);
    }
    
    public JMenuItem getItemExibirPorPri() {
        return itemExibirPorPri;
    }

    public JTable getTabelaExibT() {
        return tabelaExibT;
    }

    public JMenuItem getMenuNovaTarefa() {
        return menuNovaTarefa;
    }

    public JMenu getMenuArquivo() {
        return menuArquivo;
    }

    public ImageIcon getIconeNovaTarefa() {
        return iconeNovaTarefa;
    }

    public JMenuItem getMenuNovoGrupo() {
        return menuNovoGrupo;
    }

    public JMenuItem getMenuExcluirTudo() {
        return menuExcluirTudo;
    }

    public JMenuItem getMenuExcluirGrupo() {
        return menuExcluirGrupo;
    }

    public JMenuItem getMenuTrocarUsuario() {
        return menuTrocarUsuario;
    }

    public JMenuItem getMenuSair() {
        return menuSair;
    }

    public JMenuItem getItemMenuExcluirTarefa() {
        return itemMenuExcluirTarefa;
    }

    public JMenuItem getItemFazerBackup() {
        return itemFazerBackup;
    }

    public JMenuItem getItemCriarAta() {
        return itemCriarAta;
    }

    public JLabel getLbQTF() {
        return lbQTF;
    }

    public JMenuItem getItemSalvarComoTexto() {
        return itemSalvarComoTexto;
    }

    public JMenuItem getItemExportarXML() {
        return itemExportarXML;
    }

    public JMenuItem getItemImportarXML() {
        return itemImportarXML;
    }

    public JMenuItem getItemOpcoes() {
        return itemOpcoes;
    }

    public ImageIcon getIconeExcluirGrupo() {
        return iconeExcluirGrupo;
    }

    /**
     * Constroi janela principal <del>Minhas tarefas</del>
     */
    //TODO: Slvar modo classifica??o
    //PENDING: Por campo data fazer uma linha acima de barra total faz
    private void constroiJanela() {
        LOG_PRINC.traceEntry();
        // Icones
        Image iconePrograma = new ImageIcon(JanelaPrincipal.class
                .getResource("/imagens/icone lapis.png")).getImage();//l\u00e1pis.png"))
        // .getImage();
        iconeNovaTarefa = new ImageIcon(JanelaPrincipal.class
                .getResource("/imagens/nova.png"));
        iconeExcluirTarefa = new ImageIcon(JanelaPrincipal.class
                .getResource("/imagens/excluir.png"));
        iconeAdicionarGrupo = new ImageIcon(JanelaPrincipal.class
                .getResource("/imagens/novo grupo.png"));
        iconeExcluirGrupo = new ImageIcon(JanelaPrincipal.class
                .getResource("/imagens/excluir grupo.png"));
        iconeExcluirTudo = new ImageIcon(JanelaPrincipal.class
                .getResource("/imagens/excluir tudo.png"));
        iconeTrocarUsuario = new ImageIcon(JanelaPrincipal.class
                .getResource("/imagens/trocar usu\u00e1rio.png"));
        iconeSair = new ImageIcon(JanelaPrincipal.class
                .getResource("/imagens/sair.png"));

        setTitle("Gerenciador de tarefas");
        setIconImage(iconePrograma);
        //  setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationByPlatform(true);

        // setLayout(new GridBagLayout());
        //Adicionar componentes
        //Barra de menus
        adicionaBarraMenus();

        //Barra de ferramentas
        criaBarraDeFerr();

        //Visu que fica abaixo da barra de ferr
//        JPanel painelDet = getPainDetTar();
//        GridBagConstraints constPDet = new GridBagConstraints();
//        constPDet.gridy = 1;
//        constPDet.gridx = 4;
//        add(painelDet, constPDet);
//        GridBagConstraints constDivisor = new GridBagConstraints();
//        constDivisor.gridy = 1;
//        constDivisor.gridx = 0;
//        constDivisor.gridheight = 14;
//        constDivisor.weighty = 1;//Metade para ele
//        constDivisor.fill = GridBagConstraints.BOTH;
//
//        Insets insDiv = new Insets(2, 2, 2, 2);
//        //4 arv e 4 tabela conforme quant comp em cima tab
//        constDivisor.gridwidth = 8;
//        constDivisor.insets = insDiv;
        add(getPainelLadoEsq(), BorderLayout.LINE_START);

        //Terceira coluna
//        GridBagConstraints constPainelLadoDir = new GridBagConstraints();
//        constPainelLadoDir.gridy = 2;
//        constPainelLadoDir.gridx = 1;
//        constPainelLadoDir.gridheight = 14;
//        constPainelLadoDir.weighty = 1;//Metade para ele
//        constPainelLadoDir.fill = GridBagConstraints.BOTH;
//        //4 arv e 4 tabela conforme quant comp em cima tab
//
//        painelConteudo.add(tabPaneLadoDireito, constPainelLadoDir);
        add(getPainelCentral(), BorderLayout.CENTER);
        add(getTabPaneLadoDir(), BorderLayout.LINE_END);
        add(getPainelInferior(), BorderLayout.PAGE_END);
        // adicionaWidParteInferior();

        pack();
        LOG_PRINC.traceExit();
    }
//Fim de controi janela

    private JSplitPane getDivEsq() {
        tabPaneLadoEsq = getPainelLadoEsq();
        /*A seguir se encontra o JSPlit spane que divide a janela em dois lados
         */
        divisorArvTab = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, tabPaneLadoEsq, getPainelCentral());
        //Localização proporcional de 30%
        divisorArvTab.setDividerLocation(0.2);
        divisorArvTab.setOneTouchExpandable(true);
        divisorArvTab.setResizeWeight(0.4);

        return divisorArvTab;
    }

    private JSplitPane getDivDir() {
        JSplitPane spLadoDi = new JSplitPane();
        divisorArvTab = getDivEsq();
        spLadoDi.setLeftComponent(divisorArvTab);

        spLadoDi.setRightComponent(getTabPaneLadoDir());
        spLadoDi.setDividerLocation(0.9);
        spLadoDi.setOneTouchExpandable(true);
        spLadoDi.setResizeWeight(0.9);

        return spLadoDi;
    }

    private JTabbedPane getTabPaneLadoDir() {
        if (tabPaneLadoDireito == null) {
            tabPaneLadoDireito = new JTabbedPane();
            tabPaneLadoDireito.insertTab("Funções", null, new JScrollPane(getPainelFunLadoDire()), "Ações", 0);
            tabPaneLadoDireito.insertTab("Notificações", null, getPainelNotif(), "Notificações", 1);
            tabPaneLadoDireito.insertTab("Agendamentos", null, getPainelAgendamentos(), "Agendamentos", 2);
            tabPaneLadoDireito.setMinimumSize(new Dimension(200, 400));
            tabPaneLadoDireito.setPreferredSize(new Dimension(250, 400));
            tabPaneLadoDireito.setMaximumSize(new Dimension(250, 400));
        }

        return tabPaneLadoDireito;
    }

    /**
     *
     * @return
     */
    public PainelNotificacoesM getPainelNotif() {
        if (painelNotif == null) {
            painelNotif = new PainelNotificacoesM();//JPanel(new GridBagLayout());
        }

//        tabelaNotif = new JTable();
//
//        JScrollPane scrollPTabNot = new JScrollPane(getTabelaNotif());
        return painelNotif;
    }

    private JPanel getPainelSubNotif() {
        JPanel paineSup = new JPanel();

        return paineSup;
    }

    private JPanel getPainelInfNotif() {
        JPanel paineInfNot = new JPanel();

        return paineInfNot;
    }
//
//    public JTable getTabelaNotif() {
//        if (tabelaNotif == null) {
//            tabelaNotif = new JTable();
//        }
//
//        return tabelaNotif;
//    }

    public PainelAgendamentosMat getPainelAgendamentos() {
        if (painelAge == null) {
            painelAge = new PainelAgendamentosMat();
        }

        return painelAge;
    }

    //TODO: Está par bom usar um pain e sep em outra classe
    public JTabbedPane getPainelLadoEsq() {
        if (tabPaneLadoEsq == null) {
            tabPaneLadoEsq = new JTabbedPane();

            painelLista = new PainelListaTarefas();

            LOG_PRINC.trace("Adic arv");
            JScrollPane painRArv = new JScrollPane(getArvoreTarefas());
            painRArv.setPreferredSize(new Dimension(200, 400));

            tabPaneLadoEsq.insertTab("Lista", null, painelLista, "Visu lista", 0);
            tabPaneLadoEsq.insertTab("Vis arv", null, painRArv, "Visualização como árvore", 1);

            tabPaneLadoEsq.setMinimumSize(new Dimension(1000, 400));
            //tabPaneLadoEsq.setPreferredSize(new Dimension(250, 400));
        }

        return tabPaneLadoEsq;
    }

    public JPopupMenu getMenuContextoTabTar() {
        if (menuContextoTabTar == null) {
            menuContextoTabTar = new JPopupMenu();
        }

        return menuContextoTabTar;
    }

    public JPopupMenu getMenuContextoArvore() {
        if (menuContextoArvore == null) {
            //TODO: Bot?o reiniciar banco
            //Menu de contexto da árvore
            menuContextoArvore = new JPopupMenu();
            //TODO: Op??o classificar

            menuAgendar = new JMenuItem();
            menuNovTAr = new JMenuItem(acaoNovaTar);
            menuNovGAr = new JMenuItem();
            menuDetalhes = new JMenuItem();
            menuRec = new JMenuItem();
            menuCopiaG = new JMenuItem();
            menuColar = new JMenuItem();

            menuContextoArvore.add(menuAgendar);
            menuContextoArvore.add(menuNovTAr);
            menuContextoArvore.add(menuNovGAr);
            menuContextoArvore.add(menuDetalhes);
            menuContextoArvore.add(menuRec);
            menuContextoArvore.add(menuCopiaG);
            menuContextoArvore.add(menuColar);
        }

        return menuContextoArvore;
    }

//    
//     public JPopupMenu getMenuContextoArvore() {
//        if (menuContextoArvore == null) {
//            //TODO: Bot?o reiniciar banco
//            //Menu de contexto da árvore
//            menuContextoArvore = new JPopupMenu();
//            //TODO: Op??o classificar
//
//            menuAgendar = new JMenuItem();
//            menuNovTAr = new JMenuItem(acaoNovaTar);
//            menuNovGAr = new JMenuItem();
//            menuDetalhes = new JMenuItem();
//            menuRec = new JMenuItem();
//            menuCopiaG = new JMenuItem();
//            menuColar = new JMenuItem();
//
//            menuContextoArvore.add(menuAgendar);
//            menuContextoArvore.add(menuNovTAr);
//            menuContextoArvore.add(menuNovGAr);
//            menuContextoArvore.add(menuDetalhes);
//            menuContextoArvore.add(menuRec);
//            menuContextoArvore.add(menuCopiaG);
//            menuContextoArvore.add(menuColar);
//        }
//
//        return menuContextoArvore;
//    }
    public JMenuItem getMenuAgendar() {
        return menuAgendar;
    }

    public JMenuItem getMenuDetalhes() {
        return menuDetalhes;
    }

    public JMenuItem getMenuAgendarBM() {
        return menuAgendarBM;
    }

    public JMenuItem getMenuVerDetBM() {
        return menuVerDetBM;
    }

    private void adicionaBarraMenus() {
        // Barra de menus
        JMenuBar barraMenus = new JMenuBar();
        setJMenuBar(barraMenus);
        menuArquivo = new JMenu("Arquivo");
        barraMenus.add(menuArquivo);

        menuNovaTarefa = new JMenuItem();
        menuNovoGrupo = new JMenuItem();
        menuAgendarBM = new JMenuItem();
        menuVerDetBM = new JMenuItem();

        menuArquivo.add(menuNovaTarefa);
        menuArquivo.add(menuNovoGrupo);
        menuArquivo.add(menuAgendarBM);
        menuArquivo.add(menuVerDetBM);
        menuExcluirTudo = new JMenuItem();
        menuArquivo.add(menuExcluirTudo);
        menuTrocarUsuario = new JMenuItem("Trocar usu\u00e1rio",
                iconeTrocarUsuario);
        menuTrocarUsuario.setActionCommand("relogin");
        menuArquivo.add(menuTrocarUsuario);
        menuSair = new JMenuItem("Sair", iconeSair);
        menuSair.setActionCommand("exit");
        menuArquivo.add(menuSair);
        menuExcluirGrupo = new JMenuItem();
        menuArquivo.add(menuExcluirGrupo);
        itemMenuExcluirTarefa = new JMenuItem("Excluir tarefa", iconeExcluirTarefa);
        itemMenuExcluirTarefa.setActionCommand("deleta_tarefa");
        menuArquivo.add(itemMenuExcluirTarefa);
        menuArquivo.add(control.getAcaoReiniciarBanco());
        itemFazerBackup = new JMenuItem();
        menuArquivo.add(itemFazerBackup);
        itemCriarAta = new JMenuItem();
        //TODO: Hbilitar após estar completo. 
        //menuArquivo.add(itemCriarAta);
        itemSalvarComoTexto = new JMenuItem("Exportar como texto");
        menuArquivo.add(itemSalvarComoTexto);
        itemExportarXML = new JMenuItem("Exportar XML");
        menuArquivo.add(itemExportarXML);
        itemImportarXML = new JMenuItem("Importar XML");
        menuArquivo.add(itemImportarXML);
        menuSinc = new JMenuItem();
        menuArquivo.add(menuSinc);
        //Aqui termina const de menu arquivo

        JMenu menuEditar = new JMenu("Editar");
        barraMenus.add(menuEditar);

        menuRecorta = new JMenuItem();

        //OBS: Parece que se adic menu em dois fica apenas em 1
        menuEditar.add(menuRecorta);

        miCop = new JMenuItem();
        menuEditar.add(miCop);
        miCol = new JMenuItem();
        menuEditar.add(miCol);
        itemOpcoes = new JMenuItem("Op\u00e7\u00f5es");
        menuEditar.addSeparator();
        menuEditar.add(itemOpcoes);

        /*
         */
        JMenu menuFuncio = new JMenu("Exibir");
        itemExibirPorPri = new JMenuItem();
        menuFuncio.add(itemExibirPorPri);
        barraMenus.add(menuFuncio);

        JMenu menuAjuda = new JMenu("Ajuda");
        barraMenus.add(menuAjuda);
        itemSobre = new JMenuItem("Sobre");
        itemSobre.setActionCommand("sobre");
        menuAjuda.add(itemSobre);

    }

    private JScrollPane getSPTabelaExibT() {
        tabelaExibT = new JTable();
//XXX: adic col hora pois nõ fica bom ed data e hor em ima col

        JScrollPane scrolT = new JScrollPane(tabelaExibT);

        return scrolT;
    }

    private JPanel getPainelCentral() {
        JPanel painelCent = new JPanel(new GridBagLayout());

        JPanel painelDet = getPainDetTar();
        GridBagConstraints gbcPaDet = new GridBagConstraints();
        gbcPaDet.gridx = 0;
        gbcPaDet.gridy = 0;
        gbcPaDet.anchor = GridBagConstraints.FIRST_LINE_START;
        painelCent.add(painelDet, gbcPaDet);

        GridBagConstraints gbcTabExiT = new GridBagConstraints();
        gbcTabExiT.gridx = 0;
        gbcTabExiT.gridy = 1;
        gbcTabExiT.gridheight = 14;
        gbcTabExiT.weighty = 1;
        gbcTabExiT.weightx = 1;
        gbcTabExiT.fill = GridBagConstraints.BOTH;
        gbcTabExiT.gridwidth = 3;

        Insets insMa = new Insets(2, 2, 2, 2);
        gbcTabExiT.insets = insMa;

        painelCent.add(getSPTabelaExibT(), gbcTabExiT);

        GridBagConstraints constBarrF = new GridBagConstraints();
        constBarrF.gridx = 0;
        constBarrF.gridy = 15;
        constBarrF.anchor = GridBagConstraints.FIRST_LINE_START;
        constBarrF.gridwidth = 3;
        painelCent.add(getBarrFPaiCen(), constBarrF);

        painelCent.setMinimumSize(new Dimension(400, 300));

        return painelCent;
    }
    private JDatePanel painelData;

    private JPanel getPainelFunPCen() {
        JPanel pain = new JPanel();

        botaoAdic = new JButton();
        GridBagConstraints consBA = new GridBagConstraints();
        consBA.gridx = 0;
        consBA.gridy = 0;
        //   consBA.weighty = 0;//Todo espaço vertical

        pain.add(botaoAdic, consBA);

        botaRem = new JButton();
        GridBagConstraints consBR = new GridBagConstraints();
        consBR.gridx = 1;
        consBR.gridy = 0;
        pain.add(botaRem, consBR);

        btVerDetTarefa = new JButton();
        GridBagConstraints constBtVerTar = new GridBagConstraints();
        constBtVerTar.gridx = 2;
        constBtVerTar.gridy = 0;

        pain.add(btVerDetTarefa, constBtVerTar);

        return pain;
    }

    private JToolBar getBarrFPaiCen() {
        JToolBar barrFPC = new JToolBar("Ações com tar");

        barrFPC.setBorderPainted(true);
        barrFPC.setFloatable(false);

        botaoAdic = new JButton();
        botaRem = new JButton();
        btVerDetTarefa = new JButton();

        barrFPC.add(botaoAdic);
        barrFPC.add(botaRem);
        barrFPC.add(btVerDetTarefa);

        return barrFPC;
    }

    /**
     *
     */
    private JPanel getPainDetTar() {
        JPanel painel = new JPanel(new GridBagLayout());

        final JLabel rotuloTitulo = new JLabel("Tarefa: ");
        GridBagConstraints consT = new GridBagConstraints();
        consT.gridy = 0;
        consT.gridx = 0;
        consT.anchor = GridBagConstraints.FIRST_LINE_START;
        painel.add(rotuloTitulo, consT);

        //Campo título
        campoTituloTarefa = new JTextField();
        campoTituloTarefa.setEditable(false);
        campoTituloTarefa.setColumns(25);
        campoTituloTarefa.setMinimumSize(new Dimension(300, 25));
        //campoTituloTarefa.setPreferredSize(new Dimension(320, 25));

        GridBagConstraints conCampoT = new GridBagConstraints();

        conCampoT.gridx = 1;
        conCampoT.fill = GridBagConstraints.HORIZONTAL;
        painel.add(campoTituloTarefa, conCampoT);

        caixaMarcarF = new JCheckBox("Conclu\u00edda");

        GridBagConstraints consCaixaMar = new GridBagConstraints();

        consCaixaMar.gridx = 2;
        painel.add(caixaMarcarF, consCaixaMar);
        painelData = new JDatePanelImpl(new ModeloData(), new Properties());

        //XXX- Apenas getInstance tinha at? horas
        campoDataConc = new JDatePickerImpl((JDatePanelImpl) painelData,
                new FormatDatePick());

//JFormattedTextField(new FormatadorJTime(false));
        //    campoDataConc.setColumns(11);
        campoDataConc.setTextEditable(false);
        //setEditable(false);
        campoDataConc.setMinimumSize(new Dimension(120, 25));
        GridBagConstraints consDataCon = new GridBagConstraints();

        consDataCon.gridx = 3;
        consDataCon.fill = GridBagConstraints.HORIZONTAL;
        painel.add(campoDataConc, consDataCon);

        campoHoraCon = new JFormattedTextField(new FormatadorJTime(true));
        campoHoraCon.setColumns(8);
        campoHoraCon.setEditable(false);
        campoHoraCon.setMinimumSize(new Dimension(50, 25));

        GridBagConstraints consCH = new GridBagConstraints();
        consCH.gridx = 4;
        Insets insMargEsq = new Insets(0, 4, 0, 0);
        consCH.insets = insMargEsq;
        painel.add(campoHoraCon, consCH);

        return painel;
    }

    private JToolBar criaBarraDeFerr() {
        //Barra de ferram
        JToolBar barraFerramentas = new JToolBar();
        barraFerramentas.setFloatable(false);

        add(barraFerramentas, BorderLayout.PAGE_START);

        // Bot?es tarefa
        botaoNovaTarefa = new JButton();

        botaoExcluirTarefa = new JButton("Excluir", iconeExcluirTarefa);

        botaoExcluirTarefa.setActionCommand("deleta_tarefa");
        botaoExcluirTudo = new JButton();

        botaoExcluirGrupo = new JButton();
        botaoNovoGrupo = new JButton();

        barraFerramentas.add(botaoNovaTarefa);
        barraFerramentas.add(botaoExcluirTarefa);
        //barraFerramentas.addSeparator();
        barraFerramentas.add(botaoNovoGrupo);
        barraFerramentas.add(botaoExcluirGrupo);
        // barraFerramentas.add(botaoExcluirTudo);

        return barraFerramentas;
    }

    public PainelFunc getPainelFunLadoDire() {
        if (painelFunLa == null) {
            painelFunLa = new PainelFunc();

        }
        return painelFunLa;
    }

    private void adicionaWidParteInferior() {

        GridBagConstraints consLTF = new GridBagConstraints();
        JLabel lbTTF = new JLabel("Total fazer:");
        consLTF.gridx = 0;
        consLTF.gridy = 17;//= 10;
        consLTF.anchor = GridBagConstraints.FIRST_LINE_START;
        add(lbTTF, consLTF);

        lbQTF = new JLabel();
        consLTF.gridx = 1;
        add(lbQTF, consLTF);
    }

    private JPanel getPainelInferior() {
        JPanel painelInf = new JPanel();
        FlowLayout alES = new FlowLayout(FlowLayout.LEFT);
        painelInf.setLayout(alES);

        JLabel lbTTF = new JLabel("Total fazer:");

        painelInf.add(lbTTF);

        lbQTF = new JLabel();
        painelInf.add(lbQTF);

        return painelInf;
    }

    public PainelListaTarefas getPainelLista() {
        return painelLista;
    }

//
//    public JTable getTabelaTarefas() {
//        return tabelaTarefas;
//    }
    public JMenuItem getMenuRecorta() {
        return menuRecorta;
    }

    public JButton getBtVerDetTarefa() {
        return btVerDetTarefa;
    }

    public JButton getBotaRem() {
        return botaRem;
    }

    public JMenuItem getMenuNovTAr() {

        return menuNovTAr;
    }

    public JMenuItem getMenuNovGAr() {
        return menuNovGAr;
    }

    public JMenuItem getMenuRec() {
        return menuRec;
    }

    public JButton getBotaoNovoGrupo() {
        return botaoNovoGrupo;
    }

    public JButton getBotaoExcluirGrupo() {
        return botaoExcluirGrupo;
    }

    public JButton getBotaoExcluirTudo() {
        return botaoExcluirTudo;
    }

    public JButton getBotaoNovaTarefa() {
        return botaoNovaTarefa;
    }

    public JTextField getCampoTituloTarefa() {
        return campoTituloTarefa;
    }

    public JMenuItem getMenuColarTar() {
        return menuColarTar;
    }

    public JMenuItem getMenuCopiaG() {
        return menuCopiaG;
    }

    public JMenuItem getMenuColar() {
        return menuColar;
    }

    public ImageIcon getIconeExcluirTudo() {
        return iconeExcluirTudo;
    }

    public Object getDivisorArvTab() {
        return divisorArvTab;
    }

    public ImageIcon getIconeAdicionarGrupo() {
        return iconeAdicionarGrupo;
    }

    public JMenuItem getMenuSinc() {
        return menuSinc;
    }

    public JMenuItem getItemSobre() {
        return itemSobre;
    }

    public JMenuItem getMiCop() {
        return miCop;
    }

    public JMenuItem getMiCol() {
        return miCol;
    }

    public JButton getBotaoExcluirTarefa() {
        return botaoExcluirTarefa;
    }

    public JDatePicker getCampoDataConc() {
        return campoDataConc;
    }

    public JFormattedTextField getCampoHoraCon() {
        return campoHoraCon;
    }

    //Getters
    public JCheckBox getCaixaMarcarF() {
        return caixaMarcarF;
    }

    public JanelaPrincipalController getControl() {
        return control;
    }

    public JButton getBotaoAdic() {
        return botaoAdic;
    }

    public JTree getArvoreTarefas() {
        if (arvoreTarefas == null) {
            //=== Construção da árvore
            arvoreTarefas = new JTree();

            arvoreTarefas.setDragEnabled(true);
            arvoreTarefas.setDropMode(DropMode.ON_OR_INSERT);
            arvoreTarefas.setEditable(true);
            arvoreTarefas.
                    getSelectionModel().setSelectionMode(
                            TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

            //XXX: Implementar pr?prio modelo com Usu. Verficar se ? bom obter direto do banc
            TreeCellRenderer reTa = new RenderizadorGT();
            arvoreTarefas.setCellRenderer(reTa);
        }

        return arvoreTarefas;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
