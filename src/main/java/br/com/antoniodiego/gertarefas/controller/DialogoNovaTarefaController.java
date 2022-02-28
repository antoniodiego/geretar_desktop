package br.com.antoniodiego.gertarefas.controller;

import br.com.antoniodiego.gertarefas.igu.FormatadorJTime;
import br.com.antoniodiego.gertarefas.igu.modelos.ModeloData;
import br.com.antoniodiego.gertarefas.telas.principal.JanelaPrincipal;
import br.com.antoniodiego.gertarefas.igu.modelos.ModeloTabelaTarefa;
import br.com.antoniodiego.gertarefas.pojo.Notificacao;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import br.com.antoniodiego.gertarefas.telas.DialogoNovaTarView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelListener;
import javax.swing.text.DefaultFormatterFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ant�noio Diego <antoniodiegoluz at gmail.com>
 */
public class DialogoNovaTarefaController {

    public static final Logger logDNT = LogManager.
            getLogger(DialogoNovaTarefaController.class);
             /**
     *
     */
    public static final Logger LOG_ARQUIVO = LogManager.getLogger("saida_para_arquivo");

    private DialogoNovaTarView view;
    private ModeloTabelaTarefa modeloTab;
    private final SpinnerNumberModel modeloCampoPro;
    private final RemovCam acaoRemover;
    private final ModeloData modeloCDataFz;

