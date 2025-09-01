package br.com.antoniodiego.gertarefas.controller;

import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.TabExpander;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.antoniodiego.gertarefas.persist.DAOGrupos;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.Usuario;
import br.com.antoniodiego.gertarefas.service.GrupoService;
import br.com.antoniodiego.gertarefas.service.LoginService;
import br.com.antoniodiego.gertarefas.ui.modelos.ModeloArvore;
import br.com.antoniodiego.gertarefas.util.Comparadores;
import br.com.antoniodiego.gertarefas.util.Comparadores.ComparaGruposPrio;
import br.com.antoniodiego.gertarefas.util.Comparadores.ComparaTarPrio;
import java.awt.event.*;

public class ArvoreTarefasController {

    private Logger logArvore = LoggerFactory.getLogger(ArvoreTarefasController.class);

    private AcaoEditarTarefa acaoEditar;

    /**
     * Grupo selecionado. Da tarefa atual. Essa váriável guarda o grupo
     * selecionado na árvore se houver um. Se não tiver um grupo, mas houver uma
     * tarefa selecionada o pai dela é guardado no campo. Se um nó ramo que não
     * for grupo for selecionado ela deve guardar uma ref para o nó raiz
     */
    private GrupoTarefas grupoDaAtu;
    private Tarefa tarefaExibida;
    private ModeloArvore modeloArv;
    private javax.swing.JTree arvoreTarefas;
    private LoginService loginService = new LoginService();
    private Tarefa tarefaEditar;
private GrupoService grupoService=new GrupoService(new DAOGrupos());

    public ArvoreTarefasController(
            JTree arvoreTarefas) {
        this.arvoreTarefas = arvoreTarefas;
    }

    private void passaDadosParaArv(Usuario usu) {
        logArvore.trace("string");
        // modeloArv.setUsu(usu);
        logArvore.trace("string");
    }

    public void ordenaGrupo(GrupoTarefas grupo) {
        /*
         * Primeiro deve ser bom ordenar as tarefas do grupo e dos subgrupos. Depois
         * disso os subgrupos poderão ser orde- nados
         */

        List<Tarefa> lT = grupo.getTarefas();
        Comparadores.ComparaTarPrio comparadorPrio = new Comparadores.ComparaTarPrio();
        lT.sort(comparadorPrio);

        List<GrupoTarefas> subGrupos = grupo.getSubgrupos();
        // for(gf:subGrupos){
        //
        // }
        subGrupos.forEach((grupoF) -> {
            grupoF.getTarefas().sort(comparadorPrio);

            // Quando o grupo não tiver mais subgrupos esse método não será invocado
            ordenaGrupo(grupoF);
        });

        // Nesse ponto deve poder ser admitido que os subgrupos deste deve estar com as
        // tarefas e a list de sub ord
        subGrupos.sort(new Comparadores.ComparaGruposPrio());
    }

    /**
     * Ordena tarefas do grupo e de seus subgrupos
     *
     * Por que esse método é importante ?
     *
     * @param grupo
     */
    public void ordenaTarefas(GrupoTarefas grupo) {
        List<Tarefa> lT = grupo.getTarefas();
        Comparadores.ComparaTarPrio comparadorPrio = new Comparadores.ComparaTarPrio();
        lT.sort(comparadorPrio);

        List<GrupoTarefas> subGrupos = grupo.getSubgrupos();
        subGrupos.forEach((grupoF) -> {
            grupoF.getTarefas().sort(comparadorPrio);

            ordenaGrupo(grupo);
        });

    }

    private LoginService login;

    public void ordenaTarefas() {
        logArvore.trace("string");
        GrupoTarefas gRaiz = login.getUsuario().getGrupoRaiz();

        // Ordena tarefas
        List<Tarefa> lT = gRaiz.getTarefas();
        Comparadores.ComparaTarPrio comparadorPrio = new Comparadores.ComparaTarPrio();
        lT.sort(comparadorPrio);

        List<GrupoTarefas> subGrupos = gRaiz.getSubgrupos();
        subGrupos.forEach((grupo) -> {
            grupo.getTarefas().sort(comparadorPrio);
        });
    }

