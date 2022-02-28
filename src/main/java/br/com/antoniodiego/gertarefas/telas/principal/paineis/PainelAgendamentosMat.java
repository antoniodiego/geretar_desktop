/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.telas.principal.paineis;

import br.com.antoniodiego.gertarefas.beans.BeanDatePick;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class PainelAgendamentosMat extends javax.swing.JPanel {

    /**
     * Creates new form PainelAgendamentosMat
     */
    public PainelAgendamentosMat() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoFilData = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        checkData = new javax.swing.JCheckBox();
        rbHoje = new javax.swing.JRadioButton();
        rbAmanha = new javax.swing.JRadioButton();
        rbDataEsp = new javax.swing.JRadioButton();
        campoDaF = new br.com.antoniodiego.gertarefas.beans.BeanDatePick();
        btFiltrar = new javax.swing.JButton();
        btLimpar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaAgendamentos = new javax.swing.JTable();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar"));

        checkData.setText("Data");

        grupoFilData.add(rbHoje);
        rbHoje.setSelected(true);
        rbHoje.setText("Hoje");

        grupoFilData.add(rbAmanha);
        rbAmanha.setText("Amanha");

        grupoFilData.add(rbDataEsp);
        rbDataEsp.setText("Data esp");

        btFiltrar.setText("Filtrar");

        btLimpar.setText("Limpar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkData)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbHoje)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbAmanha, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbDataEsp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoDaF, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 53, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btFiltrar)
                .addGap(18, 18, 18)
                .addComponent(btLimpar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(campoDaF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(checkData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbHoje)
                            .addComponent(rbAmanha))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbDataEsp)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btFiltrar)
                    .addComponent(btLimpar))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        add(jPanel1);

        tabelaAgendamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabelaAgendamentos);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtFiltrar() {
        return btFiltrar;
    }

    public JButton getBtLimpar() {
        return btLimpar;
    }

    public BeanDatePick getCampoDaF() {
        return campoDaF;
    }

    public JCheckBox getCheckData() {
        return checkData;
    }

    public JRadioButton getRbAmanha() {
        return rbAmanha;
    }

    public JRadioButton getRbDataEsp() {
        return rbDataEsp;
    }

    public JRadioButton getRbHoje() {
        return rbHoje;
    }

    public JTable getTabelaAgendamentos() {
        return tabelaAgendamentos;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btFiltrar;
    private javax.swing.JButton btLimpar;
    private br.com.antoniodiego.gertarefas.beans.BeanDatePick campoDaF;
    private javax.swing.JCheckBox checkData;
    private javax.swing.ButtonGroup grupoFilData;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbAmanha;
    private javax.swing.JRadioButton rbDataEsp;
    private javax.swing.JRadioButton rbHoje;
    private javax.swing.JTable tabelaAgendamentos;
    // End of variables declaration//GEN-END:variables
}