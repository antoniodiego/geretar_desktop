package br.com.antoniodiego.gertarefas.telas.principal.paineis;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import br.com.antoniodiego.gertarefas.telas.FormatadorJTime;
import br.com.antoniodiego.gertarefas.telas.modelos.FormatDatePick;
import br.com.antoniodiego.gertarefas.telas.modelos.ModeloData;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class PainelFunc extends JPanel {

    private JButton botaoEditarTarefa;
    private JButton botaoCancelarEditar;

    private JButton botaoExcluirTarefa2;
    private JButton botaoCopiaConteudo;

    private JDatePickerImpl campoData;
    private JDatePickerImpl campoDataFazer;
    private JSpinner campoPrioridade;
    private JDatePickerImpl campoDataAl;
    private JFormattedTextField campoHoraAl;
    private JButton btVotarLembrei;
    private JButton btVotarProclast;
    private JButton btAumentaPrio;
    private JButton btDiminuiPrio;
    private JDatePanelImpl painelData;
    private JDatePanelImpl painelDataFaz;
    private JDatePanelImpl painelDataAl;

    public PainelFunc() {
        constr();
    }

    private void constr() {
        setLayout(new GridBagLayout());

        JPanel painelBotoes = new JPanel();
        painelBotoes.setBorder(BorderFactory.createTitledBorder("Tarefa"));
        painelBotoes.setLayout(new GridBagLayout());

        GridBagConstraints consB = new GridBagConstraints();
        botaoEditarTarefa = new JButton();
        painelBotoes.add(botaoEditarTarefa, consB);

        botaoCancelarEditar = new JButton("Cancelar");
        botaoCancelarEditar.setActionCommand("cancel_edit");
        botaoCancelarEditar.setEnabled(false);

        consB.gridy = 1;
        painelBotoes.add(botaoCancelarEditar, consB);

        botaoExcluirTarefa2 = new JButton("Excluir");

        botaoExcluirTarefa2.setActionCommand(
                "deleta_tarefa");
        // cons.gridy = 4;
        // painelConteudo.add(botaoExcluirTarefa2, cons);
        consB.gridy = 2;
        painelBotoes.add(botaoExcluirTarefa2, consB);
        //TODO: height 3

        GridBagConstraints conPB = new GridBagConstraints();
        conPB.gridy = 2;
        conPB.gridx = 0;
        conPB.fill = GridBagConstraints.BOTH;
        conPB.anchor = GridBagConstraints.FIRST_LINE_START;
        conPB.weightx = 1;
        conPB.weighty = 1;
        add(painelBotoes, conPB);

        JPanel painelC = new JPanel();
        botaoCopiaConteudo = new JButton("Copiar");
        botaoCopiaConteudo.setActionCommand(
                "copy_content");
        painelC.setBorder(BorderFactory.createTitledBorder("Descri\u00e7\u00e3o"));
        painelC.add(botaoCopiaConteudo);

        GridBagConstraints consPC = new GridBagConstraints();
        consPC.gridx = 0;
        consPC.gridy = 3;
        consPC.anchor = GridBagConstraints.FIRST_LINE_START;
        // cons.gridwidth = 2;
        add(painelC, consPC);

        JLabel dataCria\u00e7\u00e3o = new JLabel("Data cria\u00e7\u00e3o:");
        painelData = new JDatePanelImpl(new ModeloData(), new Properties());

        //XXX- Apenas getInstance tinha at? horas
        campoData = new JDatePickerImpl(painelData, new FormatDatePick());//JFormattedTextField(new FormatadorJTime(false));
        // campoData.setColumns(
        //         11);
        campoData.setTextEditable(
                false);

        dataCria\u00e7\u00e3o.setLabelFor(campoData);

        GridBagConstraints consLDC = new GridBagConstraints();
        consLDC.gridx = 0;
        consLDC.gridy = 4;
        consLDC.anchor = GridBagConstraints.FIRST_LINE_START;
        add(dataCria\u00e7\u00e3o, consLDC);

        consLDC.gridy = 5;
        add(campoData, consLDC);

        JLabel rotDataFazer = new JLabel("Data fazer:");
        painelDataFaz = new JDatePanelImpl(new ModeloData(), new Properties());

        campoDataFazer = new JDatePickerImpl(painelDataFaz, new FormatDatePick());
        //JFormattedTextField(new FormatadorJTime(false));
        //  campoDataFazer.setColumns(
        //          11);
        campoDataFazer.setTextEditable(
                false);

        rotDataFazer.setLabelFor(campoDataFazer);
        GridBagConstraints consLDF = new GridBagConstraints();
        consLDF.gridx = 0;
        consLDF.gridy = 6;
        consLDF.anchor = GridBagConstraints.FIRST_LINE_START;

        add(rotDataFazer, consLDF);

        consLDF.gridy++;
        campoDataFazer.setMinimumSize(new Dimension(100, 30));
        add(campoDataFazer, consLDF);

        //Campo prioridade
        JLabel rotCPrio = new JLabel("Prioridade:");

        campoPrioridade = new JSpinner();
        campoPrioridade.setEnabled(false);
        rotCPrio.setLabelFor(campoPrioridade);

        rotCPrio.setDisplayedMnemonic(KeyEvent.VK_P);

        GridBagConstraints constRotPrio = new GridBagConstraints();
        constRotPrio.gridx = 0;
        constRotPrio.gridy = 8;
        add(rotCPrio, constRotPrio);

        GridBagConstraints constCampoPrio = new GridBagConstraints();
        constCampoPrio.gridx = 0;
        constCampoPrio.gridy = 9;
        constCampoPrio.anchor = GridBagConstraints.FIRST_LINE_START;
        add(campoPrioridade, constCampoPrio);

        //Campo prioridade
        JLabel rotCAlarme = new JLabel("Inst alarme:");

        GridBagConstraints constRotInstAl = new GridBagConstraints();
        constRotInstAl.gridx = 0;
        constRotInstAl.gridy = 10;
        add(rotCAlarme, constRotInstAl);

        painelDataAl = new JDatePanelImpl(new ModeloData(), new Properties());

        campoDataAl = new JDatePickerImpl(painelDataAl, new FormatDatePick());//JFormattedTextField(new FormatadorJTime(false));
        campoDataAl.setEnabled(false);
        rotCAlarme.setLabelFor(campoDataAl);
        rotCAlarme.setDisplayedMnemonic(KeyEvent.VK_D);

        GridBagConstraints constCampoDA = new GridBagConstraints();

        constCampoDA.gridx = 0;
        constCampoDA.gridy = 11;
        constCampoDA.anchor = GridBagConstraints.FIRST_LINE_START;
        //  constCampoDA.fill = GridBagConstraints.HORIZONTAL;

        campoDataAl.setMinimumSize(new Dimension(100, 30));
        add(campoDataAl, constCampoDA);

        campoHoraAl = new JFormattedTextField(new FormatadorJTime(true));
        campoHoraAl.setColumns(5);
        campoHoraAl.setEnabled(false);
        campoHoraAl.setMaximumSize(new Dimension(50, 20));
        campoHoraAl.setMinimumSize(new Dimension(50, 20));

        rotCAlarme.setDisplayedMnemonic(KeyEvent.VK_H);

        GridBagConstraints constCampoHA = new GridBagConstraints();

        constCampoHA.gridx = 0;
        constCampoHA.gridy = 12;
        constCampoHA.anchor = GridBagConstraints.FIRST_LINE_START;
        //    constCampoHA.fill = GridBagConstraints.HORIZONTAL;

        add(campoHoraAl, constCampoHA);

        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);

        GridBagConstraints constSep = new GridBagConstraints();

        constSep.gridx = 0;
        constSep.gridy = 13;
        constSep.fill = GridBagConstraints.HORIZONTAL;
        add(sep, constSep);

        JLabel rotVotar = new JLabel("Votar:");

        GridBagConstraints constRotVotar = new GridBagConstraints();

        constRotVotar.gridx = 0;
        constRotVotar.gridy = 14;
        add(rotVotar, constRotVotar);

        //Botões de votar
        btVotarLembrei = new JButton();
        GridBagConstraints constBotVLe = new GridBagConstraints();

        constBotVLe.gridx = 0;
        constBotVLe.gridy = 15;
        constBotVLe.anchor = GridBagConstraints.FIRST_LINE_START;
        add(btVotarLembrei, constBotVLe);

        btVotarProclast = new JButton();
        GridBagConstraints constBotVPro = new GridBagConstraints();

        constBotVPro.gridx = 0;
        constBotVPro.gridy = 16;
        constBotVPro.anchor = GridBagConstraints.FIRST_LINE_START;

        add(btVotarProclast, constBotVPro);

        btAumentaPrio = new JButton("Aumentar prio");

        GridBagConstraints constBotAuPrio = new GridBagConstraints();

        constBotAuPrio.gridx = 0;
        constBotAuPrio.gridy = 17;
        constBotAuPrio.anchor = GridBagConstraints.FIRST_LINE_START;

        add(btAumentaPrio, constBotAuPrio);

        btDiminuiPrio = new JButton("Diminuir prio");

        GridBagConstraints constBotDPrio = new GridBagConstraints();

        constBotDPrio.gridx = 0;
        constBotDPrio.gridy = 18;
        constBotDPrio.anchor = GridBagConstraints.FIRST_LINE_START;

        add(btDiminuiPrio, constBotDPrio);
    }

    public JButton getBotaoEditarTarefa() {
        return botaoEditarTarefa;
    }

    public JButton getBotaoCancelarEditar() {
        return botaoCancelarEditar;
    }

    public JButton getBotaoExcluirTarefa2() {
        return botaoExcluirTarefa2;
    }

    public JButton getBotaoCopiaConteudo() {
        return botaoCopiaConteudo;
    }

    public JDatePicker getCampoData() {
        return campoData;
    }

    public JDatePicker getCampoDataFazer() {
        return campoDataFazer;
    }

    public JSpinner getCampoPrioridade() {
        return campoPrioridade;
    }

    public JDatePicker getCampoDataAl() {
        return campoDataAl;
    }

    public JFormattedTextField getCampoHoraAl() {
        return campoHoraAl;
    }

    public JButton getBtVotarLembrei() {
        return btVotarLembrei;
    }

    public JButton getBtVotarProclast() {
        return btVotarProclast;
    }

    public JButton getBtAumentaPrio() {
        return btAumentaPrio;
    }

    public JButton getBtDiminuiPrio() {
        return btDiminuiPrio;
    }

}
