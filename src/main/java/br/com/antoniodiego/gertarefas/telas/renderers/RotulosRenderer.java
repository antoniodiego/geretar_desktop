package br.com.antoniodiego.gertarefas.telas.renderers;

import java.awt.Color;
import java.awt.Component;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.collection.internal.PersistentBag;

import br.com.antoniodiego.gertarefas.Constantes;
import br.com.antoniodiego.gertarefas.pojo.Rotulo;

public class RotulosRenderer implements TableCellRenderer {

    private final DateTimeFormatter fd;// SimpleDateFormat fd;
    public static final Logger LOG_CONTR_PRINC = LogManager.getLogger("Controller_Principal");
    private List<Rotulo> rotulos;

    public RotulosRenderer() {
        fd = DateTimeFormatter.ofPattern("dd/MM/yyyy", Constantes.LOCAL_BR);// SimpleDateFormat("dd/MM/yyyy");
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {

        LOG_CONTR_PRINC.debug("Alt val de des rotulo"
                + " para {}", value);

        PersistentBag d = (PersistentBag) value;

        JPanel p = new JPanel();

        rotulos = new ArrayList();

        LOG_CONTR_PRINC.debug("Size: {}", d.size());

        if (d.size() > 0) {

            Iterator i = d.iterator();

            while (i.hasNext()) {
                Rotulo r = (Rotulo) i.next();
                LOG_CONTR_PRINC.debug("Rót {}", r.getNome());
                JLabel label = new JLabel(r.getNome());
                label.setOpaque(true);
                label.setBackground(Color.decode(r.getCor()));
              //  label.setSize(200, 200);
                p.add(label);
            }

        }

        return p;
    }

}
