package br.com.antoniodiego.gertarefas.app;

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

import br.com.antoniodiego.gertarefas.controller.PrincipalController;
import net.bytebuddy.description.annotation.AnnotationDescription.Latent;

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
        configuraLogger();
        inicializarSistema();
    }

    private static void configuraLogger() {
        System.out.println("Configura loggers");

        // Config arq
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration cf = ctx.getConfiguration();

        String caminhoArquivoLogger = constroiCaminhoArquivoLog();

        Appender appenderArquivo = constroiFileAppender(caminhoArquivoLogger);

        adicionaAppender(appenderArquivo, cf);
        appenderArquivo.start();

        // System.out.println("Level Root "+ confLogRoot.getLevel());
        System.out.println("Atualizando loggers...");

        // confLogRoot.setLevel(Level.ERROR);
        ctx.updateLoggers();

        // System.out.println("Level Root "+ confLogRoot.getLevel());
        LOG_ARQUIVO.debug("Loggers configurados");
        LOG_PRINC.debug("Loggers configurados");
    }

    private static void adicionaAppender(Appender appender, Configuration confLogGered) {
        LoggerConfig confLogArquivo = confLogGered.getLoggerConfig("saida_para_arquivo");
        confLogArquivo.setLevel(Level.TRACE);
        confLogArquivo.addAppender(appender, Level.TRACE, null);

        LoggerConfig confLogPrincipal = confLogGered.getLoggerConfig("principal");
        confLogPrincipal.addAppender(appender, Level.TRACE, null);
    }

    private static Layout<String> constroiLayout() {
        return PatternLayout.newBuilder()
                .withPattern("%d{HH:mm:ss} %-5level %logger{36} %class{36} %L %M %m%n").build();
    }

    private static FileAppender constroiFileAppender(String caminhoArquivoLogger) {
        return FileAppender.newBuilder().withName("arquivo").withFileName(caminhoArquivoLogger.toString())
                .withAppend(true)
                .withLayout(constroiLayout())
                .build();
    }

    /**
     * Constroi o caminho do arquivo de log
     */
    private static String constroiCaminhoArquivoLog() {
        // TODO: Implementar a construção do caminho do arquivo de log
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
        return caminhoArquivoLogger.toString();
    }

    /**
     * Inicializa o sistema
     */
    private static void inicializarSistema() {
        PrincipalController contPrinc = new PrincipalController();

        contPrinc.instanciaJanelaPrincipal();

        contPrinc.exibeJanelaPrincipal();
        contPrinc.inicializaSistema();
    }

    private void verificaModoDesenvolvimento() {
        if (DESENV == false) {
            // TODO: Desativar debug
        }
    }
}
