package br.com.antoniodiego.gertarefas.service;

import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.antoniodiego.gertarefas.ui.confirmacoes.DialogoConfirmarExcTudo;
import java.awt.event.*;

public class GerenciamentoService {

    private Logger logerGer = LoggerFactory.getLogger(GerenciamentoService.class);
    private AcaoReiniciar acaoReiniciarBanco;
    private AcaoBackup acaoBackup;

    private BackupService backupService;
    private LoginService loginService;
    private Consumer<String> consumer;
  
    private AcaoExcluirTudo acaoExT;
    private JComponent view;
    
    private DialogoConfirmarExcTudo dialogoConf; // = new DialogoConfirmarExcTudo(view);
    public GerenciamentoService(JComponent v, Consumer<String> cons) {
        this.view = v;
        this.consumer = cons;
        this.backupService = new BackupService();
        this.loginService = new LoginService();

        this.dialogoConf = new DialogoConfirmarExcTudo(null);
    }
    /**
     *
     */
    private class AcaoBackup extends AbstractAction {

        public AcaoBackup() {
            super("Fazer backup banco");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Executar sql bakup com execupdate
           backupService.fazBackupB();
        }

    }

    public class AcaoExcluirTudo extends AbstractAction {

        public AcaoExcluirTudo(Icon ic) {
            super("Excluir tudo", ic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DialogoConfirmarExcTudo dialogoCon; //new DialogoConfirmarExcTudo(view);

            dialogoConf.setVisible(true);

            int st = dialogoConf.getReturnStatus();

            if (st == DialogoConfirmarExcTudo.RET_OK) {
                if (dialogoConf.getCheckSim().isSelected()) {
                    if (dialogoConf.getCheckBackup().isSelected()) {
                        // TODP: Backup
                    }
                    consumer.accept("Excluindo tudo");
                    //TODO: Modelo arv remove
                   // modeloArv.removeTudo();

                }
            }

        }
    }

    public class AcaoReiniciar extends AbstractAction {

        public AcaoReiniciar() {
            super("Reiniciar banco");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: Reinicicar
            loginService.carregaUsuarioEDados();
            // TODO: Atualizar tab. rec usu
            System.out.println("Reiniciou banco!");
            // TODO: Exib JOpP
        }
    }

    public AcaoReiniciar getAcaoReiniciarBanco() {
        if (acaoReiniciarBanco == null) {
            acaoReiniciarBanco = new AcaoReiniciar();
        }
        return acaoReiniciarBanco;
    }
}
