package br.com.antoniodiego.gertarefas.service;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 */
public class DialogService {
      private boolean mostraConfirmacao(String titulo, String mensagem, JComponent parent) {
        return JOptionPane.showConfirmDialog(parent, mensagem, titulo, JOptionPane.YES_NO_OPTION) == 0;
    }
}
