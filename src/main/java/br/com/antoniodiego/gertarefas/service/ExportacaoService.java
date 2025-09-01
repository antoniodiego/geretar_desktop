package br.com.antoniodiego.gertarefas.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.antoniodiego.gertarefas.controller.PrincipalController;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.ui.modelos.ModeloArvore;
import br.com.antoniodiego.gertarefas.util.ConversXMLD;

public class ExportacaoService {

    private Logger logExpo = LoggerFactory.getLogger("exportacao");
    private PropriedadesService proprie = new PropriedadesService();
    private LoginService usuarioService = new LoginService();
private ModeloArvore modeloArvore;

    ExportacaoService(ModeloArvore modeloArvore) {
        this.modeloArvore = modeloArvore;
    }

    private String converteTodasAsTarefasParaXML() {
        ConversXMLD c = new ConversXMLD();
        c.setCharsetSaida(proprie.getConfig("encoding-exporta", "UTF-8"));
        String xml = c.geraXML(usuarioService.getUsuario().getGrupoRaiz().getSubgrupos(),
                usuarioService.getUsuario().getGrupoRaiz().getTarefas());

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

        logExpo.debug("Adicionando no usu {} itens lidos...", gt.size());

        gt.forEach((Object o) -> {
            if (o instanceof GrupoTarefas) {
                GrupoTarefas gl = (GrupoTarefas) o;
                // Add para alterar pai
                modeloArvore.insere(usuarioService.getUsuario().getGrupoRaiz(), gl);
            } else if (o instanceof Tarefa) {
                System.out.println("Taref enc im");
                // Add de GrupT para alterar pai
                modeloArvore.insere(usuarioService.getUsuario().getGrupoRaiz(), o);
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
        cx.setCharsetEntrada(proprie.getConfig("encoding-importacao", "UTF-8"));
        FileInputStream en = null;
        try {
            en = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            logExpo.error("Arquivo não encontrado", ex);
        }
        logExpo.info("Interpretando...");
        List<Object> gt = cx.leGrupoETars(en);
        return gt;
    }

}
