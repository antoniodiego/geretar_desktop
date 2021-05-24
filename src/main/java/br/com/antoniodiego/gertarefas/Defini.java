package br.com.antoniodiego.gertarefas;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class Defini {

    /**
     *
     */
    public static final String STRING_VER = "2.4-beta";
    public static final Estado ESTADO = Estado.DESEN;

    public enum Estado {
        DESEN, TESTE, DEBUG, PRODUCAO;
    }
}
