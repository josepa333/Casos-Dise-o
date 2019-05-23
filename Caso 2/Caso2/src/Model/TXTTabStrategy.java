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
public class TXTTabStrategy implements IStrategy{

    @Override
    public String readFile(String file) {
        return file.replace("\t", "");
    }

    @Override
    public void processText(String text, String fileName) {
        String buildString = "";
        try (PrintWriter out = new PrintWriter(fileName)) {
            for (int i = 0; i < text.length(); i++) {
                buildString += text.charAt(i);
                if((i+1) % 10 == 0){
                    buildString += "\t";
                }
            }
            out.println(buildString);
        }
        catch(Exception e){
        }
    }
}
