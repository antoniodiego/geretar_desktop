/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.persist.daos;

import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class DAO {

    private static Session ses;

    static {
        ses = HibernateUtil.getInstance().getSessionFactory().openSession();
    }

    public static Session getSession() {
        return ses;
    }

    public void salva(Object o) {
        ses.beginTransaction();
        ses.save(o);
        ses.getTransaction().commit();
    }

    public void atualiza(Tarefa o) {
        ses.beginTransaction();
        ses.update(o);
        ses.getTransaction().commit();
    }
}
