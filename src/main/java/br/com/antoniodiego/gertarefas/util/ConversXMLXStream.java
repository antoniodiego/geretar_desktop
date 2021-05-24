package br.com.antoniodiego.gertarefas.util;

import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ant?nio Diego- Comp:Ant?nio Diego <your.name at your.org>
 */
public class ConversXMLXStream {

    /**
     * Gera um documento xml a partir de uma lista de grupos
     *
     * @param grupos
     * @return
     */
    public static String geraXML(List<GrupoTarefas> grupos) {
        XStream xt = new XStream();
        ByteArrayOutputStream csb = new ByteArrayOutputStream();
        GrupoTarefas g;
        for (int i = 0; i < grupos.size(); i++) {
            g = grupos.get(i);
            xt.toXML(g, csb);
            // Element nog = geraElNog(doc, g);
        }
        return new String(csb.toByteArray());
    }

    /**
     *
     * @param doc
     * @param g
     * @return
     */
//    public static Element geraElNog(Document doc, GrupoTarefas g) {
//        Element nog = doc.createElement("grupo");
//        Element id = doc.createElement("id");
//        id.appendChild(doc.createTextNode(String.valueOf(g.getId())));
//        nog.appendChild(id);
//        Element tit = doc.createElement("titulo");
//        tit.appendChild(doc.createTextNode(g.getTitulo()));
//        nog.appendChild(tit);
//
//        //Subgrupos
//        List<GrupoTarefas> sub = g.getSubgrupos();
//    //    Document subd = geraXML(sub);
//        doc.appendChild(subd);
//
//        //Tarefas
//        List<Tarefa> tars = g.getTarefas();
//
//        tars.stream().map((t) -> {
//            //TODO: Criar m?todo para criar elemento Nova tar-bot
//            Element not = geraNoTarefa(doc, t);
//            return not;
//        }).forEachOrdered((not) -> {
//            nog.appendChild(not);
//        });
//        return nog;
//    }
    /**
     *
     * @param doc
     * @param t
     * @return
     */
    //TODO: TarefaComposta e Simples
    public static Element geraNoTarefa(Document doc, Tarefa t) {

        return null;
    }

    /**
     *
     */
    public static String geraPedacoXML(TarefaComposta tc) {
        XStream xt = new XStream(new DomDriver());
        xt.alias("tarefacomposta", TarefaComposta.class);
        String pedaco = xt.toXML(tc);
        return pedaco;
    }

    /**
     * L? um grupo de um Node grupo. XML da minha api de impo/expo
     *
     * @param nogrupo
     * @return
     */
    public static GrupoTarefas leGrupo(Node nogrupo) {
        NodeList filhos = nogrupo.getChildNodes();
        GrupoTarefas gl = new GrupoTarefas();
        //N?o l? o id antigo para n?o causar conflitos
        //gl.setId(Integer.parseInt(filhos.item(0).getTextContent()));
        gl.setTitulo(filhos.item(1).getTextContent());
        int idx = 2;
        while (idx < filhos.getLength()) {
            Node nt = filhos.item(idx++);
            Tarefa t = leTar(nt);
            gl.add(t);
        }
        return gl;
    }

    private static Tarefa leTarefaPedXT(InputStream x) {
        XStream xt = new XStream();
        Tarefa t = (Tarefa) xt.fromXML(x);
        return t;
    }

    /**
     * Le tarefa de n?
     *
     * @param nt
     * @return
     */
    //TODO: Simples ou comp
    //Obs: deveria levar em conta nomes dos campos e n?o posics
    public static Tarefa leTar(Node nt) {
   
        return null;
    }

    private static String procuraValor(String elemento, Node no) {
        NodeList filT = no.getChildNodes();
        for (int i = 0; i < filT.getLength(); i++) {
            Node n = filT.item(i);
            if (n.getNodeName().equals(elemento)) {
                return n.getTextContent();
            }
        }
        return null;
    }
}
