/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.model;

import clientserverapi.Client;
import java.io.IOException;

/**
 *
 * @author Angelo PC
 */
public class Seguidor extends Client{
    private String idSeguidor;

    public Seguidor(String idSeguidor) {
        this.idSeguidor = idSeguidor;
    }

    public String getIdSeguidor() {
        return idSeguidor;
    }

    public void setIdSeguidor(String idSeguidor) {
        this.idSeguidor = idSeguidor;
    }

    @Override
    public void procesarConexion() throws IOException, ClassNotFoundException{
        Mensaje mensaje = leerMensaje();
        
        if(null == mensaje.getTipo()) {
            System.out.println("No se reconocio el tipo de mensaje");
        }
        else switch (mensaje.getTipo()) {
            case LIKEMENSAJE:
                enviarMensaje( mensaje);
                break;
            case DISLIKEMENSAJE:
                break;
            case SEGUIRVIP:
                break;
            case DEJARSEGUIRVIP:
                break;
            default:
                System.out.println("No se reconocio el tipo de mensaje");
                break;
        }
    }
}