    private final ActionListener listA = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            Object org = e.getSource();
            if (org == view.getBotAdic()) {
                modeloTab.novaCoordenada();
            }
        }
    };

    private final ChangeListener clBtDig = (ChangeEvent e) -> {
        if (view.getBtDig().isSelected()) {
            view.getCampoDataFazer().setEnabled(true);
            view.getComboDataFaz().setEnabled(false);
        }
    };

    private final ChangeListener clBtEsco = (ChangeEvent e) -> {
        if (view.getRbEscolh().isSelected()) {
            view.getComboDataFaz().setEnabled(true);
            view.getCampoDataFazer().setEnabled(false);
        }
    };

    private final DocumentListener dlCampoTit = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            atualizEstDoBotSa();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            atualizEstDoBotSa();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            atualizEstDoBotSa();
        }

    };

    /**
     *
     */
    private final TableModelListener listTabelaTar = (ev) -> {
        // getView().getBotaoConcluido().setEnabled(modeloTab.getRowCount() > 0);
        getAcaoRemover().setEnabled(modeloTab.getRowCount() > 1);
        getView().getBotAdic().setEnabled(modeloTab.getRowCount() < 5);
    };

    private JanelaPrincipalMatisseController princ;

    public DialogoNovaTarefaController(DialogoNovaTarView view
    ) {
        this.view = view;
        // this.princ = princ;

        modeloCDataFz = (ModeloData) view.getCampoDataFazer().getModel();
     //   modeloCDataAl = (ModeloData) view.getCampoDataFazer().getModel();

        FormatadorJTime formatadorHora = new FormatadorJTime(true);
        DefaultFormatterFactory factoryFormHoras
                = new DefaultFormatterFactory(formatadorHora, formatadorHora, formatadorHora);

        view.getCampoHoraAl().setFormatterFactory(factoryFormHoras);

        modeloTab = new ModeloTabelaTarefa(false);
        modeloTab.addTableModelListener(listTabelaTar);

        view.getTabelaDesc().setModel(modeloTab);

        acaoRemover = new RemovCam();
        view.getbRem().setAction(acaoRemover);
        view.getBotAdic().addActionListener(listA);

        view.getBotaoConcluido().setAction(new AcaoSalvaTarefa());

        modeloCampoPro = new SpinnerNumberModel(5, 1, 100, 2);

//        view.getBotaoCancelar().addActionListener(princ.
//                getAcGere());
        view.getBotaoCancelar().setActionCommand("cancel_new_task");

        view.getBtDig().addChangeListener(clBtDig);
        view.getRbEscolh().addChangeListener(clBtEsco);

        view.getCampoTitulo().getDocument().addDocumentListener(dlCampoTit);

        atualizEstDoBotSa();
    }

    private void atualizEstDoBotSa() {
        if (view.getCampoTitulo().getText().length() > 0) {
            view.getBotaoConcluido().setEnabled(true);
        } else {
            view.getBotaoConcluido().setEnabled(false);
        }

        view.getRootPane().setDefaultButton(view.getBotaoConcluido());
    }

    public DialogoNovaTarView getView() {
        return view;
    }

    public RemovCam getAcaoRemover() {
        return acaoRemover;
    }

    /**
     * Limpa os campos e exibe o diálogo
     */
    public void nova() {
        view.getCampoTitulo().setText(null);
        modeloTab.limpa();
        view.setVisible(true);
    }

    /**
     * Gera uma instânica de TarefaComposta contendo tarefas coordenadas obtidas da tabela de descrições
     *
     * @return
     */
    public TarefaComposta getNova() {
        TarefaComposta tn = new TarefaComposta();
        tn.getTarefasFilhas().clear();
        tn.getTarefasFilhas().addAll(modeloTab.getCoords());
        return tn;

    }

    /**
     *
     */
    private class AcaoSalvaTarefa extends AbstractAction {

        public AcaoSalvaTarefa() {
            super("Salvar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            logDNT.trace("Salvando tarefa...");

            //Ao criar nova
            //Obtem uma instância da tarefa
            TarefaComposta novaTarefa = getNova();
            String tit = view.getCampoTitulo().getText();
            novaTarefa.setTitulo(tit);
            //obs: redund
            novaTarefa.setDataCriacao(LocalDate.now());

            //Det a data fa
            if (view.getBtDig().isSelected()) {
                LocalDate da = modeloCDataFz.getValue();
                novaTarefa.setDataFazer(da);
            } else if (view.getRbEscolh().isSelected()) {
                LocalDate dataFa = null;
                switch (view.getComboDataFaz().getSelectedIndex()) {
                    case 0:
                        dataFa = LocalDate.now();
                        break;
                    case 1:
                        //Amanhã
                        dataFa = LocalDate.now().plusDays(1);
                        break;
                    case 2:
                        dataFa = LocalDate.now().plusDays(2);
                        break;
                }

                novaTarefa.setDataFazer(dataFa);
            }

            LocalDate dataAl = (LocalDate) view.getCampoDataAl().getModel().
                    getValue();
            LocalTime horaAl = (LocalTime) view.getCampoHoraAl().getValue();

            if (dataAl != null && horaAl != null) {
                LocalDateTime instAl = LocalDateTime.of(dataAl, horaAl);
                novaTarefa.setDataHoraLembrete(instAl);

                Notificacao notif = new Notificacao(instAl, novaTarefa);
                /**
                 * Penso que talvez fosse bom já gravar a notificação aqui usando um dao dela para evitar muito proces-
                 * samento com flush
                 */
                novaTarefa.setNotificacao(notif);

                logDNT.trace("Notificação def: " + notif);
            } else {
                System.out.println(
                        "br.com.antoniodiego.gertarefas.igu.DialogoNovaTarefa."
                        + "AcaoSalvaTarefa.actionPerformed()"
                        + " da" + dataAl + " ha " + horaAl);
            }

            novaTarefa.setPrioridade(modeloCampoPro.getNumber().intValue());

            String idStr = view.getCampoId().getText();

            for (int i = 0; i < idStr.length(); i++) {
                if (!Character.isDigit(idStr.charAt(i))) {
                    JOptionPane.showMessageDialog(view, "Id inválido", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Long idNum = Long.parseLong(idStr);

            novaTarefa.setIdPers(idNum);

            view.dispose();

            logDNT.trace("Adic tar na janela principal");

            // princ.adicTarefa(novaTarefa);
            logDNT.trace("Após adic tar na janela principal");
        }
    }

    public class RemovCam extends AbstractAction {

        public RemovCam() {
            super("-");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int idx = view.getTabelaDesc().getSelectedRow();
            modeloTab.remove(idx);
        }
    }

}
