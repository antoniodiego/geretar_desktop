package br.com.antoniodiego.gertarefas;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class Defini {

    /**
     *
     */
    public static final String STRING_VER = "2.4.8-beta";

    /**
     * Importante
     *
     * Muda estágio. O local do banco muda de acordo com ele.
     *
     * O estado de desenv deve ser configurado na classe Principal
     */
    public static final Estado ESTADO = Estado.PRODUCAO;

    public enum Estado {
        TESTE, DEBUG, PRODUCAO;
    }
}
