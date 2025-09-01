package br.com.antoniodiego.gertarefas.controller;

import java.io.IOException;

import br.com.antoniodiego.gertarefas.service.LoginService;

/**
 * Controlador da tela de login
 */
public class TelaLoginController {
     /**
     *
     * @param mantem
     * @throws java.io.IOException
     */
    public void fazLogin(boolean mantem) throws IOException {
     new LoginService().fazLogin(mantem);
    }
}
