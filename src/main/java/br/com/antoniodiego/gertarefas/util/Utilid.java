/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.util;

import br.com.antoniodiego.gertarefas.Constantes;
import br.com.antoniodiego.gertarefas.CriptoUtils;
import br.com.antoniodiego.gertarefas.telas.principal.JanelaPrincipalMatisse;
import static br.com.antoniodiego.gertarefas.telas.principal.paineis.PainelTabelaTarefas.LOG_PAINEL_T;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

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

    public static void persisteInfoTabela(JTable tabelaTarefas) {
        TableColumnModel modelC = tabelaTarefas.
                getColumnModel();
        TableColumn col;

        JSONObject js = new JSONObject();
        JSONObject config;
        for (int i = 0; i < modelC.getColumnCount(); i++) {
            col = modelC.getColumn(i);

            LOG_PAINEL_T.trace("Key: {}, largura: {}", col.getIdentifier(),
                    col.getWidth());
            config = new JSONObject();
            config.put("width", col.getWidth());
            config.put("index", i);
            js.put(String.valueOf(col.getIdentifier()), config);
        }

        File arquivoTam = new File("colunas.json");
        FileWriter fw;
        try {
            fw = new FileWriter(arquivoTam);
            fw.append(js.toJSONString());
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(JanelaPrincipalMatisse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static final void carregaInfoTabela(JTable tabelaTarefas) {
        File arquivoTam = new File("colunas.json");
        if (arquivoTam.exists()) {
            try {
                FileReader fr = new FileReader(arquivoTam);
                JSONParser js = new JSONParser(JSONParser.ACCEPT_NAN);
                Object res = js.parse(fr);

                if (res instanceof JSONObject) {
                    JSONObject jsO = (JSONObject) res;

                    jsO.entrySet().forEach((e) -> {
                        //    LOG_PAINEL_T.trace("Key: {}", e.getKey());

                        try {
                            TableColumn coluna = tabelaTarefas.
                                    getColumn(e.getKey());
                            //    LOG_PAINEL_T.debug("" + e.getKey());
                            JSONObject config = (JSONObject) jsO.get(e.getKey());

                            int width = config.
                                    getAsNumber("width").intValue();
                            LOG_PAINEL_T.debug("Alterando larg coluna {} para {}", e.getKey(), width);
                            coluna.setPreferredWidth(width);
                            coluna.setWidth(width);

                        } catch (Exception ex) {
                            LOG_PAINEL_T.catching(ex);
                        }
                        // coluna.setModelIndex(config.getAsNumber("index").intValue());
                    });
                }

                // StringBuilder leit = new StringBuilder();
                // char[] cbuf = new char[1024];
                // int len = 0;
                // while ((len = fr.read(cbuf)) != -1) {
                // leit.append(new String(cbuf, 0, len));
                // }
                // fr.close();
            } catch (FileNotFoundException | ParseException ex) {
                LOG_PAINEL_T.catching(ex);
            }
        }
    }
}
