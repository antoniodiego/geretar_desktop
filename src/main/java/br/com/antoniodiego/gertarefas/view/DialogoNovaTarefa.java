package br.com.antoniodiego.gertarefas.view;

import br.com.antoniodiego.gertarefas.view.principal.JanelaPrincipal;
import br.com.antoniodiego.gertarefas.controller.DialogoNovaTarefaController;
import br.com.antoniodiego.gertarefas.igu.modelos.ModeloData;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

/**
 * Di?logo para cria??o de nova tarefa.
 *
 * @author Ant?nio Diego
 *
 */
//TODO: P?r tarefas compostas
public class DialogoNovaTarefa extends JDialog {

    private static final long serialVersionUID = 718289796785614603L;
    public static final int LARGURA_PREF = 400;
    public static final int ALTURA_PREF = 400;
    private JTextField campoTitulo;
    private final JanelaPrincipal pai;

    private JDatePickerImpl campoDataFz;
    private JButton botAdic;
    private JButton bRem;
    private JTable tabelaDesc;
    private JRadioButton rbEscolh;
    private JRadioButton btDig;
    private GrupoTarefas grupoDest;
     private ButtonGroup grupoBotDataFazer;
    
    private JButton botaoConcluido;
    private JSpinner campoPrioridade;
    private JDatePickerImpl campoDataAl;
    private JFormattedTextField campoHoraAl;
    private final DialogoNovaTarefaController contro;
    private JButton botaoCancelar;
    private JDatePanel painelData;
    private JDatePanelImpl painelDataAl;
    private JComboBox comboDataFaz;
   

    public DialogoNovaTarefa(JanelaPrincipal pai) {
        super(pai, "Nova tarefa", false);
        this.pai = pai;
        constJan();
        contro = null;// new DialogoNovaTarefaController(this, pai);
    }

