/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.antoniodiego.gertarefas.util;

import br.com.antoniodiego.gertarefas.persist.daos.DAOTarefa;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class FuncoesTarefas {

    public FuncoesTarefas(){
        super();
    }
    
    /**
     *
     * @param t
     * @param posicao
     */
    public static void alteraPosicao(Tarefa t, int posicao) {
        Integer posAnt = t.getPosicao();
        DAOTarefa daoT = new DAOTarefa();

        /*
        Pega tarefa atualmente na posição
         */
        Tarefa tarefaPos = daoT.getByPosicao(posicao);
        if (tarefaPos != null) {
            //Posição já ocupada
            if (posicao < posAnt) {
                //Posição nova é mais acima que a atual

                //Move a nova tarefa pro final
                Integer maiorP = daoT.getMaiorPosicao();
                t.setPosicao(maiorP + 1);
                daoT.atualiza(t);

                //Desvia tarefas para baixo da posição anterior pra cima
                deslocaTarefasBaixo(posAnt - 1, posicao);

                t.setPosicao(posicao);
            }
        } else {
            t.setPosicao(posicao);
        }
    }

    /**
     * Desloca posição de intervalo de tarefas para baixo no intervalo
     *
     * @param posicaoUltima posição tarefas mais abaixo, de onde deve ser começado o desvio para baixo
     * @param posicaoCima Posição da tarefa no topo, que deve descer
     */
    public static void deslocaTarefasBaixo(int posicaoUltima, int posicaoCima) {
        DAOTarefa daoT = new DAOTarefa();
        Tarefa tarP;
        
        for (int i = posicaoUltima; i >= posicaoCima; i--) {
            tarP = daoT.getByPosicao(i);
            if (tarP != null) {
                tarP.setPosicao(i + 1);
                daoT.atualiza(tarP);
            }
        }
    }

    /**
     * Desloca posição de intervalo de tarefas para baixo no intervalo [posicaoCima,maiorPosicao]
     *
     * @param posicaoCima Posição da tarefa no topo, que deve descer
     */
    public static void deslocaTarefasBaixo(int posicaoCima) {
        DAOTarefa daoT = new DAOTarefa();
        deslocaTarefasBaixo(daoT.getMaiorPosicao(), posicaoCima);
    }
}
