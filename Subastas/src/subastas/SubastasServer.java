package subastas;

import client_server_API.AbstractObservable;
import client_server_API.Client;
import client_server_API.Message;
import client_server_API.Server;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SubastasServer extends Server{
    
    //XML
    static XStream xstream;
    
    public SubastasServer(int serverPort) throws Exception {
        super(serverPort);
    }
    
    @Override
    public void process() throws Exception  {
        System.out.println("\n\n++++++++++++++++++++++++++++++++++++");
        System.out.println("Esperando una Conexión... Proccess Override");
        while( true) {
           this.connection = this.serverSocket.accept();
           Handler c = new Handler(this.connection);
           System.out.println("Conectado a :" + connection.getInetAddress().getHostName());
        }
    }
    
    public class Handler extends Server.HandleClient{
        public Handler(Socket client) throws Exception{
            super(client);
        }

        @Override
        public boolean processConnection(Message message){
            boolean flag = true;
              System.out.println("el tipo es: " + message.getType());
              xstream = new XStream(new DomDriver());
            System.out.println("Proccess Override");
            TipoMensaje tipo = TipoMensaje.values()[message.getType()];
            switch (tipo) {
            case INICIARSUBASTA: //nuevaSubasta
                ArrayList<String> tmpArrayList = (ArrayList<String>) (xstream.fromXML(message.getContent()));
                AbstractObservable observable = new AbstractObservable();
                observable.setData(tmpArrayList);
                addObservable(observable);
                observable.notifyAllObservers(new Message(1,Integer.toString( observable.getIdObservable() ), ""));
                observable.getData().set(0, Integer.toString(observable.getIdObservable()) );
                break;
            case CONSULTASUBASTA:
                AbstractObservable tmpObservable =  new AbstractObservable();
                addObservable(tmpObservable);
                ArrayList<ArrayList<String>> resultado = new ArrayList();
                for (int i = 0; i < observables.size() - 1 ; i++) {
                    resultado.add(observables.get(i).getData());
                }
                tmpObservable.notifyAllObservers(new Message(2, xstream.toXML( resultado ) , ""));
                observables.remove(observables.size() - 1);
                break;
            case OFERTA:
                ArrayList<String> data =(ArrayList<String>) xstream.fromXML(message.getContent());
                AbstractObservable tmpOferta = findObservable(Integer.parseInt(data.get(0)));
                tmpOferta.notifyFirts( new Message(3,message.getContent(),message.getUser()));
                break;
           case RECHAZAROFERTA:
                AbstractObservable tmpOfertaRechazada = findObservable( Integer.parseInt(message.getContent() ));
                tmpOfertaRechazada.notifyAllObservers(new Message(4,"Se rechazó la oferta de "+message.getUser(),message.getContent()) );
                break;
          case ACEPTAROFERTA:
                ArrayList<String> dataAceptar = (ArrayList<String>) xstream.fromXML(message.getContent());
                AbstractObservable tmpOfertaAceptada = findObservable( Integer.parseInt(dataAceptar.get(0)));
                tmpOfertaAceptada.getData().set(3, dataAceptar.get(1));
                tmpOfertaAceptada.notifyAllObservers(new Message(5,"Se aceptó la oferta de "+dataAceptar.get(1),message.getContent()) );
                tmpOfertaAceptada.getData().add( message.getUser() );
                break;
          case UNIRSESUBASTA:
              addObserver(findObservable(Integer.parseInt(message.getContent())), new Client(this.output, this.clientInetAddress));
              findObservable(Integer.parseInt(message.getContent())).notifyAllObservers(new Message(6,
              "Se ha unido a la subasta: " + message.getUser(), message.getContent() ));
                break;
         case SUBASTACANCELADA:
             System.out.println("Cancelamos la oferta");
              ArrayList<String> datosCancelada = new ArrayList();
              datosCancelada.add(message.getContent());
              findObservable(Integer.parseInt(message.getContent())).notifyAllObservers(new Message(5,
              "La subasta ha sido cancelada", xstream.toXML(datosCancelada) ));
              findObservable(Integer.parseInt(message.getContent())).getData().set(4, "Cancelada");
                break;
          case SUBASTAFINALIZADA:
              System.out.println("finalizamos la oferta ");
              ArrayList<String> datosFinalizada = new ArrayList();
              datosFinalizada.add(message.getContent());
              AbstractObservable tmpObservableFinalizar = findObservable(Integer.parseInt(message.getContent()));
              tmpObservableFinalizar.notifyAllObservers(new Message(5,
              "La subasta ha finalizado con un monto de: " + message.getUser()+"\n, " + 
                      "felicidades al ganador: " + tmpObservableFinalizar.getData().get(5) , message.getContent() ));
              tmpObservableFinalizar.getData().set(4, "Finalizada");
                break;
            default:
                System.out.println("No se reconocio el tipo de mensaje");
                break;
            }
            return flag;
        }
    
    }
    
    
    public static void main(String ... args) throws Exception {
        SubastasServer server = new SubastasServer(9999);
    } 
    
}
