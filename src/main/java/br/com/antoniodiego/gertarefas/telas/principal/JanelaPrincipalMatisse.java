package br.com.antoniodiego.gertarefas.telas.principal;

import br.com.antoniodiego.gertarefas.persist.daos.DAOTarefa;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.com.antoniodiego.gertarefas.telas.novatarefa.DialogoNovaTarView;
import br.com.antoniodiego.gertarefas.telas.principal.paineis.PainelTabelaTarefas;
import javax.swing.JTable;
import net.minidev.json.JSONObject;

/**
 *
 * @author Antonio Diêgo
 */
public class JanelaPrincipalMatisse extends javax.swing.JFrame {

    /**
     * Creates new form JanelaPrincipalMatisse
     */
    public JanelaPrincipalMatisse() {
        initComponents();
    }

    public PainelTabelaTarefas getPainelTarefas() {
        return painelTabelaTarefas1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPaneDireito = new javax.swing.JSplitPane();
        painelTabelaTarefas1 = new br.com.antoniodiego.gertarefas.telas.principal.paineis.PainelTabelaTarefas();
        painelFuncoes1 = new br.com.antoniodiego.gertarefas.telas.principal.paineis.PainelFuncoes(painelTabelaTarefas1,this);
        barraDeMenus = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        itemNovaTarefa = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        miExcluirTarefa = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        menuExportarComoXML = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        menuEditar = new javax.swing.JMenu();
        sepMenuEd = new javax.swing.JPopupMenu.Separator();
        menuOp = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerente de tarefas");
        setLocationByPlatform(true);
        setPreferredSize(new java.awt.Dimension(1200, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        splitPaneDireito.setResizeWeight(1.0);
        splitPaneDireito.setOneTouchExpandable(true);

        painelTabelaTarefas1.setReferenciaJan(this);
        painelTabelaTarefas1.setPreferredSize(new java.awt.Dimension(800, 600));
        splitPaneDireito.setLeftComponent(painelTabelaTarefas1);
        splitPaneDireito.setRightComponent(painelFuncoes1);

        menuArquivo.setText("Arquivo");

        itemNovaTarefa.setText("Nova tarefa");
        itemNovaTarefa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNovaTarefaActionPerformed(evt);
            }
        });
        menuArquivo.add(itemNovaTarefa);

        jMenuItem2.setText("Novo grupo");
        menuArquivo.add(jMenuItem2);

        jMenuItem3.setText("Agendar");
        menuArquivo.add(jMenuItem3);

        jMenuItem4.setText("Ver detalhes");
        menuArquivo.add(jMenuItem4);

        jMenuItem5.setText("Excluir tudo");
        menuArquivo.add(jMenuItem5);

        jMenuItem6.setText("Trocar usuário");
        menuArquivo.add(jMenuItem6);

        jMenuItem7.setText("Sair");
        menuArquivo.add(jMenuItem7);

        jMenuItem8.setText("jMenuItem8");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        menuArquivo.add(jMenuItem8);

