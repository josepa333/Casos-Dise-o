package Model;

import java.io.*;
import java.awt.Color;
import org.faceless.pdf2.*;


public class PDFTextProcessor implements IStrategy{

    
    private String determineColorName(String text, int startPoint){
        String result = "";
        
        for (int i = startPoint; i < text.length()  ; i++) {
             if(text.charAt(i) == '>' ){
                 break;
             }
             result += Character.toString(text.charAt(i));
        }
        return result;
    }
    
    private Color determineFillColor(String colorName){
        switch(colorName){
                case "RED": return Color.RED;
                case "BLUE": return Color.BLUE;
                case "GREEN": return Color.GREEN;
                case "PINK": return Color.PINK;
                case "YELLOW": return Color.YELLOW;
                default: return Color.BLACK;
        }
    }
    
    private int drawWithColor(String text, int startPoint,PDFPage page, PDFStyle plain){
        
        int counter = 0;
        String colorName = determineColorName(text,startPoint + 1);
        PDFStyle colored = new PDFStyle();
        colored.setFont(new StandardFont(StandardFont.HELVETICAOBLIQUE), 12);
        colored.setFillColor( determineFillColor( colorName ) );
        page.setStyle(colored);
        startPoint += colorName.length() + 2;
        counter += colorName.length() + 2;
        
        for (int i = startPoint; i < text.length()  ; i++) {
            if( text.charAt(i) == '<' && text.charAt(i + 1) == '/'){
                counter += 2 + colorName.length();
                break;
            }
            page.drawText(  Character.toString(text.charAt(i)) );
            counter++;
        }
        
        plain.setFillColor(Color.BLACK);
        page.setStyle(plain);
        return counter;
    }
    
    @Override
    public void processText(String text, String fileName) {
        
        
        try{
            
            PDF pdf = new PDF();
            PDFPage page = pdf.newPage("Letter");
            
            PDFStyle plain = new PDFStyle();
            plain.setFont(new StandardFont(StandardFont.HELVETICA), 12);
            plain.setFillColor(Color.black);
            
            page.beginText(50, 50, page.getWidth()-100, page.getHeight()-100);
            page.setStyle(plain);
  
            for (int i = 7; i < text.length() - 7 ; i++) {
                if(text.charAt(i) == '<' ){
                     i += drawWithColor(text, i, page, plain);
                }
                else{
                    page.drawText(  Character.toString(text.charAt(i)) );
                }
            }

            page.endText(false);
            
            OutputStream fo = new FileOutputStream("Fonts.pdf");
            pdf.render(fo);
            fo.close();

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String readFile(String file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}

/*  PDF p = new PDF();
            PDFPage page = p.newPage("A4");
            
            PDFStyle mystyle = new PDFStyle();
            mystyle.setFont(new StandardFont(StandardFont.HELVETICA), 24);
            mystyle.setFillColor(Color.BLACK);

            
            page.beginText(50, 50, page.getWidth()-100, page.getHeight()-100);
            page.setStyle(mystyle);

            
            
            
            
            // Set some meta-information in the document
            p.setInfo("Author", "ORANJO");
            p.setInfo("Title", fileName);

            OutputStream fo = new FileOutputStream(fileName + ".pdf");
            p.render(fo);
            fo.close();*/
