package br.com.antoniodiego.gertarefas.controller;

import br.com.antoniodiego.gertarefas.model.ModeloTabAgend;

public class AgendamentoController {

    private ModeloTabAgend modAg;
    
    // private PainelAgController contrPA;
    private void confPainelAg() {
        modAg = new ModeloTabAgend();

    }
    public ModeloTabAgend getModAg() {
        return modAg;
    }
}
