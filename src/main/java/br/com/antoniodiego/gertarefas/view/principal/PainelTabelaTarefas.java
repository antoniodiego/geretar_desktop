package br.com.antoniodiego.gertarefas.view.principal;

import static br.com.antoniodiego.gertarefas.controller.JanelaPrincipalMatisseController.LOG_CONTR_PRINC;
import br.com.antoniodiego.gertarefas.model.ModeloTabelaTarefasLista;
import br.com.antoniodiego.gertarefas.persist.daos.DAOTarefa;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;

/**
 * O painel expõe o modelo da tabela de tarefas para que os da
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class PainelTabelaTarefas extends javax.swing.JPanel {

    private ModeloTabelaTarefasLista modeloTabela;
    TableRowSorter<ModeloTabelaTarefasLista> rs;

    /**
     * Creates new form PainelListaTarefas2
     */
    public PainelTabelaTarefas() {
        initComponents();
        modeloTabela = new ModeloTabelaTarefasLista();
        tabelaTarefas.setModel(modeloTabela);

        List<RowSorter.SortKey> sortKeys
                = new ArrayList<RowSorter.SortKey>();
        sortKeys.add(new RowSorter.SortKey(8, SortOrder.ASCENDING));

        rs = new TableRowSorter<>(modeloTabela);

        
      //  rs.setSortKeys(sortKeys);

        rs.setComparator(8, Comparator.nullsLast(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {

                if (o1 != null && o2 != null) {

                    return LOG_CONTR_PRINC.traceExit("Compare n n", o1.compareTo(o2));
                }

                if (o1 == null && o2 == null) {
                    return LOG_CONTR_PRINC.traceExit("2 n", 0);
                } else if (o1 == null) {
                    return LOG_CONTR_PRINC.traceExit("1 n", 0);
                } else {
                    return LOG_CONTR_PRINC.traceExit("2 n", 0);
                }

            }
        })
        );
        tabelaTarefas.setRowSorter(rs);

    }

    public TableRowSorter<ModeloTabelaTarefasLista> getRs() {
        return rs;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoDataAgFiltr = new javax.swing.ButtonGroup();
        painelDeBusca = new javax.swing.JPanel();
        btBuscar = new javax.swing.JButton();
        campoTextoBusca = new javax.swing.JTextField();
        painelTabela = new javax.swing.JPanel();
        scrollPaneTabela = new javax.swing.JScrollPane();
        tabelaTarefas = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btSubir = new javax.swing.JButton();
        btDescer = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(500, 65534));
        setPreferredSize(new java.awt.Dimension(600, 400));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        painelDeBusca.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));
        painelDeBusca.setMaximumSize(new java.awt.Dimension(32767, 100));
        painelDeBusca.setPreferredSize(new java.awt.Dimension(420, 100));

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
                .addGap(17, 17, 17)
                .addComponent(campoTextoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btBuscar)
                .addContainerGap(367, Short.MAX_VALUE))
        );
        painelDeBuscaLayout.setVerticalGroup(
            painelDeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDeBuscaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(painelDeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoTextoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btBuscar))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        add(painelDeBusca);

        painelTabela.setLayout(new javax.swing.BoxLayout(painelTabela, javax.swing.BoxLayout.LINE_AXIS));

        tabelaTarefas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "ID Universal", "Tarefa", "Prazo", "Comentários", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaTarefas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        scrollPaneTabela.setViewportView(tabelaTarefas);

        painelTabela.add(scrollPaneTabela);

        btSubir.setText("Subir Posição");
        btSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSubirActionPerformed(evt);
            }
        });

        btDescer.setText("Descer");
        btDescer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDescerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btDescer)
                    .addComponent(btSubir))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btSubir)
                .addGap(18, 18, 18)
                .addComponent(btDescer)
                .addContainerGap(239, Short.MAX_VALUE))
        );

        painelTabela.add(jPanel1);

        add(painelTabela);
    }// </editor-fold>//GEN-END:initComponents

    private void btBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuscarActionPerformed

    }//GEN-LAST:event_btBuscarActionPerformed

    private void btSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSubirActionPerformed
        Tarefa t
                = modeloTabela.getTarefas().get(tabelaTarefas.convertRowIndexToModel(tabelaTarefas.getSelectedRow()));

        if (t.getPosicao() > 0) {
            t.setPosicao(t.getPosicao() - 1);
        }

        DAOTarefa daoT = new DAOTarefa();
        daoT.atualiza(t);

        modeloTabela.ordena();
    }//GEN-LAST:event_btSubirActionPerformed

    private void btDescerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDescerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btDescerActionPerformed

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

    public ModeloTabelaTarefasLista getModeloTabela() {
        return modeloTabela;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBuscar;
    private javax.swing.JButton btDescer;
    private javax.swing.JButton btSubir;
    private javax.swing.JTextField campoTextoBusca;
    private javax.swing.ButtonGroup grupoDataAgFiltr;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel painelDeBusca;
    private javax.swing.JPanel painelTabela;
    private javax.swing.JScrollPane scrollPaneTabela;
    private javax.swing.JTable tabelaTarefas;
    // End of variables declaration//GEN-END:variables
}
