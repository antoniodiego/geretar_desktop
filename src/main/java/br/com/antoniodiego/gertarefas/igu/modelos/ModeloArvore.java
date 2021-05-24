/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.igu.modelos;

import br.com.antoniodiego.gertarefas.Constantes;
import br.com.antoniodiego.gertarefas.persist.daos.DAOUsuario;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import br.com.antoniodiego.gertarefas.pojo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Ant?nio Diego- Comp:Ant?nio Diego <your.name at your.org>
 */
//Obs; Poderia guardar apenas o grupo raiz do usu
public class ModeloArvore implements TreeModel {

    private Usuario usu;
    private final List<TreeModelListener> lis;
    private DAOUsuario gere;

    public ModeloArvore() {
        this(new Usuario(Constantes.NOME_USR_PADR, Constantes.SENHA_PADR.toCharArray()));
        //    this.usu = new ;
        //  lis = new ArrayList<>();
    }

    public ModeloArvore(Usuario usu) {
        this.usu = usu;
        //XXX: Simplifi com param
        // this.gere = gere;
        lis = new ArrayList<>();
    }

    public void iniciaGer(DAOUsuario gere) {
        this.gere = gere;
    }

    public void insere(GrupoTarefas g, Object filho) {
        g.add(filho);
        fiI(g, new Object[]{filho});
    }

    public TreePath geraCam(Object g) {
        List<Object> no = new ArrayList<>();
        no.add(g);

        GrupoTarefas pai = null;

        if (g instanceof GrupoTarefas) {
            GrupoTarefas ig = (GrupoTarefas) g;
            pai = ig.getPai();
            //    System.out.println("pai de " + ig + ": " + pai);

        } else if (g instanceof Tarefa) {
            Tarefa t = (Tarefa) g;
            pai = t.getPai();
        }
        while (pai != null) {
            no.add(0, pai);
            pai = pai.getPai();
        }
        TreePath cam = new TreePath(no.toArray());
        return cam;
    }

    @Override
    public Object getRoot() {
        //XXX: OBS: poderia ser usado um grupo principal (raiz)
        return usu.getGrupoRaiz();//"Tarefas";
    }

    @Override
    public Object getChild(Object parent, int index) {
        //    System.out.println("Gc: " + parent + " " + index);
        if (parent instanceof GrupoTarefas) {
            GrupoTarefas gr = (GrupoTarefas) parent;
            if (index < gr.getSubgrupos().size()) {
                return gr.getSubgrupos().get(index);
            } else {
                return gr.getTarefas().get(index - gr.getSubgrupos().size());
            }
        }
        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof GrupoTarefas) {
            GrupoTarefas gr = (GrupoTarefas) parent;
            return gr.getSubgrupos().size() + gr.getTarefas().size();
        }
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        if (node instanceof TarefaComposta) {
            return true;
        } else if (node instanceof GrupoTarefas) {
            GrupoTarefas gr = (GrupoTarefas) node;
            return getChildCount(gr) == 0;
        }
        return false;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        //  System.out.println(path + " " + newValue.getClass());
        Object el = path.getLastPathComponent();
        if (el instanceof GrupoTarefas) {
            GrupoTarefas g = (GrupoTarefas) el;
            g.setTitulo((String) newValue);
            if (g.getPai() != null) {
                gere.flush();
                notifAt(g.getPai(), el);
            }
        } else if (el instanceof TarefaComposta) {
            TarefaComposta t = (TarefaComposta) el;
            t.setTitulo((String) newValue);
            if (t.getPai() != null) {
                gere.flush();
                notifAt(t.getPai(), el);
            }
        }
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if (!(parent instanceof GrupoTarefas)) {
            return 0;
        }

        //OBS: Parece ser bom usar uma list só para por grupo e tarefas
        GrupoTarefas gr = (GrupoTarefas) parent;
        int idx = gr.getSubgrupos().indexOf(child);
        if (idx == -1) {
            idx = gr.getTarefas().indexOf(child) + gr.getSubgrupos().size();
        }
        return idx;
    }

    public void remove(GrupoTarefas pai, Object filho) {
        int indiceA = getIndexOfChild(pai, filho);

        if (indiceA < 0) {
            return;
        }

        System.out.println("Enc remov...");

        if (filho instanceof GrupoTarefas) {
            GrupoTarefas f = (GrupoTarefas) filho;
            pai.getSubgrupos().remove(f);
//            f.setDono(null);
            f.setPai(null);
        } else if (filho instanceof TarefaComposta) {
            TarefaComposta ta = (TarefaComposta) filho;
            System.out.println("Remov ta");
            pai.remove(ta);
        }

        fiR(pai, new int[]{indiceA}, new Object[]{filho});
    }

    public void removeTudo() {
        List<GrupoTarefas> gr = usu.getGrupoRaiz().getSubgrupos();
        // int tam = gr.size();
        while (!gr.isEmpty()) {
            gr.remove(gr.get(0));
        }

        usu.getGrupoRaiz().getTarefas().clear();
        fiSC();
    }

    private void fiR(Object pai, int[] indicesAntes, Object[] filhos) {
        lis.forEach((TreeModelListener l) -> {
            TreePath cam = geraCam(pai);
            System.out.println("Not rem: " + cam);

            TreeModelEvent ev = new TreeModelEvent(this, cam, indicesAntes, filhos);
            l.treeNodesRemoved(ev);
        });
    }

    private void fiI(Object pai, Object[] filhos) {
        int[] indices = new int[filhos.length];
        for (int i = 0; i < filhos.length; i++) {
            indices[i] = getIndexOfChild(pai, filhos[i]);
        }

        lis.forEach((TreeModelListener l) -> {
            TreeModelEvent eventoInserir = new TreeModelEvent(this, geraCam(pai), indices, filhos);
            l.treeNodesInserted(eventoInserir);
        });
    }

    private void fiSC() {
        lis.forEach((TreeModelListener l) -> {
            TreeModelEvent ev = new TreeModelEvent(this, new TreePath(getRoot()));
            l.treeStructureChanged(ev);
        }
        );
    }

    /**
     * Notifca alteracão em nó
     *
     * @param pai
     * @param fil
     */
    public void notifAt(GrupoTarefas pai, Object fil) {
        TreeModelEvent e = new TreeModelEvent(this, geraCam(pai), new int[getIndexOfChild(pai, fil)], new Object[]{fil});
        notEvM(e);
    }
//
//    private void notEv(TreeModelEvent e) {
//        lis.forEach((TreeModelListener l) -> {
//            l.treeStructureChanged(e);
//        });
    //  }

    private void notEvM(TreeModelEvent e) {
        lis.forEach((TreeModelListener l) -> {
            l.treeNodesChanged(e);
        });
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        //  System.out.println("Adic mod: " + l.getClass());
        lis.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        lis.remove(l);
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
        fiSC();
    }

}
