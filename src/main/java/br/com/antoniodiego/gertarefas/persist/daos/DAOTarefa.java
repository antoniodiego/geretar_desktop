/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.persist.daos;

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
        TypedQuery<Long> maiorId = getSession().createQuery("SELECT t.idPers FROM Tarefa t ORDER by t.idPers DESC").setMaxResults(1);
        //    NativeQuery quer = getSession().createNativeQuery("SELECT MAX(id_pers) AS maior_id FROM tarefas");
        List<Long> res = maiorId.getResultList();

        LOG_DAO_T.debug("Enc: " + res.size());

        if (res.size() > 0) {
            LOG_DAO_T.debug("Maior: " + res.get(0));
        }
        
        return res.get(0);
    }
}
