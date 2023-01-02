/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.antoniodiego.gertarefas.telas.vercomentarios;

import br.com.antoniodiego.gertarefas.pojo.Comentario;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.time.format.DateTimeFormatter;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

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

        System.out.println("Desenhando");

        System.out.println("Tam: " + cp.getWidth() + " " + cp.getHeight());

        cp.setSize(200, 200);

        return cp;
    }

    private class CardComentario extends Component {

        int index;
        Comentario c;

        public CardComentario(int index, Comentario c) {
            this.index = index;
            this.c = c;
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.black);

            int x = index * 20;

            RoundRectangle2D retanguloCabecalho = new RoundRectangle2D.Float(0,
                    10, getWidth(),
                    20, 10, 10);

            g2d.draw(retanguloCabecalho);
            g2d.setColor(Color.lightGray);
            g2d.fill(retanguloCabecalho);

            RoundRectangle2D retanguloCorpo = new RoundRectangle2D.Float(0,
                    10, getWidth(),
                    getHeight() - 20, 10, 10);

            g2d.setColor(Color.black);
            g2d.draw(retanguloCorpo);

            String s = c.getData().format(DateTimeFormatter.ISO_LOCAL_DATE)
                    + c.getHora().format(DateTimeFormatter.ISO_LOCAL_TIME);
            g2d.drawString(s,
                    5, 29);

            g2d.drawString(c.getComentario(), 5, getHeight() - 25);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 70);
        }

    }
}
