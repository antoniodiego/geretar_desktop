package br.com.antoniodiego.gertarefas.view.principal;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class PainelTabelaTarefas extends javax.swing.JPanel {

    /**
     * Creates new form PainelListaTarefas2
     */
    public PainelTabelaTarefas() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoDataAgFiltr = new javax.swing.ButtonGroup();
        painelDeAcoes = new javax.swing.JPanel();
        painelDeBusca = new javax.swing.JPanel();
        btBuscar = new javax.swing.JButton();
        campoTextoBusca = new javax.swing.JTextField();
        scrollPaneTabela = new javax.swing.JScrollPane();
        tabelaTarefas = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(500, 65534));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        painelDeBusca.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

        btBuscar.setText("Buscar");
        btBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelDeBuscaLayout = new javax.swing.GroupLayout(painelDeBusca);
        painelDeBusca.setLayout(painelDeBuscaLayout);
        painelDeBuscaLayout.setHorizontalGroup(
            painelDeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDeBuscaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(campoTextoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btBuscar)
                .addContainerGap(223, Short.MAX_VALUE))
        );
        painelDeBuscaLayout.setVerticalGroup(
            painelDeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDeBuscaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelDeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoTextoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btBuscar))
                .addContainerGap())
        );

        javax.swing.GroupLayout painelDeAcoesLayout = new javax.swing.GroupLayout(painelDeAcoes);
        painelDeAcoes.setLayout(painelDeAcoesLayout);
        painelDeAcoesLayout.setHorizontalGroup(
            painelDeAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelDeBusca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        painelDeAcoesLayout.setVerticalGroup(
            painelDeAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDeAcoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelDeBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        add(painelDeAcoes);

        tabelaTarefas.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelaTarefas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        scrollPaneTabela.setViewportView(tabelaTarefas);

        add(scrollPaneTabela);
    }// </editor-fold>//GEN-END:initComponents

    private void btBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btBuscarActionPerformed

    public JTable getTabelaTarefas() {
        return tabelaTarefas;
    }

    public ButtonGroup getGrupoDataAgFiltr() {
        return grupoDataAgFiltr;
    }

    public JButton getBtBuscar() {
        return btBuscar;
    }

    public void setBtBuscar(JButton btBuscar) {
        this.btBuscar = btBuscar;
    }

    public JTextField getCampoTextoBusca() {
        return campoTextoBusca;
    }

    public void setCampoTextoBusca(JTextField campoTextoBusca) {
        this.campoTextoBusca = campoTextoBusca;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBuscar;
    private javax.swing.JTextField campoTextoBusca;
    private javax.swing.ButtonGroup grupoDataAgFiltr;
    private javax.swing.JPanel painelDeAcoes;
    private javax.swing.JPanel painelDeBusca;
    private javax.swing.JScrollPane scrollPaneTabela;
    private javax.swing.JTable tabelaTarefas;
    // End of variables declaration//GEN-END:variables
}