/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.view.principal;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class PainelFuncoes extends javax.swing.JPanel {

    /**
     * Creates new form PainelFuncoes
     */
    public PainelFuncoes() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btAumentPrio = new javax.swing.JButton();
        btDiminuiPrio = new javax.swing.JButton();

        btAumentPrio.setText("Aumenta prio");
        btAumentPrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAumentPrioActionPerformed(evt);
            }
        });

        btDiminuiPrio.setText("Diminui prio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btDiminuiPrio)
                    .addComponent(btAumentPrio))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(274, Short.MAX_VALUE)
                .addComponent(btAumentPrio)
                .addGap(26, 26, 26)
                .addComponent(btDiminuiPrio)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btAumentPrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAumentPrioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btAumentPrioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAumentPrio;
    private javax.swing.JButton btDiminuiPrio;
    // End of variables declaration//GEN-END:variables
}
