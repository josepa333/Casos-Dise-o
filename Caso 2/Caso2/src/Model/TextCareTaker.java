/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author jose pablo
 */
public class TextCareTaker {
    private int currentIndex = -1;
    private final ArrayList<TextMemento> states = new ArrayList();
    

    public TextCareTaker() {
    }
    
    public void addNewMemento(TextMemento memento){
        if(states.size() < 20){
            states.add(memento);
        }
        else{
            states.remove(0);
            states.add(memento);
        }
        currentIndex++;
    }
    
    public TextMemento getCurrentMemento() {           
        return states.get(currentIndex);       
    }
    
    public TextMemento getNextMemento() {           
        int newIndex = currentIndex +1;           
        if( newIndex >= states.size()){               
            return null;           
        }
        
        currentIndex = newIndex;           
        return states.get(currentIndex);       
    }     
    
    public TextMemento getPreviousMemento() {           
        int newIndex = currentIndex-1;                      
        
        if(newIndex  <= -1 || newIndex >= states.size()-1){               
            return null;           
        }
        currentIndex= newIndex;           
        
        return states.get(currentIndex);       
    }
    
    public void undoWithoutRedo(){
        for (int i = currentIndex + 1; i < states.size(); i++) {
            states.remove(i);
        }
    }
    
    public int getTotalMementos(){
        return states.size();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
    
}
