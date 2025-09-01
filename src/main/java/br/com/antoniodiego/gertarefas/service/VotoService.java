package br.com.antoniodiego.gertarefas.service;

import java.time.LocalDateTime;

import javax.swing.AbstractAction;

import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TipoVoto;
import br.com.antoniodiego.gertarefas.pojo.Voto;
import java.awt.event.*;

public class VotoService {

    
    public void adicionaVotoProclastinacao(Tarefa t) {
        if (t != null) {
            Voto votoPro = new Voto(TipoVoto.Proclastinei);
            t.getVotos().add(votoPro);
            t.aumentaPrio();
          //  ordenadorTabelaLista.sort();
            
        }
    }

    private class AcaoVotoLembrei extends AbstractAction {

        public AcaoVotoLembrei() {
            super("Lembrei");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // tarefaExibida.aumentaPrio();
            // tarefaExibida.setDataModif(LocalDateTime.now());
            // ordenadorTabelaLista.sort();

            // TODO
        }
    }

}
