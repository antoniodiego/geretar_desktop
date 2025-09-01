package br.com.antoniodiego.gertarefas.service;

import java.util.List;

import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;

public class StatusService {
    
    private int contaTotalFazer(GrupoTarefas gr) {
        List<GrupoTarefas> subg = gr.getSubgrupos();
        List<Tarefa> grupTars = gr.getTarefas();
        int totF = 0;

        if (subg != null) {
            // TODO: Por em metodo sep
            for (GrupoTarefas g : subg) {
                // TODO: sub e filhas
                List<GrupoTarefas> subgB = g.getSubgrupos();
                totF = subgB.stream().map((gf) -> contaTotalFazer(gf)).reduce(totF, Integer::sum);
                List<Tarefa> liT = g.getTarefas();
                totF = liT.stream().filter((t) -> (!t.isConcluida())).map((_item) -> 1).reduce(totF, Integer::sum);
            }
        }

        // Conta tarefas
        grupTars.stream().filter((ta) -> (ta.isConcluida())).map((ite) -> 1).reduce(totF, Integer::sum);
        return totF;
    }

}
