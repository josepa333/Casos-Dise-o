/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.PrintWriter;

/**
 *
 * @author Angelo PC
 */
public class TXTStrategy implements IStrategy{

    @Override
    public String readFile(String file) {
        return file;
    }

    @Override
    public void processText(String text, String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(text);
        }
        catch(Exception e){
        }
    }
}
