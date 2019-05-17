/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_server_API;

import java.util.ArrayList;

/**
 *
 * @author Ricardo Bonilla
 */
public class AbstractObservable implements IObservable{

    private final ArrayList<IObserver> observers = new ArrayList<>();
    private int idObservable;
    
    public AbstractObservable() {
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

    public int getIdObservable() {
        return idObservable;
    }

    public void setIdObservable(int idObservable) {
        this.idObservable = idObservable;
    }
}
