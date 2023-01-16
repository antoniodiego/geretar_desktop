/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.antoniodiego.gertarefas.telas.vercomentarios;

import br.com.antoniodiego.gertarefas.pojo.Comentario;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.davidmoten.text.utils.WordWrap;

/**
 *
 * @author anton
 */
public class ComponenteComentario extends Panel {

    Dimension prefSize = new Dimension(100, 300);

    Comentario c;

    Font f = new Font("Dialog", Font.PLAIN, 12);
    Color foreg = new Color(51, 51, 51);

    public static final Logger LOG_C_RENDERER = LogManager.
            getLogger("coment_renderer");

    public ComponenteComentario(int index, Comentario c) {
        this.c = c;
        setFont(f);

    }

    int marginLeft = 10;
    int marginRight = 10;

    public void setaAltura(int width) {
        String comentario = c.getComentario();

        int largura = width - (marginLeft + marginRight);

        if (largura < 0) {
            return;
        }

        System.out.println("Largura setA: " + (largura - 10));

        FontMetrics fm = getFontMetrics(f);
        List<String> linhasComent = WordWrap.from(comentario).
                breakWords(true).
                maxWidth(largura - 10).stringWidth((str) -> {
            return fm.stringWidth(str.toString());
        }).wrapToList();

        //   System.out.println("Total linhas sA: " + linhasComent.size());
        //Altura da região para os comnentários
        int alturaRegiao = (fm.getHeight() + 4) * linhasComent.size();

        // LOG_C_RENDERER.debug("altura reg: " + alturaRegiao);
        int alturaRetangulo = 10 + 20 + alturaRegiao + 10;

        //  LOG_C_RENDERER.debug("Nova altura: " + alturaRetangulo);
        super.setBounds(getX(), getY(), getWidth(), alturaRetangulo);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        System.out.println("Set b wh");
        System.out.println("Chamando superSetBounds");
        super.setBounds(x, y, width, height);
        LOG_C_RENDERER.debug("Larg: {}, Altura: {}, x: {}, y: {}", width,
                height,
                x, y);

        System.out.println("Chamando setAltura");
        setaAltura(width);

    }

    @Override
    public void paint(Graphics g) {
        LOG_C_RENDERER.traceEntry();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);

        LOG_C_RENDERER.debug("p. Larg: {}, Altura: {}", getWidth(),
                getHeight());

        //    System.out.println("Largura paint: " + (largura - 10));
        int largura = getWidth() - (marginLeft + marginRight);

        RoundRectangle2D retanguloCabecalho = new RoundRectangle2D.Float(marginLeft,
                10, largura,
                20, 10, 10);

        g2d.draw(retanguloCabecalho);
        g2d.setColor(Color.lightGray);
        g2d.fill(retanguloCabecalho);

        String comentario = c.getComentario();

        System.out.println("Largura paint: " + (largura - 10));
        FontMetrics fm = getFontMetrics(f);
        List<String> linhasComent = WordWrap.from(comentario).
                breakWords(true).
                maxWidth(largura - 10).stringWidth((str) -> {
            return fm.stringWidth(str.toString());
        }).wrapToList();

        System.out.println("Total linhas paint: " + linhasComent.size());
        //Altura da região para os comnentários
//            int alturaRegiao = (fm.getHeight() + 4) * linhasComent.size();
//            int alturaRetangulo = 10 + 20 + alturaRegiao + 10;
//            
//            setPreferredSize(new Dimension(getWidth(), alturaRetangulo));

        // System.out.println("Altura: " + (alturaRet));
        //Margem superior 10 e inferior 10
        RoundRectangle2D retanguloCorpo = new RoundRectangle2D.Float(marginLeft,
                10, largura,
                getHeight() - 20, 10, 10);

        g2d.setColor(Color.black);
        g2d.draw(retanguloCorpo);

        String s = c.getDataComentario().
                format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "  " + c.getHora().
                        format(DateTimeFormatter.ISO_LOCAL_TIME);
        g2d.setFont(f);
        g2d.setColor(foreg);
        g2d.drawString(s,
                marginLeft + 5, 29);

        int yBaseline = 30 + fm.getAscent();
        //    g2d.drawLine(10, yBaseline, largura, yBaseline);

//        System.out.println(g2d.getFont());
//        System.out.println(g2d.getFont().getStyle());
        for (String linha : linhasComent) {
            System.out.println("Desenhando: " + linha);

            g2d.drawString(linha, marginLeft + 5,
                    yBaseline);
            yBaseline += 4 + fm.getAscent();
        }

//        g2d.setColor(Color.red);
//        g2d.draw(new Rectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1));
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        System.out.println("Set psize");
        this.prefSize = preferredSize;

    }

    @Override
    public Dimension getPreferredSize() {
        LOG_C_RENDERER.debug("Ger p s");
        return prefSize;
    }

    @Override
    public String toString() {
        return this.c.getComentario();
    }

}