    private void constJan() {
        setLocationRelativeTo(pai);
        setPreferredSize(new Dimension(LARGURA_PREF, ALTURA_PREF));
        JPanel painelConteudo = new JPanel();
        painelConteudo.setBorder(BorderFactory.
                createTitledBorder("Nova tarefa"));
        setContentPane(painelConteudo);
        painelConteudo.setLayout(new GridBagLayout());

        // Adiciona componentes.
        JLabel rotuloTitulo = new JLabel("T\u00edtulo (opc): ");

        GridBagConstraints consRotTit = new GridBagConstraints();
        consRotTit.gridx = 0;
        consRotTit.gridy = 0;
        consRotTit.anchor = GridBagConstraints.FIRST_LINE_START;
        consRotTit.weightx = 0.3;
        painelConteudo.add(rotuloTitulo, consRotTit);

        campoTitulo = new JTextField();
        campoTitulo.setColumns(32);
        rotuloTitulo.setLabelFor(campoTitulo);

        campoTitulo.setMinimumSize(new Dimension(200, 20));

        GridBagConstraints consCampoT = new GridBagConstraints();
        consCampoT.gridx = 1;
        consCampoT.gridwidth = 2;

        painelConteudo.add(campoTitulo, consCampoT);

        //Campos descri?ao
        tabelaDesc = new JTable();
        tabelaDesc.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        GridBagConstraints consTaabDesc = new GridBagConstraints();
        consTaabDesc.gridx = 0;
        consTaabDesc.gridy = 1;
        //[Para bot - 2 campo des e 1 bot +]

        consTaabDesc.gridwidth = 3;
        consTaabDesc.gridheight = 4;
        consTaabDesc.weighty = 1;
        consTaabDesc.weightx = 1;
        Insets insTab = new Insets(5, 2, 5, 2);
        consTaabDesc.insets = insTab;

        consTaabDesc.fill = GridBagConstraints.BOTH;

        JScrollPane scT = new JScrollPane(tabelaDesc);
        painelConteudo.add(scT, consTaabDesc);

        //Bot?o deve fica ? dir do ultimo campoDe
        botAdic = new JButton("Adicionar");
        //XXX: Obs ActionLis poderia ser esta classe.

        GridBagConstraints consBad = new GridBagConstraints();
        consBad.gridy = 5;
        consBad.gridx = 1;
        //   consBad.gridwidth = 1;
        consBad.weightx = 0.3;
        painelConteudo.add(botAdic, consBad);

        bRem = new JButton();
        GridBagConstraints conM = new GridBagConstraints();
        conM.gridy = 5;
        conM.gridx = 2;
        conM.weightx = 0.3;
        //TODO: salvar em lista um para cada campo
        painelConteudo.add(bRem, conM);

        //XXX: Campo data fazer est? ficando muito alto. Corrigido: Foi o ipady
        //Campo para data fazer
        JLabel rotuloDataFaz = new JLabel("Data fazer: ");

        //Adic na GUI
        GridBagConstraints constRotFa = new GridBagConstraints();
        constRotFa.gridx = 0;
        constRotFa.gridy = 6;
        constRotFa.anchor = GridBagConstraints.LINE_START;
        painelConteudo.add(rotuloDataFaz, constRotFa);

        grupoBotDataFazer = new ButtonGroup();

        rbEscolh = new JRadioButton("Escolher");
        grupoBotDataFazer.add(rbEscolh);
        rbEscolh.setSelected(true);

        GridBagConstraints constRbEsc = new GridBagConstraints();
        constRbEsc.gridx = 0;
        constRbEsc.gridy = 7;
        constRbEsc.anchor = GridBagConstraints.LINE_START;
        painelConteudo.add(rbEscolh, constRbEsc);

        comboDataFaz = new JComboBox(new String[]{"Amanhã", "Hoje", "Depois de amanhã"});

        GridBagConstraints constComboEsc = new GridBagConstraints();
        constComboEsc.gridx = 1;
        constComboEsc.gridy = 7;

        painelConteudo.add(comboDataFaz, constComboEsc);

        btDig = new JRadioButton("Digitar");

        grupoBotDataFazer.add(btDig);

        GridBagConstraints constBtDig = new GridBagConstraints();
        constBtDig.gridx = 0;
        constBtDig.gridy = 8;
        constBtDig.anchor = GridBagConstraints.LINE_START;
        painelConteudo.add(btDig, constBtDig);

        painelData = new JDatePanelImpl(new ModeloData(), new Properties());
        campoDataFz = new JDatePickerImpl((JDatePanelImpl) painelData, new FormatDatePick());

        campoDataFz.setEnabled(false);

        GridBagConstraints constCampoDaFa = new GridBagConstraints();
        constCampoDaFa.gridx = 1;
        constCampoDaFa.gridy = 8;
        constCampoDaFa.weightx = 0.3;
        //Insets para adi margem de 5 pixes na parte de baixo
        Insets insCampoDF = new Insets(0, 0, 5, 0);

        constCampoDaFa.insets = insCampoDF;

        campoDataFz.setMinimumSize(new Dimension(100, 30));
        painelConteudo.add(campoDataFz, constCampoDaFa);

        //Campo prioridade
        JLabel rotCPrio = new JLabel("Prioridade:");

        campoPrioridade = new JSpinner();
        campoPrioridade.setMinimumSize(new Dimension(50, 20));
        campoPrioridade.setMaximumSize(new Dimension(50, 20));

        rotCPrio.setLabelFor(campoPrioridade);
        rotCPrio.setDisplayedMnemonic(KeyEvent.VK_P);

        GridBagConstraints constRotPrio = new GridBagConstraints();
        constRotPrio.gridx = 0;
        constRotPrio.gridy = 9;

        constRotPrio.anchor = GridBagConstraints.LINE_START;
        painelConteudo.add(rotCPrio, constRotPrio);

        GridBagConstraints constCampoPrio = new GridBagConstraints();
        constCampoPrio.gridy = 9;
        constCampoPrio.gridx = 1;

        painelConteudo.add(campoPrioridade, constCampoPrio);

        JLabel rotuloMomeAlarme = new JLabel("Instante alarme: ");

        //Adic na GUI
        GridBagConstraints constRotInsAl = new GridBagConstraints();
        constRotInsAl.gridx = 0;
        constRotInsAl.gridy = 10;
        constRotInsAl.fill = GridBagConstraints.HORIZONTAL;
        painelConteudo.add(rotuloMomeAlarme, constRotInsAl);

        painelDataAl = new JDatePanelImpl(new ModeloData(), new Properties());
        campoDataAl = new JDatePickerImpl((JDatePanelImpl) painelDataAl, new FormatDatePick());

        // DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR")));
        //10 colunas para data no form DD/MM/YYYY
        //    campoDataAl.setColumns(10);
        campoDataAl.setMaximumSize(getPreferredSize());
        campoDataAl.setMinimumSize(new Dimension(100, 30));

        GridBagConstraints constCampoDaAl = new GridBagConstraints();
        constCampoDaAl.gridx = 1;
        constCampoDaAl.gridy = 10;

        //Insets para adi margem de 5 pixes na parte de baixo
        Insets insCampoDA = new Insets(5, 0, 5, 0);

        constCampoDaAl.insets = insCampoDA;
        constCampoDaAl.anchor = GridBagConstraints.FIRST_LINE_START;
        painelConteudo.add(campoDataAl, constCampoDaAl);

        campoHoraAl = new JFormattedTextField();
        campoHoraAl.setColumns(5);
        campoHoraAl.setMaximumSize(new Dimension(50, 20));
        campoHoraAl.setMinimumSize(new Dimension(50, 20));

        GridBagConstraints constCampoHorAl = new GridBagConstraints();
        constCampoHorAl.gridx = 2;
        constCampoHorAl.gridy = 10;

        //Insets para adi margem de 5 pixes na parte de baixo
        Insets insCampoDHa = new Insets(5, 2, 5, 0);

        constCampoHorAl.insets = insCampoDHa;
        constCampoHorAl.anchor = GridBagConstraints.FIRST_LINE_START;
        //constCampoHorAl.fill = GridBagConstraints.HORIZONTAL;
        //TODO: formatar para data
        painelConteudo.add(campoHoraAl, constCampoHorAl);

        Dimension tamPB = new Dimension(80, 10);

        botaoCancelar = new JButton("Cancelar");

        botaoCancelar.setPreferredSize(tamPB);
        GridBagConstraints constBotCan = new GridBagConstraints();

        constBotCan.gridx = 2;
        constBotCan.gridy = 11;

        painelConteudo.add(botaoCancelar, constBotCan);

        botaoConcluido = new JButton();
        botaoConcluido.setPreferredSize(tamPB);

        GridBagConstraints constBotSalv = new GridBagConstraints();

        constBotSalv.gridy = 11;
        constBotSalv.gridx = 1;
        painelConteudo.add(botaoConcluido, constBotSalv);

        pack();

        getRootPane().setDefaultButton(botaoConcluido);
    }

