/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserverapi;

import java.util.ArrayList;

/**
 *
 * @author Ricardo Bonilla
 */
public abstract class AbstractObservable implements IObservable{

    private final ArrayList<IObserver> observers = new ArrayList<>();
    private int idObservable;
    
    public AbstractObservable(int idObservable) {
        this.idObservable = idObservable;
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
    public void notifyAllObservers(Message message, Object source) {           
        for (IObserver observer : observers) {        
            observer.notifyObserver(message, source);
        }       
    }

    public int getIdObservable() {
        return idObservable;
    }
    
}
