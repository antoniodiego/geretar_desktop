/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.antoniodiego.gertarefas.controller.JanelaPrincipalController;
import br.com.antoniodiego.gertarefas.Constantes;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import br.com.antoniodiego.gertarefas.util.ConversXMLD;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Ant?nio Diego <your.name at your.org>
 */
public class TesteOrden {

    private static ConversXMLD cxd;
    private Logger logTe;

    public TesteOrden() {
        logTe = LogManager.getLogger();
    }

    @BeforeClass
    public static void setUpClass() {
        cxd = new ConversXMLD();
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
    public void testOrd() {
        List<Tarefa> ts = new ArrayList<>();

        TarefaComposta c = new TarefaComposta();
        c.setPrioridade(2);
        ts.add(c);

        TarefaComposta t2 = new TarefaComposta();
        t2.setPrioridade(0);
        ts.add(t2);

        TarefaComposta t3 = new TarefaComposta();
        t3.setPrioridade(3);
        ts.add(t3);
        
        ts.sort(new JanelaPrincipalController.ComparaTarPrio());
        
        ts.forEach((t)->{System.out.println(t.getPrioridade());});
    }

    @Test
    public void testaExp() {

    }

    @Test
    public void testaImSun() {

    }

    @Test
    public void testF() {
        LocalDate d = LocalDate.now();
        System.out.println(Constantes.FORMATADOR_DATA_BR_T.format(d));
    }

}
