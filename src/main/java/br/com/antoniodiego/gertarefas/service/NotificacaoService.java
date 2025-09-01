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

import br.com.antoniodiego.gertarefas.persist.DAONotifcacao;
import br.com.antoniodiego.gertarefas.pojo.Notificacao;

public class NotificacaoService {
    private Timer timerAlarme;

    private Logger lognot = LoggerFactory.getLogger(NotificacaoService.class);
    private List<Notificacao> nots;

    private void carregaNotificacoes() {
        lognot.trace("Carregando notificações");
        DAONotifcacao daoN = new DAONotifcacao();

        nots = daoN.getAll();

        lognot.debug("Enc: " + nots.size());

        nots.forEach((not) -> {
            confDespNot(not);
        });

        lognot.trace("Notificações carregadas");
    }

    private void testarTimer() {

    }

    // private Banana b() {
    //     return new Banana();
    // }

    private void confDespNot(Notificacao not) {
        LocalDateTime horaN = not.getHoraExibicao();

        lognot.debug("Hora enc: " + horaN);

        LocalDateTime agora = LocalDateTime.now();

        if (horaN.isAfter(agora)) {
            lognot.debug("É depois de ag");
            Instant ins = horaN.toInstant(ZoneOffset.of("-3"));

            Date dataNot = Date.from(ins);

            TimerTask tarefaAl = new TimerTask() {
                @Override
                public void run() {
                    lognot.trace("Exib mens");
                  //  iconeGeretar.displayMessage("Tarefa", not.getTarefaMae().getTitulo(), TrayIcon.MessageType.WARNING);

                    // JOptionPane.showMessageDialog(view, not.getTarefaMae().getTitulo(), "Tarefa",
                    //         JOptionPane.INFORMATION_MESSAGE);

                    not.setFoiExibida(true);
                }
            };

            SimpleDateFormat formData = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
            System.out.println(formData.format(dataNot));
            timerAlarme.schedule(tarefaAl, dataNot);
            lognot.debug("Da c: " + dataNot);
            lognot.debug("Da conf: " + horaN);
            lognot.trace("Tar age! ");
        }
    }
}
