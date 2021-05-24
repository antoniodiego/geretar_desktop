package br.com.antoniodiego.gertarefas.pojo;

import javax.persistence.Embeddable;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
@Embeddable
public class Voto {
    private final TipoVoto tipo;

    public Voto() {
        this.tipo = null;
    }

    public Voto(TipoVoto tipo) {
        this.tipo = tipo;
    }

    public TipoVoto getTipo() {
        return tipo;
    }   
}
