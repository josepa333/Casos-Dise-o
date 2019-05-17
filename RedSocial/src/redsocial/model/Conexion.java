package redsocial.model;

import client_server_API.Connection;
import client_server_API.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Conexion extends Connection{
    
    // Constructor
    /**
     * @param pDireccionIP  donde se establece la conexion
     * @param pPuerto el puerto donde se establace la conexion
     * @throws java.lang.Exception
     */
    public Conexion(String pDireccionIP, int pPuerto) throws Exception {
        super(pDireccionIP,pPuerto);
    }
    
    //Manejo de conexiones
     @Override
     public void processConnection(Message message){
        switch (TipoMensaje.valueOf(message.getType())) {
        case SEGUIRVIP:
            System.out.println("Confirmacion:\n" + message.getContent());
            break;
        case DEJARSEGUIRVIP:
            System.out.println("Confirmacion:\n" + message.getContent());
            break;
        case LIKEMENSAJE:
            System.out.println("Confirmacion:\n" + message.getContent());
            break;
        case DISLIKEMENSAJE:
            System.out.println("Confirmacion:\n" + message.getContent());
            break;
        case DARSEBAJA:
            System.out.println("Confirmacion:\n" + message.getContent());
            break;
        case POSTEARMENSAJE:
            System.out.println("Confirmacion:\n" + message.getContent());
            break;
        case NOTIFICACIONNIVEL:
            System.out.println("Confirmacion:\n" + message.getContent());
            break;
        case NOTIFICACIONMENSAJE:
            System.out.println("Confirmacion:\n" + message.getContent());
            break;
        default:
            System.out.println("No se reconocio el tipo de mensaje");
            break;
        }
    }
}