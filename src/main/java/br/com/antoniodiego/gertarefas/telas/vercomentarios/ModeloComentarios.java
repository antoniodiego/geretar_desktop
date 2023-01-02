/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.antoniodiego.gertarefas.telas.vercomentarios;

import br.com.antoniodiego.gertarefas.pojo.Comentario;
import java.util.ArrayList;
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

    public List<Comentario> getComent() {
        return coment;
    }

    
    @Override
    public Object getElementAt(int index) {
        return coment.get(index);
    }

}
