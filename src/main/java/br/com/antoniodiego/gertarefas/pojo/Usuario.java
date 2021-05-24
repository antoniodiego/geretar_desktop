package br.com.antoniodiego.gertarefas.pojo;

import br.com.antoniodiego.gertarefas.CriptoUtils;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

/**
 * POJO Usu?rio do aplicativo
 *
 * @author Ant?nio Diego
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NaturalId(mutable = true)
    @Column(unique = true)
    private String nome;
    @NaturalId(mutable = true)
    @Column(unique = true)
    private String emb;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @Basic(optional = false)
    private GrupoTarefas grupoRaiz;

    public Usuario() {
        grupoRaiz = new GrupoTarefas("Tarefas");
    }

    /**
     * Senha n?o ? arama
     *
     * @param nome
     * @param cs
     */
    public Usuario(String nome, char[] cs) {
        this();
        this.nome = nome;
        this.emb = geraEmb(cs);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmb(String emb) {
        this.emb = emb;
    }

    public String getEmb() {
        return emb;
    }

    public String geraEmb(char[] senha) {
        byte[] hash = null;

        StringBuilder in = new StringBuilder(nome);
        in.append(senha);

        try {
            hash = CriptoUtils.digest(in.toString().getBytes(), "MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String hex = CriptoUtils.byteArrayToHexString(hash);
        return hex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public List<GrupoTarefas> getGrupos() {
//        return grupos;
//    }
//
//    public List<Tarefa> getTarefasAv() {
//        return tarefasAv;
//    }
    public GrupoTarefas getGrupoRaiz() {
        //Garante nao nu

        return grupoRaiz;
    }

    public void setGrupoRaiz(GrupoTarefas grupoRaiz) {
        this.grupoRaiz = grupoRaiz;
    }

}
