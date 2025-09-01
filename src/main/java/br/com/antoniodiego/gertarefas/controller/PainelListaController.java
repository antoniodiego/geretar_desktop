package br.com.antoniodiego.gertarefas.controller;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.AbstractAction;
import javax.swing.table.TableRowSorter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.antoniodiego.gertarefas.model.ModeloTabAgend;
import br.com.antoniodiego.gertarefas.model.ModeloTabelaTarefasLista;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TipoVoto;
import br.com.antoniodiego.gertarefas.pojo.Voto;
import br.com.antoniodiego.gertarefas.service.LoginService;
import br.com.antoniodiego.gertarefas.ui.modelos.ModeloTabelaTarefa;
import br.com.antoniodiego.gertarefas.ui.principal.paineis.PainelListaTarefas;
import br.com.antoniodiego.gertarefas.util.TarefaUtil;
import br.com.antoniodiego.gertarefas.util.Comparadores.ComparaTarPrio;

/**
 *
 * @author antonio
 */
public class PainelListaController {

    private Logger logLista = LoggerFactory.getLogger(PainelListaController.class);
    /**
     * Tarefa selecionada.
     */

    private LoginService loginService = new LoginService();
    private ModeloTabelaTarefasLista modeloTab;
    private ModeloTabelaTarefa modeloTabelaTarefa;

    private TableRowSorter ordenadorTabelaLista;
    private Tarefa tarefaExibida;

    /**
     * Nesse método as tarefas são carregadas do banco
     */
    private void preencheATabela() {
        // Aqui deve ser bom dar com de preench tab

        List<Tarefa> todasAsTarefas = obtemTodasAsTar();
        logLista.info("Qaunt de tarefas ob: " + todasAsTarefas.size());
        todasAsTarefas.sort(new ComparaTarPrio());
        // modeloTab.setTarefas(todasAsTarefas);
    }

    private class AcaoDiminuiPrio extends AbstractAction {

        public AcaoDiminuiPrio() {
            super("Diminui prio");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // int linhaSel = view.getPainelLista().getTabelaTarefas().getSelectedRow();
            // if (linhaSel == -1) {
            // return;
            // }
            // int idxMod =
            // view.getPainelLista().getTabelaTarefas().convertRowIndexToModel(linhaSel);
            // Tarefa tarSel = modeloTab.getTarefas().get(idxMod);
            // tarSel.setPrioridade(tarSel.getPrioridade() - 1);
            // ordenadorTabelaLista.sort();
        }
    }

    public ModeloTabelaTarefa getModeloTabela() {
        return modeloTabelaTarefa;
    }

    public ModeloTabelaTarefasLista getModeloTab() {
        return modeloTab;
    }

    private class AcaoVotProc extends AbstractAction {

        public AcaoVotProc() {
            super("Proclastinei");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (tarefaExibida != null) {
                Voto votoPro = new Voto(TipoVoto.Proclastinei);
                tarefaExibida.getVotos().add(votoPro);
                tarefaExibida.aumentaPrio();
                ordenadorTabelaLista.sort();

            }
        }
    }

    private class AcaoAumentaPrio extends AbstractAction {

        public AcaoAumentaPrio() {
            super("Aumenta prio");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // int linhaSel = view.getPainelLista().getTabelaTarefas().getSelectedRow();
            // if (linhaSel == -1) {
            // return;
            // }
            // int idxMod =
            // view.getPainelLista().getTabelaTarefas().convertRowIndexToModel(linhaSel);
            // Tarefa tarSel = modeloTab.getTarefas().get(idxMod);
            // tarSel.setPrioridade(tarSel.getPrioridade() + 1);
            // tarSel.setDataModif(LocalDateTime.now());
            // ordenadorTabelaLista.sort();
        }
    }

    private List<Tarefa> obtemTodasAsTar() {
        List<Tarefa> todasAsTarefas = new ArrayList<>();
        GrupoTarefas gRaiz = loginService.getUsuario().getGrupoRaiz();
        TarefaUtil.copiaTarefas(gRaiz, todasAsTarefas);

        return todasAsTarefas;
    }

    private class AcaoBuscar extends AbstractAction {

        public AcaoBuscar() {
            super("Buscar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // PainelListaTarefas painelLista = view.getPainelLista();
            // String termo = painelLista.getCampoTextoBusca().getText();
            // filtraTarefasLPorTit(termo, false);
        }
    }

    private class AcaoFiltrarTarefas extends AbstractAction {

        public AcaoFiltrarTarefas() {
            super("Filtrar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            PainelListaTarefas painLT = null;// = view.getPainelLista();

            LocalDate dataAg = null;
            if (painLT.getCheckDataAgend().isSelected()) {
                if (painLT.getRbHoje().isSelected()) {
                    dataAg = LocalDate.now();
                } else if (painLT.getRbFiltroAma().isSelected()) {
                    dataAg = LocalDate.now().plusDays(1);
                } else if (painLT.getRbDataEsp().isSelected()) {
                    dataAg = painLT.getCampoDataAgFil().getModeloDef().getValue();
                }

                // TODO: Adic campo
            }

            boolean apVenc = painLT.getCheckApenasVencidas().isSelected();

            filtraTarefasL(dataAg, apVenc);
        }
    }

    private void filtraTarefasL(LocalDate dataAg, boolean apVenc) {
        // TODO OBS Esse proc parece lento
        List<Tarefa> todasAsTar = obtemTodasAsTar();

        Stream<Tarefa> st = todasAsTar.stream();

        if (dataAg != null) {
            st = st.filter(t -> (t.getDataFazer() != null) && t.getDataFazer().equals(dataAg));
        }

        if (apVenc) {
            st = st.filter(t -> t.getDataFazer() != null && t.getDataFazer().isBefore(LocalDate.now()));
        }

        modeloTab.setTarefas(st.collect(Collectors.toList()));

        ordenadorTabelaLista.sort();
    }

    /**
     *
     * @param termo
     * @param apVenc
     */
    private void filtraTarefasLPorTit(String termo, boolean naoFeitas) {
        // TODO OBS Esse proc parece lento
        List<Tarefa> todasAsTar = obtemTodasAsTar();

        Stream<Tarefa> st = todasAsTar.stream();

        st = st.filter(t -> t.getTitulo().toLowerCase().contains(termo.toLowerCase()));

        if (naoFeitas) {
            st = st.filter(t -> t.isConcluida() == false);
        }

        modeloTab.setTarefas(st.collect(Collectors.toList()));

        ordenadorTabelaLista.sort();
    }

    public TableRowSorter getOrdenadorTabelaLista() {
        return ordenadorTabelaLista;
    }

}
