package br.com.antoniodiego.gertarefas.principal;

import br.com.antoniodiego.gertarefas.persist.daos.DAOUsuario;
import br.com.antoniodiego.gertarefas.telas.modelos.ModeloArvore;

public interface Controller {

    DAOUsuario getDaoUsuario();

    ModeloArvore getModeloArv();

    void atualizaEstadoDosMenusBotoes();

    void atualizaBarraDeStatus();
    
}
