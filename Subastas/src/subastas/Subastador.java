/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import subastasViews.SubastaSubastadorController;

/**
 *
 * @author jose pablo
 */
public class Subastador extends IServidor{ //TODO Hacerlo observer 
    
    private String idSubastador;
    private Subasta subasta;
    private SubastaSubastadorController controller;
    
    //Servidor
     
     
    static XStream xstream;
    static String xml;
    
    public Subastador(String idSubastador,String fechaInicial,String nombreProducto,
            String precioInicial, SubastaSubastadorController controller) { //Crear hilo para la interfaz
        this.idSubastador = idSubastador;
        this.controller = controller;
        this.subasta = new Subasta(fechaInicial, new Producto(nombreProducto, precioInicial));
        xstream = new XStream(new DomDriver());
        
        //whileTrue(); //Todo hilo server 
    }
    
    public String getIdSubastador() {
        return idSubastador;
    }

    public void setIdSubastador(String idSubastador) {
        this.idSubastador = idSubastador;
    }

    public Subasta getSubasta() {
        return subasta;
    }

    public void setSubasta(Subasta subasta) {
        this.subasta = subasta;
    }

    @Override
    public void procesarConexion() throws IOException, ClassNotFoundException{
        Mensaje mensaje = leerMensaje();
        
        if(null == mensaje.getTipo()) {
            System.out.println("No se reconocio el tipo de mensaje");
        }
        else switch (mensaje.getTipo()) {
            case OFERTA:
                enviarMensaje( procesarOferta(mensaje));
                break;
            default:
                System.out.println("No se reconocio el tipo de mensaje");
                break;
        }
    }
    
    private Mensaje procesarOferta(Mensaje mensaje){
        ArrayList<String> data = (ArrayList<String>) (xstream.fromXML(mensaje.getCuerpo()));
        Mensaje mensajeResultado;
        int resultado = JOptionPane.showConfirmDialog((Component) null,
                "El usuario " + mensaje.getUsuario() + "ha ofrecido " + data.get(0) + ", desea aceptar la oferta?",
                "alert", JOptionPane.OK_CANCEL_OPTION);
        if(resultado == 0){
            mensajeResultado = new Mensaje(TipoMensaje.RECHAZAROFERTA, idSubastador,data.get(0));
        }
        else{
            mensajeResultado = new Mensaje(TipoMensaje.ACEPTAROFERTA, idSubastador,data.get(0));
        }
        return mensajeResultado;
    }

}
