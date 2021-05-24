/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import br.com.antoniodiego.gertarefas.Constantes;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import br.com.antoniodiego.gertarefas.pojo.TarefaCoordenada;
import br.com.antoniodiego.gertarefas.util.ConversXMLD;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
 * @author Ant?nio Diego <your.name at your.org>
 */
public class TesteJUnit {

    private static ConversXMLD cxd;
    private Logger logTe;

    public TesteJUnit() {
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
    public void testImpTarAv() {
         logTe.traceEntry();
        String xml = "<?xml version='1.0'?><tarefas><tarefacomposta><id>1</id><tarefacoordenada><concluida>false</concluida><dataconclusao/><descricao>teste</descricao></tarefacoordenada></tarefacomposta></tarefas>";
        logTe.debug("Lendr g e t");
        List<Object> t = cxd
                .leGrupoETars(new ByteArrayInputStream(xml.getBytes()));
          logTe.debug("Após l");
        Assert.assertEquals(1, t.size());
        Object o = t.get(0);
        Assert.assertTrue("Correto!", o instanceof TarefaComposta);
        TarefaComposta tc = (TarefaComposta) o;
        Assert.assertEquals(1, tc.getTarefasFilhas().size());
        TarefaCoordenada tfA = tc.getTarefasFilhas().get(0);
        Assert.assertEquals(tfA.isConcluida(), false);
        Assert.assertEquals("teste", tfA.getDescricao());
    }

    @Test
    public void testaExp() {
        logTe.trace("Inic testaExp");
        GrupoTarefas g = new GrupoTarefas("Teste");
        GrupoTarefas sub = new GrupoTarefas("Subt");
        TarefaComposta t = new TarefaComposta();
        TarefaCoordenada t1 = new TarefaCoordenada(true, LocalDateTime.now(), "", "Teste tar c");
        TarefaCoordenada t2 = new TarefaCoordenada(true, LocalDateTime.now(), "", "De tar cr 2");
        t.getTarefasFilhas().add(t1);
        t.getTarefasFilhas().add(t2);
        sub.add(t);

        g.add(sub);

        List<GrupoTarefas> grs = new ArrayList<>();
        grs.add(g);

        String xmlG = cxd.geraXML(grs, null);
        System.out.println("xmlG: " + xmlG);

        List<Object> li = cxd.leGrupoETars(new ByteArrayInputStream(xmlG.getBytes()));
        Assert.assertTrue("Verd", li.size() == 1);
        Object prim = li.get(0);
        Assert.assertTrue("Gr", prim instanceof GrupoTarefas);
        GrupoTarefas prG = (GrupoTarefas) prim;
        Assert.assertEquals(1, prG.getSubgrupos().size());
        GrupoTarefas subL = prG.getSubgrupos().get(0);
        Assert.assertEquals(1, subL.getTarefas().size());
        Tarefa pr = subL.get(0);
        Assert.assertTrue("T", pr instanceof TarefaComposta);
        TarefaComposta tc = (TarefaComposta) pr;
        TarefaCoordenada f1 = tc.getTarefasFilhas().get(0);
        logTe.traceExit("Fim testeExp");
    }

    @Test
    public void testaImSun() {
        String x = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><tarefas><grupo><id>34</id><titulo>beta 2</titulo><grupo><id>35</id><titulo>Tarefas</titulo><tarefacomposta><id>185</id><titulo>Concl</titulo><concluida>true</concluida><dataconclusao>13/07/18</dataconclusao><data>2018-07-08</data><datafazer>null</datafazer><prioridade>0</prioridade><tarefacoordenada><concluida>true</concluida><dataconclusao>13/07/18</dataconclusao><descricao>Concluir tarefa comp pai ao conc todas as coorden filhas</descricao><comentario>10/07. fiz mas falta preencher data conc</comentario></tarefacoordenada></tarefacomposta></grupo><grupo><id>36</id><titulo>Ideias</titulo><tarefacomposta><id>186</id><titulo>Data e hor</titulo><concluida>true</concluida><dataconclusao>13/07/18</dataconclusao><data>2018-07-08</data><datafazer>null</datafazer><prioridade>0</prioridade><tarefacoordenada><concluida>true</concluida><dataconclusao>13/07/18</dataconclusao><descricao>Usar estilo data e hora no date form tar coode</descricao><comentario>10/07/18 usei mas ao salva apen data dava erro. 11/07/18 pus uma coluna para data e coluna para horas mas ficou dificil controlar editores</comentario></tarefacoordenada></tarefacomposta></grupo></grupo></tarefas>";
        List<Object> li = cxd.leGrupoETars(new ByteArrayInputStream(x.getBytes()));
        GrupoTarefas g0 = (GrupoTarefas) li.get(0);
        GrupoTarefas sub0 = g0.getSubgrupos().get(0);
        Assert.assertEquals("Tarefas", sub0.getTitulo());
        TarefaComposta ccon = (TarefaComposta) sub0.getTarefas().get(0);
        Assert.assertEquals("Concl", ccon.getTitulo());
        //Está lendo 18 e não 2018
        Assert.assertEquals("Falha leitura dc comp", LocalDate.of(18, 07, 13), ccon.getDataConclusao().toLocalDate());//Constantes.FORMATADOR_DATA_BR_T.format(ccon.getDataConclusao().toLocalDate()));
    }

    @Test
    public void testF() {
        LocalDate d = LocalDate.now();
        System.out.println(Constantes.FORMATADOR_DATA_BR_T.format(d));
    }
    
    
}
