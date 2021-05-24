/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.igu.modelos;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.swing.JFormattedTextField;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class FormatDatePick extends JFormattedTextField.AbstractFormatter{
    
        private DateTimeFormatter df = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        private Locale lcBrasil = new Locale("pt", "BR");
        private DateFormat dfB = DateFormat.getDateInstance(DateFormat.SHORT, lcBrasil);

        @Override
        public Object stringToValue(String text) throws ParseException {
            Date data = dfB.parse(text);
            GregorianCalendar gc = new GregorianCalendar(lcBrasil);
            gc.setTime(data);
            return gc;
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            GregorianCalendar gc = (GregorianCalendar) value;
            if(gc== null){
                return "";
            }
            Date data = gc.getTime();
            return dfB.format(data);
        }

}
