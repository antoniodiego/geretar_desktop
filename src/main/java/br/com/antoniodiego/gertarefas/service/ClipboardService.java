package br.com.antoniodiego.gertarefas.service;

import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import java.awt.event.*;

public class ClipboardService {
    private AcaoRecortar acaoRec;
    private AcaoCopiar acaoCop;
    private AcaoColar acaoColar;
   // private ClipboardListener listener;

    private class AcaoRecortar extends AbstractAction {

        public AcaoRecortar() {
            super("Recortar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
            recortar(null);
        }

    }

    public void recortar(JTree arvoreTarefas) {
        TransferHandler.getCutAction()
                .actionPerformed(new ActionEvent(arvoreTarefas, ActionEvent.ACTION_PERFORMED, "cut"));

    }

    private class AcaoColar extends AbstractAction {

        public AcaoColar() {
            super("Colar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            // TODO: Arvore

            // TransferHandler.getPasteAction()
            //         .actionPerformed(new ActionEvent(arvoreTarefas, ActionEvent.ACTION_PERFORMED, "paste"));
        }

    }

    private class AcaoCopiar extends AbstractAction {

        public AcaoCopiar() {
            super("Copiar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TransferHandler.getCopyAction()
            //         .actionPerformed(new ActionEvent(arvoreTarefas, ActionEvent.ACTION_PERFORMED, "copy"));
        }

    }

}
