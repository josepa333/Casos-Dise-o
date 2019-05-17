/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas;

import client_server_API.AbstractObservable;
import client_server_API.Connection;
import client_server_API.Message;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.ArrayList;
import subastasViews.SubastaClienteController;
import subastasViews.SubastasController;


/**
 *
 * @author jose pablo
 */
public class OferenteConnection extends Connection{
    
    private SubastasController oferenteController;
    private XStream xstream;
    
    public OferenteConnection(String servername, SubastasController oferenteController ) throws Exception {
        super(servername, 9999);
        this.oferenteController = oferenteController;
        System.out.println( "aaaaaaa = " + clientSocket.getInetAddress() );
        sendMessage(new Message(2,"",""));
    }
    
    @Override
    public void processConnection(Message message){
        xstream = new XStream(new DomDriver());
        TipoMensaje tipo = TipoMensaje.values()[message.getType()];
        System.out.println("la jugada papi tosti es la siguiente: " + message.getContent() + Integer.toString(message.getType()));
        switch (tipo) {
            case CONSULTASUBASTA://Ocupo el mensaje que me diga el id 
                System.out.println("la jugada papi tosti es la siguiente: " + message.getContent());
                System.out.println(message.getContent());
                oferenteController.cargarTabla((ArrayList<ArrayList<String>>) xstream.fromXML(message.getContent()));
                break;
               
            case ACEPTAROFERTA://Ocupo el mensaje que me diga el id 
                for(SubastaClienteController controller: oferenteController.oferente.getSubastas()){
                    if(controller.getIdSubasta() == Integer.parseInt(   ((ArrayList<String>) xstream.fromXML(message.getUser())).get(0) ) ){
                        String feed  = controller.getVista().feedArea.getText();
                        controller.getVista().feedArea.setText(feed + message.getContent() + "\n" );
                        break;
                    }
                }
                
                break;
            case  UNIRSESUBASTA:
                for(SubastaClienteController controller: oferenteController.oferente.getSubastas()){
                    if(controller.getIdSubasta() ==  Integer.parseInt(message.getUser()))   {
                        String feed  = controller.getVista().feedArea.getText();
                        controller.getVista().feedArea.setText(feed + message.getContent() + "\n" );
                        break;
                    }
                }
            default:
                System.out.println("No se reconocio el tipo de mensaje");
                break;
        }
    }
    
    
   
    
}
