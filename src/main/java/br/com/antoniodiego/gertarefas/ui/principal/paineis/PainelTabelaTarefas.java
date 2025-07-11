package br.com.antoniodiego.gertarefas.ui.principal.paineis;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.antoniodiego.gertarefas.model.ModeloTabelaTarefasLista;
import br.com.antoniodiego.gertarefas.persist.DAOTarefa;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.ui.renderers.LocalDateRenderer;
import br.com.antoniodiego.gertarefas.ui.renderers.LocalDateTimeRenderer;
import br.com.antoniodiego.gertarefas.util.Utilid;
import java.util.Arrays;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;

/**
 * O painel expõe o modelo da tabela de tarefas para que os da
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class PainelTabelaTarefas extends javax.swing.JPanel {

    private ModeloTabelaTarefasLista modeloTabela;
    TableRowSorter<ModeloTabelaTarefasLista> rs;

    /**
     * É melhor setar esse campo manualmente pelo Matisse, usando a opção custo-
     * mizar código no menu de contexto
     */
    private JFrame referenciaJan;
    /**
     *
     */
    public static final Logger LOG_PAINEL_T = LogManager.
            getLogger("painel_tabela");

    /**
     * Creates new form PainelListaTarefas2
     *
     * @param dono
     */
    public PainelTabelaTarefas() {
        initComponents();

        modeloTabela = new ModeloTabelaTarefasLista(tabelaTarefas);
        tabelaTarefas.setModel(modeloTabela);

        tabelaTarefas.setAutoCreateRowSorter(true);

        TableRowSorter<ModeloTabelaTarefasLista> rowS = 
                (TableRowSorter<ModeloTabelaTarefasLista>) tabelaTarefas.getRowSorter();

        SortKey sk = new TableRowSorter.SortKey(8, SortOrder.ASCENDING);
        rowS.setSortKeys(Arrays.asList(sk));

//        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
//        sortKeys.add();
//
//        rs = new TableRowSorter<>(modeloTabela);
//
//        rs.setSortKeys(sortKeys);
//        
//        rs.setComparator(8, Comparator.nullsLast((Integer o1, Integer o2) -> {
//            if (o1 != null && o2 != null) {
//                return LOG_PAINEL_T.traceExit("Compare n n", o1.compareTo(o2));
//            }
//
//            if (o1 == null && o2 == null) {
//                return LOG_PAINEL_T.traceExit("2 n", 0);
//            } else if (o1 == null) {
//                return LOG_PAINEL_T.traceExit("1 n", 0);
//            } else {
//                return LOG_PAINEL_T.traceExit("2 n", 0);
//            }
//        }));
//        
//        
//        
//        tabelaTarefas.setRowSorter(rs);
        TableColumnModel colM = tabelaTarefas.getColumnModel();

        TableColumn col;
        for (int i = 0; i < colM.getColumnCount(); i++) {
            col = colM.getColumn(i);

            switch (i) {
                case 0:
                    col.setPreferredWidth(50);
                    break;
                case 1:
                    col.setPreferredWidth(50);
                    break;
                case 2:
                    col.setPreferredWidth(200);
                    break;
                case 3:
                    col.setPreferredWidth(80);
                    col.setCellRenderer(new LocalDateRenderer());
                    break;
                case 4:
                    col.setPreferredWidth(40);
                    break;
                case 5:
                    col.setPreferredWidth(80);
                    col.setCellRenderer(new LocalDateRenderer());
                    break;
                case 6:
                    col.setPreferredWidth(80);
                    col.setCellRenderer(new LocalDateTimeRenderer());
                    break;
                case 7:
                    col.setPreferredWidth(40);
                    break;
                case 8:
                    // Posição
                    col.setPreferredWidth(40);
                    break;
                case 9:
                    col.setPreferredWidth(100);
                    break;
                case 10:
                    col.setPreferredWidth(70);
                    break;
                default:
                    break;
            }
        }

        Utilid.carregaInfoTabela(tabelaTarefas);
    }

    public JFrame getReferenciaJan() {
        return referenciaJan;
    }

    public void setReferenciaJan(JFrame referenciaJan) {
        this.referenciaJan = referenciaJan;
    }

    public TableRowSorter<ModeloTabelaTarefasLista> getRs() {
        return rs;
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

        grupoDataAgFiltr = new javax.swing.ButtonGroup();
        painelDeBusca = new javax.swing.JPanel();
        btBuscar = new javax.swing.JButton();
        campoTextoBusca = new javax.swing.JTextField();
        checkResolvidas = new javax.swing.JCheckBox();
        painelTabela = new javax.swing.JPanel();
        scrollPaneTabela = new javax.swing.JScrollPane();
        tabelaTarefas = new javax.swing.JTable();

        painelDeBusca.setMaximumSize(new java.awt.Dimension(32767, 100));
        painelDeBusca.setPreferredSize(new java.awt.Dimension(20, 50));

        btBuscar.setText("Buscar");
        btBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBuscarActionPerformed(evt);
            }
        });

        checkResolvidas.setText("Resolvidas");

        javax.swing.GroupLayout painelDeBuscaLayout = new javax.swing.GroupLayout(painelDeBusca);
        painelDeBusca.setLayout(painelDeBuscaLayout);
        painelDeBuscaLayout.setHorizontalGroup(
            painelDeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDeBuscaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelDeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelDeBuscaLayout.createSequentialGroup()
                        .addComponent(campoTextoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btBuscar))
                    .addComponent(checkResolvidas))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelDeBuscaLayout.setVerticalGroup(
            painelDeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDeBuscaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelDeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoTextoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(checkResolvidas))
        );

        painelTabela.setLayout(new javax.swing.BoxLayout(painelTabela, javax.swing.BoxLayout.LINE_AXIS));

        tabelaTarefas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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
        tabelaTarefas.setRowHeight(30);
        tabelaTarefas.setRowMargin(4);
        scrollPaneTabela.setViewportView(tabelaTarefas);

        painelTabela.add(scrollPaneTabela);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelDeBusca, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
            .addComponent(painelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelDeBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btBuscarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btBuscarActionPerformed
        String termo = campoTextoBusca.getText();
        filtraTarefasLPorTit(termo, !checkResolvidas.isSelected());
    }// GEN-LAST:event_btBuscarActionPerformed

    
    /**
     *
     * @param termo
     * @param apVenc
     */
    private void filtraTarefasLPorTit(String termo, boolean naoFeitas) {
        // TODO OBS Esse proc parece lento
        DAOTarefa daoT = new DAOTarefa();
        List<Tarefa> todasAsTar = daoT.getByTituloAndConcluida(termo, !naoFeitas);
//
//        Stream<Tarefa> st = todasAsTar.stream();
//
//        st = st.filter(t -> t.getTitulo().toLowerCase().contains(termo.toLowerCase()));

        modeloTabela.setTarefas(todasAsTar);//st.collect(Collectors.toList()));
        modeloTabela.ordena();
    }

    private void btSubirActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btSubirActionPerformed
        LOG_PAINEL_T.traceEntry();

        // A linha escolhida na tabela
        int linhaSel = tabelaTarefas.getSelectedRow();

        // Posição da tarefa no modelo
        int posicaoSelModelo = tabelaTarefas.convertRowIndexToModel(linhaSel);

        Tarefa tarefaMover = modeloTabela.getTarefas().get(posicaoSelModelo);

        if (tarefaMover.getPosicao() <= 0) {
            return;
        }

        LOG_PAINEL_T.debug("Pos {} maior que 0", tarefaMover.getTitulo());

        // Remove a posição dela
        DAOTarefa daoT = new DAOTarefa();

        Integer posicaoAt = tarefaMover.getPosicao();
        LOG_PAINEL_T.debug("Pos at {}", posicaoAt);

        Integer posSucMaior = daoT.getMaiorPosicao() + 1;
        LOG_PAINEL_T.debug("Próx m {}", posSucMaior);
        tarefaMover.setPosicao(posSucMaior);
        daoT.atualiza(tarefaMover);

        // Muda a pos da que está na frente pra dela
        Tarefa tLogoFrente = modeloTabela.getTarefas().get(posicaoSelModelo - 1);// daoT.getByPosicao(posicaoAt - 1);

        if (tLogoFrente == null) {
            return;
        }

        LOG_PAINEL_T.debug("T acima {} em {}", tLogoFrente.getTitulo(), posicaoAt - 1);

        tLogoFrente.setPosicao(posicaoAt);

        LOG_PAINEL_T.debug("Pos {} alt para {}", tLogoFrente.getTitulo(), posicaoAt);

        /*
         * Aqui seria importante recarregar a tarefa do banco na tabela da janela
         * principal
         * 
         */
        daoT.atualiza(tLogoFrente);

        tarefaMover.setPosicao(posicaoAt - 1);
        daoT.atualiza(tarefaMover);

        // modeloTabela.setTarefas(daoT.listaTodas());
        modeloTabela.ordena();

        /**
         * Após a ordenação a seleção é perdida
         *
         */
        ListSelectionModel modeloSelecao = tabelaTarefas.getSelectionModel();

        /**
         * Importante não ser duplicada
         */
        int idxNovoModelo = modeloTabela.getTarefas().indexOf(tarefaMover);

        LOG_PAINEL_T.debug("Novo índice tarefa modelo: {}", idxNovoModelo);

        int idxTabela = tabelaTarefas.convertRowIndexToView(idxNovoModelo);

        LOG_PAINEL_T.debug("Novo índice tarefa tabela: {}", idxTabela);

        /**
         * O parâmetro é o índice na tabela
         */
        modeloSelecao.setSelectionInterval(idxTabela, idxTabela);
    }// GEN-LAST:event_btSubirActionPerformed

    private void btDescerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btDescerActionPerformed
        LOG_PAINEL_T.traceEntry();

        Tarefa tarefasDescer = modeloTabela.getTarefas()
                .get(tabelaTarefas.convertRowIndexToModel(tabelaTarefas.getSelectedRow()));

        if (tarefasDescer.getPosicao() >= modeloTabela.getTarefas().size() - 1) {
            return;
        }

        Integer posicaoAt = tarefasDescer.getPosicao();

        if (tarefasDescer.getPosicao() < modeloTabela.getTarefas().size() - 1) {
            tarefasDescer.setPosicao(tarefasDescer.getPosicao() + 1);
            LOG_PAINEL_T.debug("Pos alt para " + tarefasDescer.getPosicao());
        }

        // Remove a pos
        DAOTarefa daoT = new DAOTarefa();

        tarefasDescer.setPosicao(daoT.getMaiorPosicao() + 1);
        daoT.atualiza(tarefasDescer);

        LOG_PAINEL_T.debug("Tarefa movida para o fim. Pos: " + tarefasDescer.getPosicao());

        // TODO: Trocar posição da de baixo com a dela
        Tarefa tarAbaixo = daoT.getByPosicao(posicaoAt + 1);
        if (tarAbaixo != null) {
            LOG_PAINEL_T.debug("Tarefa enc pos seg. pos: " + (posicaoAt + 1));
            tarAbaixo.setPosicao(posicaoAt);

            daoT.atualiza(tarAbaixo);
        }

        // TODO: Rever ter se galhar
        tarefasDescer.setPosicao(posicaoAt + 1);
        daoT.atualiza(tarefasDescer);

        modeloTabela.ordena();

        /**
         * Após a ordenação a seleção é perdida
         *
         */
        ListSelectionModel modeloSelecao = tabelaTarefas.getSelectionModel();

        /**
         * Importante não ser duplicada
         */
        int idxNovoModelo = modeloTabela.getTarefas().indexOf(tarefasDescer);

        LOG_PAINEL_T.debug("Novo índice tarefa modelo: {}", idxNovoModelo);

        int idxTabela = tabelaTarefas.convertRowIndexToView(idxNovoModelo);

        LOG_PAINEL_T.debug("Novo índice tarefa tabela: {}", idxTabela);

        /**
         * O parâmetro é o índice na tabela
         */
        modeloSelecao.setSelectionInterval(idxTabela, idxTabela);
    }// GEN-LAST:event_btDescerActionPerformed

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
    private javax.swing.JTextField campoTextoBusca;
    private javax.swing.JCheckBox checkResolvidas;
    private javax.swing.ButtonGroup grupoDataAgFiltr;
    private javax.swing.JPanel painelDeBusca;
    private javax.swing.JPanel painelTabela;
    private javax.swing.JScrollPane scrollPaneTabela;
    private javax.swing.JTable tabelaTarefas;
    // End of variables declaration//GEN-END:variables
}
