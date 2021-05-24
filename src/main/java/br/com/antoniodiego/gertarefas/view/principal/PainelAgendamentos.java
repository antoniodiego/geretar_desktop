/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.view.principal;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Ant�noio Diego <antoniodiegoluz at gmail.com>
 */
public class PainelAgendamentos extends JPanel {

    private JTable tabelaAgendamentos;
    private JButton btCopiarAmanha;

    public PainelAgendamentos() {
        GridBagConstraints constPSup = new GridBagConstraints();
        constPSup.gridx = 0;
        constPSup.gridy = 0;
        add(getPainelSup(), constPSup);

        GridBagConstraints constTab = new GridBagConstraints();
        constTab.gridx = 0;
        constTab.gridy = 1;
        add(new JScrollPane(getTabelaAge()), constTab);

        GridBagConstraints constPInf = new GridBagConstraints();
        constPInf.gridx = 0;
        constPInf.gridy = 12;
        add(getPainelInf(), constPInf);
    }

    private JTable getTabelaAge() {
        if (tabelaAgendamentos == null) {
            tabelaAgendamentos = new JTable();
        }

        return tabelaAgendamentos;
    }

    private JPanel getPainelSup() {
        JPanel painelSup = new JPanel(new GridBagLayout());

        return painelSup;
    }

    private JPanel getPainelInf() {
        JPanel painelInf = new JPanel(new GridBagLayout());

        btCopiarAmanha = new JButton();

        GridBagConstraints gbcBtCopA = new GridBagConstraints();
        gbcBtCopA.gridx = 0;
        gbcBtCopA.gridy = 0;

        painelInf.add(btCopiarAmanha, gbcBtCopA);

        return painelInf;

    }
}
