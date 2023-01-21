package br.com.antoniodiego.gertarefas.telas.renderers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.table.DefaultTableCellRenderer;

import br.com.antoniodiego.gertarefas.Constantes;
import java.time.format.FormatStyle;

public class LocalDateRenderer extends DefaultTableCellRenderer {

    private final DateTimeFormatter formatter;

    public LocalDateRenderer() {
        formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void setValue(Object novoValor) {
        LocalDate d = (LocalDate) novoValor;
        setText((d == null) ? "" : d.format(formatter));
    }

}
