/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.telas.principal.paineis;

import br.com.antoniodiego.gertarefas.persist.daos.DAOAgendamentos;
import br.com.antoniodiego.gertarefas.pojo.Agendamento;
import br.com.antoniodiego.gertarefas.telas.principal.JanelaPrincipalController;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class PainelAgController {

    private final JanelaPrincipalController controlP;
    private final PainelAgendamentosMat view;

    public PainelAgController(JanelaPrincipalController contrPrinc, PainelAgendamentosMat painelAg) {
        this.controlP = contrPrinc;
        this.view = painelAg;
        config();
    }

    private void config() {
        view.getBtFiltrar().setAction(new AcaoFiltrar());
        view.getBtLimpar().setAction(new AcaoLimpar());
    }

    private class AcaoFiltrar extends AbstractAction {

        public AcaoFiltrar() {
            super("Filtrar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (view.getCheckData().isSelected()) {

                LocalDate dataAg = null;
                if (view.getRbHoje().isSelected()) {
                    dataAg = LocalDate.now();
                } else if (view.getRbAmanha().isSelected()) {
                    dataAg = LocalDate.now().plusDays(1);
                } else if (view.getRbDataEsp().isSelected()) {
                    dataAg = view.getCampoDaF().getModeloDef().getValue();
                }

                filtraAg(dataAg);
            }
        }

        private void filtraAg(LocalDate data) {
            DAOAgendamentos daG = new DAOAgendamentos();
            List<Agendamento> ag = daG.getAll();
            List<Agendamento> fil;
            if (data == null) {
                return;
            }
            fil = ag.stream().filter(age -> (age.getDataAgendamento() != null
                    && age.getDataAgendamento()
                            .equals(data))).collect(Collectors.toList());
            controlP.getModAg().setAg(fil);
        }
    }

    private class AcaoLimpar extends AbstractAction {

        public AcaoLimpar() {
            super("Limpar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            view.getCheckData().setSelected(false);
            view.getRbHoje().setSelected(true);
            view.getCampoDaF().getModeloDef().setValue(null);

            DAOAgendamentos daG = new DAOAgendamentos();
            List<Agendamento> ag = daG.getAll();
            controlP.getModAg().setAg(ag);
        }
    }
}
