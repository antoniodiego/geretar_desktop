/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.igu.renderers;

import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import java.awt.Component;
import java.time.LocalDate;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Ant?nio Diego- Comp:Ant?nio Diego <your.name at your.org>
 */
public class RenderizadorGT extends DefaultTreeCellRenderer {

    private final ImageIcon iconeFeita;
    private final ImageIcon iconeAtrasada;
    private final ImageIcon iconeFazer;
    private final ImageIcon iconeGrupo;
    private final ImageIcon iconeGrupoCon;
    private final ImageIcon iconeGrupoAtr;

    public RenderizadorGT() {
        iconeFeita = new ImageIcon(getClass().getResource("/imagens/icone_concluida.png"));
        iconeAtrasada = new ImageIcon(getClass().getResource("/imagens/tarefa atrasada.png"));
        iconeFazer = new ImageIcon(getClass().getResource("/imagens/nao feita.png"));
        iconeGrupo = new ImageIcon(getClass().getResource("/imagens/icone_grupo.png"));
        iconeGrupoCon = new ImageIcon(getClass().getResource("/imagens/icone_grupo_conc.png"));
        iconeGrupoAtr = new ImageIcon(getClass().getResource("/imagens/grupo_tar_atrasada.png"));

    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        //    System.out.println("cv: "+value.getClass());
        Object ov;
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode no = (DefaultMutableTreeNode) value;
            ov = no.getUserObject();
        } else {
            ov = value;
        }

        if (ov instanceof TarefaComposta) {
            TarefaComposta t = (TarefaComposta) ov;
            if (t.isConcluida()) {
                setIcon(iconeFeita);
            } else if (t.getDataFazer() != null && t.getDataFazer().isBefore(LocalDate.now())) {//before(new Date())) {
                setIcon(iconeAtrasada);
            } else {
                setIcon(iconeFazer);
            }
        } else if (ov instanceof GrupoTarefas) {
            //PENDING: Ícone grupo aberto
            int est = analisaGrupo((GrupoTarefas) ov);
            switch (est) {
                case 1:
                    setIcon(iconeGrupo);
                    break;
                case 0:
                    setIcon(iconeGrupoCon);
                    break;
                case 2:
                    setIcon(iconeGrupoAtr);
                    break;
                default:
                    break;
            }
        }

        return this;
    }

    //TODO: Usar eneum
    /**
     *
     * @return 0 se estiver em dia, 1 se contiver tarefa para fa e 2 se tiver
     * atrasada
     */
    private int analisaGrupo(GrupoTarefas g) {
        Enum r = null;
        //Assume está em dia. Atras pior
        //    int retorno = 0;
        List<GrupoTarefas> gs = g.getSubgrupos();
        boolean temTarF = false;
        for (GrupoTarefas gr : gs) {
            //Analisa sub
            int res = analisaGrupo(gr);
            if (res == 2) {
                //Muito gr
                return res;
            } else if (res == 1) {
                temTarF = true;
            }
            //Tranquilo continu se 0
        }

        List<Tarefa> tars = g.getTarefas();

        for (Tarefa t : tars) {
            if (!t.isConcluida()) {
                if (t.getDataFazer() != null) {
                    //Não conc e tem uma data fazer
                    if (t.getDataFazer().isBefore(LocalDate.now())) {
                        //Atrasou
                        return 2;
                    }
                } else {
                    //Não con sem data f
                    temTarF = true;
                }
            }
            //Se for con cont
        }

        //Se chegou aqui não tem atras
    //    System.out.println("Nenhuma atras");
        if (temTarF) {
       //     System.out.println("Alguma faz");
            return 1;
        }

        //System.out.println("Gr em dia");
        //Não enc para faz nem atras. Em dia!
        return 0;
    }
}
