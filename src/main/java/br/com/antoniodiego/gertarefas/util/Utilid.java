/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.util;

import br.com.antoniodiego.gertarefas.Constantes;
import br.com.antoniodiego.gertarefas.CriptoUtils;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 *
 * @author Antonio
 */
public class Utilid {
//
//    public static String recHas(Usuario u) {
//        byte[] hash = null;
//
//        StringBuilder in = new StringBuilder(u.getNome());
//        in.append(u.getSenha());
//
//        try {
//            hash = CriptoUtils.digest(in.toString().getBytes(), "MD5");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//
//        String hex = CriptoUtils.byteArrayToHexString(hash);
//        return hex;
//    }

    public static String geraEmb(String nome, char[] senha) {
        byte[] hash = null;

        StringBuilder in = new StringBuilder(nome);
        in.append(senha);

        try {
            hash = CriptoUtils.digest(in.toString().getBytes(), "MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String hex = CriptoUtils.byteArrayToHexString(hash);
        return hex;
    }

    private static LocalDate convertData(Date d) {
        Instant t = d.toInstant();
        return t.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * ou null
     *
     * @param d
     * @return
     */
    public static LocalDateTime leD(String d) {
        TemporalAccessor t = Constantes.FORMATADOR_DATA_H_BR_T.parse(d);
        LocalDate ld = LocalDate.from(t);
        LocalTime h = null;
        if (t.isSupported(ChronoField.HOUR_OF_DAY)) {
            h = LocalTime.from(t);
        }
        return ld == null ? null : LocalDateTime.of(ld, h == null ? LocalTime.now() : h);
    }
}
