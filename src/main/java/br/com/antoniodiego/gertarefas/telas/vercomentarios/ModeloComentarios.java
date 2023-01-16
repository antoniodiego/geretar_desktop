/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.antoniodiego.gertarefas.telas.vercomentarios;

import br.com.antoniodiego.gertarefas.pojo.Comentario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author anton
 */
public class ModeloComentarios extends AbstractListModel {

    private final List<Comentario> coment;

    public ModeloComentarios() {
        coment = new ArrayList();

    }

    @Override
    public int getSize() {
        return coment.size();
    }

    public void adiciona(Comentario c) {
        coment.add(c);
        int idx = coment.indexOf(c);
        Collections.sort(coment);
        fireIntervalAdded(this, idx, idx);
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.coment.clear();
        this.coment.addAll(comentarios);

        //Ordena pela data
        Collections.sort(coment);

        fireContentsChanged(this, 0, coment.size() - 1);
    }

    public List<Comentario> getComent() {

        return coment;
    }

    @Override
    public Object getElementAt(int index) {
        return coment.get(index);
    }
    
}
