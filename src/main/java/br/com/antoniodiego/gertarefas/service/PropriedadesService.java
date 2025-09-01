package br.com.antoniodiego.gertarefas.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class PropriedadesService {

    private Logger LOGGER_PROP = LoggerFactory.getLogger("Painel_Tarefas");

    private Properties propriedades;
    private File arquivoPropriedades;

    public String getConfig(String chave, String padrao){

        if(this.propriedades == null){
            this.propriedades = new Properties();
            carregaPropriedades();
        }

        return this.propriedades.getProperty(chave, padrao);
    }

    public void carregaPropriedades(){
          File arquivoProp = new File("propriedades.json");

        if (arquivoProp.exists()) {
            try {
                Object res = null;

                try (FileReader fr = new FileReader(arquivoProp)) {
                    JSONParser js = new JSONParser(JSONParser.ACCEPT_NAN);
                    res = js.parse(fr);
                } catch (IOException ex) {
                    LOGGER_PROP.error("Erro ao ler propriedades: ", ex);
                }

                if (res instanceof JSONObject) {
                    JSONObject jsO = (JSONObject) res;

                    Number largura = jsO.getAsNumber("largura");
                    Number altura = jsO.getAsNumber("altura");
                    Number expState = jsO.getAsNumber("estado");


                    // TODO: Carregar configuração do tamanho janela
                    // princ.setSize(largura.intValue(), altura.intValue());
                    // princ.setExtendedState(expState.intValue());

                }

            } catch (ParseException | NullPointerException ex) {
                LOGGER_PROP.error("Erro ao carregar propriedades: ", ex);
            }
        }
    }

      public void gravaProp() throws FileNotFoundException, IOException {
        try (FileOutputStream sai = new FileOutputStream(arquivoPropriedades)) {
            this.propriedades.store(sai, "arqu conf");
        }
    }
}
