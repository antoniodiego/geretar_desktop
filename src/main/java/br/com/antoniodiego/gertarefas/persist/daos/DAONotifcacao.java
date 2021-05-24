/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.persist.daos;

import br.com.antoniodiego.gertarefas.pojo.Notificacao;
import br.com.antoniodiego.gertarefas.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class DAONotifcacao {

    private HibernateUtil hUtil;
    private SessionFactory sessFact;

    public DAONotifcacao() {
        hUtil = HibernateUtil.getInstance();
        sessFact = hUtil.getSessionFactory();
    }

    public List<Notificacao> getAll() {
        Session ses = sessFact.openSession();
        ses.beginTransaction();
        List<Notificacao> n = ses.createQuery("select n from Notificacao n", Notificacao.class).list();

        try {
            /*
            TODO: Será que é necessario fazer commit mesmo em queries ?
             */
            ses.getTransaction().commit();
        } catch (Exception ex) {
            ses.getTransaction().rollback();
            ses.close();
        }
        return n;
    }
}
