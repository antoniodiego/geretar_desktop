package br.com.antoniodiego.gertarefas.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

/**
 * Tarefa que pode ter uma ou mais tarefas (menores).
 *
 * @author Ant?nio Diego- Comp:Ant?nio Diego <antonio.diego at antonio.org>
 */
@Entity
public class TarefaComposta extends Tarefa {

    @ElementCollection
    @CollectionTable(name = "tarefasfilhas_de_comps")
    private List<TarefaCoordenada> tarefasFilhas;

    public TarefaComposta() {
        //Parece ser importante para iniciar alguns campos. Constraint violation
        super();
        tarefasFilhas = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public List<TarefaCoordenada> getTarefasFilhas() {
        return tarefasFilhas;
    }

    public void setTarefasFilhas(List<TarefaCoordenada> tarefasFilhas) {
        this.tarefasFilhas = tarefasFilhas;
    }


}
