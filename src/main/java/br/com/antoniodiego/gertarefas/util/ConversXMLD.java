package br.com.antoniodiego.gertarefas.util;

import br.com.antoniodiego.gertarefas.Constantes;
import br.com.antoniodiego.gertarefas.pojo.GrupoTarefas;
import br.com.antoniodiego.gertarefas.pojo.Tarefa;
import br.com.antoniodiego.gertarefas.pojo.TarefaComposta;
import br.com.antoniodiego.gertarefas.pojo.TarefaCoordenada;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Ant?nio Diego- Comp:Ant?nio Diego <your.name at your.org>
 */
public class ConversXMLD extends ConversXML {

    private final DateTimeFormatter formApeDat;
    private final DateTimeFormatter formDatHora;
    private String charsetEntrada;
    private String charsetSaida;
    private Logger logConv;

    public ConversXMLD() {
        formApeDat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);//new DateTimeFormatterBuilder().appendPattern("dd/MM/YY").toFormatter(Constantes.LOCAL_BR);
        formDatHora = new DateTimeFormatterBuilder().appendPattern("dd/MM/YY[ kk:mm]").toFormatter(Constantes.LOCAL_BR);
        //Padrões
        charsetEntrada = "UTF-8";
        charsetSaida = "UTF-8";
        logConv = LogManager.getLogger("ConvXML");
    }

    /**
     * Gera um documento xml a partir de uma lista de grupos
     *
     * @param grupos
     * @param trs
     * @return
     */
    public Document geraXMLD(List<GrupoTarefas> grupos, List<Tarefa> trs) {
        logConv.debug("Início de gera XML dom");
        DocumentBuilderFactory fab = DocumentBuilderFactory.newInstance();
        DocumentBuilder conD = null;

        try {
            conD = fab.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        if (conD == null) {
            return null;
        }

        Document doc = conD.newDocument();

        System.out.println("Gereando XML dom");
        try {
            Element elraiz = doc.createElement("tarefas");
            doc.appendChild(elraiz);

            GrupoTarefas g;
            for (int i = 0; i < grupos.size(); i++) {
                g = grupos.get(i);// (GrupoTarefas) modLG.getElementAt(i);
                logConv.debug("Marshalando grupo...");
                Element nog = geraElNog(doc, g);
                elraiz.appendChild(nog);
            }

            if (trs != null) {
                System.out.println("Adici tare XML");
                logConv.debug("Marshalando tarefa...");
                trs.stream().map((t) -> geraNoTarefa(doc, t)).forEachOrdered((et) -> {
                    elraiz.appendChild(et);
                });
            }
        } catch (DOMException e) {
            JOptionPane.showMessageDialog(null, "Problema ao exp: " + e.getLocalizedMessage());
        }
        System.out.println("con retor doc");
        return doc;
    }

    /**
     *
     * @param doc
     * @param g
     * @return
     */
    public Element geraElNog(Document doc, GrupoTarefas g) {
        System.out.println("Gerando n? grupo");

        Element nog = doc.createElement("grupotarefas");
        Element id = doc.createElement("id");
        id.appendChild(doc.createTextNode(String.valueOf(g.getId())));
        nog.appendChild(id);
        Element tit = doc.createElement("titulo");
        tit.appendChild(doc.createTextNode(g.getTitulo()));
        nog.appendChild(tit);

        //Subgrupos  
        logConv.debug("Marshalando subgrupos...");
        List<GrupoTarefas> sub = g.getSubgrupos();
        //Aqui deveria ser necess?rio aninhar v?rios n?s grupos n? do grupo atual
        //  Document subd = geraXMLD(sub, new ArrayList<>());

        GrupoTarefas gr;
        for (int i = 0; i < sub.size(); i++) {
            gr = sub.get(i);// (GrupoTarefas) modLG.getElementAt(i);
            Element nosg = geraElNog(doc, gr);
            nog.appendChild(nosg);
        }

        //Tarefas
        List<Tarefa> tars = g.getTarefas();
        logConv.debug("Marshalando tarefas...");
        tars.stream().map((t) -> {
            //TODO: Criar m?todo para criar elemento Nova tar-bot
            Element not = geraNoTarefa(doc, t);

            return not;
        }).forEachOrdered((not) -> {
            nog.appendChild(not);
        });
        return nog;
    }

    /**
     *
     * @param doc
     * @param t
     * @return
     */
    //TODO: TarefaComposta e Simples
    //OBS: XXX: Problema que est dando null era por causa do coment null tarefa ntig
    public Element geraNoTarefa(Document doc, Tarefa t) {
        logConv.debug("Em geraNoTarefa");
        Element not;
        TarefaComposta vc = (TarefaComposta) t;
        not = doc.createElement("tarefacomposta");

        Element idT = doc.createElement("id");
        idT.appendChild(doc.createTextNode(String.valueOf(t.getId())));
        not.appendChild(idT);
        Element titulo = doc.createElement("titulo");
        titulo.appendChild(doc.createTextNode(t.getTitulo()));
        not.appendChild(titulo);

        Element conc = doc.createElement("concluida");
        conc.appendChild(doc.createTextNode(String.valueOf(t.isConcluida())));
        not.appendChild(conc);

        //Data conc - formato PT-BR
        if (t.getDataConclusao() != null) {
            System.out.println("data con tc: " + formDatHora.format(t.getDataConclusao()));
            Element dataConc = doc.createElement("dataconclusao");
            dataConc.appendChild(doc.createTextNode(t.getDataConclusao() == null ? " " : formDatHora.format(t.getDataConclusao())));
            not.appendChild(dataConc);
        }

        logConv.debug("Gerando tag data (criação)...");
        Element data = doc.createElement("data");

        String dataF = "";

        if (t.getDataCriacao() != null) {
            dataF = t.getDataCriacao().format(formApeDat);
            logConv.debug("Data cria. form: " + dataF);
        }
        //TODO: Tentar fazer testes de unidade com gera String.v, leitura de ger

        data.appendChild(doc.createTextNode(dataF));
        not.appendChild(data);

        logConv.debug("Adic. tag. data conc.");
        Element dataf = doc.createElement("datafazer");
        String dataFF = "";
        if (t.getDataFazer() != null) {
            dataFF = t.getDataFazer().format(formApeDat);
            logConv.debug("Data fazer form.: " + dataFF);
        }

        dataf.appendChild(doc.createTextNode(dataFF));
        not.appendChild(dataf);

        Element prio = doc.createElement("prioridade");
        prio.appendChild(doc.createTextNode(String.valueOf(vc.getPrioridade())));
        not.appendChild(prio);

        List<TarefaCoordenada> tarF = vc.getTarefasFilhas();
        tarF.forEach((TarefaCoordenada filha) -> {
            Element nof = geraNoTarC(doc, filha);
            not.appendChild(nof);
        });
        return not;
    }

    public Element geraNoTarC(Document doc, TarefaCoordenada tc) {
        Element not = doc.createElement("tarefacoordenada");
        Element conc = doc.createElement("concluida");
        conc.appendChild(doc.createTextNode(String.valueOf(tc.isConcluida())));
        not.appendChild(conc);

        //Deveria gerar com data e hora
        if (tc.getDataConclusao() != null) {
            System.out.println("dc tar cor");
            Element dataCon = doc.createElement("dataconclusao");
            if (tc.getDataConclusao() != null) {
                dataCon.appendChild(doc.createTextNode(formApeDat.format(tc.getDataConclusao())));
            } else {
                //XXX: Obs parece nÃ£o ser bom adi ele sem filh
                dataCon.appendChild(doc.createTextNode(" "));
            }
            not.appendChild(dataCon);
        }
        Element conteudo = doc.createElement("descricao");
        conteudo.appendChild(doc.createTextNode(tc.getDescricao()));
        not.appendChild(conteudo);

        if (tc.getComentario() != null) {
            Element comentario = doc.createElement("comentario");
            comentario.appendChild(doc.createTextNode(String.valueOf(tc.getComentario())));
            not.appendChild(comentario);
        }

        return not;
    }

    /**
     * L? um grupo de um Node grupo. XML da minha api de impo/expo
     *
     * @param nogrupo
     * @return
     * @throws java.text.ParseException
     */
    public GrupoTarefas leGrupo(Node nogrupo) throws ParseException {
        logConv.debug("Inic. le grupo");
        GrupoTarefas gl = new GrupoTarefas();
        //TODO:Proc sub

        //N?o l? o id antigo para n?o causar conflitos
        //gl.setId(Integer.parseInt(filhos.item(0).getTextContent()));
        logConv.debug("Lendo tit.");
        gl.setTitulo(procuraValor("titulo", nogrupo));//pfilhos.item(1).getTextContent());

        NodeList filhos = nogrupo.getChildNodes();

        int idx = 0;
        while (idx < filhos.getLength()) {
            Node nt = filhos.item(idx);
            if (nt.getNodeName().equals("grupotarefas") || nt.getNodeName().equals("grupo")) {
                GrupoTarefas tf = leGrupo(nt);
                gl.add(tf);
            } else if (nt.getNodeName().equals("tarefa") || nt.getNodeName().equals("tarefacomposta")) {
                Tarefa t = leTar(nt);
                gl.add(t);
            }
            idx++;
        }
        logConv.debug("Leitura conc");
        //TODO: Checar se grupo já existe
        return gl;
    }

    /**
     * Le tarefa de n?
     *
     * @param nt
     * @return
     * @throws java.text.ParseException
     */
    //TODO: Simples ou comp
    //Obs: deveria levar em conta nomes dos campos e n?o posics
    public Tarefa leTar(Node nt) throws ParseException {
        logConv.traceEntry();
        logConv.debug("Inic letar");
        TarefaComposta tarX = new TarefaComposta();
        // boolean comp = false;

        if (nt.getNodeType() == Node.ELEMENT_NODE) {

            //    String supId = filT.item(0).getTextContent();
            //   System.out.println("sup: " + supId);
            //N?o por id para n?o causa conf
            //tarX.setId(Integer.parseInt(supId));
            tarX.setTitulo(procuraValor("titulo", nt));

            //TODO: Procurar Tarefas Coordenadas
            List<TarefaCoordenada> tc = tarX.getTarefasFilhas();
            logConv.trace("Procurando tar coordens...");
            List<Node> tarc = procuraTarCo(nt);

            //Obs: primeira ? ant
            tarc.forEach((Node no) -> {
                //   try {
                tc.add(leTC(no));
//                } catch (ParseException ex) {
//                    Logger.getLogger(ConversXMLD.class.getName()).log(Level.SEVERE, null, ex);
//                }
            });

            logConv.trace("Após ler tcs");

            tarX.setConcluida(Boolean.parseBoolean(procuraValor("concluida", nt)));
            String dataCon = procuraValor("dataconclusao", nt);
            LocalDateTime dc = null;

            if (dataCon != null && !dataCon.equals("null") && !dataCon.isEmpty()) {
                try {
                    // TemporalAccessor ta = formDatHora.parse(dataCon);
                    dc = Utilid.leD(dataCon);// LocalDateTime.from(ta);
                } catch (NullPointerException | DateTimeParseException ex) {
                    ex.printStackTrace();
                    dc = null;
                }
            }

            //Retrocomp
            String cont = procuraValor("conteudo", nt);

            if (cont != null) {
                TarefaCoordenada ant = new TarefaCoordenada(tarX.isConcluida(), dc, cont, "");
                tc.add(0, ant);
            }

            System.out.println("Alter data con...");
            tarX.setDataConclusao(dc);

            logConv.debug("Lendo data cria...");
            //XXX- Não lê no formato AAAA-MM-DD (No Brasil)
            try {
                //      if (!data.equals("null")) {
                logConv.debug("Procur...");
                String strData = procuraValor("data", nt);

                logConv.debug("Enc.: " + strData);
                if (strData == null) {
                    logConv.info("Sem data cria.");
                } else {
                    logConv.debug("Dividi data c. ...");

//                TemporalAccessor ta = null;
//                try {
//                    ta = formApeDat.parse(strData);
//                } catch (DateTimeParseException ex) {
//                    ex.printStackTrace();
//                }
                    logConv.debug("Config no objeto...");
                    LocalDate dl = null;

                    try {
                        dl = LocalDate.parse(strData, formApeDat);//from(ta);
                        logConv.debug("Data dividida!");
                    } catch (ClassCastException ex) {
                        logConv.debug("Não leu data");
                        ex.printStackTrace();
                    } catch (DateTimeParseException ex) {
                        logConv.warn("Falha ao interpretar data: " + strData);
                        ex.printStackTrace();
                        logConv.catching(ex);
                    }

                    if (dl != null) {
                        tarX.setDataCriacao(dl);
                    } else {
                        logConv.warn("Data nula. Config como hoje...");
                        tarX.setDataCriacao(LocalDate.now());
                    }
                    //   }
                    logConv.debug("Feito.");
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                logConv.error("Falha ao ler data cria");
                logConv.debug("Configur como nula");
                tarX.setDataCriacao(null);
                JOptionPane.showMessageDialog(null, "Erro ao ler data de criação", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            try {
                logConv.debug("Procur data fazer...");
                String strData = procuraValor("datafazer", nt);
                logConv.debug("Enc.: " + strData);
                logConv.debug("Dividi. ...");
                logConv.debug("Enc.: " + strData);
                if (strData == null) {
                    logConv.info("Sem data cria.");
                } else {
                    LocalDate dl = null;

                    try {
                        dl = LocalDate.parse(strData, formApeDat);//from(ta);
                        logConv.debug("Data dividida!");
                    } catch (ClassCastException ex) {
                        logConv.debug("Não leu data");
                        ex.printStackTrace();
                    }catch (DateTimeParseException ex) {
                        logConv.warn("Falha ao interpretar data fazer: " + strData);
                        //TODO: Tentar fornecer suporte data estados unid
                        ex.printStackTrace();
                        logConv.catching(ex);
                    }

                    logConv.debug("Config no objeto...");
                    tarX.setDataFazer(dl);
//Obs: deve causar erro ap?s incluir camp e tent im ver ant
//TODO: Ler tar filhas
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                logConv.error("Falha ao ler data f");
                tarX.setDataFazer(null);
            }
        }
        return tarX;
    }

    private TemporalAccessor paraD(DateTimeFormatter f, String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        try {
            TemporalAccessor ret = f.parse(str);
            return ret;
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    /**
     *
     * @param no
     * @return
     */
    //TODO: Tentar criar testes
    private TarefaCoordenada leTC(Node no) {
        logConv.debug("Lendo tar. coord....");

        TarefaCoordenada ret = new TarefaCoordenada();
        ret.setConcluida(Boolean.parseBoolean(procuraValor("concluida", no)));
        logConv.debug("Lendo data con...");
        String dcon = procuraValor("dataconclusao", no);
        logConv.debug("Data lida: " + dcon);
        try {
            if (dcon != null && !dcon.isEmpty()) {
                logConv.debug("Data lida nem nula nem vazia");
                //  Date dc = Constantes.FORMATADOR_BR.parse(dcon);
                //XXX: Obervar se func com data e hor
                //XXX: OBS: Hora pode ser nula. parece ser bom sep.
                ret.setDataConclusao(Utilid.leD(dcon));
            } else {
                logConv.info("data con vazia ou nula");
            }
        } catch (NullPointerException | DateTimeParseException ex) {
            ex.printStackTrace();
            logConv.debug("Configurando data como nula");
            ret.setDataConclusao(null);
        }
        ret.setDescricao(procuraValor("descricao", no));
        ret.setComentario(procuraValor("comentario", no));
        return logConv.traceExit(ret);
    }

    /**
     * Fil tarc
     *
     * @param n
     * @return
     */
    private static List<Node> procuraTarCo(Node n) {
        NodeList nf = n.getChildNodes();
        List<Node> tcs = new ArrayList<>();
        //OBS: prob era use tcs.size que ? = 0
        for (int i = 0; i < nf.getLength(); i++) {
            if (nf.item(i).getNodeName().equals("tarefacoordenada")) {
                tcs.add(nf.item(i));
            }
        }
        return tcs;
    }

    private static String procuraValor(String elemento, Node no) {
        NodeList filT = no.getChildNodes();
        for (int i = 0; i < filT.getLength(); i++) {
            Node n = filT.item(i);
            if (n.getNodeName().equals(elemento)) {
                return n.getTextContent();
            }
        }
        return null;
    }

    @Override
    public String geraXML(List<GrupoTarefas> grs, List<Tarefa> trs) {
        logConv.debug("Início de geraXML");
        StringWriter es = new StringWriter();

        try {
            TransformerFactory ft = TransformerFactory.newInstance();
            Transformer tr = ft.newTransformer();
            tr.setOutputProperty(OutputKeys.ENCODING, charsetSaida);//"UTF-8");
            Document doc = geraXMLD(grs, trs);
            //      System.out.println("d: " + doc);
            DOMSource orgd = new DOMSource(doc);
            StreamResult rs = new StreamResult(es);
            //         StringResult rs = new StringResult();
            tr.transform(orgd, rs);

        } catch (TransformerConfigurationException ex) {
            logConv.catching(ex);
        } catch (TransformerException ex) {
            logConv.catching(ex);
        }

//        try {
//            return sa.toString("UTF-8");
//        } catch (UnsupportedEncodingException ex) {
//         Logger.getLogger(ConversXMLD.class.getName()).log(Level.SEVERE, null, ex);
        return es.toString();
        //    }
    }

//    public List<Object> leGruposETars(String xml) throws UnsupportedEncodingException {
//        return leGrupoETars(new ByteArrayInputStream(xml.getBytes("UTF-8")));
//    }
    /**
     * Ret
     *
     * @param in
     * @return
     */
    @Override
    public List<Object> leGrupoETars(InputStream in) {
        logConv.debug("Inici le gr e tars");

        List<Object> gt = new ArrayList<>();
        DocumentBuilderFactory fcd = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder cd = fcd.newDocumentBuilder();
            //27/07/18 ~09:11
            //Tenter ler exp xml de lan cand 1 25/06/18 -1657 com ISO-8859-1 e alguns acentos foram lidos corretamente, o que sugere que ele fora exportado nesse encoding
            //Exportei com a versão dev (o qual estava exportando com UTF-8)  e tentei importar ainda com ISO-8859-1 e alguns caracteres fdicaram corrompidos.
            logConv.debug("Preparando buffer rea ...");
            logConv.debug("Charset entr: " + charsetEntrada);
            BufferedReader la = new BufferedReader(new InputStreamReader(in, charsetEntrada));// "UTF-8"));
            logConv.debug("Divid. docum...");
            Document doc = cd.parse(new InputSource(la));
            logConv.debug("Dividido!");
            //   System.out.println("dividiu");

            //Deve ser tarefas
            Element eleD = doc.getDocumentElement();
            logConv.debug("Eled: " + eleD.getTagName());
            doc.getDocumentElement().normalize();

            NodeList fiGroT = eleD.getChildNodes();

            System.out.println("quant ele: " + fiGroT.getLength());

            GrupoTarefas g;
            Tarefa ta;
            for (int c = 0; c < fiGroT.getLength(); c++) {
                Node n = fiGroT.item(c);
                if (n.getNodeName().equals("grupotarefas") || n.getNodeName().equals("grupo")) {
                    logConv.debug("Enc grupo");
                    g = leGrupo(n);
                    //TODO: Checar se grupo já existe antes ad
                    gt.add(g);
                } else if (n.getNodeName().equals("tarefa") || n.getNodeName().equals("tarefacomposta")) {
                    logConv.debug("Enc tarefa avul");
// System.out.println("Encontrou tarefa (avulsa) em XML");
                    //N? n represe n? tarefa
                    ta = leTar(n);
                    gt.add(ta);
                }
            }
        } catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | SAXException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            logConv.catching(ex);
        }
        return gt;
    }

    public String getCharsetEntrada() {
        return charsetEntrada;
    }

    public void setCharsetEntrada(String charsetEntrada) {
        this.charsetEntrada = charsetEntrada;
    }

    public String getCharsetSaida() {
        return charsetSaida;
    }

    public void setCharsetSaida(String charsetSaida) {
        this.charsetSaida = charsetSaida;
    }

}
