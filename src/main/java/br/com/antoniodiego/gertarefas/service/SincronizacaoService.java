package br.com.antoniodiego.gertarefas.service;

import java.awt.event.ActionEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import javax.swing.AbstractAction;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class SincronizacaoService {

private Logger logSinc = LoggerFactory.getLogger("sinc");
      // /**
    // * Faz a conexao e carregamento dos dados do banco
    // */
    // public void inicializa() {
    // LOG_CONTR_PRINC.traceEntry("Inicando Thread de inic");
    // Thread th = new Thread(new TarefaInicia());
    // th.start();
    // }
    /**
     * Sincroniza com backend
     */
    private class AcaoSincronizar extends AbstractAction {

        private final CloseableHttpClient cliente = HttpClients.createDefault();
        private static final String URL_POST = "http://localhost:8006/grupos/";

        public AcaoSincronizar() {
            super("Sincronizar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            logSinc.trace("Inici pro de sincro...");
        
            RestTemplate templ = new RestTemplate();

            URI uriInfo = null;

            try {
                uriInfo = new URI("http://localhost:8015/sinc/info");
            } catch (URISyntaxException ex) {
                // java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE,
                // null, ex);
            }

            LocalDateTime dataUlSincServ = templ.getForObject(uriInfo, LocalDateTime.class);

            logSinc.debug("Data ul atu rec " + dataUlSincServ);

        }
    }
    // ====== Fim das acões====== //
 private void sincronizaDados() {
        // TODO: Implementar a sincronização de dados
          /*
             * Aqui deve ser bom se com com o serv de sinc
             */ // LOG_CONTR_PRINC.trace("Inici pro de sincro...");
            //
            // RestTemplate templ = new RestTemplate();
            //
            // URI uriInfo = null;
            //
            // try {
            // uriInfo = new URI("http://localhost:8015/sinc/info");
            // } catch (URISyntaxException ex) {
            // java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE,
            // null, ex);
            // }
            //
            // LocalDateTime dataUlSincServ = null;
            // try {
            // dataUlSincServ = templ.getForObject(uriInfo, LocalDateTime.class);
            // } catch (ResourceAccessException ex) {
            //
            // Throwable cause = ex.getCause();
            // if (cause instanceof ConnectException) {
            // // Provavelmente o serv está offline
            // // Obs: talvez fosse bom apenas igonorar
            // // JOptionPane.showMessageDialog(view, "Houve uma falha de comunicão com o
            // // servidor");
            // LOG_CONTR_PRINC.info("Houve uma falha de comunicão com o servidor");
            // return;
            // }
            // }
            //
            // LOG_CONTR_PRINC.debug("Data ul atu rec " + dataUlSincServ);
            //
            // if (dataUlSincServ == null) {
            // // Primeiro cl a se con. Enviar dados
            //
            // HttpHeaders head = new HttpHeaders();
            // head.add("Accept", MediaType.APPLICATION_XML_VALUE);
            // head.setContentType(MediaType.APPLICATION_XML);
            //
            // RestTemplate reT = new RestTemplate();
            // // GrupoTarefas gr = usuario.getGrupoRaiz();
            //
            // List<GrupoTarefas> subG = gr.getSubgrupos();
            // LOG_CONTR_PRINC.trace("Qaunt g: " + subG.size());
            // GrupoTarefas g1 = subG.get(0);
            // LOG_CONTR_PRINC.debug("Envi: " + g1);
            // br.com.antoniodiego.gertarefas.pojo.Tarefa tar1 = g1.get(0);
            // LocalDate data = tar1.getDataCriacao();
            //
            // HttpEntity<GrupoTarefas> reB = new HttpEntity<>(g1, head);
            //
            // URI uriEnviaGrupo = null;
            //
            // try {
            // uriEnviaGrupo = new URI("http://localhost:8015/grupo/");
            // } catch (URISyntaxException ex) {
            // java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(Level.SEVERE,
            // null, ex);
            // }
            //
            // try {
            // LOG_CONTR_PRINC.trace("Fazendo requi...");
            // GrupoTarefas gt = reT.postForObject(uriEnviaGrupo, reB, GrupoTarefas.class);
            //
            // if (gt != null) {
            // LOG_CONTR_PRINC.info("Grupo enviado +");
            // } else {
            // LOG_CONTR_PRINC.error("Falha no envio");
            // }
            // } catch (RestClientException ex) {
            // ex.printStackTrace();
            //
            // if (ex instanceof HttpClientErrorException) {
            // HttpClientErrorException hce = (HttpClientErrorException) ex;
            // LOG_CONTR_PRINC.error("Corpo resp: " + hce.getResponseBodyAsString());
            // }
            // }
            // }
    }
}
