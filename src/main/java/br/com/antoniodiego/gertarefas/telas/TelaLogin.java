package br.com.antoniodiego.gertarefas.telas;

import br.com.antoniodiego.gertarefas.persist.daos.DAOUsuario;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import br.com.antoniodiego.gertarefas.pojo.Usuario;
import br.com.antoniodiego.gertarefas.util.HibernateUtil;
import br.com.antoniodiego.gertarefas.telas.principal.JanelaPrincipal;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JCheckBox;

/**
 * Janela de entrada.
 *
 * @author Ant?nio Diego
 *
 */
//TODO: Usar Actions nos men e bot
public class TelaLogin extends JDialog {

    private static final long serialVersionUID = 78928912605018205L;
    public JTextField campoUsuario;
    public JPasswordField campoSenha;
    private JButton botaoLogin;
    private AbstractButton botaoDeletar;
    private JButton botaoNovo;
    private final JanelaPrincipal pai;
    private JCheckBox chekManter;
    private final DAOUsuario gere;
    private AcaoLogin acLo;

    public TelaLogin(JanelaPrincipal parente, boolean modal) {
        super(parente, "Entrada", modal);
        this.pai = parente;
        gere = new DAOUsuario(HibernateUtil.getInstance());
        criaInter();
    }

    private void criaInter() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        setLocationRelativeTo(pai);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
        Container painelConteudo = getContentPane();
        //GroupLayout leiauteGrupo = new GroupLayout(painelConteudo);
        //painelConteudo.setBorder(new EmptyBorder(5, 5, 5, 5));
        //setContentPane(painelConteudo);
        GridBagLayout leiG = new GridBagLayout();
        painelConteudo.setLayout(leiG);

        GridBagConstraints cons = new GridBagConstraints();
        // Adicionar componentes
        // int rotulosX = 5;
        // int camposX = 54;
        // int x = 0;
        // int y = 8;
        JLabel rotuloUsuario = new JLabel("Nome de usu\u00e1rio: ");
        //rotuloUsuario.setBounds(rotulosX, y, 40, 20);
        //painelConteudo.add(rotuloUsuario);

        campoUsuario = new JTextField(20);
        //TODO: Enter
        //campoUsuario.registerKeyboardAction(pai, "login_ok", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
        //       JComponent.WHEN_FOCUSED);
        //campoUsuario.setBounds(camposX, y, 203, 20);
        //painelConteudo.add(campoUsuario);

        JLabel rotuloSenha = new JLabel("Senha: ");
        campoSenha = new JPasswordField(20);
//        campoSenha.registerKeyboardAction(pai, "login_ok",, 0),
//                JComponent.WHEN_FOCUSED);
        campoSenha.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Login");
        //.putValue("Login","Teste");
    //    campoSenha.addActionListener(this);
        //campoSenha.setBounds(camposX, y, 203, 20);
        //painelConteudo.add(campoSenha);

        chekManter = new JCheckBox("Manter conectado");
        // chekManter.addActionListener(this);

        acLo = new AcaoLogin();
        botaoLogin = new JButton(acLo);
        botaoLogin.setFocusPainted(true);
        
        campoSenha.getActionMap().put("Login", botaoLogin.getAction());
        //x += 60;
        botaoDeletar = new JButton("Deletar");
        botaoDeletar.setActionCommand("delete_user");
        botaoDeletar.addActionListener(pai.getControl().getAcGere());
      
        botaoNovo = new JButton("Novo usu\u00e1rio");
        botaoNovo.setActionCommand("cadastrar");
        botaoNovo.addActionListener(pai.getControl().getAcGere());
 
        //TODO: Adcio fechar
        JButton botaoFechar = new JButton("Fechar");
        botaoFechar.addActionListener((ActionEvent e) -> {
            if (TelaLogin.this.isModal()) {
                System.exit(0);
            } else {
                TelaLogin.this.dispose();
            }
        });

        CaretListener camposListener = (CaretEvent e) -> {
            atualizaBot\u00f5es();
        };

