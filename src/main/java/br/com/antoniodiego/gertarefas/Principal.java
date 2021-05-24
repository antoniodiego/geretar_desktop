
package br.com.antoniodiego.gertarefas;

import br.com.antoniodiego.gertarefas.view.principal.JanelaPrincipal;
import java.awt.EventQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class Principal {

    public static boolean DESENV = true;
    
    private static final Logger LOG_PRINC = LogManager.
            getLogger(JanelaPrincipal.class);

    public static final void main(String[] args) {
        JanelaPrincipal princ = new JanelaPrincipal();
        // Boas práticas
        EventQueue.invokeLater(() -> {

            LOG_PRINC.traceEntry();
            LOG_PRINC.trace("Em run invo lat");
            /*FIXME: Parece que os c?digos seguintes est?o iniciando o
            Hibernate antes de 
                 *exibir a janela do aplicativo o que est? causando demora
            para ele iniciar
               * Corrigido*/
            LOG_PRINC.trace("Antes instanc pri");
            LOG_PRINC.trace("Antes setVisi");

            princ.setVisible(true);
        });

        princ.getControl().inicializa();
        
        if(DESENV == false){
           //TODO: Desativar debug
        }
    }
}
