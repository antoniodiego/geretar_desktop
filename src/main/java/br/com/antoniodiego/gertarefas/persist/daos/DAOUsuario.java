package br.com.antoniodiego.gertarefas.persist.daos;

import br.com.antoniodiego.gertarefas.Constantes;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import br.com.antoniodiego.gertarefas.pojo.Usuario;
import br.com.antoniodiego.gertarefas.util.HibernateUtil;
import br.com.antoniodiego.gertarefas.util.Utilid;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

/**
 *
 * @author Antonio
 */
public class DAOUsuario {

    private final SessionFactory fabS;
    private Session s;
    private Logger logGereUSU;
    private final HibernateUtil hu;

    public DAOUsuario(HibernateUtil hu) {
        this.hu = hu;
        logGereUSU = LogManager.getLogger("DAO Usu");
        this.fabS = hu.getSessionFactory();
    }

    public void abresSS() {
        this.s = fabS.openSession();
    }

    public List<Usuario> receUs() {
        return null;
    }

    public void salva(Usuario us) {
        //   try (Session s = fabS.openSession()) {
        s.beginTransaction();
        s.save(us);
        s.getTransaction().commit();
        //  }
    }

    public void atu(Usuario us) {
        // try (Session s = fabS.openSession()) {
        s.beginTransaction();
        s.update(us);
        s.getTransaction().commit();
        //  }
    }

    /**
     *
     * @param tarefa
     */
    public void atualiza(TarefaComposta tarefa) {
        // try (Session s = fabS.openSession()) {
        s.beginTransaction();
        s.update(tarefa);
        s.getTransaction().commit();
        // }
    }

    /**
     *
     * @param tarefa
     */
    public void salva(TarefaComposta tarefa) {
        /// try (Session s = fabS.openSession()) {
        s.beginTransaction();
        s.save(tarefa);
        s.getTransaction().commit();
        // }
    }

    /**
     *
     * @param grupo
     */
    public void salvaG(GrupoTarefas grupo) {
        //try (Session s = fabS.openSession()) {
        s.beginTransaction();
        s.save(grupo);
        s.getTransaction().commit();
        // }
    }

    /**
     *
     * @param g
     */
    public void atualiza(GrupoTarefas g) {
        // try (Session s = fabS.openSession()) {
        s.beginTransaction();
        s.update(g);
        s.getTransaction().commit();
        // }
    }

    public void satualiza(GrupoTarefas g) {
        //  try (Session s = fabS.openSession()) {
        s.beginTransaction();
        s.saveOrUpdate(g);
        s.getTransaction().commit();
        // }
    }

    public void dele(Usuario u) {
        //  try (Session s = fabS.openSession()) {
        s.beginTransaction();
        s.delete(receUPorH(u.getNome(), u.getEmb()));
        s.getTransaction().commit();
        //  }
    }

    public void flush() {
        //TODO: Checar se já tem berta
        s.beginTransaction();
        s.flush();
        s.getTransaction().commit();
    }

    public void deletaPorNomeES(Usuario u) {
        System.out.println("dele usu+" + u);
        System.out.println("tem: " + temU(u));

        Usuario rec = receUPorH(u.getNome(), u.getEmb());
        System.out.println("Enc: " + rec);
//TODO: Pesquisar usurio por nome NatutralID
        //   try (Session se = fabS.openSession()) {
        s.beginTransaction();
        //  se.createQuery("delete from Usuario u where u.nome like '"+u.getNome()+"' and u.emb like '"+Utilid.recHas(u)+"'");
        s.createNativeQuery("DELETE * FROM Usuario WHERE nome='" + u.getNome() + "' AND emb='" + u.getEmb() + "'");
        //TODO: excluir tars
        s.getTransaction().commit();
        //  }
    }

    /**
     *
     * @param nome
     * @param se
     * @return
     */
    public Usuario receU(String nome, char[] se) {
        Usuario ue = null;
        // try (Session se = fabS.openSession()) {
        s.beginTransaction();
        List us = s.createQuery("select u from Usuario u where u.nome like '" + nome + "' and u.emb like '"
                + Utilid.geraEmb(nome, se) + "'").getResultList();
        if (us.size() > 0) {
            ue = (Usuario) us.get(0);
           // if (ue.getGrupoRaiz() != null) {
               /// logGereUSU.trace("inic ini de colec");
//                inicializa(ue.getGrupoRaiz().getSubgrupos());
//                Hibernate.initialize(ue.getGrupoRaiz().getTarefas());
               // logGereUSU.trace("fim de ini de colec");
          //  }
        }
        s.getTransaction().commit();
        // }
        return ue;
    }

