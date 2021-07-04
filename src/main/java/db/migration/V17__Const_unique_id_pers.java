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
public class V17__Const_unique_id_pers extends BaseJavaMigration {

    private static final Logger LOG_MIG_17 = LogManager.
            getLogger("mig_17");

    @Override
    public void migrate(Context cntxt) throws Exception {
        LOG_MIG_17.traceEntry();

        Statement rejustaIds = cntxt.getConnection().createStatement();

        //Pega todas as tarefa por ordem asc de ids
        ResultSet res = rejustaIds.executeQuery("SELECT * FROM tarefas ORDER BY id ASC");

        LOG_MIG_17.debug("ret: " + res);

        int proxIdPers = 0;
        while (res.next()) {
            LOG_MIG_17.trace("próximo");
            Long id = res.getLong("id");
            int ret = rejustaIds.executeUpdate("UPDATE tarefas SET id_pers = '" + proxIdPers + "' WHERE id=" + id);
            LOG_MIG_17.debug("ret: " + ret);
            proxIdPers++;
        }

        rejustaIds.executeUpdate("ALTER TABLE tarefas ADD CONSTRAINT proibe_ids_pers_dup UNIQUE(id_pers)");

        LOG_MIG_17.traceExit();
    }
}
