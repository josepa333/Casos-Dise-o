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
    private ArrayList<String> data;
    
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
    
    public void notifyFirts(Message message){
        observers.get(0).notifyObserver(message);
    }

    public int getIdObservable() {
        return idObservable;
    }

    public void setIdObservable(int idObservable) {
        this.idObservable = idObservable;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
    


    
}
