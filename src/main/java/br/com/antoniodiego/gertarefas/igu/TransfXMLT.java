/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.antoniodiego.gertarefas.igu;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author Ant?nio Diego
 */
//TODO: Melhorar para grupos
public class TransfXMLT implements Transferable, Serializable {

    /**
     *
     */
    //TODO: 
   // public static DataFlavor XML_G = new DataFlavor(GrupoTarefas.class, "transxml_g");
 //   public static DataFlavor XML_S = new DataFlavor(TransfXMLT.class, "transxml");
//TODO: adicionar talvez campo de tipo
   // private int tipo;
    private String dadTransXML;
    private final DataFlavor sabor;

    /**
     *
     * @param sabor o sabor da transfer?ncia XML.
     */
    public TransfXMLT(DataFlavor sabor) {
        this.sabor = sabor;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{sabor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(sabor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(sabor)) {
            return dadTransXML;
        } else {
            return null;
        }
    }

    public String getDadTransXML() {
        return dadTransXML;
    }

    public void setDadTransXML(String dadTransXML) {
        this.dadTransXML = dadTransXML;
    }

//    public int getTipo() {
//        return tipo;
//    }

//    public void setTipo(int tipo) {
//        this.tipo = tipo;
//    }
}
