/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.antoniodiego.gertarefas.util;

import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.InputStream;

/**
 *
 * @author Antônio Diego- Comp:Antônio Diego <your.name at your.org>
 */
public class ConversXMLXstr {
  /**
     *
     * @param tc
     * @return
     */
    public static String geraPedacoXML(TarefaComposta tc) {
        XStream xt = new XStream(new DomDriver());
        xt.alias("tarefacomposta", TarefaComposta.class);
        String pedaco = xt.toXML(tc);
        return pedaco;
    }
    
    private static Tarefa leTarefaPedXT(InputStream x) {
        XStream xt = new XStream();
        Tarefa t = (Tarefa) xt.fromXML(x);
        return t;
    }

}
