/*
 */
package br.com.antoniodiego.gertarefas.controller;

import br.com.antoniodiego.gertarefas.pojo.Agendamento;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.telas.principal.DialogoAgend;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import javax.swing.AbstractAction;

/**
 *
 * @author Ant�noio Diego <antoniodiegoluz at gmail.com>
 */
public class DialogoAgendControl {

    private final DialogoAgend view;
    private final JanelaPrincipalController ctrP;

    public DialogoAgendControl(DialogoAgend da, JanelaPrincipalController contrP) {
        this.view = da;
        this.ctrP = contrP;
        conf();
    }

    private void conf() {
        view.getBtSalvar().setAction(new AcaoSalvar());
    }

    private class AcaoSalvar extends AbstractAction {

        public AcaoSalvar() {
            super("Salvar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Tarefa tar = view.getTar();

            Agendamento ag = new Agendamento();

            if (view.getRbHoje().isSelected()) {
                ag.setDataAgendamento(LocalDate.now());
            } else if (view.getRbAmanha().isSelected()) {
                ag.setDataAgendamento(LocalDate.now().plusDays(1));
            } else if (view.getRbDataEsp().isSelected()) {
                ag.setDataAgendamento(view.getCampoDataEsp().getModeloDef().getValue());
            }

            tar.adiciAg(ag);

            ctrP.getDaoUsuario().flush();

            ctrP.getModAg().adicAg(ag);
            
            view.dispose();
        }
    }

}
