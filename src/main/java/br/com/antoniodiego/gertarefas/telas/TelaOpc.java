/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.telas;

import br.com.antoniodiego.gertarefas.controller.JanelaPrincipalController;
import br.com.antoniodiego.gertarefas.igu.modelos.ModeloArvore;
import br.com.antoniodiego.gertarefas.persist.daos.DAOUsuario;
import br.com.antoniodiego.gertarefas.telas.principal.JanelaPrincipal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author AntÃ´nio Diego
 */
//TODO: Por configu de encoding exporta e importaÃ§ao de xml (por enc separados).
public class TelaOpc extends javax.swing.JDialog {

    Properties pr = new Properties();
    private final File arqProp;
    //private final DAOUsuario daoUs;
    //  private final ModeloArvore modelArv;
    // private final JanelaPrincipalController contPrinc;
    private final JanelaPrincipal janPrinc;

    /**
     * Creates new form TelaOpc
     *
     * @param parent
     * @param modal
     */
    public TelaOpc(JanelaPrincipal parent, boolean modal) {
        super(parent, modal);
        this.janPrinc = parent;
        arqProp = new File("conf.properties");
        //Mesma instancia carreg prin para causar efeito em tempo real
        pr = parent.getControl().getProp();
//        try {
//            pr.load(new FileInputStream(arqProp));
//        } catch (IOException ex) {
//            Logger.getLogger(TelaOpc.class.getName()).log(Level.SEVERE, null, ex);
//        }
        // pr.getProperty("mantercon","false");
        //   this.daoUs = daoUs;
        //  this.modelArv = modeloArv;
        //this.contPrinc = contPrinc;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        chekExibirIn = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboExp = new javax.swing.JComboBox<>();
        comoImp = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        checkBackupAuto = new javax.swing.JCheckBox();
        rotPastaBackups = new javax.swing.JLabel();
        campoPastaBackup = new javax.swing.JTextField();
        btProcPasta = new javax.swing.JButton();
        rotTotalManter = new javax.swing.JLabel();
        campoTotalManter = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btExcluirTudo = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Op\u00e7\u00f5es");
        setLocationByPlatform(true);

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        chekExibirIn.setSelected(pr.getProperty("exibirin","false").equalsIgnoreCase("true")
        );
        chekExibirIn.setText("Exibir di\u00e1logo login no in\u00edcio");
        chekExibirIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chekExibirInActionPerformed(evt);
            }
        });

        jLabel1.setText("Encoding de exportação:");

        jLabel3.setText("Encoding de importação:");

        SortedMap sm = Charset.availableCharsets();
        DefaultComboBoxModel mod1=new javax.swing.DefaultComboBoxModel<>(sm.keySet().toArray());//new String[]{"UTF-8","ISO-8859-1"});
    comboExp.setModel(mod1);
    mod1.setSelectedItem(pr.getProperty("encoding-exporta", "UTF-8"));

    SortedMap chs = Charset.availableCharsets();
    DefaultComboBoxModel e =new javax.swing.DefaultComboBoxModel<>(chs.keySet().toArray());
    comoImp.setModel(e);//new String[] { "UTF-8", "ISO-8859-1", "ASCII"}));
    e.setSelectedItem(pr.getProperty("encoding-importacao", "UTF-8"));

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(chekExibirIn)
            .addGap(0, 0, Short.MAX_VALUE))
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1)
                .addComponent(jLabel3))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(comoImp, 0, 49, Short.MAX_VALUE)
                .addComponent(comboExp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(chekExibirIn)
            .addGap(18, 18, 18)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(comboExp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(21, 21, 21)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(comoImp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(58, Short.MAX_VALUE))
    );

    jTabbedPane1.addTab("Exportação", jPanel1);

    jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

    checkBackupAuto.setText("Habilitar backup automático");

    rotPastaBackups.setText("Pasta dos backups:");

    btProcPasta.setText("...");
    btProcPasta.setToolTipText("Procurar  a pasta");

    rotTotalManter.setText("Total a manter:");

    campoTotalManter.setToolTipText("Total de backups para serem mantidos na pasta");

    jLabel4.setText("Período:");

    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Diário", "Semanal", "Mensal" }));

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(checkBackupAuto)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(rotTotalManter)
                                .addComponent(rotPastaBackups))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(campoPastaBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btProcPasta, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(campoTotalManter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(85, 85, 85)
                    .addComponent(jLabel4)))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(checkBackupAuto)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rotPastaBackups)
                .addComponent(campoPastaBackup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btProcPasta))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rotTotalManter)
                .addComponent(campoTotalManter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(62, Short.MAX_VALUE))
    );

    jTabbedPane1.addTab("Backup", jPanel2);

    btExcluirTudo.setBackground(new java.awt.Color(238, 100, 100));
    btExcluirTudo.setText("Excluir tudo");
    btExcluirTudo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btExcluirTudoActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(btExcluirTudo)
            .addContainerGap(320, Short.MAX_VALUE))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
            .addContainerGap(139, Short.MAX_VALUE)
            .addComponent(btExcluirTudo)
            .addContainerGap())
    );

    jTabbedPane1.addTab("Dados", jPanel3);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton2)
            .addGap(7, 7, 7))
        .addComponent(jTabbedPane1)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton1)
                .addComponent(jButton2))
            .addContainerGap())
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // JanelaPrincipal j = (JanelaPrincipal) getParent();
        //TODO: Salvar em properties
        //if (chekExibirIn.isSelected()) {
        this.pr.setProperty("exibirin", String.valueOf(chekExibirIn.isSelected()));
        this.pr.setProperty("encoding-importacao", (String) comoImp.getSelectedItem());
        this.pr.setProperty("encoding-exporta", (String) comboExp.getSelectedItem());

        try {
            //}
            pr.store(new FileOutputStream(arqProp), "Config geretar");
        } catch (IOException ex) {
            Logger.getLogger(TelaOpc.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void chekExibirInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chekExibirInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chekExibirInActionPerformed

    private void btExcluirTudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirTudoActionPerformed
        DialogoConfirmarExcTudo dialogoConf = new DialogoConfirmarExcTudo(this);

        dialogoConf.setVisible(true);

        int st = dialogoConf.getReturnStatus();

        if (st == DialogoConfirmarExcTudo.RET_OK) {
            if (dialogoConf.getCheckSim().isSelected()) {
                if (dialogoConf.getCheckBackup().isSelected()) {
                    janPrinc.getControl().getDaoUsuario().fazBackupB();
                }
                janPrinc.getControl().getModeloArv().removeTudo();
                janPrinc.getControl().getDaoUsuario().flush();
                janPrinc.getControl().atualizaEstadoDosMenusBotoes();
                janPrinc.getControl().atualizaBarraDeStatus();
            }
        }

    }//GEN-LAST:event_btExcluirTudoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btExcluirTudo;
    private javax.swing.JButton btProcPasta;
    private javax.swing.JTextField campoPastaBackup;
    private javax.swing.JSpinner campoTotalManter;
    private javax.swing.JCheckBox checkBackupAuto;
    private javax.swing.JCheckBox chekExibirIn;
    private javax.swing.JComboBox<String> comboExp;
    private javax.swing.JComboBox<String> comoImp;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel rotPastaBackups;
    private javax.swing.JLabel rotTotalManter;
    // End of variables declaration//GEN-END:variables
}