    private TreeSelectionListener ouvSelArv=new TreeSelectionListener(){

    @Override public void valueChanged(TreeSelectionEvent e){logArvore.debug("Evento de seleção");
    // DefaultMutableTreeNode sel = (DefaultMutableTreeNode) arvoreTarefas.
    // getLastSelectedPathComponent();

    Object sel=arvoreTarefas.getLastSelectedPathComponent();if(sel==null){logArvore.debug("Seleção nula");grupoDaAtu=null;tarefaExibida=null;
    // atualizaExibicaoTarefa(null);
    acaoEditar.setEnabled(false);return;}

    // Verifica se o nó selecionado é folha ou não
    if(modeloArv.isLeaf(sel)){
    // Aqui um nó folha foi selecionado

    logArvore.debug("No folha");
    // Tarefa ou grupo vazio
    if(sel instanceof Tarefa){logArvore.debug("Folha tarefa");Tarefa t=(Tarefa)sel;// (Tarefa) sel.getUserObject();
    logArvore.debug("Tit: "+t.getTitulo());
    // Pela organ deve ser GrupTa

    // OBS: Linhas poderia estar em um método
    GrupoTarefas g=(GrupoTarefas)t.getPai();grupoDaAtu=g;
    // } else {
    // grupoDaAtu = null;
    // }
    // this.noGrupo = grupoPai;
    tarefaExibida=t;
    // atualizaExibicaoTarefa(t);
    acaoEditar.setEnabled(true);}else if(sel instanceof GrupoTarefas){logArvore.debug("Grupo folha!");
    // noGrupo = sel;
    grupoDaAtu=(GrupoTarefas)sel;
    // atualizaExibicaoTarefa(null);
    logArvore.debug("Nome: "+grupoDaAtu);}else{logArvore.debug("N? folha n?o GrupoTarefas nem Tar");
    // XXX: Obs: pode ser no raiz
    }}else{
    // Não foi um nó folha que foi escolhido.
    // Deve ser g

    if(sel instanceof GrupoTarefas){
    // System.out.println("Sel grupo ramo: " + sel);

    grupoDaAtu=(GrupoTarefas)sel;
    // atualizaExibicaoTarefa(null);
    }else{
    /*
     * Neste ponto um nó ramo foi selecionado, mas que não foi um grupo
     * 
     */
    System.out.println("N? ramo n?o GrupoTarefas nem no Princ");
    // XXX: Obs: pode ser no raiz
    grupoDaAtu=loginService.getUsuario().getGrupoRaiz();}}

    // atualizaEstadoDosMenusBotoes();
    }};

    private TreeModelListener listMod = new TreeModelListener() {
        @Override
        public void treeNodesChanged(TreeModelEvent e) {
            Object[]filAlt=e.getChildren();Object fi1=filAlt[0];if(fi1==tarefaExibida){
    // Alterada aponta para a mesma que está send exib
    // atualizaExibicaoTarefa((Tarefa) fi1);
    }}

    @Override public void treeNodesInserted(TreeModelEvent e){

    }

    @Override public void treeNodesRemoved(TreeModelEvent e){

    }

    @Override public void treeStructureChanged(TreeModelEvent e){

    }};

    
    /**
    *
    */
    public void iniciaGrupoRaiz() {
        GrupoTarefas gr = loginService.getUsuario().getGrupoRaiz();
        if (gr == null) {
            System.out.println("Não tem gru ra");
            loginService.getUsuario().setGrupoRaiz(new GrupoTarefas("Tarefas"));

            grupoService.salvaG(loginService.getUsuario().getGrupoRaiz());
        }
    }

    /**
     * Atualiza conteúdo da janela de acordo com os grupos e tarefas
     * existentes no banco.
     */
    public void exibeGrupos() {
        iniciaGrupoRaiz();

    }

    private void ordenaGrupos() {
        logArvore.trace("ordena");
        GrupoTarefas gRaiz = loginService.getUsuario().getGrupoRaiz();

        // Ordena grupos
        ordenaGrupo(gRaiz);
        // List<Tarefa> lT = gRaiz.getTarefas();
        // ComparaTarPrio comparadorPrio = new ComparaTarPrio();
        // lT.sort(comparadorPrio);
        //
        // List<GrupoTarefas> subGrupos = gRaiz.getSubgrupos();
        // subGrupos.forEach((grupo) -> {
        // grupo.getTarefas().sort(comparadorPrio);
        //
        // });;
    }

    private class AcaoEditarTarefa extends AbstractAction {

        public AcaoEditarTarefa() {
            super("Editar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (tarefaExibida == null) {
                return;
            }
            tarefaEditar = tarefaExibida;
            // TODO: Btch estdo campos
          //  modeloTabela.setEditando(true);
            // PENDING: Desabilitar outros se for nec

        }
    }

    
    public ModeloArvore getModeloArv() {
        return modeloArv;
    }


    
   
    private class AcaoExcluirGrupo extends AbstractAction {

        public AcaoExcluirGrupo(Icon icone) {
            super("Excluir grupo", icone);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (grupoDaAtu != null) {
                // DefaultMutableTreeNode ngP = (DefaultMutableTreeNode) noGrupo.getParent();
                // Remove grupo da atu do pai
                GrupoTarefas grupoP = grupoDaAtu.getPai();
                System.out.println("clic em exc gr");
                if (grupoP != null) {// Subgrupo
                    System.out.println("Exc grupo sub: " + grupoDaAtu + " de " + grupoP + "...");

                    modeloArv.remove(grupoP, grupoDaAtu);
                    // Obs: atu g após del de grupo func mas de tar não. 06/07/18 ~09;18
                    
                } else {
                    // Da raiz

                    // Orphan rem
                    System.out.println("Exc grupo sem pai");
                    // grupoDaAtu.setDono(null);
                    if (!(grupoDaAtu.equals(loginService.getUsuario().getGrupoRaiz()))) {
                        grupoService.deletaGrupo(grupoDaAtu);
                    } else {
                        System.out.println("Tentou excluir grupo raiz");
                    }

                }
                // OBs-29/07/18-09:00: flush deve remover grupo sem pai
            }
        }
    }

     private AcaoExcluirGrupo acaoExG;
   

}
