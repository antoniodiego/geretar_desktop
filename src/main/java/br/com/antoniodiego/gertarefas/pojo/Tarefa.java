package br.com.antoniodiego.gertarefas.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.NaturalId;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Classe que representa uma tarefa. Pojo do Hibernate
 *
 * @author Antônio Diego
 *
 */
//TODO: Melhorar compat arrast e so
//[TODO: Tornar abstrata depois]
//Pode ser entity
@Entity
//Para mapear classes filhas
//[FIXME:] N?o est? surtindo efeito. N?o est? criando tabela com 

/*
21/06/18 20:25 - Parece que faltava mapear tarefas filhas. AS mapeei e come?ou
a funcionar com descrito no tuto: 
campo DTYPE
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonDeserialize(as = TarefaComposta.class)
@Table(name = "tarefas")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id_exp")
public abstract class Tarefa implements Externalizable, Transferable,
        ClipboardOwner, Cloneable {

    private static final long serialVersionUID = 1L;
    /**
     * Sabor que representa o POJO tarefa mais atual
     */
    public static final DataFlavor TAREFA_FLAVOR
            = new DataFlavor(Tarefa.class, "Tarefa");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @NaturalId(mutable = true)
    @Column(unique = true, name = "id_pers")
    private Long idPers;

    private String titulo;
    private boolean concluida;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "data_fazer")
    private LocalDate dataFazer;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "data_hora_lembrete")
    private LocalDateTime dataHoraLembrete;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;
    @Transient
    @JsonIgnore
    private DataFlavor[] sobs;
    /**
     * Prioridade da tarefa. Deve ser um n?mero de 1 a 10.
     */
    private Integer prioridade;
    @ManyToOne(cascade = CascadeType.PERSIST)
    //TODO: Resolver problema com Jackson
    //Talvez fosse bom que fosse enviado pelo menos o ID
    private GrupoTarefas pai;
    @ElementCollection
    @CollectionTable(name = "votos_de_tarefas", joinColumns = @JoinColumn(name
            = "id_tarefa"))
    private List<Voto> votos;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reflexoes_de_tarefas", joinColumns
            = @JoinColumn(name = "id_tarefa"))
    private List<Reflexao> reflexoes;

    @ElementCollection
    @CollectionTable(name = "relatorios_de_tarefas", joinColumns
            = @JoinColumn(name = "id_tarefa"))
    private List<Relatorio> relatorios;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_notificacao")
    private Notificacao notificacao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tarefa")
    private List<Agendamento> agendamentos;

    @Column(name = "data_modif")
    private LocalDateTime dataModif;
    /**
     *
     */
    private Integer posicao;
    /**
     *
     */

    private String comentario;
    /**
     *
     */

    private String status;

    @OneToMany(mappedBy = "tarefa", orphanRemoval = true,
            cascade = CascadeType.ALL)

    private List<Comentario> comentarios;

    public Tarefa() {
        this("");
    }

    public Tarefa(String titulo) {
        this.titulo = titulo;
        this.concluida = false;
        sobs = new DataFlavor[]{TAREFA_FLAVOR};
        this.prioridade = 0;
        this.votos = new ArrayList<>();
        this.reflexoes = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        this.relatorios = new ArrayList<>();
        this.agendamentos = new ArrayList<>();
        this.dataModif = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPers() {
        return idPers;
    }

    public void setIdPers(Long idPers) {
        this.idPers = idPers;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    @Override
    public String toString() {
        return titulo;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        // return new DataFlavor[]{new DataFlavor(this.getClass(), titulo)};
        return this.sobs;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.isFlavorSerializedObjectType();
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws
            UnsupportedFlavorException, IOException {
        return this;
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate data) {
        this.dataCriacao = data;
    }

    public LocalDate getDataFazer() {
        return dataFazer;
    }

    public void setDataFazer(LocalDate dataFazer) {
        this.dataFazer = dataFazer;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public LocalDateTime getDataHoraLembrete() {
        return dataHoraLembrete;
    }

    public void setDataHoraLembrete(LocalDateTime dataHoraLembrete) {
        this.dataHoraLembrete = dataHoraLembrete;
    }

    public Integer getPrioridade() {
        return prioridade;
    }

    //TODO: 1 a 10
    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public GrupoTarefas getPai() {
        return pai;
    }

    public void setPai(GrupoTarefas pai) {
        this.pai = pai;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public List<Reflexao> getReflexoes() {
        return reflexoes;
    }

    public List<Relatorio> getRelatorios() {
        return relatorios;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Notificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

    public void adiciAg(Agendamento ag) {
        this.agendamentos.add(ag);
        ag.setTarefa(this);
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void aumentaPrio() {
        ++this.prioridade;
    }

    public void diminuiPrio() {
        --this.prioridade;
    }

    public LocalDateTime getDataModif() {
        return dataModif;
    }

    public void setDataModif(LocalDateTime dataModif) {
        this.dataModif = dataModif;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(id);
        out.writeUTF(titulo);
        //    out.writeUTF(conteudo);
        out.writeBoolean(concluida);
        if (dataCriacao != null) {
            out.writeObject(dataCriacao);
        }
        if (dataFazer != null) {
            out.writeObject(dataFazer);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = in.readLong();
        this.titulo = in.readUTF();
        //  this.conteudo = in.readUTF();
        this.concluida = in.readBoolean();
        this.dataCriacao = (LocalDate) in.readObject();
        this.dataFazer = (LocalDate) in.readObject();
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj == this) {
//            return true;
//        }
//        if(obj==null||obj.getClass()!=this.getClass())return false;
//        Tarefa t = (Tarefa) obj;
//        return Objects.equals(this.titulo, t.titulo);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(titulo);
//    }
}
