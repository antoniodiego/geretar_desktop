package br.com.antoniodiego.gertarefas.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.antoniodiego.gertarefas.controller.PrincipalController;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.util.ConversXMLD;

public class ExportacaoService {
    
    private String converteTodasAsTarefasParaXML() {
        ConversXMLD c = new ConversXMLD();
        c.setCharsetSaida(proprie.getProperty("encoding-exporta", "UTF-8"));
        String xml = c.geraXML(usuario.getGrupoRaiz().getSubgrupos(), usuario.getGrupoRaiz().getTarefas());

        return xml;
    }

    private GrupoTarefas importaGrupoRaiz(File f) {
        ObjectMapper map = new XmlMapper();
        map.registerModule(new JavaTimeModule());
        map.registerModule(new Hibernate5Module().configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true));

        GrupoTarefas grupoRaiz;

        try {
            grupoRaiz = map.readValue(f, GrupoTarefas.class);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }

        return grupoRaiz;
    }

    private void salvaDadosLidosUsandoDOM(File ar) {
        List<Object> gt = importaXMLComDOM(ar);

        LOG_CONTR_PRINC.debug("Adicionando no usu {} itens lidos...", gt.size());

        gt.forEach((Object o) -> {
            if (o instanceof GrupoTarefas) {
                GrupoTarefas gl = (GrupoTarefas) o;
                // Add para alterar pai
                modeloArv.insere(usuario.getGrupoRaiz(), gl);
            } else if (o instanceof Tarefa) {
                System.out.println("Taref enc im");
                // Add de GrupT para alterar pai
                modeloArv.insere(usuario.getGrupoRaiz(), o);
            }
        });
    }

    /**
     *
     * @param f
     * @return
     */
    private List<Object> importaXMLComDOM(File f) {
        ConversXMLD cx = new ConversXMLD();
        cx.setCharsetEntrada(proprie.getProperty("encoding-importacao", "UTF-8"));
        FileInputStream en = null;
        try {
            en = new FileInputStream(arquivoEs);
        } catch (FileNotFoundException ex) {
            LOG_CONTR_PRINC.catching(ex);
        }
        System.out.println("Interpretando...");
        List<Object> gt = cx.leGrupoETars(en);
        return gt;
    }

}
