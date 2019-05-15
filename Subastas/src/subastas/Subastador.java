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
import java.util.Arrays;
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
     private Conexion miConexion; 
     private String ipServerServer;
     private int puerto;
     private String ipServerCliente;
     
    static XStream xstream;
    static String xml;
    
    public Subastador(String idSubastador,String fechaInicial,String nombreProducto,
            String precioInicial, SubastaSubastadorController controller) { //Crear hilo para la interfaz
        ipServerServer = "172.19.51.112";
        puerto = 6565;
        ipServerCliente = "192.168.0.1";
        this.idSubastador = idSubastador;
        this.controller = controller;
        miConexion = miConexion = new Conexion(ipServerServer, puerto); //TODO definir server
        this.subasta = new Subasta(fechaInicial, new Producto(nombreProducto, precioInicial));
        xstream = new XStream(new DomDriver());
        crearSubasta();
        
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
    
    private void crearSubasta(){
        Mensaje miMensaje = new Mensaje(TipoMensaje.INICIARSUBASTA, this.idSubastador,
        xstream.toXML(new ArrayList<>(Arrays.asList(ipServerCliente, idSubastador))));
        try {
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void procesarConexion() throws IOException, ClassNotFoundException{
        Mensaje mensaje = leerMensaje();
        
        if(null == mensaje.getTipo()) {
            System.out.println("No se reconocio el tipo de mensaje");
        }
        else switch (mensaje.getTipo()) { 
            case OFERTA:
                procesarOferta(mensaje);  //TODO responder a todos
                break;
            case CONSULTASUBASTA:
                enviarMensaje(informacionSubasta());
                break;
            default:
                System.out.println("No se reconocio el tipo de mensaje");
                break;
        }
    }
    
    private Mensaje informacionSubasta(){
        return new Mensaje(TipoMensaje.CONSULTASUBASTA, idSubastador,xstream.toXML(this)); 
    }
    
    private Mensaje procesarOferta(Mensaje mensaje){
        ArrayList<String> data = (ArrayList<String>) (xstream.fromXML(mensaje.getCuerpo()));
        Mensaje mensajeResultado;
        int resultado = JOptionPane.showConfirmDialog((Component) null,
                "El usuario " + mensaje.getUsuario() + "ha ofrecido " + data.get(0) + ", desea aceptar la oferta?",
                "alert", JOptionPane.OK_CANCEL_OPTION);
        if(resultado == 0){
            mensajeResultado = new Mensaje(TipoMensaje.RECHAZAROFERTA, idSubastador,data.get(0));//TODO deberia
            //responder
        }
        else{
            mensajeResultado = new Mensaje(TipoMensaje.ACEPTAROFERTA, idSubastador,data.get(0));
            subasta.getProducto().setPrecioFinal(Double.parseDouble(data.get(0))); //Avisar al resto
        }
        return mensajeResultado;
    }
    
    public void finalizarSubasta(){
        Mensaje miMensaje = new Mensaje(TipoMensaje.SUBASTAFINALIZADA, this.idSubastador,subasta.addToFeed("Subasta finalizada"));
        try {
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void cancelarSubasta(){
        Mensaje miMensaje = new Mensaje(TipoMensaje.SUBASTACANCELADA, this.idSubastador,subasta.addToFeed("Subasta cancelada"));
        try {
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
            System.out.println(e);
        }
        
    }

}
