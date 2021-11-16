/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.persist.daos;

import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import java.util.List;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class DAOTarefa extends DAO {

    public static final Logger LOG_DAO_T = LogManager.getLogger("dao_tarefas");

    public Long getMaiorIDPers() {
        getSession().beginTransaction();
        TypedQuery<Long> maiorId = getSession().createQuery("SELECT t.idPers FROM Tarefa t ORDER by t.idPers DESC").setMaxResults(1);
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

    public List<Tarefa> listaTodas() {
        getSession().beginTransaction();
        TypedQuery<Tarefa> queryTarefas = getSession().
                createQuery("SELECT t FROM TarefaComposta t");

        List<Tarefa> tarefas = queryTarefas.getResultList();

        getSession().getTransaction().commit();
        return tarefas;
    }

    public Tarefa getByIdPers(Long idPers) {
        getSession().beginTransaction();
        TypedQuery<Tarefa> queryTarefas = getSession().
                createQuery("SELECT t FROM TarefaComposta t where t.idPers = :idPers").setMaxResults(1);
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
}
