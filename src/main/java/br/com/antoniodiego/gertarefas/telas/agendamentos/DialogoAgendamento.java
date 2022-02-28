/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.telas.agendamentos;

import br.com.antoniodiego.gertarefas.telas.FormatadorJTime;
import br.com.antoniodiego.gertarefas.telas.principal.JanelaPrincipal;

import java.awt.GridBagLayout;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import org.jdatepicker.AbstractDateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class DialogoAgendamento extends JDialog {

    private JTextField tfTituloTar;
    private JDatePicker datePicDataAg;
    //private JTimePciker tpHoraAg;
    //private CheckBox checkAlarme;
    //   private JTimePciker tpHoraAlarme;

    private JRadioButton rbRepetir;
    private JComboBox<String> campoPerRep;
    private JCheckBox checkDomingo;
    private JCheckBox checkSegunda;
    private JCheckBox checkTerça;
    private JCheckBox checkQuarta;
    private JCheckBox checkQuinta;
    private JCheckBox checkSexta;
    private ButtonGroup grupoRepetir;
    private JRadioButton rbNaoRepetir;
    private JDatePickerImpl campoDataAl;

    public DialogoAgendamento(JanelaPrincipal princ) {
        constroi(princ);
        
    }

    private void constroi(JanelaPrincipal princ) {
        setTitle("Agendar");
        setLayout(new GridBagLayout());
        setLocationRelativeTo(princ);
        setLocationByPlatform(true);
        
        JLabel rotTarefa = new JLabel("Tarefa:");
        JLabel rotDataAge = new JLabel("Data age:");
        JLabel rotHoraAge = new JLabel("Hora age:");

        rbRepetir = new JRadioButton("Repetir");
        rbNaoRepetir = new JRadioButton("Não Repetir");

        grupoRepetir = new ButtonGroup();
        grupoRepetir.add(rbRepetir);
        grupoRepetir.add(rbNaoRepetir);

        pack();
    }

    private JPanel getPainelRep() {
        JPanel painelRep = new JPanel();

        JLabel rotPerRep = new JLabel("Período rep:");
        campoPerRep = new JComboBox<>();
        
        return painelRep;
    }

    private JPanel getPainelNaoRep() {
        JPanel painelRep = new JPanel();

        JDatePanelImpl pai = new JDatePanelImpl(new Mod(),null);
        campoDataAl = new JDatePickerImpl(pai,new FormatadorJTime(false));

        return painelRep;
    }

    private class Mod extends AbstractDateModel<LocalDate> {

        private Calendar c;

        @Override
        protected Calendar toCalendar(LocalDate from) {
            Calendar c = Calendar.getInstance();
            c.setTime(Date.from(Instant.from(from)));

            return c;
        }

        @Override
        protected LocalDate fromCalendar(Calendar from) {
            Instant ins = from.toInstant();
            return LocalDate.from(ins);
        }

    }
}
