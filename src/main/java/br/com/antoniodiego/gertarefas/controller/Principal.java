package br.com.antoniodiego.gertarefas.controller;

import static br.com.antoniodiego.gertarefas.controller.JanelaPrincipalController.LOG_CONTR_PRINC;
import br.com.antoniodiego.gertarefas.persist.daos.DAOTarefa;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.util.HibernateUtil;
import br.com.antoniodiego.gertarefas.view.principal.JanelaPrincipal;
import br.com.antoniodiego.gertarefas.view.principal.JanelaPrincipalMatisse;
import java.awt.EventQueue;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.hsqldb.HsqlException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Ponto de entrada do programa
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class Principal {

    public static boolean DESENV = false;

    private static final Logger LOG_PRINC = LogManager.
            getLogger(JanelaPrincipal.class);

    public static final void main(String[] args) {
        JanelaPrincipalMatisseController contPrinc = new JanelaPrincipalMatisseController();

        contPrinc.instanciaJanelaPrincipal();
        contPrinc.exibeJanelaPrincipal();
        contPrinc.inicializaSistema();

        if (DESENV == false) {
            //TODO: Desativar debug
        }
    }

}
