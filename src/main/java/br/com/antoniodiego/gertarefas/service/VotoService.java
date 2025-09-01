package br.com.antoniodiego.gertarefas.service;

import java.time.LocalDateTime;

import javax.swing.AbstractAction;

import br.com.antoniodiego.gertarefas.pojo.TipoVoto;
import br.com.antoniodiego.gertarefas.pojo.Voto;
import javafx.event.ActionEvent;

public class VotoService {
    
    private class AcaoVotProc extends AbstractAction {

        public AcaoVotProc() {
            super("Proclastinei");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (tarefaExibida != null) {
                Voto votoPro = new Voto(TipoVoto.Proclastinei);
                tarefaExibida.getVotos().add(votoPro);
                tarefaExibida.aumentaPrio();
                ordenadorTabelaLista.sort();
                daoUsuario.flush();
            }
        }
    }

      private class AcaoVotoLembrei extends AbstractAction {

        public AcaoVotoLembrei() {
            super("Lembrei");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            tarefaExibida.aumentaPrio();
            tarefaExibida.setDataModif(LocalDateTime.now());
            ordenadorTabelaLista.sort();
        }
    }

}
