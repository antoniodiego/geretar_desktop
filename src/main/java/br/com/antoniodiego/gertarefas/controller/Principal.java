package br.com.antoniodiego.gertarefas.controller;

import br.com.antoniodiego.gertarefas.telas.principal.JanelaPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Ponto de entrada do programa
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class Principal {

    /**
     *Banco separado
     */
    public static boolean DESENV = true;

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
