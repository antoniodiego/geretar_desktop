package br.com.antoniodiego.gertarefas.telas.sobre_2;

import br.com.antoniodiego.gertarefas.Constantes;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JTextArea;

/**
 *
 * @author Ant?nio Diego
 */
public class TelaSobre extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TelaSobre(Frame pai) {
        super(pai);
        constroi();
    }

    private void constroi() {
        setTitle("Sobre");
        setLocationByPlatform(true);
        setLocationRelativeTo(this.getOwner());
        JTextArea areaSobre = new JTextArea();
        StringBuilder texto = new StringBuilder("Gerenciador de Tarefas\n");
        texto.append("Vers\u00E3o: ").append(Constantes.VERS).append("\n");
        texto.append("Feito por: Ant\u00F4nio Diego\n");
        texto.append("E-mail: antoniodiegoluz@gmail.com\n");
        texto.append("Usu\u00e1rio padr\u00e3o: " + Constantes.NOME_USR_PADR + "\n Senha: " + Constantes.SENHA_PADR + "\n");
//        texto.append("Usu\u00e1rio atual: ").append(((JanelaPrincipalMatisse) this.getOwner()).getControl().getUsuario().getNome());
        //areaSobre.setText("Gerenciador de Tarefas\n\nFeito por: ");
        areaSobre.setText(texto.toString());
        areaSobre.setEditable(false);
        add(areaSobre);

        pack();
    }
}
