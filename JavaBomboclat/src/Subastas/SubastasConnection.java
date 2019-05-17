/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Subastas;
import client_server_API.*;
import client_server_API.Message;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import static java.lang.System.out;

/**
 *
 * @author Ricardo Bonilla
 */
public class SubastasConnection extends Connection{

    public SubastasConnection(String servername) throws Exception {
        super(servername, 9999);
    }
    
    
    public static void main(String ... args) {
    
        String servername = "localhost";  
        try {
            XStream xstream = new XStream(new DomDriver());
            Connection client = new Connection(servername, 9999);
            AbstractObservable subasta = new Subasta();
            String XML = xstream.toXML(subasta);
            client.sendMessage(new Message(1, XML, "BadBunny"));
//            client.sendMessage(new Message(2, "1", "BadBunny"));
            
        } catch(Exception ex) {
            out.println( "Error --> " + ex.getMessage());
        }
        
    } // end of main
}
