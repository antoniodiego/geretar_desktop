/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.util;

import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author Antnio Diego- Comp:Antnio Diego <your.name at your.org>
 */
public abstract class ConversXML {

    public abstract String geraXML(List<GrupoTarefas> grs,List<Tarefa> trsAv);
    public abstract List<Object> leGrupoETars(InputStream in);
}
