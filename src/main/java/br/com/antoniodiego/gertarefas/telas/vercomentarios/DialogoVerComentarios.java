package br.com.antoniodiego.gertarefas.telas.vercomentarios;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.antoniodiego.gertarefas.pojo.Comentario;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class DialogoVerComentarios extends javax.swing.JDialog {

    /**
     *
     */
    public static final Logger LOG_ARQUIVO = LogManager.
            getLogger("saida_para_arquivo");

    /**
     *
     */
    public static final Logger LOG_EDITAR_TAREFA = LogManager.
            getLogger("editar_tarefa");
    private final ModeloComentarios modelo;

    private Tarefa tarefa;

    /**
     * Creates new form DialogoNovaTarView
     *
     * @param princ
     * @param modelTab
     */
    public DialogoVerComentarios(JFrame princ, ModeloComentarios modelTab) {
        super(princ, "Comentários", false);
        this.modelo = modelTab;
        initComponents();
        // contro = new DialogoNovaTarefaController(this);

        setLocationByPlatform(true);

        listaComentarios.setCellRenderer(new ComentarioRenderer());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoDataFazer = new javax.swing.ButtonGroup();
        beanDatePick1 = new br.com.antoniodiego.gertarefas.beans.BeanDatePick();
        campoTitulo = new javax.swing.JTextField();
        botaoConcluido = new javax.swing.JButton();
        botaoCancelar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaComentarios = new javax.swing.JList<>();
        canvas1 = new Canv();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Comentários");

        campoTitulo.setEditable(false);

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

        listaComentarios.setModel(new ModeloComentarios());
        jScrollPane2.setViewportView(listaComentarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoConcluido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoCancelar))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoTitulo)
                            .addComponent(jScrollPane2)
                            .addComponent(canvas1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(campoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(canvas1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoConcluido)
                    .addComponent(botaoCancelar))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {
// GEN-FIRST:event_botaoCancelarActionPerformed
        dispose();
    }// GEN-LAST:event_botaoCancelarActionPerformed

    private void campoHoraAlActionPerformed(java.awt.event.ActionEvent evt) {
// GEN-FIRST:event_campoHoraAlActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_campoHoraAlActionPerformed

    private void botaoConcluidoActionPerformed(java.awt.event.ActionEvent evt) 
    {// GEN-FIRST:event_botaoConcluidoActionPerformed
        LOG_EDITAR_TAREFA.trace("Salvando tarefa...");

    }// GEN-LAST:event_botaoConcluidoActionPerformed

    public void setTarefa(Tarefa t) {
        this.tarefa = t;
        ModeloComentarios modCom = ((ModeloComentarios) listaComentarios.
                getModel());

        // List<Reflexao> r = t.getReflexoes();
        List<Comentario> coment = modCom.getComent();

        Comentario teste = new Comentario();
        teste.setComentario("Teste 1");

        coment.add(teste);

        Comentario teste2 = new Comentario();
        teste2.setComentario("Teste 2");
        coment.add(teste2);
        int x = 0;
        while (x < 10) {
            coment.add(teste2);
            x++;
        }
//        r.forEach((relato) -> {
//            Comentario c = new Comentario();
//            c.setData(relato.getDataCriacao());
//            c.setHora(relato.getHora());
//            c.setComentario(relato.getTexto());
//            coment.add(c);
//        });

        campoTitulo.setText(t.getTitulo());
    }

    private class Canv extends Canvas {

        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.black);

            g2d.draw(new RoundRectangle2D.Float(10, 0, 100, 100, 10, 10));
        }

    }

    // GEN-LAST:event_btDataActionPerformed
    // GEN-LAST:event_btHoraActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogoVerComentarios.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>

        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogoVerComentarios dialog = new DialogoVerComentarios(null, null);
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

    //
    // public DialogoNovaTarefaController getContro() {
    // return contro;
    // }
    public JButton getBotaoCancelar() {
        return botaoCancelar;
    }

    public JButton getBotaoConcluido() {
        return botaoConcluido;
    }

    public JTextField getCampoTitulo() {
        return campoTitulo;
    }

    public ButtonGroup getGrupoDataFazer() {
        return grupoDataFazer;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.antoniodiego.gertarefas.beans.BeanDatePick beanDatePick1;
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JButton botaoConcluido;
    private javax.swing.JTextField campoTitulo;
    private java.awt.Canvas canvas1;
    private javax.swing.ButtonGroup grupoDataFazer;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<Comentario> listaComentarios;
    // End of variables declaration//GEN-END:variables
}
