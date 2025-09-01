package br.com.antoniodiego.gertarefas.service;

import java.io.OutputStream;
import java.io.Writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.antoniodiego.gertarefas.pojo.Usuario;

public class ExportarXMLService {
    
    public void exportarXMLparaStream(Writer saida, Usuario usuario) throws Exception {
          ObjectMapper map = new XmlMapper();
        map.registerModule(new JavaTimeModule());
        map.registerModule(new Hibernate5Module().configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true));

        try{
            map.writeValue(saida, usuario.getGrupoRaiz());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // String tarefasComoXML = converteTodasAsTarefasParaXML();
        // LOG_CONTR_PRINC.debug("XML Gerado trans s: {} ", tarefasComoXML);
        // OutputStreamWriter writer = new OutputStreamWriter(saida,
        // proprie.getProperty("encoding-exporta",
        // "UTF-8"));//"UTF-8");
        // try ( //saida.write(xml.getBytes());
        // BufferedWriter ea = new BufferedWriter(writer)) {
        // ea.write(xml);
        // ea.fl
    }
}
