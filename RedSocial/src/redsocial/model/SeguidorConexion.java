package redsocial.model;

import client_server_API.AbstractObservable;
import client_server_API.Connection;
import client_server_API.Message;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import redsocial.controller.ListaCelebridadesController;

public class SeguidorConexion extends Connection{
    public ListaCelebridadesController controller;
    
    // Constructor
    /**
     * @param pDireccionIP  donde se establece la conexion
     * @param pPuerto el puerto donde se establace la conexion
     * @throws java.lang.Exception
     */
    public SeguidorConexion(String pDireccionIP, String nombre) throws Exception {
        super(pDireccionIP,9999);
        this.controller = new ListaCelebridadesController(nombre,this);
    }

    public ListaCelebridadesController getController() {
        return controller;
    }

    public void setController(ListaCelebridadesController controller) {
        this.controller = controller;
    }
    
    //Manejo de conexiones
     @Override
     public void processConnection(Message message){
         XStream xml = new XStream(new DomDriver());
        switch (TipoMensaje.valueOf(message.getType())) {
        case POSTEARMENSAJE:
            System.out.println("Confirmacion:\n" + message.getContent());
            controller.vista.textAreaActualizaciones.setText(controller.vista.textAreaActualizaciones.getText() + message.getContent() + "\n");
            break;
     
        case OBTENERMENSAJES://Recibe el identificador del observable
                System.out.println("SOY UN SEGUIDOR Y RECIBO LOS MENSAJES WIPIPIA, la puta madre");
                ArrayList<String> returnMensajes = (ArrayList<String>)xml.fromXML(message.getContent());
                controller.tablaMensajes.ver_tabla(controller.vista.TablaMensajes, returnMensajes);

                break;
            case OBTENERVIPS://No requiere nada
                ArrayList<ArrayList<String>> returnVips = (ArrayList<ArrayList<String>>)xml.fromXML(message.getContent());
                controller.tablaCelebridades.ver_tabla(controller.vista.TablaCelebridades, returnVips);
                break;
        default:
            System.out.println("No se reconocio el tipo de mensaje");
            break;
        }
    }
}