/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import br.com.antoniodiego.gertarefas.util.ConversXML;
import br.com.antoniodiego.gertarefas.util.ConversXMLD;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class TestCXMD {

    private ConversXML conv;
    private Logger logTe;

    public TestCXMD() {
        conv = new ConversXMLD();
        logTe = LogManager.getLogger("Teste");
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testaLT() {

    }

    @Test
    public void tetaG() {
        logTe.debug("Inic testaG");
         logTe.trace("Inic testaG");
        GrupoTarefas gt = new GrupoTarefas("Teste");
        Tarefa teste = new TarefaComposta();
        teste.setDataCriacao(LocalDate.now());
        gt.add(teste);

        ConversXMLD cv = new ConversXMLD();
        logTe.debug("Gerando...");
        String XML = cv.geraXML(Arrays.asList(gt), null);
        System.out.println("XML: " + XML);
        logTe.debug("XML Ger: " + XML);
        //TODO: Term
//        String XML_Teste = "<tarefas>"
//                + "<grupotarefas>\n"
//                + "<id>1</id>\n"
//                + "<titulo>Teste</titulo>\n"
//                + "<tarefacomposta>\n"
//                + "<id>1</id>\n"
//                + "<titulo>Teste data c</titulo>\n"
//                + "<concluida>false</concluida>\n"
//                + "<data>2019-04-28</data>\n"
//                + "<datafazer>null</datafazer>\n"
//                + "<prioridade>0</prioridade>\n"
//                + "<tarefacoordenada>\n"
//                + "<concluida>false</concluida>\n"
//                + "<descricao>Teste data c</descricao>\n"
//                + "<comentario>null</comentario>\n"
//                + "</tarefacoordenada>\n"
//                + "</tarefacomposta>"
//                + "</grupotarefas>"
//                + "</tarefas>";
        System.out.println("Interp xml");
        ByteArrayInputStream in = new ByteArrayInputStream(XML.getBytes());
        List<Object> o = conv.leGrupoETars(in);
        Object gr = o.get(0);
        Assert.assertTrue("Não leu grupo", gr instanceof GrupoTarefas);
        GrupoTarefas grL = (GrupoTarefas) gr;
        Object ta1 = grL.get(0);
        Assert.assertTrue("Não leu tarefa", ta1 instanceof Tarefa);
        Tarefa tarL = (Tarefa) ta1;
        LocalDate datC = tarL.getDataCriacao();
        Assert.assertNotNull("Erro na leitura da data", datC);
    }

    //@Test
    public void testLDataC() {
        logTe.debug("Teste 2");
        //TODO: Term
        String XML_Teste = "<tarefas>"
                + "<grupotarefas>\n"
                + "<id>1</id>\n"
                + "<titulo>Teste</titulo>\n"
                + "<tarefacomposta>\n"
                + "<id>1</id>\n"
                + "<titulo>Teste data c</titulo>\n"
                + "<concluida>false</concluida>\n"
                + "<data>2019-04-28</data>\n"
                + "<datafazer>null</datafazer>\n"
                + "<prioridade>0</prioridade>\n"
                + "<tarefacoordenada>\n"
                + "<concluida>false</concluida>\n"
                + "<descricao>Teste data c</descricao>\n"
                + "<comentario>null</comentario>\n"
                + "</tarefacoordenada>\n"
                + "</tarefacomposta>"
                + "</grupotarefas>"
                + "</tarefas>";
        ByteArrayInputStream in = new ByteArrayInputStream(XML_Teste.getBytes());
        List<Object> o = conv.leGrupoETars(in);
        Object gr = o.get(0);
        Assert.assertTrue("Não leu grupo", gr instanceof GrupoTarefas);
        GrupoTarefas grL = (GrupoTarefas) gr;
        Object ta1 = grL.get(0);
        Assert.assertTrue("Não leu tarefa", ta1 instanceof Tarefa);
        Tarefa tarL = (Tarefa) ta1;
        LocalDate datC = tarL.getDataCriacao();
        Assert.assertNotNull("Erro na leitura da data", datC);

    }
}
