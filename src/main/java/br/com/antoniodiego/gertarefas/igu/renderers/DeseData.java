package br.com.antoniodiego.gertarefas.igu.renderers;

import br.com.antoniodiego.gertarefas.Constantes;
import br.com.antoniodiego.gertarefas.controller.JanelaPrincipalController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.table.DefaultTableCellRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeseData extends DefaultTableCellRenderer {

    private final DateTimeFormatter fd;//SimpleDateFormat fd;
    public static final Logger LOG_DES_DATA = LogManager.
            getLogger("DesenhaData");

    public DeseData() {
        fd = DateTimeFormatter.ofPattern("dd/MM/yyyy", Constantes.LOCAL_BR);//SimpleDateFormat("dd/MM/yyyy");
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void setValue(Object novoValor) {
        LOG_DES_DATA.debug("Alt val de des data"
                + " para {}", novoValor);

        LocalDate d = (LocalDate) novoValor;
        setText((d == null) ? "" : fd.format(d));
    }

}