        campoUsuario.addCaretListener(camposListener);
        campoSenha.addCaretListener(camposListener);

        cons.weightx = 1;
        cons.weighty = 1;
        cons.anchor = GridBagConstraints.FIRST_LINE_START;
        painelConteudo.add(rotuloUsuario, cons);
        cons.gridx = 1;
        cons.gridwidth = 2;
        painelConteudo.add(campoUsuario, cons);

        cons.gridx = 0;
        cons.gridy = 1;
        cons.gridwidth = 1;
        painelConteudo.add(rotuloSenha, cons);
        cons.gridx = 1;
        cons.gridwidth = 2;
        painelConteudo.add(campoSenha, cons);

        cons.gridy = 2;
        cons.gridx = 0;
        cons.gridwidth = 1;
        painelConteudo.add(chekManter, cons);
        cons.gridy = 3;
        cons.anchor = GridBagConstraints.LAST_LINE_END;
        cons.gridx = 1;
        painelConteudo.add(botaoLogin, cons);
        cons.gridx = 2;
        painelConteudo.add(botaoDeletar, cons);
        cons.gridy = 4;
        cons.gridx = 1;
        painelConteudo.add(botaoNovo, cons);
        cons.gridx = 2;
        painelConteudo.add(botaoFechar, cons);
        // setResizable(false);
//        Group grupoHor = leiauteGrupo.createSequentialGroup();
//        grupoHor.addGroup(leiauteGrupo.createParallelGroup().addComponent(rotuloUsuario).addComponent(rotuloSenha).addComponent(botaoLogin).addComponent(botaoNovo));
//        grupoHor.addGroup(leiauteGrupo.createParallelGroup().addComponent(campoUsuario).addComponent(campoSenha).addComponent(botaoDeletar).addComponent(botaoFechar));
//        leiauteGrupo.setHorizontalGroup(grupoHor);
//
//        Group grupoVert = leiauteGrupo.createSequentialGroup();
//        grupoVert.addGroup(leiauteGrupo.createParallelGroup().addComponent(rotuloUsuario).addComponent(campoUsuario));
//        grupoVert.addGroup(leiauteGrupo.createParallelGroup().addComponent(rotuloSenha)).addComponent(campoSenha);
//        grupoVert.addGroup(leiauteGrupo.createParallelGroup().addComponent(botaoLogin).addComponent(botaoDeletar));
//        grupoVert.addGroup(leiauteGrupo.createParallelGroup().addComponent(botaoNovo).addComponent(botaoFechar));
//
//        leiauteGrupo.setVerticalGroup(grupoVert);

        getRootPane().setDefaultButton(botaoLogin);
        // atualizaBot\u00f5es();
        pack();
    }

    public Usuario getUsuario() {
        return new Usuario(campoUsuario.getText(), campoSenha.getPassword());
    }

    public void limpa() {
        campoUsuario.setText(null);
        campoSenha.setText(null);
    }

    /**
     *
     */
    public void atualizaBot\u00f5es() {
        boolean temTexto = !campoUsuario.getText().isEmpty() && campoSenha.getPassword().length > 0;
        // Analizar se est? redundate - XXX -Estava 
        boolean temUsu\u00e1rio = gere.temU(getUsuario());
//        System.out.println("Tem t"+ temTexto);
//        System.out.println("Tem n: "+temNomeUsu\u00e1rio);
//JanelaPrincipal.bancoDadosUsu\u00e1rio.hasUserName(getUsuario());
        acLo.setEnabled(temTexto && temUsu\u00e1rio);
        botaoNovo.setEnabled(temTexto && !temUsu\u00e1rio);
        botaoDeletar.setEnabled(temTexto && temUsu\u00e1rio);
    }

    private class AcaoLogin extends AbstractAction {

        public AcaoLogin() {
            super("Login");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                pai.getControl().fazLogin(chekManter.isSelected());
            } catch (IOException ex) {
                Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
