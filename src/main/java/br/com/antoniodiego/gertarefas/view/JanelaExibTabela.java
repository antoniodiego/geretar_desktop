package br.com.antoniodiego.gertarefas.view;

import br.com.antoniodiego.gertarefas.model.ModeloTabelaTarefasLista;
import br.com.antoniodiego.gertarefas.view.dialdet.DialogoVerTarefa;
import br.com.antoniodiego.gertarefas.controller.JanelaPrincipalController;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TipoVoto;
import br.com.antoniodiego.gertarefas.pojo.Voto;
import br.com.antoniodiego.gertarefas.view.principal.JanelaPrincipal;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Comparator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Essa é uma janela na qual as tarefas, independete do grupo, deverão ser
 * exibidas por ordem de prioridade
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class JanelaExibTabela extends JDialog {

    private JComboBox<TipoVoto> combOrdenarPor;
    private JButton btOrdenar;
    private JTable tabelaTarefas;
    private JButton btVerTarefa;
    private JButton btFechar;
    private ModeloTabelaTarefasLista modeloTab;
    private final JanelaPrincipal janelaMae;

    public JanelaExibTabela(JanelaPrincipal janMae) {
        this.janelaMae = janMae;

        constroi();
    }

    private void constroi() {
        setTitle("Exibição em lista");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        JLabel rotOrdenar = new JLabel("Ordenar por:");
        GridBagConstraints consRotOrd = new GridBagConstraints();
        add(rotOrdenar, consRotOrd);

        combOrdenarPor = new JComboBox<>();
        combOrdenarPor.addItem(TipoVoto.Proclastinei);

        GridBagConstraints consCombOrd = new GridBagConstraints();
        consCombOrd.gridx = 1;
        consCombOrd.gridwidth = 2;
        add(combOrdenarPor, consCombOrd);

        btOrdenar = new JButton(new AcaoOrdenar());
        GridBagConstraints consBtOrd = new GridBagConstraints();
        consBtOrd.gridx = 3;
        add(btOrdenar, consBtOrd);

        tabelaTarefas = new JTable();
        modeloTab = new ModeloTabelaTarefasLista();
        tabelaTarefas.setModel(modeloTab);

        JScrollPane painelRol = new JScrollPane(tabelaTarefas);
        GridBagConstraints consPainelTab = new GridBagConstraints();
        consPainelTab.gridy = 1;
        /*
        1 do rot, 2 do combo e 1 corresp ao botão ordenar
         */
        consPainelTab.gridwidth = 4;
        //Será que oito col de alt é bom ?
        consPainelTab.gridheight = 8;
        //Deve sempre cresc na ver quando a jan for red
        consPainelTab.weighty = 1;
        consPainelTab.weightx = 1;
        //Parece bom adicionar margens ao red da tabe
        consPainelTab.insets = new Insets(2, 2, 10, 2);
        add(painelRol, consPainelTab);

        /*quero que os botões ver e fechar fiquem sempre no canot inf direito da janela
        
         */
        btVerTarefa = new JButton(new AcaoVerTarefa());
        GridBagConstraints consBtVer = new GridBagConstraints();
        consBtVer.gridx = 2;
        consBtVer.gridy = 9;

        add(btVerTarefa, consBtVer);

        btFechar = new JButton(new AcaoFechar());
        GridBagConstraints consBtFechar = new GridBagConstraints();
        consBtFechar.gridx = 3;
        consBtFechar.gridy = 9;
        add(btFechar, consBtFechar);

        pack();
    }

    public void preenche(List<Tarefa> tarefas) {
        tarefas.sort(new JanelaPrincipalController.ComparaTarPrio());
        this.modeloTab.setTarefas(tarefas);
        
    }

    private class AcaoOrdenar extends AbstractAction {

        public AcaoOrdenar() {
            super("Ordenar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            TipoVoto tipoOrd = (TipoVoto) combOrdenarPor.getSelectedItem();
            switch (tipoOrd) {
                case Lembrei:
                    break;
                case Proclastinei:
                    modeloTab.ordena(new ComparadorVotosProc());
                    break;
            }
        }
    }

    private class AcaoVerTarefa extends AbstractAction {

        public AcaoVerTarefa() {
            super("Ver tarefa");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int idxSel = tabelaTarefas.getSelectedRow();
            int idxMod = tabelaTarefas.convertRowIndexToModel(idxSel);
            
            Tarefa tSel = modeloTab.getTarefas().get(idxMod);
            DialogoVerTarefa dVT = new DialogoVerTarefa(tSel, janelaMae);
            dVT.setVisible(true);
        }
    }

    private class AcaoFechar extends AbstractAction {

        public AcaoFechar() {
            super("Sair");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private class ComparadorVotosProc implements Comparator<Tarefa> {

        @Override
        public int compare(Tarefa o1, Tarefa o2) {
            List<Voto> votosTar1 = o1.getVotos();
            List<Voto> votosTar2 = o2.getVotos();

            int votosP1 = contaVotosProc(votosTar1);
            int votosP2 = contaVotosProc(votosTar2);

            //Os que tem maiores votos devem ser os menores (prim)
            if (votosP1 < votosP2) {
                return 1;
            } else if (votosP1 > votosP2) {
                return -1;
            } else {
                return 0;
            }
        }

        private int contaVotosProc(List<Voto> votos) {
            int total = 0;

            total = votos.stream().filter((v) -> (v.getTipo() == TipoVoto.Proclastinei)).map((_item) -> 1).reduce(total, Integer::sum);

            return total;
        }
    }
}
