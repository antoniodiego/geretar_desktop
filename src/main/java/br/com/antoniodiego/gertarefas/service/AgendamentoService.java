package br.com.antoniodiego.gertarefas.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.antoniodiego.gertarefas.persist.DAOAgendamentos;
import br.com.antoniodiego.gertarefas.pojo.Agendamento;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;

public class AgendamentoService {
    private Logger log = LoggerFactory.getLogger(AgendamentoService.class);
    /**
     *
     */
    private Timer timerAlarme;

    private void carregaAgendamentos() {
        DAOAgendamentos daAg = new DAOAgendamentos();
        List<Agendamento> agen = daAg.getAll();
        // modAg.setAg(agen);
    }

    private void agendaAl(Tarefa t) {
        LocalDateTime dataHL = t.getDataHoraLembrete();
        Instant ins = dataHL.toInstant(ZoneOffset.of("-3"));

        Date dataNot = Date.from(ins);

        TimerTask tarefaAl = new TimerTask() {
            @Override
            public void run() {

                // TODO:Exibir diálogo
                // iconeGeretar.displayMessage("Tarefa", t.getTitulo(),
                // TrayIcon.MessageType.WARNING);

                JOptionPane.showMessageDialog(null, t.getTitulo(), "Tarefa", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        SimpleDateFormat formData = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        System.out.println(formData.format(dataNot));
        timerAlarme.schedule(tarefaAl, dataNot);
        log.debug("Da c: " + dataNot);
        log.debug("Da conf: " + dataHL);
        log.trace("Tar age! ");
    }

}
