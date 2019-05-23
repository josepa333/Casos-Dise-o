/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author jose pablo
 */
public class Text implements Cloneable{
    private String state;
    private String displayState;
    
    public Text(String text){
        this.state = text;
    }
    
    public TextMemento createMemento(){
        try{
            return new TextMemento((Text) this.clone());
        }
        catch(CloneNotSupportedException e){
            throw new RuntimeException("Failed to create the text");
        }
    }
    
    public void restoreMemento(TextMemento memento){
        Text text = memento.getMemento();
        this.state = text.getState();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
