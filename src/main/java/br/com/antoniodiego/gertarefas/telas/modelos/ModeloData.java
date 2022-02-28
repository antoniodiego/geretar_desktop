/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.telas.modelos;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdatepicker.AbstractDateModel;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class ModeloData extends AbstractDateModel<LocalDate> {

    public static final Logger LOG_MOD_DATA = LogManager.getLogger(ModeloData.class);

    @Override
    protected Calendar toCalendar(LocalDate from) {
        LOG_MOD_DATA.traceEntry(from.format(DateTimeFormatter.ISO_DATE));

        Calendar c = Calendar.getInstance();
        //    LocalDateTime dtMeiaNoite = from.atTime(0, 0,0);

        //  LOG_MOD_DATA.debug("Data com hora: " + dtMeiaNoite.format(DateTimeFormatter.ISO_DATE));
        //  LOG_MOD_DATA.debug("Data " + dtMeiaNoite.getDayOfMonth());
        //   Instant inst = dtMeiaNoite.toInstant(ZoneOffset.ofHours(-3));
        c.set(from.getYear(), from.getMonthValue()-1, from.getDayOfMonth(), 0, 0, 0);
        //  c.setTime(Date.from(inst));

        LOG_MOD_DATA.debug("Data c: " + c.get(Calendar.DAY_OF_MONTH));

        return c;
    }

    @Override
    protected LocalDate fromCalendar(Calendar from) {
        LOG_MOD_DATA.traceEntry(String.valueOf(from.get(Calendar.DAY_OF_MONTH)));

        Instant ins = from.toInstant();

        LocalDateTime tempoED = LocalDateTime.ofInstant(ins, ZoneId.systemDefault());

        LOG_MOD_DATA.debug(tempoED.getDayOfMonth());

        LocalDate ret = tempoED.toLocalDate();

        LOG_MOD_DATA.debug(ret.getDayOfMonth());

        return ret;
    }
}
