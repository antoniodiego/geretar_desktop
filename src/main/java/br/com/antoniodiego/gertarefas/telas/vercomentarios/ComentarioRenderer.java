/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.antoniodiego.gertarefas.telas.vercomentarios;

import br.com.antoniodiego.gertarefas.pojo.Comentario;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.davidmoten.text.utils.WordWrap;

/**
 *
 * @author anton
 */
public class ComentarioRenderer implements ListCellRenderer<Comentario> {

    Font f = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Logger LOG_C_RENDERER = LogManager.
            getLogger("coment_renderer");

    /**
     *
     * @param list
     * @param value
     * @param index
     * @param isSelected
     * @param cellHasFocus
     * @return
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends Comentario> list,
            Comentario value, int index, boolean isSelected, boolean cellHasFocus) {

        CardComentario cp = new CardComentario(index, value);

//        System.out.println("Desenhando");
//
//        System.out.println("Tam: " + cp.getWidth() + " " + cp.getHeight());
//        String comentario = value.getComentario();
//        
//        int largura = 200 - (cp.marginLeft + cp.marginRight);
//        
//        System.out.println("Largura getList: " + (largura - 10));
//        
//        FontMetrics fm = cp.getFontMetrics(f);
//        List<String> linhasComent = WordWrap.from(comentario).
//                breakWords(true).
//                maxWidth(largura - 10).stringWidth((str) -> {
//            return fm.stringWidth(str.toString());
//        }).wrapToList();
//        
//        System.out.println("Total linhas get: " + linhasComent.size());
//        //Altura da região para os comnentários
//        int alturaRegiao = (fm.getHeight() + 4) * linhasComent.size();
//        int alturaRetangulo = 10 + 20 + alturaRegiao + 10;
//        
//        cp.setPreferredSize(new Dimension(200, alturaRetangulo));
        cp.setFont(f);

        return cp;
    }

    private class CardComentario extends Component {

        Dimension prefSize = new Dimension(100, 300);

        Comentario c;

        public CardComentario(int index, Comentario c) {
            this.c = c;
            setFont(f);
        }

        int marginLeft = 10;
        int marginRight = 10;

        @Override
        public void setSize(Dimension d) {
            System.out.println("Set size");
            super.setSize(d);
        }

        public void setaAltura(int width) {
            String comentario = c.getComentario();

            int largura = width - (marginLeft + marginRight);

            if (largura < 0) {
                return;
            }

            System.out.println("Largura setB: " + (largura - 10));

            FontMetrics fm = getFontMetrics(f);
            List<String> linhasComent = WordWrap.from(comentario).
                    breakWords(true).
                    maxWidth(largura - 10).stringWidth((str) -> {
                return fm.stringWidth(str.toString());
            }).wrapToList();

            System.out.println("Total linhas sB: " + linhasComent.size());
            //Altura da região para os comnentários
            int alturaRegiao = (fm.getHeight() + 4) * linhasComent.size();

            LOG_C_RENDERER.debug("altura reg: " + alturaRegiao);

            int alturaRetangulo = 10 + 20 + alturaRegiao + 10;

            setPreferredSize(new Dimension(width, alturaRetangulo));
            LOG_C_RENDERER.debug("Nova altura: " + alturaRetangulo);
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            System.out.println("Set b wh");

            LOG_C_RENDERER.debug("Larg: {}, Altura: {}, x: {}, y: {}", width,
                    height,
                    x, y);

            setaAltura(width);
            
            super.setBounds(x, y, width, height);
        }

        @Override
        public void paint(Graphics g) {
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
            g2d.drawString(s,
                    marginLeft + 5, 29);

            int yBaseline = 30 + fm.getAscent();
            //    g2d.drawLine(10, yBaseline, largura, yBaseline);

            System.out.println(g2d.getFont());
            System.out.println(g2d.getFont().getStyle());

            for (String linha : linhasComent) {
                System.out.println("Desenhando: " + linha);

                g2d.drawString(linha, marginLeft + 5,
                        yBaseline);
                yBaseline += 4 + fm.getAscent();
            }

            g2d.setColor(Color.red);
            g2d.draw(new Rectangle2D.Float(0, 0, getWidth(), getHeight()));

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

    }
}
