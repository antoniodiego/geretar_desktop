package br.com.antoniodiego.gertarefas.service;

import br.com.antoniodiego.gertarefas.persist.DAOGrupos;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;

public class GrupoService {
    private DAOGrupos daoGrupos;

    public GrupoService(DAOGrupos daoGrupos) {
        this.daoGrupos = daoGrupos;
    }

    public void salvaG(GrupoTarefas grupo) {
        // Lógica para salvar o grupo

        daoGrupos.salvaG(grupo);
    }


    public void atualizaGrupo(GrupoTarefas grupo) {
        // Lógica para atualizar o grupo
        daoGrupos.atualiza(grupo);
    }

    public void deletaGrupo(GrupoTarefas grupo) {
        // Lógica para deletar o grupo
        daoGrupos.deleta(grupo);
    }
}
