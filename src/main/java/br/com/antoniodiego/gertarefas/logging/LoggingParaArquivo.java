package br.com.antoniodiego.gertarefas.logging;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingParaArquivo {
    private File arquivoAtual;
    private PrintWriter writer;

    private static LoggingParaArquivo instancia;

    private LoggingParaArquivo() {

    }

    public static LoggingParaArquivo getInstancia() {
        if (instancia == null) {
            instancia = new LoggingParaArquivo();
        }

        return instancia;
    }

    public void incializa() {

    }

    private PrintWriter getWriter() {
        if (writer == null) {
            // Caminho arquivo
            StringBuilder pastaArquivoLogger = new StringBuilder("logs_2/");

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-YYYY");

            LocalDateTime dataHa = LocalDateTime.now();

            String dataAt = dataHa.format(format);

            pastaArquivoLogger.append("/").append(dataAt).append("/");

            // Nome do arquivo

            DateTimeFormatter formataHora = DateTimeFormatter.ofPattern("HH");

            // LOG_PRINC.debug("Mes data atual: {}", dataHa.getMonth());
            String dataFormatada = dataHa.format(formataHora);

            StringBuilder nomeArquivoBuilder = new StringBuilder();
            nomeArquivoBuilder.append(dataFormatada);
            nomeArquivoBuilder.append(".log");

            File pasta = new File(pastaArquivoLogger.toString());
            pasta.mkdirs();

            arquivoAtual = new File(pasta, nomeArquivoBuilder.toString());

            if (!arquivoAtual.exists())
                try {
                    arquivoAtual.createNewFile();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            try {
                writer = new PrintWriter(arquivoAtual);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return writer;
    }

    public void loga(String log) throws IOException {
        getWriter().println(log);
        getWriter().flush();
    }

    public void loga(String log, Object... param) throws IOException {
        getWriter().printf(log, param);
        getWriter().println();
        getWriter().flush();
    }
}
