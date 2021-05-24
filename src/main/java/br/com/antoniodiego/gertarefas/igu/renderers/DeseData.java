package br.com.antoniodiego.gertarefas.igu.renderers;

import br.com.antoniodiego.gertarefas.Constantes;
import br.com.antoniodiego.gertarefas.controller.JanelaPrincipalController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.table.DefaultTableCellRenderer;

public class DeseData extends DefaultTableCellRenderer {

    private final DateTimeFormatter fd;//SimpleDateFormat fd;

    public DeseData() {
        fd = DateTimeFormatter.ofPattern("dd/MM/yyyy",Constantes.LOCAL_BR);//SimpleDateFormat("dd/MM/yyyy");
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void setValue(Object novoValor) {
        JanelaPrincipalController.LOG_CONTR_PRINC.debug("Alt val de des data"
                + " para {}",novoValor);
        
        LocalDate d = (LocalDate) novoValor;
        setText((d == null) ? "" : fd.format(d));
    }

}
