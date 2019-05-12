/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.IOException;

/**
 *
 * @author jose pablo
 */
public class Subastador extends IServidor{
    
    private String idSubastador;
    private Subasta subasta;
    
    //Servidor
     
     
    static XStream xstream;
    static String xml;
    
    private Subastador(String idSubastador, int puerto) { //Crear hilo para la interfaz
        this.idSubastador = idSubastador;
        xstream = new XStream(new DomDriver());
        whileTrue();
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
                enviarMensaje( mensaje);
                break;
            default:
                System.out.println("No se reconocio el tipo de mensaje");
                break;
        }
    }

}
