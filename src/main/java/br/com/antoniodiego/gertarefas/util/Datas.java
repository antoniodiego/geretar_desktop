package br.com.antoniodiego.gertarefas.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class Datas {
     private String recebeStringData() {
        Calendar ca = Calendar.getInstance();

        StringBuilder controiDataAtual = new StringBuilder();
        controiDataAtual.append(ca.get(Calendar.DAY_OF_MONTH));
        controiDataAtual.append(ca.get(Calendar.MONTH));
        controiDataAtual.append(ca.get(Calendar.YEAR));
        controiDataAtual.append("-").append(ca.get(Calendar.HOUR_OF_DAY)).append(ca.get(Calendar.MINUTE));

        return controiDataAtual.toString();
    }

     public static LocalDate convertData(Date d) {
        Instant t = d.toInstant();
        return t.atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
