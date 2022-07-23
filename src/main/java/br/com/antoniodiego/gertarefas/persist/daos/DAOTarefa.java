/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.persist.daos;

import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import java.util.List;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class DAOTarefa extends DAO {

    public static final Logger LOG_DAO_T = LogManager.getLogger("dao_tarefas");

    public Long getMaiorIDPers() {
        getSession().beginTransaction();
        TypedQuery<Long> maiorId = getSession().createQuery("SELECT t.idPers FROM Tarefa t ORDER by t.idPers DESC",
                Long.class).setMaxResults(1);
        //    NativeQuery quer = getSession().createNativeQuery("SELECT MAX(id_pers) AS maior_id FROM tarefas");
        List<Long> res = maiorId.getResultList();
        getSession().getTransaction().commit();

        LOG_DAO_T.debug("Enc: " + res.size());

        if (res.size() > 0) {
            LOG_DAO_T.debug("Maior: " + res.get(0));

            return res.get(0);
        } else {
            return 0L;
        }

    }

    /**
     *
     * @return
     */
    public Integer getMaiorPosicao() {
        getSession().beginTransaction();
        TypedQuery<Integer> maiorId = getSession().createQuery("SELECT t.posicao FROM Tarefa t ORDER by t.posicao DESC", Integer.class)
                .setMaxResults(1);
        //    NativeQuery quer = getSession().createNativeQuery("SELECT MAX(id_pers) AS maior_id FROM tarefas");
        List<Integer> res = maiorId.getResultList();
        getSession().getTransaction().commit();

        LOG_DAO_T.debug("Enc: " + res.size());

        if (res.size() > 0) {
            LOG_DAO_T.debug("Maior: " + res.get(0));

            return res.get(0);
        } else {
            return 0;
        }

    }

    public List<Tarefa> listaTodas() {
        getSession().beginTransaction();
        TypedQuery<Tarefa> queryTarefas = getSession().
                createQuery("SELECT t FROM TarefaComposta t", Tarefa.class);

        List<Tarefa> tarefas = queryTarefas.getResultList();

        getSession().getTransaction().commit();
        return tarefas;
    }

    public List<Tarefa> listaTodasSemCommit() {
        // getSession().beginTransaction();
        TypedQuery<Tarefa> queryTarefas = getSession().
                createQuery("SELECT t FROM TarefaComposta t", Tarefa.class);

        List<Tarefa> tarefas = queryTarefas.getResultList();

        // getSession().getTransaction().commit();
        return tarefas;
    }

    public Tarefa getByIdPers(Long idPers) {
        getSession().beginTransaction();
        TypedQuery<Tarefa> queryTarefas = getSession().
                createQuery("SELECT t FROM TarefaComposta t where t.idPers = :idPers", Tarefa.class).setMaxResults(1);
        queryTarefas.setParameter("idPers", idPers);

        List<Tarefa> res = queryTarefas.getResultList();
        getSession().getTransaction().commit();

        if (res.size() > 0) {
            Tarefa tarefa = res.get(0);

            return tarefa;
        } else {
            return null;
        }

    }

    public Tarefa getByPosicaoS(Integer posicao) {
        TypedQuery<Tarefa> queryTarefas = getSession().
                createQuery("SELECT t FROM TarefaComposta t where t.posicao = :posicao", Tarefa.class).setMaxResults(1);
        queryTarefas.setParameter("posicao", posicao);

        List<Tarefa> res = queryTarefas.getResultList();

        if (!res.isEmpty()) {
            Tarefa tarefa = res.get(0);

            return tarefa;
        } else {
            return null;
        }

    }

    public Tarefa getByPosicao(Integer posicao) {
        if (!getSession().getTransaction().isActive()) {
            getSession().beginTransaction();
        }

        TypedQuery<Tarefa> queryTarefas = getSession().
                createQuery("SELECT t FROM TarefaComposta t where t.posicao = :posicao", Tarefa.class).setMaxResults(1);
        queryTarefas.setParameter("posicao", posicao);

        List<Tarefa> res = queryTarefas.getResultList();

        getSession().getTransaction().commit();

        if (res.size() > 0) {
            Tarefa tarefa = res.get(0);

            return tarefa;
        } else {
            return null;
        }

    }

    public void exclui(Tarefa t) {
        Session ses = getSession();
        ses.beginTransaction();
        ses.delete(t);

        try {
            ses.getTransaction().commit();
        } catch (RollbackException ex) {
            ses.getTransaction().rollback();
        } catch (IllegalStateException ex) {

        }
    }
}
