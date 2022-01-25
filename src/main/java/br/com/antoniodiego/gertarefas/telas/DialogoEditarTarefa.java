/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.telas;

import br.com.antoniodiego.gertarefas.beans.BeanDatePick;
import static br.com.antoniodiego.gertarefas.controller.DialogoNovaTarefaController.logDNT;
import br.com.antoniodiego.gertarefas.model.ModeloTabelaTarefasLista;
import br.com.antoniodiego.gertarefas.persist.daos.DAOTarefa;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import java.time.LocalDate;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class DialogoEditarTarefa extends javax.swing.JDialog {

    private final ModeloTabelaTarefasLista modelo;
    // private final DialogoNovaTarefaController contro;
    private final JFrame princ;
    private Tarefa tarefa;

    /**
     * Creates new form DialogoNovaTarView
     *
     * @param princ
     * @param modelTab
     */
    public DialogoEditarTarefa(JFrame princ, ModeloTabelaTarefasLista modelTab) {
        super(princ, "Nova tarefa", false);
        this.modelo = modelTab;
        this.princ = princ;
        initComponents();
        // contro = new DialogoNovaTarefaController(this);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoDataFazer = new javax.swing.ButtonGroup();
        beanDatePick1 = new br.com.antoniodiego.gertarefas.beans.BeanDatePick();
        campoTitulo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        rotuloPriori = new javax.swing.JLabel();
        campoPrioridade = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        botaoConcluido = new javax.swing.JButton();
        botaoCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        campoPrazo = new br.com.antoniodiego.gertarefas.beans.BeanDatePick();
        campoDataAl = new br.com.antoniodiego.gertarefas.beans.BeanDatePick();
        campoHoraAl = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        campoId = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        campoComentários = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        campoStatus = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        campoPosicao = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar tarefa");

        jLabel1.setText("Prazo:");

        rotuloPriori.setText("Prioridade:");

        jLabel3.setText("Alarme:");

        botaoConcluido.setText("Salvar");
        botaoConcluido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoConcluidoActionPerformed(evt);
            }
        });

        botaoCancelar.setText("Cancelar");
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });

        jLabel4.setText("Nome:");

        campoPrazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoPrazoActionPerformed(evt);
            }
        });

        campoHoraAl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoHoraAlActionPerformed(evt);
            }
        });

        jLabel2.setText("ID:");
        jLabel2.setToolTipText("ID universal (personalizado)");

        campoId.setPreferredSize(new java.awt.Dimension(5, 24));
        campoId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoIdActionPerformed(evt);
            }
        });

        jLabel5.setText("Comentários:");

        campoComentários.setColumns(20);
        campoComentários.setLineWrap(true);
        campoComentários.setRows(5);
        campoComentários.setWrapStyleWord(true);
        jScrollPane1.setViewportView(campoComentários);

        jLabel6.setText("Status:");

        campoStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoStatusActionPerformed(evt);
            }
        });

        jLabel7.setText("Posição:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 386, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(campoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(34, 34, 34)
                                    .addComponent(campoStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rotuloPriori, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(campoPosicao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(campoPrioridade, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(campoDataAl, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(39, 39, 39)
                                                    .addComponent(campoHoraAl, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(0, 0, Short.MAX_VALUE))))))
                        .addContainerGap(55, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(botaoConcluido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botaoCancelar)))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoPrazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(campoStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(campoPosicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotuloPriori)
                    .addComponent(campoPrioridade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(campoHoraAl, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botaoConcluido)
                            .addComponent(botaoCancelar))
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoDataAl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_botaoCancelarActionPerformed

    private void campoHoraAlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoHoraAlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoHoraAlActionPerformed

    private void botaoConcluidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoConcluidoActionPerformed
        logDNT.trace("Salvando tarefa...");

        String tit = campoTitulo.getText();
        tarefa.setTitulo(tit);
        tarefa.setDataFazer(campoPrazo.getModeloDef().getValue());
        tarefa.setComentario(campoComentários.getText());
        tarefa.setStatus(campoStatus.getText());
        // tarefa.setPosicao(campoPosicao.getSelectedIndex());

        //obs: redund
        tarefa.setDataCriacao(LocalDate.now());

        //  novaTarefa.setPrioridade(modeloCampoPro.getNumber().intValue());
        String idStr = campoId.getText();

        for (int i = 0; i < idStr.length(); i++) {
            if (!Character.isDigit(idStr.charAt(i))) {
                JOptionPane.showMessageDialog(this, "Id inválido", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (idStr.isEmpty()) {
            idStr = "0";
        }

        Long idNum = Long.parseLong(idStr);

        DAOTarefa daoT = new DAOTarefa();

        if (daoT.getByIdPers(idNum) != null) {
            Long maiorIdPers = daoT.getMaiorIDPers();
            tarefa.setIdPers(maiorIdPers + 1);
        } else {
            tarefa.setIdPers(idNum);
        }

        daoT.atualiza(tarefa);

        dispose();

        logDNT.trace("Adic tar na janela principal");

        modelo.ordena();

        logDNT.trace("Após adic tar na janela principal");
    }//GEN-LAST:event_botaoConcluidoActionPerformed

    public void setTarefa(Tarefa t) {
        this.tarefa = t;

        campoTitulo.setText(t.getTitulo());

        LocalDate prazo = t.getDataFazer();

        if (prazo != null) {

            campoPrazo.getModeloDef().setValue(prazo);//Date(prazo.getDayOfMonth(), prazo.getMonthValue(), prazo.getYear());
        }

        campoComentários.setText(t.getComentario());
        campoStatus.setText(t.getStatus());
        campoPosicao.setSelectedItem(t);
        campoPrioridade.setValue(t.getPrioridade());
        campoId.setValue(t.getIdPers());
    }

    private void campoIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoIdActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_campoIdActionPerformed

    private void campoPrazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoPrazoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoPrazoActionPerformed

    private void campoStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoStatusActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogoEditarTarefa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogoEditarTarefa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogoEditarTarefa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogoEditarTarefa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogoEditarTarefa dialog = new DialogoEditarTarefa(null, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    public JFormattedTextField getCampoId() {
        return campoId;
    }
//
//    public DialogoNovaTarefaController getContro() {
//        return contro;
//    }

    public JButton getBotaoCancelar() {
        return botaoCancelar;
    }

    public JButton getBotaoConcluido() {
        return botaoConcluido;
    }

    public BeanDatePick getCampoDataAl() {
        return campoDataAl;
    }

    public BeanDatePick getCampoDataFazer() {
        return campoPrazo;
    }

    public JSpinner getCampoPrioridade() {
        return campoPrioridade;
    }

    public JTextField getCampoTitulo() {
        return campoTitulo;
    }

    public ButtonGroup getGrupoDataFazer() {
        return grupoDataFazer;
    }

    public JLabel getRotuloPriori() {
        return rotuloPriori;
    }

    public JFormattedTextField getCampoHoraAl() {
        return campoHoraAl;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.antoniodiego.gertarefas.beans.BeanDatePick beanDatePick1;
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JButton botaoConcluido;
    private javax.swing.JTextArea campoComentários;
    private br.com.antoniodiego.gertarefas.beans.BeanDatePick campoDataAl;
    private javax.swing.JFormattedTextField campoHoraAl;
    private javax.swing.JFormattedTextField campoId;
    private javax.swing.JComboBox<Tarefa> campoPosicao;
    private br.com.antoniodiego.gertarefas.beans.BeanDatePick campoPrazo;
    private javax.swing.JSpinner campoPrioridade;
    private javax.swing.JTextField campoStatus;
    private javax.swing.JTextField campoTitulo;
    private javax.swing.ButtonGroup grupoDataFazer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel rotuloPriori;
    // End of variables declaration//GEN-END:variables
}