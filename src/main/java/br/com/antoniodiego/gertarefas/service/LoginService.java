package br.com.antoniodiego.gertarefas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.antoniodiego.gertarefas.persist.DAOTarefa;
import br.com.antoniodiego.gertarefas.persist.DAOUsuario;
import br.com.antoniodiego.gertarefas.pojo.Usuario;
import br.com.antoniodiego.gertarefas.util.Constantes;

public class LoginService {

    private Usuario usuario;
    private Logger loginLogger = LoggerFactory.getLogger(LoginService.class);
    private PropriedadesService propriedadeService;
    private DAOUsuario daoUsuario;
    private Long proximoId;

    /**
     *
     */
    void carregaUsuarioEDados() {
        loginLogger.trace("Carr dados");
        // Verfiica sessoes salvas
        if (propriedadeService.getConfig("manter", "false").equalsIgnoreCase("true")) {
            // Sess?o salva
            loginLogger.trace("Manter estava em prop!");

            String nomeUsuarioSalvo = propriedadeService.getConfig("usuario", "");
            String hash = propriedadeService.getConfig("hash", "");

            usuario = daoUsuario.receUPorH(nomeUsuarioSalvo, hash);

            // daoUsuario.setUsu(usuario);

            // exibeGrupos();
        } else if (propriedadeService.getConfig("exibirLogin", "false").equalsIgnoreCase("true")) {
            // N?o tem sess?o e atividao login no in?cio.
            loginLogger.trace("Exibindo tela login");
            // dialogoLogin.setModal(true);
            // permissaoECarrega();
        } else {
            // N?o tem sess?o e nem atividao login no in?cio.
            loginLogger.trace("dir carr");
            // Procura usu?rio padr?o
            // Obs: Parece que apenas o nome bastaria (id)
            // OBS: anal load e get e refresh
            loginLogger.trace("Procurando usu pad");
            Usuario padr\u00e3o = daoUsuario.receU(Constantes.NOME_USR_PADR, Constantes.SENHA_PADR.toCharArray());
            loginLogger.trace("Busca conc");

            // Salva usu?rio pad?ro se n?o existir
            if (padr\u00e3o == null) {
                // gereu.receU(padr\u00e3o.getNome(), padr\u00e3o.getSenha()) == null) {
                // if (!bl.setVisible(true)
                /* ;ancoDadosUsu?rio.temUsu?rio(padr?o)) { */
                loginLogger.trace("N?o encontrou usu?rio padr?o!." + " Salvando...");
                padr\u00e3o = new Usuario(Constantes.NOME_USR_PADR, Constantes.SENHA_PADR.toCharArray());
                daoUsuario.salva(padr\u00e3o);
            } else {
                // XXX: Tem us mesmo após reiniciar banco
                loginLogger.trace("Tem usr padrao.");
            }

            setUsuario(padr\u00e3o);
            /*
             * At? aqui os grupos (e tarefas) do usu?rio padr?o j? dever?o ter sido
             * carregadas
             */

            // carregaNotificacoes();

            // carregaAgendamentos();

            // exibeGrupos();

            // TODO
        }

    }

    /**
     * Aqui o usuário que fez login é definido no sistema. Nesse momento os
     * grupos e tarefas dele são exi na árvore
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        loginLogger.trace("Setando usuário: {}", usuario.getNome());
        this.usuario = usuario;
        // Impor disp fir strut chan depois
        // ordenaTarefas();
        // ordenaGrupos();
        // passaDadosParaArv(usuario);

        // preencheATabela();

        // this.gerg.setUsu(usuario);

        DAOTarefa daoT = new DAOTarefa();
        Long maiorId = daoT.getMaiorIDPers();

        this.proximoId = maiorId++;

        loginLogger.debug("Próx id: " + proximoId);

        loginLogger.trace(null, maiorId);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void fazLogin(boolean mantemSalvo) {
        loginLogger.trace("Iniciando processo de login");
        // Combinação de nome e senha
        // usuario = dialogoLogin.getUsuario();

        // Não permite login se não tem usuário
        if (daoUsuario.receUPorH(usuario.getNome(), usuario.getEmb()) == null) {
            loginLogger.error("Usuário não encontrado");
            return;
        }

        // db = new BancoDadosTarefas(usuario);
        if (mantemSalvo) {
            // Mantém login
            // this.configService.setProperty("manter", "true");
            // this.configService.setProperty("usuario", usuario.getNome());
            // this.configService.setProperty("hash", usuario.getEmb());
            // gravaProp();
        } else {
            // this.configService.setProperty("manter", "false");
            // this.configService.setProperty("hash", "");
            // gravaProp();
        }

        // dialogoLogin.limpa();
        // dialogoLogin.dispose();

        loginLogger.trace("Exibindo grupos...");

        // TODO:noif
        // exibeGrupos();

        loginLogger.trace("Saindo do processo de login");
    }
}
