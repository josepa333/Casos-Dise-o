/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.google.gson.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author Angelo PC
 */
public class JSONStrategy  implements IStrategy{
    
    @Override
    public String readFile(String file) {
        Gson gson = new Gson();
        JSONText text = gson.fromJson(file, JSONText.class);
        String returnString = "";
        returnString = "<!DOCTYPE html><html>";
        for(String sentence : text.parrafos){
            returnString += "<p style=\"margin-top: 0\">" + sentence + "</p>";
        }
        returnString += "</html>";
        return returnString;
    }

    @Override
    public void processText(String text, String fileName) {
        Gson gson = new Gson();
        JSONText jt = new JSONText();
        text = text.replace("</p>", "").replace("</html>", "").replace("</head>", "").replace("</body>", "").replace("<html>", "").replace("<head>", "").replace("<body>", "");
        String[] strArray = text.split("\\<p style=\"margin-top: 0\">");
        ArrayList<String> parrafos = new ArrayList<>(Arrays.asList(strArray));
        jt.parrafos = parrafos;
        text = gson.toJson(jt, JSONText.class);
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(text);
        }
        catch(Exception e){
        }
    }
    
}
