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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo Bonilla
 */
public class Observable extends Thread implements IObservable, IMessager{

    private final ArrayList<IObserver> observers = new ArrayList<>();
    private String idObservable;
    private String ipObservable;
    ObjectInputStream input;
    ObjectOutputStream output;
    private Socket connection;
    private boolean running;

    
    public Observable(String idObservable, String ipObservable) {
        try {
            this.idObservable = idObservable;
            this.ipObservable = ipObservable;
            connection = new Socket(ipObservable, 6565);
            this.output = new ObjectOutputStream(connection.getOutputStream());
            this.input = new ObjectInputStream(connection.getInputStream());
            // read name
            this.running = true;
            start();
            sendMessage(new Message(MessageType.LOGIN,idObservable,idObservable));
            this.output.flush();
        } catch (IOException ex) {
            Logger.getLogger(Observable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }
    
    @Override       
    public void addObserver(IObserver observer) {           
        this.observers.add(observer);
    }                 
    
    @Override       
    public void removeObserver(IObserver observer) {           
        this.observers.remove(observer);
    }          
    
    @Override       
    public void notifyAllObservers(Message message) {           
        for (IObserver observer : observers) {        
            observer.notifyObserver(message);
        }       
    }

    public String getIdObservable() {
        return idObservable;
    }
    
    public void procesarSolictudObservador(Message message){
        System.out.println(message.getContent());
    }
    
    @Override
    public void sendMessage(Message message){
        try{
        output.writeObject(message);
        output.flush();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void receiveMessage(){
        Message message;
        try{
        message = (Message) input.readObject();
        switch(message.getType()){
            case AGREGAROBSERVABLE:
                break;
            case AGREGAROBSERVADOR:
                this.observers.add(new Observer(message.getContent(),this.ipObservable));
                break;
            case CERRARCONEXION:
               this.running = false;
               break;
            case QUITAROBSERVABLE:
                notifyAllObservers(message);    
                break;
            case LOGIN:
                if(!message.getContent().equals("OK")){
                    this.running = false;

                }
                break;
            case NOTIFICAROBSERVABLE:
                procesarSolictudObservador(message);
                break;
            case NOTIFICAROBSERVADORES:
                notifyAllObservers(message);
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
    
    @Override
    public void run()  {
         try    {
            while(running)   {
             receiveMessage();
             }
           connection.close();
         } // try
         catch(Exception ex) {
           System.out.println(ex.getMessage());
         }
    } // end of run()
    
   } 