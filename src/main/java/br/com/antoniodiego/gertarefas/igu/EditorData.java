package br.com.antoniodiego.gertarefas.igu;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellEditor;

public class EditorData extends AbstractCellEditor implements TableCellEditor {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JFormattedTextField jtf;
    private final boolean tempo;

    public EditorData(boolean tempo) {
        this.tempo = tempo;
        constroi();
    }

    private void constroi() {
        jtf = new JFormattedTextField(new FormatadorJTime(tempo));

//        DateFormat df = tempo ? DateFormat.getTimeInstance(DateFormat.SHORT, new Locale("pt", "BR")) : Constantes.FORMATADOR_BR;//DateFormat.getDateInstance(DateFormat.SHORT, Constantes.FORMATADOR_BR);//getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new Locale("pt", "BR"));
//        DateFormatter dft = new DateFormatter(df);
//
//        DefaultFormatterFactory dff = new DefaultFormatterFactory();
//        dff.setEditFormatter(dft);
//
//        //    jtf.setFormatterFactory(dff);
        jtf.setColumns(10);
        jtf.setFocusLostBehavior(JFormattedTextField.PERSIST);
        jtf.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "checa");
        jtf.getActionMap().put("checa", new AcaoChecar());
    }

    @Override
    public Object getCellEditorValue() {
        return jtf.getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        jtf.setValue(value);
        return jtf;
    }

    @Override
    public boolean stopCellEditing() {
        //     JFormattedTextField ftf = (JFormattedTextField) getComponent();
        String texto = jtf.getText();
        if (jtf.isEditValid()) {
            if (texto == null || texto.length() == 0) {
                jtf.setValue(null);
            } else {
                System.out.println("valid");
                try {
                    jtf.commitEdit();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else if (texto != null && texto.length() > 0) {
            return false;
        }

        return super.stopCellEditing();
    }

    private class AcaoChecar extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (!jtf.isEditValid()) {
                if (jtf.getText() == null || jtf.getText().length() == 0) {
                    try {
                        jtf.commitEdit();
                        
                    } catch (ParseException e) {
                        System.out.println("Erro ler data");
                    }
                    jtf.setValue(null);
                } else {
                    // System.out.println("Inv\u00e1lido");
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(jtf, tempo ? "A hora deve estar no formato HH:MM [AM|PM]" : "A data deve estar no formato dd/mm/aaaa", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
                jtf.postActionEvent();
            } else {
                System.out.println("pos ent");
                try {
                    jtf.commitEdit();
                    jtf.postActionEvent();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                stopCellEditing();
            }
        }
    }
}
