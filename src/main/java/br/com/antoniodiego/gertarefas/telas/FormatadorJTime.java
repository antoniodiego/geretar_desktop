/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.telas;

import br.com.antoniodiego.gertarefas.Constantes;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import javax.swing.JFormattedTextField;

/**
 * Formatador de datas da API Java Time
 *
 * @author Antônio Diego- Comp:Antônio Diego <your.name at your.org>
 */
public class FormatadorJTime extends JFormattedTextField.AbstractFormatter {

    private final DateTimeFormatter fhd;
    private final boolean tempo;

    public FormatadorJTime(boolean tempo) {
        this.tempo = tempo;
        DateTimeFormatterBuilder cons = new DateTimeFormatterBuilder();
        if (tempo) {
            cons.appendPattern("kk:mm");
        } else {
            cons.appendPattern("dd/MM/u");
        }
        fhd = cons.toFormatter(Constantes.LOCAL_BR);
    }

    @Override
    public Object stringToValue(String text) throws ParseException {
        if (text.isEmpty()) {
            return null;
        }
        TemporalAccessor ta = fhd.parse(text);
        if (tempo) {
            return LocalTime.from(ta);
        } else {
            return LocalDate.from(ta);
        }
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        TemporalAccessor t = (TemporalAccessor) value;
        return t == null ? "" : fhd.format(t);
    }

}
