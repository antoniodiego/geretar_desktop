package br.com.antoniodiego.gertarefas;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public interface Constantes {

    /**
     * 
     */
    public static final String HOME_USU = System.getProperty("user.home");
    /**
     * 
     */
    public static final String SEP_ARQ = System.getProperty("file.separator");
    public static final String NOME_USR_PADR = "usr";
    public static final String SENHA_PADR = "padr";
    /**
     * Versão do aplicativo
     */

    public static final String VERS = "2.4-beta";
    public static final Locale LOCAL_BR = new Locale("pt", "BR");
    public static final DateTimeFormatter FORMATADOR_DATA_BR_T
            = DateTimeFormatter.ofPattern("dd/MM/u", LOCAL_BR);
    public static final DateTimeFormatter FORMATADOR_DATA_H_BR_T
            = DateTimeFormatter.ofPattern("dd/MM/u[ kk:mm]", LOCAL_BR);
}
