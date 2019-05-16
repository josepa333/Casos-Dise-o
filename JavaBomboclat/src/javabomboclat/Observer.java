/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javabomboclat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angelo PC
 */
public class Observer extends Thread implements IObserver, IMessager{
    private String idObserver;
    private String ipObserver;
    ObjectInputStream input;
    ObjectOutputStream output;
    private Socket connection;
    private boolean running;
    
    public Observer(String id, String ip){
        try {
            this.idObserver = id;
            this.ipObserver = ip;
            connection = new Socket(ipObserver, 6565);
            this.output = new ObjectOutputStream(connection.getOutputStream());
            this.input = new ObjectInputStream(connection.getInputStream());
            // read name
            this.running = true;
            start();
            sendMessage(new Message(MessageType.LOGIN,id,id));
            this.output.flush();
        } catch (IOException ex) {
            Logger.getLogger(Observable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getObserverId() {
        return idObserver;
    }

    public void setObserverId(String id) {
        this.idObserver = id;
    }

    @Override
    public void notifyObserver(Message messagobject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void procesarObservadorAgregado(Message message){//Override on implementation
        System.out.println(message.getContent());
    }
    
    @Override
    public void receiveMessage() {
        Message message;
        try{
        message = (Message) input.readObject();
        switch(message.getType()){
            case AGREGAROBSERVABLE:
                break;
            case AGREGAROBSERVADOR:
                procesarObservadorAgregado(message);
                break;
            case CERRARCONEXION:
               this.running = false;
               break;
            case QUITAROBSERVABLE:
                break;
            case LOGIN:
                if(!message.getContent().equals("OK")){
                    this.running = false;

                }
                break;
            case NOTIFICAROBSERVABLE:
                break;
            case NOTIFICAROBSERVADORES:
                notifyObserver(message);
                break;
            case UNKNOWN:
                break;
            default:
                break;
                } // end of while
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
}