        miExcluirTarefa.setText("Excluir tarefa");
        miExcluirTarefa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExcluirTarefaActionPerformed(evt);
            }
        });
        menuArquivo.add(miExcluirTarefa);

        jMenuItem10.setText("Reiniciar banco");
        menuArquivo.add(jMenuItem10);

        jMenuItem11.setText("Fazer backup do banco");
        menuArquivo.add(jMenuItem11);

        jMenuItem12.setText("Exportar como texto");
        menuArquivo.add(jMenuItem12);

        menuExportarComoXML.setText("Exportar XML");
        menuExportarComoXML.setToolTipText("Exporta todas as tarefas para um arquivo XML");
        menuExportarComoXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExportarComoXMLActionPerformed(evt);
            }
        });
        menuArquivo.add(menuExportarComoXML);

        jMenuItem14.setText("Importar de XML");
        menuArquivo.add(jMenuItem14);

        jMenuItem15.setText("jMenuItem15");
        menuArquivo.add(jMenuItem15);

        barraDeMenus.add(menuArquivo);

        menuEditar.setText("Editar");
        menuEditar.add(sepMenuEd);

        menuOp.setText("Opções");
        menuEditar.add(menuOp);

        barraDeMenus.add(menuEditar);

        jMenu3.setText("Exibir");
        barraDeMenus.add(jMenu3);

        jMenu4.setText("Ajuda");
        barraDeMenus.add(jMenu4);

        setJMenuBar(barraDeMenus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(splitPaneDireito, javax.swing.GroupLayout.PREFERRED_SIZE, 1086, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(splitPaneDireito, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemNovaTarefaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNovaTarefaActionPerformed
        JanPrinMatController.LOG_CONTR_PRINC.trace("Item nova tar");

        DialogoNovaTarView dialogoNovaTar = new DialogoNovaTarView(this, painelTabelaTarefas1.getModeloTabela());
        dialogoNovaTar.setVisible(true);
    }//GEN-LAST:event_itemNovaTarefaActionPerformed

    private void menuExportarComoXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExportarComoXMLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuExportarComoXMLActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        TableColumnModel modelC = painelTabelaTarefas1.getTabelaTarefas().getColumnModel();
        TableColumn col;

        JSONObject js = new JSONObject();
        JSONObject config;
        for (int i = 0; i < modelC.getColumnCount(); i++) {
            col = modelC.getColumn(i);

            config = new JSONObject();
            config.put("width", col.getWidth());
            config.put("index", i);
            js.put(String.valueOf(col.getIdentifier()), config);
        }

        File arquivoTam = new File("colunas.json");
        FileWriter fw;
        try {
            fw = new FileWriter(arquivoTam);
            fw.append(js.toJSONString());
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(JanelaPrincipalMatisse.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONObject objetoProp = new JSONObject();
        objetoProp.appendField("largura", this.getWidth());
        objetoProp.appendField("altura", this.getHeight());
objetoProp.appendField("estado", this.getExtendedState());

        File arquivoProp = new File("propriedades.json");

        try {
            fw = new FileWriter(arquivoProp);
            fw.append(objetoProp.toJSONString());
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(JanelaPrincipalMatisse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void miExcluirTarefaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExcluirTarefaActionPerformed
        JTable tabelaTarefas = painelTabelaTarefas1.getTabelaTarefas();
        int idxEscolhida = tabelaTarefas.getSelectedRow();
        int idxModel = tabelaTarefas.convertRowIndexToModel(idxEscolhida);

//Exclui do banco
        Tarefa t = painelTabelaTarefas1.getModeloTabela().getTarefas().get(idxModel);
        DAOTarefa daoT = new DAOTarefa();
        daoT.exclui(t);

        painelTabelaTarefas1.getModeloTabela().removeTarefa(idxModel);

        tabelaTarefas.getSelectionModel().setSelectionInterval(idxEscolhida, idxEscolhida);
    }//GEN-LAST:event_miExcluirTarefaActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

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
            java.util.logging.Logger.getLogger(JanelaPrincipalMatisse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipalMatisse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipalMatisse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipalMatisse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaPrincipalMatisse().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraDeMenus;
    private javax.swing.JMenuItem itemNovaTarefa;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenu menuArquivo;
    private javax.swing.JMenu menuEditar;
    private javax.swing.JMenuItem menuExportarComoXML;
    private javax.swing.JMenuItem menuOp;
    private javax.swing.JMenuItem miExcluirTarefa;
    private br.com.antoniodiego.gertarefas.telas.principal.paineis.PainelFuncoes painelFuncoes1;
    private br.com.antoniodiego.gertarefas.telas.principal.paineis.PainelTabelaTarefas painelTabelaTarefas1;
    private javax.swing.JPopupMenu.Separator sepMenuEd;
    private javax.swing.JSplitPane splitPaneDireito;
    // End of variables declaration//GEN-END:variables
}
