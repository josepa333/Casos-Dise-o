/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashMap;

/**
 *
 * @author Angelo PC
 */
public class FormatProvider {
    private IStrategy formatStrategy;
    private HashMap<String,IStrategy> strategies;

    public FormatProvider(){
        strategies.put("txt", new TXTStrategy());
        strategies.put("xml", new XMLStrategy());
        strategies.put("csv", new CSVStrategy());
        strategies.put("json", new JSONStrategy());
    }
    public void setFormatStrategy(String formatStrategy) {
        this.formatStrategy = strategies.get(formatStrategy);
    }
    
        
    public void processText(String text){
        formatStrategy.processText(text);
    }
    
    public String readFile(String file){
        return formatStrategy.readFile(file);
    }
}
