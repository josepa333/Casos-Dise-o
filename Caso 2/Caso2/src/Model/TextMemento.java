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
public class TextMemento {
    public Text text;
    public TextMemento(Text text){
        this.text = text;
    }

    public Text getMemento() {
        return text;
    }
    
}
