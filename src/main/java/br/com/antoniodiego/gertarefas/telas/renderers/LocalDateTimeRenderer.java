package br.com.antoniodiego.gertarefas.telas.renderers;

import java.time.format.DateTimeFormatter;

import javax.swing.table.DefaultTableCellRenderer;

import java.time.LocalDateTime;
import java.time.format.FormatStyle;

public class LocalDateTimeRenderer extends DefaultTableCellRenderer {

    private final DateTimeFormatter fd;

    public LocalDateTimeRenderer() {
        fd = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void setValue(Object novoValor) {
        LocalDateTime d = (LocalDateTime) novoValor;
        setText((d == null) ? "" : d.format(fd));
    }

}
