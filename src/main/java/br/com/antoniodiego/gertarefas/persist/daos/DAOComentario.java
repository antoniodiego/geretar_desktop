/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.persist.daos;

import br.com.antoniodiego.gertarefas.pojo.Comentario;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import java.util.List;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class DAOComentario extends DAO {

    public static final Logger LOG_DAO_T = LogManager.getLogger("dao_tarefas");

    public List<Comentario> getByTarefa(Tarefa t) {
        Session ses = getSession();
        ses.beginTransaction();
        Query<Comentario> query = ses.createQuery("select c from Comentario c"
                + " where c.tarefa = :tarefa");

        query.setParameter("tarefa", t);
        
        List<Comentario> coment = query.list();

        ses.getTransaction().commit();
        
        return coment;
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
