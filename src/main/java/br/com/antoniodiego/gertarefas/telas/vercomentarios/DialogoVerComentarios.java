package br.com.antoniodiego.gertarefas.telas.vercomentarios;

import br.com.antoniodiego.gertarefas.model.ModeloTabelaTarefasLista;
import br.com.antoniodiego.gertarefas.persist.daos.DAO;
import br.com.antoniodiego.gertarefas.persist.daos.DAOTarefa;
import javax.swing.ButtonGroup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.antoniodiego.gertarefas.pojo.Comentario;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.telas.principal.JanelaPrincipalMatisse;
import java.time.LocalDateTime;
import javax.swing.JTextArea;
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
    private final JanelaPrincipalMatisse janPrinc;

    private Tarefa tarefa;
    private final ModeloTabelaTarefasLista modeloLista;
    //   private final ModeloComentarios modCom;

    /**
     * Creates new form DialogoNovaTarView
     *
     * @param princ
     * @param modeloLista
     */
    public DialogoVerComentarios(JanelaPrincipalMatisse princ,
            ModeloTabelaTarefasLista modeloLista) {
        super(princ, "Comentários", false);

        initComponents();
        this.janPrinc = princ;
        this.modeloLista = modeloLista;

        setLocationByPlatform(true);

        System.out.println(rotTar.getFont());
        System.out.println(campoTitulo.getFont());
        
           System.out.println(rotTar.getForeground());
        System.out.println(campoTitulo.getForeground());
        
        System.out.println(painelComentarios.getWidth());
        System.out.println(painelComentarios.getPreferredSize());
        System.out.println(spPainelC.getWidth());
        System.out.println(spPainelC.getViewport().getWidth());
        //  listaComentarios.setCellRenderer(new ComentarioRenderer());
//        modCom = ((ModeloComentarios) listaComentarios.
//                getModel());
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
        jScrollPane1 = new javax.swing.JScrollPane();
        campoComentario = new javax.swing.JTextPane();
        btAdicionar = new javax.swing.JButton();
        btLimpar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        campoTitulo = new javax.swing.JTextArea();
        rotTar = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        spPainelC = new javax.swing.JScrollPane();
        painelComentarios = new br.com.antoniodiego.gertarefas.telas.vercomentarios.PainelComentarios();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Comentários");

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

        campoTitulo.setEditable(false);
        campoTitulo.setColumns(20);
        campoTitulo.setLineWrap(true);
        campoTitulo.setRows(5);
        jScrollPane3.setViewportView(campoTitulo);

        rotTar.setText("Tarefa:");

        jLabel2.setText("Comentários:");

        jLabel3.setText("Novo comentário:");

        painelComentarios.setMinimumSize(new java.awt.Dimension(50, 100));

        javax.swing.GroupLayout painelComentariosLayout = new javax.swing.GroupLayout(painelComentarios);
        painelComentarios.setLayout(painelComentariosLayout);
        painelComentariosLayout.setHorizontalGroup(
            painelComentariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        painelComentariosLayout.setVerticalGroup(
            painelComentariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        spPainelC.setViewportView(painelComentarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btAdicionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btLimpar)
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rotTar)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(spPainelC))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rotTar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spPainelC, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAdicionar)
                    .addComponent(btLimpar))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 public void setTarefa(Tarefa t) {
        //Carrega tarefa do banco com nova sessão
        Session s = DAO.getSession();

        s.getTransaction().begin();
        Tarefa tC = s.get(Tarefa.class, t.getId());
        this.tarefa = tC;
        atualizaTarefa(t, tarefa);

        painelComentarios.setComent(tC.getComentarios());
        //   modCom.setComentarios(tC.getComentarios());
        s.getTransaction().commit();

        campoTitulo.setText(t.getTitulo());
    }

    private void btAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarActionPerformed
        String texto = campoComentario.getText();

        Comentario coment = new Comentario();
        coment.setComentario(texto);

        tarefa.getComentarios().add(coment);

        painelComentarios.add(coment);
        // modCom.adiciona(coment);

        coment.setTarefa(tarefa);

        tarefa.setDataModif(LocalDateTime.now());

        //Atualiza linha tabela
        int idx = modeloLista.getTarefas().indexOf(tarefa);
        modeloLista.fireTableRowsUpdated(idx, idx);

        DAOTarefa daoT = new DAOTarefa();
        daoT.atualiza(tarefa);

        campoComentario.setText("");
    }//GEN-LAST:event_btAdicionarActionPerformed

    /**
     *
     * @param antiga Versão antiga [atual no modelo]
     * @param versaoNova
     */
    public void atualizaTarefa(Tarefa antiga, Tarefa versaoNova) {
        //Faz tarefa ser recarregada
        int idx = modeloLista.getTarefas().indexOf(antiga);

        modeloLista.getTarefas().set(idx, versaoNova);

        modeloLista.fireTableRowsUpdated(idx, idx);

    }

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
        java.awt.EventQueue.invokeLater(() -> {
            DialogoVerComentarios dialog = new DialogoVerComentarios(null, null);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    public JTextArea getCampoTitulo() {
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
    private javax.swing.JTextArea campoTitulo;
    private javax.swing.ButtonGroup grupoDataFazer;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private br.com.antoniodiego.gertarefas.telas.vercomentarios.PainelComentarios painelComentarios;
    private javax.swing.JLabel rotTar;
    private javax.swing.JScrollPane spPainelC;
    // End of variables declaration//GEN-END:variables
}
