/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.ui.notificacao;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import br.com.antoniodiego.gertarefas.ui.adiar.DialogoAdiar;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class DialogoNotificacaoController {

    private DialogoNotificacao dialogoNotif;
    private final Frame princ;

    /**
     *
     * @param princ
     * @param dNotif
     */
    public DialogoNotificacaoController(Frame princ, DialogoNotificacao dNotif) {
        this.princ = princ;
        this.dialogoNotif = dNotif;

        dNotif.getRotuloNotif().setText(dNotif.getTarefa().getTitulo());

        dNotif.getBtConcluida().setAction(new AcaoConcluida());
        dNotif.getBtAdiar().setAction(new AcaoAdiar());
        dNotif.getBtOk().setAction(new AcaoOK());
    }

    private class AcaoOK extends AbstractAction {

        public AcaoOK() {
            super("OK");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dialogoNotif.dispose();
        }
    }

    private class AcaoAdiar extends AbstractAction {

        public AcaoAdiar() {
            super("Adiar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DialogoAdiar da = new DialogoAdiar(princ, false);
            da.setVisible(true);
        }
    }

    private class AcaoConcluida extends AbstractAction {

        public AcaoConcluida() {
            super("Conclúida");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dialogoNotif.getTarefa().setConcluida(true);
            dialogoNotif.dispose();
        }
    }

}
