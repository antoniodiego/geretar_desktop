/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import javax.swing.tree.TreePath;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.telas.modelos.ModeloArvore;

/**
 *
 * @author Antônio Diego <your.name at your.org>
 */
public class TesteModArv {

    private ModeloArvore mdA;

    public TesteModArv() {
        mdA = new ModeloArvore();
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
    public void teataGerC() {
        GrupoTarefas ul = new GrupoTarefas("Ul");
        GrupoTarefas me = new GrupoTarefas("Me");
        GrupoTarefas ra = new GrupoTarefas("Ra");

        me.add(ul);
        ra.add(me);

        TreePath cam = mdA.geraCam(ul);
        Assert.assertEquals(3, cam.getPathCount());
        Assert.assertEquals(ra,cam.getPathComponent(0)
        );
         Assert.assertEquals(me,cam.getPathComponent(1)
        );
          Assert.assertEquals(ul,cam.getPathComponent(2)
        );
    }
}
