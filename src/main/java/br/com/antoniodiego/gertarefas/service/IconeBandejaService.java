package br.com.antoniodiego.gertarefas.service;

import javax.swing.ImageIcon;

import br.com.antoniodiego.gertarefas.ui.principal.JanelaPrincipalMatisse;
import br.com.antoniodiego.gertarefas.util.Constantes;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class IconeBandejaService {
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
                LOG_CONTR_PRINC.catching(ex);
            }

            iconeGeretar.setPopupMenu(menuPopUp);
            iconeGeretar.addActionListener(acIconeBand);
        }

    }
}
