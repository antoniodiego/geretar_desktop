/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.util;

import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class ConversXML_Jackson extends ConversXML {

    @Override
    public String geraXML(List<GrupoTarefas> grs, List<Tarefa> trsAv) {
        ObjectMapper map = new XmlMapper();
        map.registerModules(new Hibernate5Module());
        map.registerModule(new JavaTimeModule());

        StringWriter writ = new StringWriter();

        try {
            map.writeValue(writ, grs);
        } catch (IOException ex) {
            Logger.getLogger(ConversXML_Jackson.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            map.writeValue(writ, trsAv);
        } catch (IOException ex) {
            Logger.getLogger(ConversXML_Jackson.class.getName()).log(Level.SEVERE, null, ex);
        }

        return writ.toString();
    }

    @Override
    public List<Object> leGrupoETars(InputStream in) {
        ObjectMapper map = new XmlMapper();
        map.registerModules(new Hibernate5Module());
        map.registerModule(new JavaTimeModule());

        List dadosLidos = null;
        try {
          dadosLidos=  map.readValue(in, List.class);
        } catch (IOException ex) {
            Logger.getLogger(ConversXML_Jackson.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dadosLidos;
    }

}
