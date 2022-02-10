package br.com.antoniodiego.gertarefas.controller;

import java.time.LocalDateTime;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * Ponto de entrada do programa
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class Principal {

    /**
     * Banco separado
     */
    public static boolean DESENV = false;

    private static final Logger LOG_PRINC = LogManager.
            getLogger("principal");

    /**
     *
     */
    public static final Logger LOG_ARQUIVO = LogManager.getLogger("saida_para_arquivo");

    public static final void main(String[] args) {
        configuraLogger();

        LOG_ARQUIVO.traceEntry();
        LOG_PRINC.traceEntry();
        JanelaPrincipalMatisseController contPrinc = new JanelaPrincipalMatisseController();

        contPrinc.instanciaJanelaPrincipal();
        contPrinc.exibeJanelaPrincipal();
        contPrinc.inicializaSistema();

        if (DESENV == false) {
            //TODO: Desativar debug
        }

        LOG_ARQUIVO.traceExit();
    }

    private static void configuraLogger() {
        //Config arq
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration cf = ctx.getConfiguration();

        //Camin arqui
        StringBuilder camArq = new StringBuilder().append("logs/log_geretar");

        LocalDateTime dataHa = LocalDateTime.now();

        camArq.append(dataHa.getDayOfMonth()).
                append(dataHa.getMonth()).
                append(dataHa.getYear());

        camArq.append("-");
        camArq.append(dataHa.getHour()).append(dataHa.getMinute()).append(dataHa.getSecond());
        camArq.append(".log");

        Layout<String> la = PatternLayout.newBuilder().
                withPattern("%d{HH:mm:ss} %-5level %logger{36} %class{36} %L %M %m%n").
                build();

        Appender arqu = FileAppender.newBuilder().withName("arquivo").
                withFileName(camArq.toString()).
                withAppend(true).withLayout(la)
               .
                build();

        arqu.start();
        LoggerConfig confLogArquivo = cf.getLoggerConfig("saida_para_arquivo");
        confLogArquivo.setLevel(Level.TRACE);
        confLogArquivo.addAppender(arqu, Level.TRACE, null);

        LoggerConfig confLogGered = cf.getLoggerConfig("principal");
        confLogGered.addAppender(arqu, Level.TRACE, null);

        ctx.updateLoggers();

        LOG_ARQUIVO.debug("Loggers configurados");
        LOG_PRINC.debug("Loggers configurados");
    }

}
