package br.com.antoniodiego.gertarefas.persist.daos;

import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import br.com.antoniodiego.gertarefas.pojo.Usuario;
import br.com.antoniodiego.gertarefas.util.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Gerenciador de grupos Hibernate
 *
 * @author Ant?nio Diego
 */
//TODO: Melhorar DO Crud pra sessão por app
public class DAOGrupos {

    private final SessionFactory fabS;
    private Usuario usu;
    private HibernateUtil hu;

    public DAOGrupos(HibernateUtil hu) {
        fabS = hu.getSessionFactory();
    }

//    /**
//     * Lista todos os grupos do banco
//     *
//     * @return
//     */
//    public List<GrupoTarefas> receG() {
//        List<GrupoTarefas> l;
//        try (Session s = fabS.openSession()) {
//            s.beginTransaction();
//            l = s.createQuery("from GrupoTarefas").list();
//            l.stream().map((o) -> (GrupoTarefas) o).map((g) -> {
//                Hibernate.initialize(g.getTarefas());
//                return g;
//            }).forEachOrdered((g) -> {
//                Hibernate.initialize(g.getSubgrupos());
//            });
//            s.getTransaction().commit();
//        }
//        return l;
//    }
//    /**
//     * Lista todos os grupos de um usu?rio
//     *
//     * @return
//     */
//    //N]ao est? dando para criar nova tarefa em subgrupo  - CORRIGIDO 
//    public List<GrupoTarefas> receGU() {
//        List<GrupoTarefas> l;
//        try (Session s = fabS.openSession()) {
//            s.beginTransaction();
//            //TODO: Pai tem que ser nulo
//            l = s.createQuery("select g from GrupoTarefas g where g.dono.nome like :nomeUsu and g.pai is null").setParameter("nomeUsu", this.usu.getNome()).list();
////            l.stream().map((o) -> (GrupoTarefas) o).forEachOrdered((g) -> {
////                Hibernate.initialize(g.recebeConte\u00fado());
////            });
////Inicializa as tarefas dos grupos filhos da raiz
//            inicializa(l);
////            l.stream().map((o) -> (GrupoTarefas) o).map((g) -> {
////                Hibernate.initialize(g.recebeConte\u00fado());
////                Hibernate.initialize(g.getSubgrupos());
////                return g;
////            }).forEachOrdered((g) -> {
////                Hibernate.initialize(g.getSubgrupos());
////                Hibernate.initialize(g.recebeConte\u00fado());
////            });
//            s.getTransaction().commit();
//        }
//        return l;
//    }
    /**
     * Inicializa tare sub e faz o mesmo com os subs
     *
     * @param li
     */
    private void inicializa(List<GrupoTarefas> li) {
        li.forEach((GrupoTarefas g) -> {
            //incializa tarefas do grupo
            Hibernate.initialize(g.getTarefas());
            //Inicializa tarefas filhas de cada tarefa comp
            List<Tarefa> tarefas = g.getTarefas();
            tarefas.forEach((Tarefa t) -> {
                if (t instanceof TarefaComposta) {
                    TarefaComposta tc = (TarefaComposta) t;
                    Hibernate.initialize(tc.getTarefasFilhas());
                }
            });
            Hibernate.initialize(g.getSubgrupos());
            List<GrupoTarefas> sub = g.getSubgrupos();
            inicializa(sub);
        });
    }

    public void salvaG(GrupoTarefas g) {
        try (Session s = fabS.openSession()) {
            s.beginTransaction();
            //TODO: Melhorar
            //        g.setDono(usu);//NomeUsu(this.usu.getNome());
            s.save(g);
            s.getTransaction().commit();
        }
    }

    public void atuG(GrupoTarefas g) {
        try (Session s = fabS.openSession()) {
            s.beginTransaction();
            s.update(g);
            s.getTransaction().commit();
        }
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public void deletaTudo(String nomeUsu) {
        try (Session s = fabS.openSession()) {
            s.beginTransaction();
            s.createQuery("delete from GrupoTarefas where nomeUsu like '" + nomeUsu + "'");//this.usu.getNome() + "'");
            s.getTransaction().commit();
        }
    }

    public void deleta(GrupoTarefas g) {
        try (Session s = fabS.openSession()) {
            s.beginTransaction();
            s.delete(g);
            s.getTransaction().commit();
        }
    }

    public void deletaTudo(List<GrupoTarefas> grupos) {
        grupos.stream().forEach((g) -> {
            deleta(g);
        });
    }

//    /**
//     *
//     */
//    public void fazBackupB() {
//        
//        s.beginTransaction();
//        s.createNativeQuery("BACKUP DATABASE TO 'copiseg/' NOT BlOCKING").executeUpdate();
//        s.getTransaction().commit();
//    }
}
