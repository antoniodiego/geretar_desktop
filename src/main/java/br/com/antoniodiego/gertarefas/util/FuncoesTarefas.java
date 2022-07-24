/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.antoniodiego.gertarefas.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import br.com.antoniodiego.gertarefas.persist.daos.DAOTarefa;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class FuncoesTarefas {
    /**
     *
     */
    public static final Logger LOG_PAINEL_T = LogManager.getLogger("funcoes");

    public FuncoesTarefas() {
        super();
    }

    /**
     *
     * @param t
     * @param posicao
     */
    public static void alteraPosicao(Tarefa t, int posicao) {
        LOG_PAINEL_T.traceEntry("Alterando posição");

        Integer posAnt = t.getPosicao();
        DAOTarefa daoT = new DAOTarefa();

        /*
         * Pega tarefa atualmente na posição
         */
        Tarefa tarefaPos = daoT.getByPosicao(posicao);
        if (tarefaPos != null) {
            // Posição já ocupada
            if (posicao < posAnt) {
                // Posição nova é mais acima que a atual

                // Move a nova tarefa pro final
                Integer maiorP = daoT.getMaiorPosicao();
                t.setPosicao(maiorP + 1);
                daoT.atualiza(t);

                // Desvia tarefas para baixo da posição anterior pra cima
                deslocaTarefasBaixo(posAnt - 1, posicao);

                t.setPosicao(posicao);
            } else {
                LOG_PAINEL_T.trace("Mover tarefa para baixo");

            }
        } else {
            t.setPosicao(posicao);
        }
    }

    /**
     * Desloca posição de intervalo de tarefas para baixo no intervalo
     *
     * @param posicaoUltima posição tarefas mais abaixo, de onde deve ser
     *                      começado o desvio para baixo
     * @param posicaoCima   Posição da tarefa no topo, que deve descer
     */
    public static void deslocaTarefasBaixo(int posicaoUltima, int posicaoCima) {

        deslocaTarefasBaixoV2(posicaoUltima, posicaoCima);
        // long currentMillis = System.currentTimeMillis();

        // DAOTarefa daoT = new DAOTarefa();
        // Tarefa tarP;
        // Session sessao = DAOTarefa.getSession();
        // sessao.beginTransaction();
        // for (int i = posicaoUltima; i >= posicaoCima; i--) {

        // tarP = daoT.getByPosicaoS(i);

        // if (tarP != null) {
        // tarP.setPosicao(i + 1);
        // sessao.update(tarP);
        // // daoT.atualiza(tarP);
        // }
        // }
        // sessao.getTransaction().commit();

        // long instanteFinal = System.currentTimeMillis();
        // ;

        // long duracaoDeslocamento = instanteFinal - currentMillis;

        // LOG_NOVA_TAREFA.debug("Deslocamento levou: {}", duracaoDeslocamento);
        // LOG_NOVA_TAREFA.debug("Deslocamento levou: {}", duracaoDeslocamento / 1000);

    }

    public static void deslocaTarefasBaixoImp1(int posicaoUltima, int posicaoCima) {

        long currentMillis = System.currentTimeMillis();

        DAOTarefa daoT = new DAOTarefa();
        Tarefa tarP;
        Session sessao = DAOTarefa.getSession();
        sessao.beginTransaction();
        for (int i = posicaoUltima; i >= posicaoCima; i--) {

            tarP = daoT.getByPosicaoS(i);

            if (tarP != null) {
                tarP.setPosicao(i + 1);
                sessao.update(tarP);
                // daoT.atualiza(tarP);
            }
        }
        sessao.getTransaction().commit();

        long instanteFinal = System.currentTimeMillis();
        ;

        long duracaoDeslocamento = instanteFinal - currentMillis;

        LOG_PAINEL_T.debug("Deslocamento levou: {}", duracaoDeslocamento);
        LOG_PAINEL_T.debug("Deslocamento levou: {}", duracaoDeslocamento / 1000);
    }

    /**
     * Desloca posição de intervalo de tarefas para baixo no intervalo
     * 
     * Otimizado para não fazer muitas consultas no banco.
     * 
     * Ele faz todas as alt na memória e depois atualiza
     *
     * @param posicaoUltima posição tarefas mais abaixo, de onde deve ser
     *                      começado o desvio para baixo
     * @param posicaoCima   Posição da tarefa no topo, que deve descer
     */
    public static void deslocaTarefasBaixoV2(int posicaoUltima, int posicaoCima) {
        LOG_PAINEL_T.traceEntry("Posicao ult: {}. Pos cima: {}", posicaoUltima, posicaoCima);

        long currentMillis = System.currentTimeMillis();

        DAOTarefa daoT = new DAOTarefa();
        Tarefa tarP;
        // Session sessao = DAOTarefa.getSession();

        List<Tarefa> todas = daoT.listaTodas();
        for (int i = posicaoUltima; i >= posicaoCima; i--) {

          //  LOG_PAINEL_T.debug("Pegando: {}", i);

            tarP = getByPosicao(todas, i);

            if (tarP != null) {
            //    LOG_PAINEL_T.debug("Movendo: {}", i + 1);
                tarP.setPosicao(i + 1);
                //LOG_PAINEL_T.debug("Update");
                // sessao.update(tarP);
                daoT.atualiza(tarP);
            }
        }
        // sessao.getTransaction().commit();

        long instanteFinal = System.currentTimeMillis();

        long duracaoDeslocamento = instanteFinal - currentMillis;

        LOG_PAINEL_T.debug("Deslocamento levou: {}", duracaoDeslocamento);
        LOG_PAINEL_T.debug("Deslocamento levou: {}", duracaoDeslocamento / 1000);
    }

    private static Tarefa getByPosicao(List<Tarefa> tarefas, int posicao) {
        for (Tarefa t : tarefas) {
            if (t.getPosicao() == posicao) {
                return t;
            }
        }

        return null;
    }

    /**
     * Desloca posição de intervalo de tarefas para baixo no intervalo
     * [posicaoCima,maiorPosicao]
     *
     * @param posicaoCima Posição da tarefa no topo, que deve descer
     */
    public static void deslocaTarefasBaixo(int posicaoCima) {
        DAOTarefa daoT = new DAOTarefa();
        deslocaTarefasBaixo(daoT.getMaiorPosicao(), posicaoCima);
    }
}
