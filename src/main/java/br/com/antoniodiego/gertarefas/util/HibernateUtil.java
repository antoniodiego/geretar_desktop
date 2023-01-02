/*
 *Copyright
 * 
 * 
 */
package br.com.antoniodiego.gertarefas.util;

import br.com.antoniodiego.gertarefas.Defini;
import br.com.antoniodiego.gertarefas.pojo.Agendamento;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Notificacao;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import br.com.antoniodiego.gertarefas.pojo.Usuario;
import br.com.antoniodiego.gertarefas.principal.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author Antonio Diego
 */
public class HibernateUtil {

    private final static HibernateUtil INSTANCIA = new HibernateUtil();
    public static final String PASTA_GERETAR = "gerente_tarefas_si";
    public static final String PASTA_BANCO = "banco";
    public static final String NOME_BANCO = "bancotarefas";

    private static SessionFactory sessionFactory;
    private static BootstrapServiceRegistry bsr;
    private static MetadataSources smd;
    private static Metadata dm;
    private static Logger logUt = LogManager.getLogger("Util");

    public static HibernateUtil getInstance() {
        return INSTANCIA;
    }

    /**
     * Incializa o Hibernate
     *
     * @return
     */
    //TODO: Talv fosse bom que em caso de erro fossem feitas outras tent
    public boolean inicia() {
        logUt.traceEntry("Iniciando bootstrap do Hib");

        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            logUt.trace("Criando bsr...");
            bsr = new BootstrapServiceRegistryBuilder().build();
            StandardServiceRegistryBuilder cons
                    = new StandardServiceRegistryBuilder(bsr);
            cons.configure("hibernate.cfg.xml");

            logUt.trace("Subreescrevendo conf...");
            //Define local do banco para pasta do usuário

            cons.applySetting("hibernate.connection.url", determinaURIBanco());

            StandardServiceRegistry sr = cons.build();
            smd = new MetadataSources(sr);
            // smd.addAnnotatedClass(Tarefa.class);
            smd.addAnnotatedClass(Usuario.class).
                    addAnnotatedClass(GrupoTarefas.class);
            smd.addAnnotatedClass(Tarefa.class);
            //Importante mapear pois se n?o n?o reconhece heran?a
            smd.addAnnotatedClass(TarefaComposta.class);
            //porque tarcom referenci uma entidade desco (TarSim)
            //smd.addAnnotatedClass(TarefaSimples.class);

            smd.addAnnotatedClass(Notificacao.class);
            smd.addAnnotatedClass(Agendamento.class);

            dm = smd.buildMetadata();
            sessionFactory = dm.buildSessionFactory();
            //sessionFactory = new BootstrapServiceRegistryBuilder().build();
            logUt.debug("Final do bootstrap");

            return logUt.traceExit(true);
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            return false;
            // throw new ExceptionInInitializerError(ex);
        }
    }

    public static String determinaURIBanco() {
        StringBuilder consURL = new StringBuilder("jdbc:hsqldb:file:");
        String homeU = System.getProperty("user.home");
        String pathS = System.getProperty("file.separator");
        consURL.append(homeU);

        consURL.append(pathS).append(HibernateUtil.PASTA_GERETAR);

        StringBuilder nomePastaBanco = new StringBuilder("banco");

        switch (Defini.ESTADO) {
            case TESTE:
                nomePastaBanco.append("_").append("teste");
                break;
            case DEBUG:
                nomePastaBanco.append("_").append("debug");
                break;
        }

        if (Principal.DESENV) {
            nomePastaBanco.append("_dese");
        }

        consURL.append(pathS).
                append(nomePastaBanco).append(pathS).append(HibernateUtil.
                        NOME_BANCO);

        logUt.debug("Sep pas: " + pathS);
        logUt.debug("URL Cons: " + consURL);

        return consURL.toString();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Metadata getDm() {
        return dm;
    }
}
