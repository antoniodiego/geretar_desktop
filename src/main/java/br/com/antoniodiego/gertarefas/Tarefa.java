package br.com.antoniodiego.gertarefas;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;

/**
 * Classe que representa uma tarefa.
 * 
 * @author Ant?nio Diego
 *
 */
public class Tarefa implements Serializable, Transferable, ClipboardOwner {
	private static final long serialVersionUID = -7609111725547109443L;
	public static final DataFlavor SABOR_TAREFA_AN = new DataFlavor(Tarefa.class, null);
	private String conteudo;
	private String titulo;
	private boolean concluida;
	private Date data;
	private Date dataFazer;

	public Tarefa() {
		this("","");
	}
	
	public Tarefa(String titulo, String conteudo) {
		this.titulo = titulo;
		this.conteudo = conteudo;
		this.concluida = false;
	}

	public String getConteudo() {
		return conteudo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
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
		return new DataFlavor[] { new DataFlavor(this.getClass(), titulo) };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.isFlavorSerializedObjectType();
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return this;
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {

	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataFazer() {
		return dataFazer;
	}

	public void setDataFazer(Date dataFazer) {
		this.dataFazer = dataFazer;
	}

}
