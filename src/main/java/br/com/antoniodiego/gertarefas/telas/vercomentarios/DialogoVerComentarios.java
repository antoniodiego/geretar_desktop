package br.com.antoniodiego.gertarefas.telas.vercomentarios;

import br.com.antoniodiego.gertarefas.persist.daos.DAO;
import br.com.antoniodiego.gertarefas.persist.daos.DAOComentario;
import br.com.antoniodiego.gertarefas.persist.daos.DAOTarefa;
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
import org.hibernate.Hibernate;
import org.hibernate.Session;

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
        jScrollPane2 = new javax.swing.JScrollPane();
        listaComentarios = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        campoComentario = new javax.swing.JTextPane();
        btAdicionar = new javax.swing.JButton();
        btLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Comentários");

        campoTitulo.setEditable(false);

        listaComentarios.setModel(new ModeloComentarios());
        jScrollPane2.setViewportView(listaComentarios);

        jScrollPane1.setViewportView(campoComentario);

        btAdicionar.setText("Adicionar");
        btAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarActionPerformed(evt);
            }
        });

        btLimpar.setText("Limpar");
        btLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoTitulo)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btAdicionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btLimpar)
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(campoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAdicionar)
                    .addComponent(btLimpar))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 public void setTarefa(Tarefa t) {
        this.tarefa = t;

        ModeloComentarios modCom = ((ModeloComentarios) listaComentarios.
                getModel());

        DAOComentario daoT = new DAOComentario();
        List<Comentario> cmT = daoT.getByTarefa(t);

        modCom.setComentarios(cmT);

        campoTitulo.setText(t.getTitulo());
    }

    private void btAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarActionPerformed
        String texto = campoComentario.getText();

        Comentario coment = new Comentario();
        coment.setComentario(texto);

        Session s = DAO.getSession();

        s.getTransaction().begin();
        Tarefa tC = s.load(Tarefa.class, tarefa.getId());

        Hibernate.initialize(tC);
        Hibernate.initialize(tC.getComentarios());

        s.getTransaction().commit();

        setTarefa(tC);

        tC.getComentarios().add(coment);
        coment.setTarefa(tC);

        DAOTarefa daoT = new DAOTarefa();
        daoT.atualiza(tC);

        ModeloComentarios modCom = ((ModeloComentarios) listaComentarios.
                getModel());

        modCom.adiciona(coment);

        campoComentario.setText("");
    }//GEN-LAST:event_btAdicionarActionPerformed

    private void btLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparActionPerformed
        campoComentario.setText("");
    }//GEN-LAST:event_btLimparActionPerformed


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

    public JTextField getCampoTitulo() {
        return campoTitulo;
    }

    public ButtonGroup getGrupoDataFazer() {
        return grupoDataFazer;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.antoniodiego.gertarefas.beans.BeanDatePick beanDatePick1;
    private javax.swing.JButton btAdicionar;
    private javax.swing.JButton btLimpar;
    private javax.swing.JTextPane campoComentario;
    private javax.swing.JTextField campoTitulo;
    private javax.swing.ButtonGroup grupoDataFazer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<Comentario> listaComentarios;
    // End of variables declaration//GEN-END:variables
}
