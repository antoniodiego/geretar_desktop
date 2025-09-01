package br.com.antoniodiego.gertarefas.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import br.com.antoniodiego.gertarefas.persist.DAONotifcacao;
import br.com.antoniodiego.gertarefas.pojo.Notificacao;

public class NotificacaoService {
    
    private void carregaNotificacoes() {
        LOG_CONTR_PRINC.traceEntry();
        DAONotifcacao daoN = new DAONotifcacao();

        nots = daoN.getAll();

        LOG_CONTR_PRINC.debug("Enc: " + nots.size());

        nots.forEach((not) -> {
            confDespNot(not);
        });

        LOG_CONTR_PRINC.traceExit();
    }

    private void confDespNot(Notificacao not) {
        LocalDateTime horaN = not.getHoraExibicao();

        LOG_CONTR_PRINC.debug("Hora enc: " + horaN);

        LocalDateTime agora = LocalDateTime.now();

        if (horaN.isAfter(agora)) {
            LOG_CONTR_PRINC.debug("É depois de ag");
            Instant ins = horaN.toInstant(ZoneOffset.of("-3"));

            Date dataNot = Date.from(ins);

            TimerTask tarefaAl = new TimerTask() {
                @Override
                public void run() {
                    LOG_CONTR_PRINC.trace("Exib mens");
                    iconeGeretar.displayMessage("Tarefa", not.getTarefaMae().getTitulo(), TrayIcon.MessageType.WARNING);

                    JOptionPane.showMessageDialog(view, not.getTarefaMae().getTitulo(), "Tarefa",
                            JOptionPane.INFORMATION_MESSAGE);

                    not.setFoiExibida(true);
                }
            };

            SimpleDateFormat formData = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
            System.out.println(formData.format(dataNot));
            timerAlarme.schedule(tarefaAl, dataNot);
            LOG_CONTR_PRINC.debug("Da c: " + dataNot);
            LOG_CONTR_PRINC.debug("Da conf: " + horaN);
            LOG_CONTR_PRINC.trace("Tar age! ");
        }
    }
}
