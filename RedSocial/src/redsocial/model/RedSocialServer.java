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
import java.util.ArrayList;
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
        XStream xstream = new XStream(new DomDriver());
        ArrayList<String> data;
        AbstractObservable tmpVip;
        Vip vip;

        switch (TipoMensaje.valueOf(message.getType())) {
            case SEGUIRVIP: //Recibe el identificador del observable y su nombre como datos y el usuario seguidor como getUser
                data = (ArrayList<String>) xstream.fromXML(message.getContent());
                addObserver(findObservable(Integer.parseInt(data.get(0))), new Client(this.output, this.clientInetAddress));
                findObservable(Integer.parseInt(data.get(0))).notifyFirts(new Message(1, "", message.getUser()));
                findObservable(Integer.parseInt(data.get(0))).notifyAllObservers(new Message(6,
                "El usuario: " + message.getUser() + " ha seguido al VIP " + data.get(1), data.get(1)));
                break;
            case CREARVIP://Recibe un arreglo de datos como contenido.
                Vip tmpArrayList = (Vip) (xstream.fromXML(message.getContent()));
                AbstractObservable observable = (AbstractObservable) tmpArrayList;
                addObservable(observable);
                break;
            case LIKEMENSAJE://Recibe el identificador del observable y el identificador del mensaje y el usuario seguidor como getUser
                data =(ArrayList<String>) xstream.fromXML(message.getContent());
                tmpVip = findObservable(Integer.parseInt(data.get(0)));
                vip = (Vip)tmpVip;
                for(MensajeVip mensaje : vip.getMensajes())
                    {
                        if(mensaje.getIdMensaje() == Integer.parseInt(data.get(1))){
                            System.out.println("Message liked");
                            mensaje.agregarLike(message.getUser());
                            tmpVip.notifyFirts(new Message(3,data.get(1),message.getUser()));
                            return true;
                        }
                    }
                break;
            case DISLIKEMENSAJE://Recibe el identificador del observable y el identificador del mensaje y el usuario seguidor como getUser
                data =(ArrayList<String>) xstream.fromXML(message.getContent());
                tmpVip = findObservable(Integer.parseInt(data.get(0)));
                vip = (Vip)tmpVip;
                for(MensajeVip mensaje : vip.getMensajes())
                    {
                        if(mensaje.getIdMensaje() == Integer.parseInt(data.get(1))){
                            System.out.println("Message disliked");
                            mensaje.agregarDislike(message.getUser());
                            tmpVip.notifyFirts(new Message(4,data.get(1),message.getUser()));
                            return true;
                        }
                    }
            case DARSEBAJA://Recibe el identificador del observable y el nombre del observable en getuser
                findObservable(Integer.parseInt(message.getContent())).notifyAllObservers(new Message(6,
                "El artista: " + message.getUser() + " ha cerrado su cuenta", message.getUser() ));
                observables.remove(findObservable(Integer.parseInt(message.getContent())));
                break; 
            case POSTEARMENSAJE://Recibe el id del observable y el contenido del mensaje y en getuser el nombre del observable
                data =(ArrayList<String>) xstream.fromXML(message.getContent());
                tmpVip = findObservable(Integer.parseInt(data.get(0)));
                vip = (Vip)tmpVip;
                vip.addMensaje(message.getContent());
                tmpVip.notifyAllObservers(new Message(6,data.get(1),message.getUser()));
                break;
            case NOTIFICACIONNIVEL://Recibe el contenido del mensaje y en getuser el nombre del observable
                data =(ArrayList<String>) xstream.fromXML(message.getContent());
                tmpVip = findObservable(Integer.parseInt(data.get(0)));
                tmpVip.notifyAllObservers(new Message(6,"El usuario " + message.getUser() + " ha subido al nivel " + data.get(1),message.getUser()));
                break;
            case NOTIFICACIONMENSAJE://Recibe el contenido del mensaje y en getuser el nombre del observable
                data =(ArrayList<String>) xstream.fromXML(message.getContent());
                tmpVip = findObservable(Integer.parseInt(data.get(0)));
                tmpVip.notifyAllObservers(new Message(6,"Un mensaje del usuario " + message.getUser() + " ha obtenido " + data.get(1) + " likes",message.getUser()));
                break;
            case OBTENERMENSAJES://Recibe el identificador del observable
                tmpVip = findObservable(Integer.parseInt(message.getContent()));
                vip = (Vip)tmpVip;
                ArrayList<String> returnMensajes = new ArrayList();
                vip.getMensajes().forEach((mensaje) -> {
                    returnMensajes.add(mensaje.toString());});

                AbstractObservable tmpObservable =  new AbstractObservable();
                addObservable(tmpObservable);
                tmpObservable.notifyAllObservers(new Message(9, xstream.toXML( returnMensajes ) , ""));
                observables.remove(observables.size() - 1);
                break;
            case OBTENERVIPS://No requiere nada
                tmpObservable =  new AbstractObservable();
                addObservable(tmpObservable);
                ArrayList<ArrayList<String>> resultado = new ArrayList();
                for (int i = 0; i < observables.size() - 1 ; i++) {
                    data = ((Vip)observables.get(i)).getData();
                    data.add(String.valueOf(i));
                    resultado.add(data);
                }
                tmpObservable.notifyAllObservers(new Message(10, xstream.toXML( resultado ) , ""));
                observables.remove(observables.size() - 1);
                break;
            default:
                System.out.println("No se reconocio el tipo de mensaje");
                break;
        }
         return true;
     }
        public static void main(String ... args) throws Exception {
        RedSocialServer server = new RedSocialServer(9999);
    } 
}
