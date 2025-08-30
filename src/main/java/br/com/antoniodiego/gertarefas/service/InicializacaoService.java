package br.com.antoniodiego.gertarefas.service;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.hsqldb.HsqlException;

import br.com.antoniodiego.gertarefas.controller.PrincipalController;
import br.com.antoniodiego.gertarefas.util.HibernateUtil;

public class InicializacaoService {
    
    private static final Logger LOG_CONTR_PRINC = LogManager.getLogger(PrincipalController.class);

    public void inicializar() {
        fazerMigracaoBanco();
        fazerBootstrapHibernate();
    }

    private void fazerMigracaoBanco() {
         /*
             * Faz migração do banco
             * 
             */
            Flyway fw = Flyway.configure().baselineOnMigrate(true).
                    baselineVersion("0")
                    .dataSource(HibernateUtil.determinaURIBanco(), "SA", "").
                    load();

            try {
                fw.migrate();
            } catch (FlywayException ex) {
                if (ex.getCause() instanceof SQLException) {
                    SQLException excSQL = (SQLException) ex.getCause();
                    if (excSQL.getCause() instanceof HsqlException) {
                        HsqlException excHSQL = (HsqlException) excSQL.
                                getCause();
                        LOG_CONTR_PRINC.trace(excHSQL.getErrorCode());
                        LOG_CONTR_PRINC.trace(excHSQL.getLevel());
                        LOG_CONTR_PRINC.trace(excHSQL.getMessage());
                        LOG_CONTR_PRINC.trace(excHSQL.getStatementCode());
                        LOG_CONTR_PRINC.trace(excHSQL.info);
                    }
                }
                LOG_CONTR_PRINC.catching(ex);
                try {
                    fw.repair();
                } catch (FlywayException ex2) {

                }
            }

    }

    private void fazerBootstrapHibernate() {
        HibernateUtil.getInstance().inicia();
    }

    private void carregarTarefas() {
        // TODO: Implementar a lógica para carregar as tarefas
           /* Nesse ponto o sist já dev estar inc
           * A partir daqui já deve ser possível fazer consulta do banco de dados.
            Seria interessante poder fazer isso mexendo apenas nos models.
             */
            
    }
}
        