    public Usuario receUPorH(String nome, String hash) {
        List us;
        // try (Session se = fabS.openSession()) {
        s.beginTransaction();
        us = s.
                createQuery("select u from Usuario u where u.nome like '"
                        + nome + "' and u.emb like '" + hash + "'").
                getResultList();
        s.getTransaction().commit();
        // }
//TODO: pesqui um so - NaturalId
        if (us.size() > 0) {
            return (Usuario) us.get(0);
        } else {
            return null;
        }
    }

    public boolean temU(Usuario u) {
//        Session s = fabS.openSession();
//        s.beginTransaction();
//        List<Usuario> l = s.createNativeQuery("SELEC * FROM Usuario WHERE nome='" + u.getNome() + "'").list();
//        if (l.size() > 0) {
//            return true;
//        } else {
//            return false;
//        }
        return temU(u.getNome());
    }

    /**
     *
     * @param n
     * @return
     */
    public boolean temU(String n) {
        s.beginTransaction();
        Usuario u = s.byNaturalId(Usuario.class).using("nome", n).getReference();
        s.getTransaction().commit();

        return u != null;
    }

    /**
     * Inicializa tare sub e faz o mesmo com os subs
     *
     * @param li
     */
    private void inicializa(List<GrupoTarefas> li) {
        logGereUSU.traceEntry();
        Hibernate.initialize(li);
        li.forEach((GrupoTarefas g) -> {
            //incializa tarefas do grupo
            Hibernate.initialize(g.getTarefas());
            //Inicializa tarefas filhas de cada tarefa comp
            List<br.com.antoniodiego.gertarefas.pojo.Tarefa> tarefas = g.getTarefas();
            tarefas.forEach((br.com.antoniodiego.gertarefas.pojo.Tarefa t) -> {
                if (t instanceof TarefaComposta) {
                    TarefaComposta tc = (TarefaComposta) t;
                    Hibernate.initialize(tc.getTarefasFilhas());
                }
            });
            Hibernate.initialize(g.getSubgrupos());
            List<GrupoTarefas> sub = g.getSubgrupos();
            inicializa(sub);
        });

        logGereUSU.traceExit();
    }

    /**
     * Reinicia o banco
     *
     * @throws SQLException
     */
    //XXX: Obs: não func
    public void reinicia() throws SQLException {
        // Configuration conf = new Configuration(smd);
        SchemaExport es = new SchemaExport();
        // SchemaUpdate ea = new SchemaUpdate();
        // EnumSet<TargetType> en = new

        es.create(EnumSet.of(TargetType.DATABASE), hu.getDm());
        // String procedimento = "DROP TABLE Transacoes";
        // try (Session se = sf.openSession()) {
        // se.beginTransaction();
        // se.createNativeQuery(procedimento).executeUpdate();
        // se.getTransaction().commit();
        // }
        // se.c
        // Statement pre = conexao.createStatement();
        // pre.executeUpdate(procedimento);
        // criaTabela();
    }

    /**
     *
     */
    public void fazBackupB() {
        logGereUSU.traceEntry();
        inicTr();
        logGereUSU.trace("Gerando SQL backup...");
        StringBuilder cSQL = new StringBuilder("BACKUP DATABASE TO ");
        cSQL.append("'");
        cSQL.append(Constantes.HOME_USU).append(Constantes.SEP_ARQ);
        cSQL.append("copiseg").append(Constantes.SEP_ARQ).append("'");
        cSQL.append("NOT BlOCKING");
        logGereUSU.debug("Geraado: " + cSQL);
        s.createNativeQuery(cSQL.toString()).executeUpdate();
        s.getTransaction().commit();
        logGereUSU.traceExit();
    }

    public Transaction inicTr() {
        if (!s.getTransaction().isActive()) {
            return s.beginTransaction();
        } else {
            //TODO: Rollba
            return s.getTransaction();
        }
    }
}
