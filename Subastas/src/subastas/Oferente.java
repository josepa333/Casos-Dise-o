/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jose pablo
 */
public class Oferente extends IServidor{ //Tener un arreglo de ControlerSubasta, que tiene al modelo y a la vista
    //recorro la madre buscando la que calze con ese id y le mando la vara.
    
    
    private String idOferente;
    private Conexion miConexion; 

    static XStream xstream;
    static String xml;
    
    public Oferente(String idOferente) {
        this.idOferente = idOferente;
        miConexion = miConexion = new Conexion("172.19.51.112", 6565); //TODO definir server
    }

    public String getIdOferente() {
        return idOferente;
    }

    public void setIdOferente(String idOferente) {
        this.idOferente = idOferente;
    }

    @Override
    public void procesarConexion() throws IOException, ClassNotFoundException{
        Mensaje mensaje = leerMensaje();
        
        if(null == mensaje.getTipo()) {
            System.out.println("No se reconocio el tipo de mensaje");
        }
        else switch (mensaje.getTipo()) {
            case ACEPTAROFERTA:
                ofertaAceptada(mensaje);
                break;
            
            default:
                System.out.println("No se reconocio el tipo de mensaje");
                break;
        }
    }
    
    private void ofertaAceptada(Mensaje mensaje){
        ///TODO
    }
    
    private ArrayList<Subastador> cargarSubastadores(){
        Mensaje miMensaje = new Mensaje(TipoMensaje.CONSULTASUBASTA, idOferente ,"");
        Mensaje resultado = null;
        try {
            miConexion.abrirConexion();
            miConexion.obtenerFlujos();
            miConexion.enviarMensaje(miMensaje);
            resultado = miConexion.recibirMensaje();
            miConexion.cerrarConexion();
        }
        catch (IOException e) {
            System.out.println(e);
        }
        
        return obtenerSubastadoresDeXml(resultado);
    }
    
    private ArrayList<Subastador> obtenerSubastadoresDeXml(Mensaje datos){
        ArrayList<String> subastadoresXML = (ArrayList<String>) xstream.fromXML(datos.getCuerpo());
        ArrayList<Subastador> resultado = new ArrayList();
        for (int i = 0; i < subastadoresXML.size(); i++) {
            resultado.add((Subastador) xstream.fromXML(subastadoresXML.get(i)) );
        }
        return resultado;
    }
    
}
