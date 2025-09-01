package br.com.antoniodiego.gertarefas.util;

import java.util.List;

import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;

public class TarefaUtil {
       /**
     *
     * @param grupo
     * @param destino
     */
    public static void copiaTarefas(GrupoTarefas grupo, List<Tarefa> destino) {
        // Adiciona tarefas nele
        destino.addAll(grupo.getTarefas());

        // Adiciona de filhos
        List<GrupoTarefas> subGr = grupo.getSubgrupos();
        subGr.forEach((gr) -> {
            copiaTarefas(gr, destino);
        });
    }
}