    public JRadioButton getRbEscolh() {
        return rbEscolh;
    }

    public JRadioButton getBtDig() {
        return btDig;
    }

    public ButtonGroup getGrupoBotDataFazer() {
        return grupoBotDataFazer;
    }

    public JComboBox getComboDataFaz() {
        return comboDataFaz;
    }

    public JButton getBotaoCancelar() {
        return botaoCancelar;
    }

    public DialogoNovaTarefaController getContro() {
        return contro;
    }

    public JDatePicker getCampoDataAl() {
        return campoDataAl;
    }

    public JFormattedTextField getCampoHoraAl() {
        return campoHoraAl;
    }

    public JTextField getCampoTitulo() {
        return campoTitulo;
    }

    public JDatePickerImpl getCampoDataFazer() {
        return campoDataFz;
    }

    public JButton getBotaoConcluido() {
        return botaoConcluido;
    }

    public JTable getTabelaDesc() {
        return tabelaDesc;
    }

    public JButton getBotAdic() {
        return botAdic;
    }

    public JButton getbRem() {
        return bRem;
    }

    public class FormatDatePick extends JFormattedTextField.AbstractFormatter {

        private DateTimeFormatter df = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        private Locale lcBrasil = new Locale("pt", "BR");
        private DateFormat dfB = DateFormat.getDateInstance(DateFormat.SHORT, lcBrasil);

        @Override
        public Object stringToValue(String text) throws ParseException {
            Date data = dfB.parse(text);
            GregorianCalendar gc = new GregorianCalendar(lcBrasil);
            gc.setTime(data);
            return gc;
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            GregorianCalendar gc = (GregorianCalendar) value;
            if (gc == null) {
                return "";
            }
            Date data = gc.getTime();
            return dfB.format(data);
        }

    }
}
