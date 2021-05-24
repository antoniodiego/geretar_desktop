package br.com.antoniodiego.gertarefas.pojo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Objeto que representa um grupo de tarefas.
 *
 * @author Ant?nio Diego
 *
 */
@Entity(name = "GrupoTarefas")
//Obs: parece ser bom por grupo e tarefa em uma lista só
@Table(name = "grupos_tarefas")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id_exp")
public class GrupoTarefas implements Externalizable {

    private static final long serialVersionUID = 7825973700936302897L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
//    @NaturalId(mutable = true)
//    @Column(unique = true)
    private String titulo;

    /**
     * Novo tipo de grupo tem subgrupos
     */
    //Associa??o de entidades
    /*Para salvar em castcata (filhos depois) e remover o que for removido 
    da lista*/
    //Para salvar em castcata (filhos depois)
    @OneToMany(mappedBy = "pai", cascade = javax.persistence.CascadeType.ALL,
            orphanRemoval = true)
    private List<GrupoTarefas> subgrupos;
    //Tarefas podem ser instancia de compostas ou simples
    //cascade all para salvar em castcata (filhos depois)
    @OneToMany(mappedBy = "pai", cascade = javax.persistence.CascadeType.ALL,
            orphanRemoval = true)
    private List<Tarefa> tarefas;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    //Deveria ser enviado apenas o id
    @JsonProperty("paiGrupo")
    private GrupoTarefas pai;

    public GrupoTarefas() {
        this("");
    }

    public GrupoTarefas(String titulo) {
        //setTitulo(titulo);
        this.titulo = titulo;
        tarefas = new ArrayList<>();
        subgrupos = new ArrayList<>();
    }

    public void add(Object o) {
        if (o instanceof Tarefa) {
            add((Tarefa) o);
        } else if (o instanceof GrupoTarefas) {
            add((GrupoTarefas) o);
        }
    }

    public void add(Tarefa tarefa) {
        tarefas.add(tarefa);
        tarefa.setPai(this);
    }

    /**
     *
     *
     * @param grtarefa
     */
    public void add(GrupoTarefas grtarefa) {
        subgrupos.add(grtarefa);
        //Define este como pai conforme documenta??o.
        grtarefa.pai = this;
        //Certamente o usu?rios tamb?m ? dono dele
//        grtarefa.dono = this.dono;
    }

    public void clear() {
        tarefas.clear();
    }

    public Tarefa get(int index) {
        return tarefas.get(index);
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isEmpty() {
        return tarefas.isEmpty();
    }

    public void remove(int index) {
        tarefas.remove(index);
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<GrupoTarefas> getSubgrupos() {
        return subgrupos;
    }

    @Override
    public String toString() {
        return titulo;
    }

    public int size() {
        return tarefas.size();
    }

    public void add(int index, TarefaComposta t) {
        tarefas.add(index, (TarefaComposta) t);
    }

    public void remove(TarefaComposta t) {
        tarefas.remove(t);
        t.setPai(null);
    }

    public int indexOf(TarefaComposta editando) {
        return tarefas.indexOf(editando);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GrupoTarefas getPai() {
        return pai;
    }

    public void setPai(GrupoTarefas pai) {
        this.pai = pai;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(id);
        out.writeUTF(titulo);
        out.writeObject(pai);//UTF(dono);
        out.writeObject(tarefas);
        out.writeObject(subgrupos);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = in.readLong();
        this.titulo = in.readUTF();
        this.pai = (GrupoTarefas) in.readObject();//UTF();
        this.tarefas = (List<Tarefa>) in.readObject();
        this.subgrupos = (List<GrupoTarefas>) in.readObject();
    }
}
