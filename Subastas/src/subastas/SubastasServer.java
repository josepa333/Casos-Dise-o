package subastas;

import client_server_API.AbstractObservable;
import client_server_API.Client;
import client_server_API.Message;
import client_server_API.Server;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.ArrayList;


public class SubastasServer extends Server{
    
    //XML
     static XStream xstream;
    
    public SubastasServer(int serverPort) throws Exception{
        super(serverPort);
    }
    
    @Override
    public boolean processConnection(Message message){
        boolean flag = true;
          System.out.println("el tipo es: " + message.getType());
          xstream = new XStream(new DomDriver());
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
                tmpOfertaRechazada.notifyAllObservers(new Message(5,"Se rechazó la oferta de "+message.getUser(),message.getContent()) );
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
              findObservable(Integer.parseInt(message.getContent())).notifyAllObservers(new Message(5,
              "La subasta ha sido cancelada", message.getContent() ));
              findObservable(Integer.parseInt(message.getContent())).getData().set(4, "Cancelada");
                break;
          case SUBASTAFINALIZADA:
              System.out.println("finalizamos la oferta ");
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
    
    
    public static void main(String ... args) throws Exception {
        SubastasServer server = new SubastasServer(9999);
    } 
    
}
