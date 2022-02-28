/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.telas.principal;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.TreePath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.DOMException;

import br.com.antoniodiego.gertarefas.exportacao_backup.TransfXMLT;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import br.com.antoniodiego.gertarefas.util.ConversXML;
import br.com.antoniodiego.gertarefas.util.ConversXMLD;

/**
 *
 * @author Antônio Diego <antoniodiegoluz at gmail.com>
 */
public class TransfHandArv extends TransferHandler {

    JanelaPrincipalController contr;
    /**
     *
     */
    public static final Logger LOG_TRANSFER_HANDLER = LogManager.getLogger("editar_tarefa");

    public TransfHandArv(JanelaPrincipalController cont) {
        super(null);
        this.contr = cont;
    }

    private static final long serialVersionUID = -7220352246768596588L;

    /**
     * Cont?m os grupos que foram arrastados
     */
    private TreePath[] selecoesMovi;

    @Override
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport t) {
        LOG_TRANSFER_HANDLER.debug("Importando...");

        Transferable tr = t.getTransferable();
        JTree.DropLocation ld;
        TreePath caminhoD;
        GrupoTarefas grupInserir;
        // DefaultMutableTreeNode noGrin = null;

        if (t.isDrop()) {
            LOG_TRANSFER_HANDLER.debug("É soltar");
            // ? um ato de soltar
            ld = (JTree.DropLocation) t.getDropLocation();
            // Cam sel
            caminhoD = ld.getPath();
        } else {
            // Acho que seria colar
            // TODO: Implementar colar
            LOG_TRANSFER_HANDLER.debug("Não é soltar");
            // Prim sel
            caminhoD = contr.getView().getArvoreTarefas().getSelectionPath();
        }

        Object sel = caminhoD.getLastPathComponent();// PathComponent(indF);
        // System.out.println("No ult cam: " + noIn);
        // Object obU = noIn.getUserObject();
        LOG_TRANSFER_HANDLER.debug("Locl drop: " + sel);

        if (sel instanceof GrupoTarefas) {
            LOG_TRANSFER_HANDLER.debug("Soltar em GrupoTarefas. Correto!");
            grupInserir = (GrupoTarefas) sel;
        } else {
            LOG_TRANSFER_HANDLER.debug("Não esta soltand em grupo");
            return false;
        }

        if (tr.isDataFlavorSupported(Tarefa.TAREFA_FLAVOR)) {
            LOG_TRANSFER_HANDLER.debug("Imp Tarefa fla");
            Tarefa im = null;
            try {
                im = (Tarefa) t.getTransferable().getTransferData(Tarefa.TAREFA_FLAVOR);
            } catch (UnsupportedFlavorException | IOException e1) {

            }
            if (im != null && t.isDrop()) {
                // if (grupInserir != null) {
                grupInserir.add(im);
                /// daoUsuario.flush();
                // }
            }
            // TODO: Recuperar apagada
        } else if (tr.isDataFlavorSupported(br.com.antoniodiego.gertarefas.Tarefa.SABOR_TAREFA_AN)) {
            // System.out.println("Antiga");
            // TODO: Estudar retrocompatbi e DnD (Falvor)
            br.com.antoniodiego.gertarefas.Tarefa tarA;
            try {
                tarA = (br.com.antoniodiego.gertarefas.Tarefa) tr
                        .getTransferData(br.com.antoniodiego.gertarefas.Tarefa.SABOR_TAREFA_AN);
                TarefaComposta tareN = new TarefaComposta();
                tareN.setTitulo(tarA.getTitulo());
                tareN.getTarefasFilhas();
                tareN.setConcluida(tarA.isConcluida());

                tareN.setDataCriacao(contr.convertData(tarA.getData()));
                tareN.setDataFazer(contr.convertData(tarA.getDataFazer()));
                if (t.isDrop()) {
                    // if (grupInserir != null) {
                    grupInserir.add(tareN);
                    // } else {
                    // TODO: Raiz
                    // }
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                LOG_TRANSFER_HANDLER.catching(ex);
            }
        } else if (tr.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            LOG_TRANSFER_HANDLER.debug("Recebeu string transf");
            // Recebeu uma string que pode ser xml
            String strX;

            // FIXME: Alterar xml causa incompatibilidade
            try {
                strX = (String) tr.getTransferData(DataFlavor.stringFlavor);
                LOG_TRANSFER_HANDLER.debug("Importand XML Tar. Cont: " + strX);

                // ConversXML cd = new ConversXMLD();
                ObjectMapper map = new XmlMapper();
                map.registerModule(new JavaTimeModule());
                map.registerModule(new Hibernate5Module().configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true));

                List<GrupoTarefas> grupos;

                StringReader leit = new StringReader(strX);
                try {
                    grupos = map.readValue(leit, List.class);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    return false;
                }
                List<GrupoTarefas> tarefas;
                try {
                    tarefas = map.readValue(leit, List.class);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    return false;
                }
                // List<Object> gt = cd.leGrupoETars(new
                // ByteArrayInputStream(strX.trim().getBytes()));
                // LOG_TRANSFER_HANDLER.debug("Gr ou T lidos: " + gt.size());

                // TODO: Melhorar
                if (t.isDrop()) {
                    // JList.DropLocation ld = (JList.DropLocation) t.getDropLocation();
                    // int idx = ld.getIndex();
                    // System.out.println("Iserir: " + ld.isInsert());
                    // if (!ld.) {
                    // GrupoTarefas gi = modGt.getElementAt(idx);
                    LOG_TRANSFER_HANDLER.debug("Adicionando em grupo ins");
                    // grupInserir.add(tarX);
                    // modeloArv.insertNodeInto(new DefaultMutableTreeNode(tarX), noGrin, 0);
                    // gerg.atuG(gi);
                    // }
                }

                // selec.add(tarX);
                grupos.forEach((g) -> {
                    contr.getModeloArv().insere(grupInserir, g);
                });

                tarefas.forEach((tar) -> {
                    contr.getModeloArv().insere(grupInserir, tar);
                });

                // gt.forEach((o) -> {
                // contr.getModeloArv().insere(grupInserir, o);
                // });
            } catch (UnsupportedFlavorException | IOException | DOMException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(contr.getView(), "Problema ao soltar");

            }
        }

        LOG_TRANSFER_HANDLER.debug(
                "Flushando após importar");
        // OBS: Flush pode falahar mas dados vao para a tela.
        contr.getDaoUsuario().flush();

        LOG_TRANSFER_HANDLER.debug("Após flush");

        // XXX OBS Parece que não estava retornando true por causa de erro em flush e
        // não estv chamndo exp done e
        // removendo.
        return true;
    }

    /**
     * Dever? verificar se a ?rvore poder? importar uma coisa arrastada.
     *
     * @param support
     * @return
     */
    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        LOG_TRANSFER_HANDLER.debug("pode imp?");
        boolean podeIm = false;
        // A ?rvore pode importar uma tarefa
        if (support.isDataFlavorSupported(Tarefa.TAREFA_FLAVOR) /*
                                                                 * || support.isDataFlavorSupported(br.diego.gertarefas.
                                                                 * core.Tarefa.SABOR_TAREFA_AN)
                                                                 */) {
            podeIm = true;
        } else if (support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            podeIm = true;
        }
        return podeIm;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if (action == MOVE) {
            LOG_TRANSFER_HANDLER.debug("Export mov con");
            if (selecoesMovi != null) {
                for (TreePath mov : selecoesMovi) {
                    System.out.println("removendo objeto movido");

                    Object o = mov.getLastPathComponent();// .getUserObject();
                    GrupoTarefas gp;
                    if (o instanceof GrupoTarefas) {
                        GrupoTarefas instG = (GrupoTarefas) o;
                        gp = instG.getPai();
                        if (gp != null) {
                            // instG.setDono(null);
                            contr.getModeloArv().remove(gp, instG);
                            // instG.getPai().remove(instG);
                            // Dever? causar remo? orf?o.
                            // TODO: Remover da ?rvore.
                            // Obs: parece bom criar um modelo arv com grupos para remover junt
                        }
                    } else if (o instanceof TarefaComposta) {
                        /// DefaultMutableTreeNode noP = (DefaultMutableTreeNode) no.getParent();
                        // if (noP.equals(noPrinc)) {
                        // usuario.getGrupoRaiz().getTarefas().remove((TarefaComposta) o);
                        // } else {
                        gp = ((TarefaComposta) o).getPai();// noP.getUserObject();
                        if (gp instanceof GrupoTarefas) {
                            // GrupoTarefas grP = (GrupoTarefas) gP;
                            contr.getModeloArv().remove(gp, o);
                        }
                        // }
                    }
                }
                contr.getDaoUsuario().flush();
            }
        } else {
            System.out.println("Não foi mover");
        }
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        LOG_TRANSFER_HANDLER.traceEntry("Criando o transferable. Comp {}", c);

        // TODO: Adaptar para arrastar grupos e tarefas tamb?m
        /*
         * Obtem os caminhos selecionados na árvore
         * 
         */
        TreePath[] selecoes = contr.getView().getArvoreTarefas().getSelectionPaths();
        // TODO: Obter

        // Lista para receber os grupos escolhidos
        List<GrupoTarefas> gruposS = new ArrayList<>();

        // TODO: Expo tarefa tamb
        // Lista para receber as tarefas escolhidas
        List<Tarefa> tarefas = new ArrayList<>();

        for (TreePath ca : selecoes) {
            Object ou = ca.getLastPathComponent();
            LOG_TRANSFER_HANDLER.debug("Obje exp:" + ou);
            if (ou instanceof GrupoTarefas) {
                gruposS.add((GrupoTarefas) ou);
            } else if (ou instanceof Tarefa) {
                LOG_TRANSFER_HANDLER.debug("Tarefa exp");
                tarefas.add((Tarefa) ou);
            }
        }

        // XXX: ideia criar metodos de convers?o/leitura de grupos e tarefas de xml
        TransfXMLT trsX = new TransfXMLT(DataFlavor.stringFlavor);

        ConversXML cx = new ConversXMLD();

        ObjectMapper map = new XmlMapper();
        map.registerModule(new JavaTimeModule());
        map.registerModule(new Hibernate5Module().configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true));

        StringWriter writer = new StringWriter();

        try {
            map.writeValue(writer, gruposS);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            map.writeValue(writer, tarefas);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String xml = writer.toString(); // cx.geraXML(gruposS, tarefas);

        trsX.setDadTransXML(xml);
        this.selecoesMovi = selecoes;// .movidos = gruposS;

        return trsX;
    }
}
