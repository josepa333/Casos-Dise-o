/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas;

import java.io.IOException;

/**
 *
 * @author jose pablo
 */
public class Oferente extends IServidor{ //Tener un arreglo de ControlerSubasta, que tiene al modelo y a la vista
    //recorro la madre buscando la que calze con ese id y le mando la vara.
    
    
    private String idOferente;

    public Oferente(String idOferente) {
        this.idOferente = idOferente;
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
                enviarMensaje(mensaje);
                break;
            default:
                System.out.println("No se reconocio el tipo de mensaje");
                break;
        }
    }
    
}
