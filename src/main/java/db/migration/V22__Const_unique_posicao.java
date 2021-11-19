/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.migration;

import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

/**
 *
 * @author Antonio Diêgo <antoniodiegoluz at gmail.com>
 */
public class V22__Const_unique_posicao extends BaseJavaMigration {

    private static final Logger LOG_MIG_22 = LogManager.
            getLogger("mig_22");

    @Override
    public void migrate(Context cntxt) throws Exception {
        LOG_MIG_22.traceEntry();

        Statement sentenca = cntxt.getConnection().createStatement();

        //Pega todas as tarefa por ordem asc de ids
        ResultSet res = sentenca.executeQuery("SELECT * FROM tarefas ORDER BY posicao ASC");

        LOG_MIG_22.debug("ret: " + res);

        int anterior = -1;

        ResultSet resM;

        Integer maiorPosicao = 0;
        Integer posicao;
        Long id;
        while (res.next()) {
            LOG_MIG_22.trace("próximo");

            posicao = res.getInt("posicao");
            id = res.getLong("id");

            if (posicao == anterior) {
                //Posição duplicada

                String queryBuscaMaiorP = "SELECT MAX(posicao) AS maiorP FROM tarefas";
                resM = sentenca.executeQuery(queryBuscaMaiorP);

                if (resM.next()) {
                    maiorPosicao = resM.getInt("maiorP");
                }

                int ret = sentenca.executeUpdate("UPDATE tarefas SET posicao = '" + (maiorPosicao+1) + "' WHERE id=" + id);
                LOG_MIG_22.debug("ret: " + ret);
            }

            anterior = posicao;
        }

        sentenca.executeUpdate("ALTER TABLE tarefas ADD CONSTRAINT proibe_posicao_dup UNIQUE(posicao)");

        LOG_MIG_22.traceExit();
    }
}
