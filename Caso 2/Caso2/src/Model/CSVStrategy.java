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
public class CSVStrategy implements IStrategy{

    @Override
    public String readFile(String file) {
        return file.replace(",", " ").replace(";", "\n");
    }

    @Override
    public void processText(String text, String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(text.replace(" ", ",").replace("\n",";"));
        }
        catch(Exception e){
        }
    }
}
