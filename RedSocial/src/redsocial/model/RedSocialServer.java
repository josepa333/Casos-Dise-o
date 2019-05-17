/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.model;
import client_server_API.AbstractObservable;
import client_server_API.Client;
import client_server_API.Message;
import client_server_API.Server;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 *
 * @author Angelo PC
 */
public class RedSocialServer extends Server {
    
    public RedSocialServer(int ip) throws Exception{
        super(ip);
        
    }
    
    @Override
     public boolean processConnection(Message message){
         boolean flag = true;
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
         return true;
     }
    
}
