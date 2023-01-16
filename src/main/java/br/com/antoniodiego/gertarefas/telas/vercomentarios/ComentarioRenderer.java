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
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.davidmoten.text.utils.WordWrap;

/**
 *
 * @author anton
 */
public class ComentarioRenderer implements ListCellRenderer<Comentario> {

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
        String comentario = value.getComentario();

        int largura = 200 - (cp.marginLeft + cp.marginRight);

        System.out.println("Largura: " + (largura - 10));
        List<String> linhasComent = WordWrap.from(comentario).
                breakWords(false).
                maxWidth(largura - 10).wrapToList();

        Font f = new Font("Segoe UI", Font.PLAIN, 14);
        FontMetrics fm = cp.getFontMetrics(f);

        int alturaRet = (fm.getHeight() + 8) * linhasComent.size()+30;
        cp.setPreferredSize(new Dimension(200, alturaRet));

        cp.setFont(f);

        return cp;
    }

    private class CardComentario extends Component {

        Dimension prefSize;

        Comentario c;

        public CardComentario(int index, Comentario c) {

            this.c = c;
        }

        int marginLeft = 10;
        int marginRight = 10;

        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.black);

            int largura = getWidth() - (marginLeft + marginRight);

            RoundRectangle2D retanguloCabecalho = new RoundRectangle2D.Float(marginLeft,
                    10, largura,
                    20, 10, 10);

            g2d.draw(retanguloCabecalho);
            g2d.setColor(Color.lightGray);
            g2d.fill(retanguloCabecalho);

            String comentario = c.getComentario();

            System.out.println("Largura: " + (largura - 10));
            FontMetrics fm = g2d.getFontMetrics();
            List<String> linhasComent = WordWrap.from(comentario).
                    breakWords(true).
                    maxWidth(largura - 10).stringWidth((str) -> {
                return fm.stringWidth(str.toString());
            }).wrapToList();

            int alturaRet = ((fm.getHeight() + 8) * linhasComent.size()) + 30;

            setPreferredSize(new Dimension(getWidth(), alturaRet));

            System.out.println("Altura: " + (alturaRet));
            RoundRectangle2D retanguloCorpo = new RoundRectangle2D.Float(marginLeft,
                    10, largura,
                    alturaRet, 10, 10);

            g2d.setColor(Color.black);
            g2d.draw(retanguloCorpo);

            String s = c.getDataComentario().
                    format(DateTimeFormatter.ISO_LOCAL_DATE)
                    + "  " + c.getHora().
                            format(DateTimeFormatter.ISO_LOCAL_TIME);
            g2d.drawString(s,
                    marginLeft + 5, 29);

            int yBaseline = 30 + fm.getAscent();
            g2d.drawLine(10, yBaseline, largura, yBaseline);

            for (String linha : linhasComent) {
                System.out.println("Desenhando: " + linha);
                g2d.drawString(linha, marginLeft + 5,
                        yBaseline);
                yBaseline += 4 + fm.getAscent();
            }
            
            g2d.setColor(Color.red);
            g2d.draw(new Rectangle2D.Float(0,0,getWidth(),getHeight()));

        }

        @Override
        public void setPreferredSize(Dimension preferredSize) {
            this.prefSize = preferredSize;

        }

        @Override
        public Dimension getPreferredSize() {
            return prefSize;
        }

    }
}
