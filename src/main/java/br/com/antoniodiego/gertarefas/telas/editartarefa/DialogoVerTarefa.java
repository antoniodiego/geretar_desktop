package br.com.antoniodiego.gertarefas.telas.editartarefa;

import br.com.antoniodiego.gertarefas.pojo.Reflexao;
import br.com.antoniodiego.gertarefas.pojo.Relatorio;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.telas.principal.JanelaPrincipal;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableColumnModel;

/**
 * Díalogo para ver detalhes da tarefa e gerenica-la
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class DialogoVerTarefa extends JDialog {

    private final Tarefa tarefaVisu;
    private JTable tabReflexoes;
    private JTable tabRelatorios;
    private JTextArea campoNRef;
    private JButton btNovaRefl;
    private JTextArea campoNRel;
    private JButton btAdRel;
    private JButton btDeletaRef;
    private JButton btDeletaRel;
    private JLabel campoT;
    private JLabel campoDC;
    private JLabel campoDR;
    private JLabel campoPr;
    private ModeloTabRefl modTabRef;
    private ModeloTabRel modTabRel;
    private final JanelaPrincipal janelaMae;

    public DialogoVerTarefa(Tarefa tarefa, JanelaPrincipal janelaMae) {
        super(janelaMae);
        this.tarefaVisu = tarefa;
        this.janelaMae = janelaMae;

        inicializa();
        preencheCampos();
    }

    private void inicializa() {
        setTitle("Ver tarefa");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        JLabel rotTit = new JLabel("Título:");
        GridBagConstraints consT = new GridBagConstraints();
        add(rotTit, consT);

        campoT = new JLabel();
        GridBagConstraints consCT = new GridBagConstraints();
        consCT.gridx = 1;
        add(campoT, consCT);

        //Data c
        JLabel dataDeCriacao = new JLabel("Data de criação:");

        GridBagConstraints consRDC = new GridBagConstraints();
        consRDC.gridy = 1;
        add(dataDeCriacao, consRDC);

        campoDC = new JLabel();
        GridBagConstraints consCDC = new GridBagConstraints();
        consCDC.gridx = 1;
        consCDC.gridy = 1;
        add(campoDC, consCDC);

        //Data real
        JLabel dataParaReal = new JLabel("Data para real:");

        GridBagConstraints consRDR = new GridBagConstraints();
        consRDR.gridy = 2;
        add(dataParaReal, consRDR);

        campoDR = new JLabel();
        GridBagConstraints consCDR = new GridBagConstraints();
        consCDR.gridx = 1;
        consCDR.gridy = 2;
        add(campoDR, consCDR);

        JLabel rRrioridade = new JLabel("Prioridade:");
        GridBagConstraints consRPr = new GridBagConstraints();
        consRPr.gridx = 0;
        consRPr.gridy = 3;
        add(rRrioridade, consRPr);

        campoPr = new JLabel();
        GridBagConstraints consCPr = new GridBagConstraints();
        consCPr.gridx = 1;
        consCPr.gridy = 3;
        add(campoPr, consCPr);

        /*
        Parece bom que haja um painel com borda e título votos
         */
        JPanel painelDadosVotos = new JPanel(new GridBagLayout());

        JLabel rVProcls = new JLabel("Proclastinei:");
        JLabel rVLembrei = new JLabel("Lembrei");

        tabReflexoes = new JTable();
        modTabRef = new ModeloTabRefl();
        tabReflexoes.setModel(modTabRef);

        TableColumnModel modCTR = tabReflexoes.getColumnModel();
        modCTR.getColumn(0).setMaxWidth(100);
        modCTR.getColumn(1).setMaxWidth(100);

        JScrollPane paRTabRef = new JScrollPane(tabReflexoes);

        GridBagConstraints consTabRefl = new GridBagConstraints();
        consTabRefl.gridx = 0;
        consTabRefl.gridy = 4;
        consTabRefl.gridwidth = 2;
        consTabRefl.gridheight = 4;
        consTabRefl.weightx = 1;
        consTabRefl.fill = GridBagConstraints.BOTH;
        consTabRefl.weighty = 0.25;
        add(paRTabRef, consTabRefl);

        campoNRef = new JTextArea();
        campoNRef.setRows(3);
        campoNRef.setLineWrap(true);
        campoNRef.setColumns(20);
        GridBagConstraints consCNRefl = new GridBagConstraints();
        consCNRefl.gridx = 0;
        consCNRefl.gridy = 9;
        consCNRefl.gridwidth = 2;
        consCNRefl.gridheight = 3;
        consCNRefl.weighty = 0.25;
        consCNRefl.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(campoNRef), consCNRefl);

        btNovaRefl = new JButton(new AcaoSalvaRef());
        GridBagConstraints consBtNRef = new GridBagConstraints();
        consBtNRef.gridx = 0;
        consBtNRef.gridy = 13;
        add(btNovaRefl, consBtNRef);

        btDeletaRef = new JButton(new AcaoDeletaRef());
        GridBagConstraints consBtDelRef = new GridBagConstraints();
        consBtDelRef.gridx = 1;
        consBtDelRef.gridy = 13;
        add(btDeletaRef, consBtDelRef);

        btDeletaRef.setBackground(Color.red);
        btDeletaRef.setForeground(Color.BLACK);

        tabRelatorios = new JTable();
        modTabRel = new ModeloTabRel();
        tabRelatorios.setModel(modTabRel);

        TableColumnModel modCTRel = tabRelatorios.getColumnModel();
        modCTRel.getColumn(0).setMaxWidth(100);
        modCTRel.getColumn(1).setMaxWidth(100);

        JScrollPane paRTabRel = new JScrollPane(tabRelatorios);

        GridBagConstraints consTabRel = new GridBagConstraints();
        consTabRel.gridx = 0;
        consTabRel.gridy = 14;
        consTabRel.gridwidth = 2;
        consTabRel.gridheight = 4;
        consTabRel.weightx = 1;
        consTabRel.weighty = 0.25;
        consTabRel.fill = GridBagConstraints.BOTH;
        add(paRTabRel, consTabRel);

        campoNRel = new JTextArea();
        campoNRel.setRows(3);
        campoNRel.setLineWrap(true);
        campoNRel.setColumns(20);
        GridBagConstraints consCamoRel = new GridBagConstraints();
        consCamoRel.gridx = 0;
        consCamoRel.gridy = 19;
        consCamoRel.gridwidth = 2;
        consCamoRel.gridheight = 3;
        consCamoRel.weighty = 0.25;
        consCamoRel.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(campoNRel), consCamoRel);

        btAdRel = new JButton(new AcaoSalvaRel());
        GridBagConstraints consBtAdRel = new GridBagConstraints();
        consBtAdRel.gridx = 0;
        consBtAdRel.gridy = 23;
        add(btAdRel, consBtAdRel);

        btDeletaRel = new JButton(new AcaoDeletaRel());
        GridBagConstraints consBtDelRel = new GridBagConstraints();
        consBtDelRel.gridx = 1;
        consBtDelRel.gridy = 23;
        add(btDeletaRel, consBtDelRel);

        btDeletaRel.setBackground(Color.red);
        btDeletaRel.setForeground(Color.BLACK);

        JButton btSair = new JButton(new AcaoSair());
        btSair.setBackground(Color.red);
        btSair.setForeground(Color.BLACK);

        setPreferredSize(new Dimension(400, 600));
        pack();
    }

    /**
     *
     */
    private void preencheCampos() {
        campoT.setText(tarefaVisu.getTitulo());

        DateTimeFormatter formD = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

        if (tarefaVisu.getDataCriacao() != null) {
            campoDC.setText(tarefaVisu.getDataCriacao().format(formD));
        }

        if (tarefaVisu.getDataFazer() != null) {
            campoDR.setText(tarefaVisu.getDataFazer().format(formD));
        }

        campoPr.setText(String.valueOf(tarefaVisu.getPrioridade()));

        modTabRef.setRef(tarefaVisu.getReflexoes());
        modTabRel.setRelatorios(tarefaVisu.getRelatorios());

        // System.out.println("Ref enc: " + tarefaVisu.getReflexoes().size());
    }

    private class AcaoSalvaRef extends AbstractAction {

        public AcaoSalvaRef() {
            super("Adicionar refl");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (tarefaVisu == null) {
                //TODO: Talvez fosse bom desab bot nesse c
                return;
            }
            String textoNRef = campoNRef.getText();
            Reflexao novaRef = new Reflexao(LocalDate.now(), LocalTime.now(),
                    textoNRef);
            tarefaVisu.getReflexoes().add(novaRef);
            //Aqui deve ser bom mudar a data de mod da tarefa
            tarefaVisu.setDataModif(LocalDateTime.now());

            janelaMae.getControl().getDaoUsuario().flush();
            janelaMae.getControl().getOrdenadorTabelaLista().sort();

            modTabRef.notificaAdic();

            campoNRef.setText("");

        }
    }

    private class AcaoSalvaRel extends AbstractAction {

        public AcaoSalvaRel() {
            super("Adicionar relatório");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (tarefaVisu == null) {
                //TODO: Talvez fosse bom desab bot nesse c
                return;
            }
            String textoNRel = campoNRel.getText();
            Relatorio novoRel = new Relatorio(LocalDate.now(), LocalTime.now(),
                    textoNRel);
            tarefaVisu.getRelatorios().add(novoRel);
            janelaMae.getControl().getDaoUsuario().flush();

            //Aqui deve ser bom mudar a data de mod da tarefa
            tarefaVisu.setDataModif(LocalDateTime.now());
            janelaMae.getControl().getOrdenadorTabelaLista().sort();
            modTabRel.notificaAdic();

            campoNRel.setText("");
        }

    }

    private class AcaoDeletaRef extends AbstractAction {

        public AcaoDeletaRef() {
            super("Deleta refl");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int lS = tabReflexoes.getSelectedRow();
            int iM = tabReflexoes.convertRowIndexToModel(lS);
            Reflexao sel = modTabRef.getRef().get(iM);
            tarefaVisu.getReflexoes().remove(sel);
            //TODO: Talv disp alt na data de modif
            janelaMae.getControl().getDaoUsuario().flush();
            modTabRef.notificaRem(iM);
        }
    }

    private class AcaoDeletaRel extends AbstractAction {

        public AcaoDeletaRel() {
            super("Deleta relatório");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int lS = tabRelatorios.getSelectedRow();
            int iM = tabRelatorios.convertRowIndexToModel(lS);
            Relatorio sel = modTabRel.getRelatorios().get(iM);
            tarefaVisu.getRelatorios().remove(sel);
            janelaMae.getControl().getDaoUsuario().flush();
            modTabRel.notificaRem(iM);
        }

    }

    private class AcaoSair extends AbstractAction {

        public AcaoSair() {
            super("Sair");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }

    }
}
