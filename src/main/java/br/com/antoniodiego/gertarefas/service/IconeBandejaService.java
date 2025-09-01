package br.com.antoniodiego.gertarefas.service;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.antoniodiego.gertarefas.pojo.Notificacao;
import br.com.antoniodiego.gertarefas.ui.principal.JanelaPrincipalMatisse;
import br.com.antoniodiego.gertarefas.util.Constantes;

public class IconeBandejaService {

    private Logger loggerIcone = LoggerFactory.getLogger("IconeBandeja");
    private TrayIcon iconeGeretar;

    
    public void configuraIconeBandeja(){
         if (SystemTray.isSupported()) {
            SystemTray st = SystemTray.getSystemTray();
            ImageIcon imageIcGer = new ImageIcon(JanelaPrincipalMatisse.class.getResource("/imagens/icone lapis.png"));

            iconeGeretar = new TrayIcon(imageIcGer.getImage(), "Gerenciador de"
                    + " tarefas " + Constantes.VERS);

            PopupMenu menuPopUp = new PopupMenu();
            MenuItem sair = new MenuItem("Sair");
            sair.addActionListener((ActionEvent e) -> {
                System.exit(0);
            });

            menuPopUp.add(sair);
            try {
                st.add(iconeGeretar);
            } catch (AWTException ex) {
              loggerIcone.error("Erro ao adicionar ícone na bandeja do sistema", ex);
            }

            iconeGeretar.setPopupMenu(menuPopUp);
            iconeGeretar.addActionListener(acIconeBand);
        }

    }

    
    private ActionListener acIconeBand = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
           // view.setVisible(true);

            buscaNotifPerdidas();
        }

        private void buscaNotifPerdidas() {
            loggerIcone.trace("Buscando notif perd");

            try {
                // TODO: Corrigir exc aqui
                // List<Notificacao> notifPerd = nots.stream()
                //         .filter(notif -> (notif.getHoraExibicao() != null
                //                 && notif.getHoraExibicao().isBefore(LocalDateTime.now())))
                //         .filter(notif -> !notif.isFoiExibida()).collect(Collectors.toList());

               // modTabNotif.setNotif(notifPerd);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private WindowAdapter ouvJane = new WindowAdapter() {
        public void windowIconified(WindowEvent e) {
            // Janela deve ter sido min
            // view.setVisible(false);
        }

        @Override
        public void windowStateChanged(WindowEvent e) {
            loggerIcone.trace("Nov est: " + e.getNewState());

        }
    };
}
