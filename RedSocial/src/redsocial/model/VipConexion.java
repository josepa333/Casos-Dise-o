package redsocial.model;

import client_server_API.Connection;
import client_server_API.Message;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import redsocial.controller.CelebridadController;
import redsocial.controller.ListaCelebridadesController;

public class VipConexion extends Connection{
    
    public Vip vip;
    public CelebridadController controller;

    // Constructor
    public VipConexion(String pDireccionIP, Vip vip) throws Exception {
        super(pDireccionIP,9999);
        this.vip = vip;
        this.controller = new CelebridadController(vip.getUser(),this);

    }
    
    //Manejo de conexiones
     @Override
     public void processConnection(Message message){
         XStream xml = new XStream(new DomDriver());
        switch (TipoMensaje.valueOf(message.getType())) {
        case SEGUIRVIP:
            vip.addSeguidor(new Seguidor(message.getUser()));
            if(vip.getSeguidores().size() % 10 == 0){
                ArrayList<String> messageData = new ArrayList();
                messageData.add(String.valueOf(vip.getIdObservable()));
                messageData.add(String.valueOf(vip.getSeguidores().size()));
                sendMessage(new Message(7,xml.toXML(messageData),vip.getUser()));
            }
            break;
        case LIKEMENSAJE:
            vip.likeMensaje(Integer.parseInt(message.getContent()), message.getUser());
            if(vip.getMensajes().get(Integer.parseInt(message.getContent())).getLikes() % 10 == 0){
                ArrayList<String> messageData = new ArrayList();
                messageData.add(String.valueOf(vip.getIdObservable()));
                messageData.add(String.valueOf(vip.getMensajes().get(Integer.parseInt(message.getContent())).getLikes()));
                sendMessage(new Message(8,xml.toXML(messageData),vip.getUser()));
            }
            break;
        case DISLIKEMENSAJE:
            vip.dislikeMensaje(Integer.parseInt(message.getContent()), message.getUser());
            break;
        default:
            System.out.println("No se reconocio el tipo de mensaje");
            break;
        }
    }
}