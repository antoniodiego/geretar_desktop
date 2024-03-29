package br.com.antoniodiego.gertarefas.principal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

import br.com.antoniodiego.gertarefas.telas.principal.JanPrinMatController;

/**
 * Ponto de entrada do programa
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class Principal {

    public Principal() {
        super();
    }

    /**
     * Banco separado
     */
    public static boolean DESENV = false;

    private static final Logger LOG_PRINC = LogManager.getLogger("principal");

    /**
     *
     */
    public static final Logger LOG_ARQUIVO = LogManager.
            getLogger("saida_para_arquivo");

    public static final void main(String[] args) {
        System.out.println("main");

        LOG_PRINC.traceEntry();

        LOG_PRINC.trace("Configurando loggers...");
        configuraLogger();

        LOG_ARQUIVO.traceEntry();
        LOG_PRINC.traceEntry();
        JanPrinMatController contPrinc = new JanPrinMatController();

        contPrinc.instanciaJanelaPrincipal();

        contPrinc.exibeJanelaPrincipal();
        contPrinc.inicializaSistema();

        if (DESENV == false) {
            // TODO: Desativar debug
        }

        LOG_ARQUIVO.traceExit();
    }

    private static void configuraLogger() {
        System.out.println("Configura loggers");

        // Config arq
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration cf = ctx.getConfiguration();

        // LoggerConfig confLogRoot= cf.getLoggerConfig("root");
        // System.out.println("Level Root "+ confLogRoot.getLevel());
        // Caminho arquivo
        StringBuilder caminhoArquivoLogger = new StringBuilder("logs/");

        LocalDateTime dataHa = LocalDateTime.now();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-YYYY");

        String dataAt = dataHa.format(format);

        caminhoArquivoLogger.append(dataAt).append("/");

        // Adiciona a data
        DateTimeFormatter formataHora = DateTimeFormatter.ofPattern("HH");

        // LOG_PRINC.debug("Mes data atual: {}", dataHa.getMonth());
        String dataFormatada = dataHa.format(formataHora);

        caminhoArquivoLogger.append(dataFormatada);
        caminhoArquivoLogger.append(".log");

        System.out.println("Caminho arquivo: " + caminhoArquivoLogger.toString());

        Layout<String> la = PatternLayout.newBuilder()
                .withPattern("%d{HH:mm:ss} %-5level %logger{36} %class{36} %L %M %m%n").build();

        Appender arqu = FileAppender.newBuilder().withName("arquivo").withFileName(caminhoArquivoLogger.toString())
                .withAppend(true)
                .withLayout(la)
                .build();

        arqu.start();
        LoggerConfig confLogArquivo = cf.getLoggerConfig("saida_para_arquivo");
        confLogArquivo.setLevel(Level.TRACE);
        confLogArquivo.addAppender(arqu, Level.TRACE, null);

        LoggerConfig confLogGered = cf.getLoggerConfig("principal");
        confLogGered.addAppender(arqu, Level.TRACE, null);

        // System.out.println("Level Root "+ confLogRoot.getLevel());
        System.out.println("Atualizando loggers...");

        // confLogRoot.setLevel(Level.ERROR);
        ctx.updateLoggers();

        // System.out.println("Level Root "+ confLogRoot.getLevel());
        LOG_ARQUIVO.debug("Loggers configurados");
        LOG_PRINC.debug("Loggers configurados");
    }

}
