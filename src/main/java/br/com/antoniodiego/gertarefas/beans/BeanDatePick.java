/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.beans;

import br.com.antoniodiego.gertarefas.igu.modelos.FormatDatePick;
import br.com.antoniodiego.gertarefas.igu.modelos.ModeloData;
import java.util.Properties;
import javax.swing.JFormattedTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class BeanDatePick extends JDatePickerImpl {

   // private JDatePanelImpl painelDataAl;

    private ModeloData modelo;
    
    public BeanDatePick() {
        this(new JDatePanelImpl(new ModeloData(), new Properties()), new FormatDatePick());
        this.modelo = (ModeloData) this.getModel();
    }

    public BeanDatePick(JDatePanelImpl datePanel, JFormattedTextField.AbstractFormatter formatter) {
        super(datePanel, formatter);
    }

    public ModeloData getModeloDef() {
        return modelo;
    } 
}
