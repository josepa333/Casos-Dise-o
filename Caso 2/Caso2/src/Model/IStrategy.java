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
public interface IStrategy {
    
    public void processText(String text);
    public String readFile(String file);
    
    
    
}
