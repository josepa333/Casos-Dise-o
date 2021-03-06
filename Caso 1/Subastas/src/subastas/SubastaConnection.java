/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas;

import client_server_API.Connection;
import client_server_API.Message;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static subastas.SubastasServer.xstream;

/**
 *
 * @author jose pablo
 */
public class SubastaConnection extends Connection{
    
    private Subastador subastador;

    public SubastaConnection(String servername, Subastador subastador) throws Exception {
        super(servername, 9999);
        this.subastador = subastador;
       // sendMessage(new Message(2,"",""));
    }
    
    
    @Override
    public void processConnection(Message message){
        TipoMensaje tipo = TipoMensaje.values()[message.getType()];
        xstream = new XStream(new DomDriver());
        switch (tipo) {
            case INICIARSUBASTA://Ocupo el mensaje que me diga el id 
                System.out.println("Nueva subasta con id: " + message.getContent());
                subastador.setIdObservable(Integer.parseInt(message.getContent()));
                break;
            case OFERTA://Ocupo el mensaje que me diga el id 
                Message mensajeResultado;
                ArrayList<String> data = (ArrayList<String>) xstream.fromXML(message.getContent());
                int resultado = JOptionPane.showConfirmDialog((Component) null,
                        "El usuario " + message.getUser()+ " ha ofrecido " + data.get(1) + ", desea aceptar la oferta?",
                        "alert", JOptionPane.OK_CANCEL_OPTION);
                if(resultado == 0){
                     mensajeResultado = new Message(5, xstream.toXML(data) ,message.getUser());
                    subastador.getSubasta().pujar(data.get(1));
                    String tmpString = subastador.getController().getVista().feedTextArea.getText();
                    subastador.getController().getVista().feedTextArea.setText( 
                            tmpString + "\nSe ha aceptado la oferta de " + message.getUser() + " de " + data.get(1));
                }
                else{
                    System.out.println("SE cancela la oferta");
                    mensajeResultado = new Message(4, data.get(0),data.get(1));
                    String tmpString = subastador.getController().getVista().feedTextArea.getText();
                    subastador.getController().getVista().feedTextArea.setText( 
                            tmpString + "\nSe ha rechazado la oferta de " + message.getUser() + " de " + data.get(1));
                }
                sendMessage(mensajeResultado);
                break;
            case  UNIRSESUBASTA:
              String tmpString = subastador.getController().getVista().feedTextArea.getText();
              subastador.getController().getVista().feedTextArea.setText( 
                            tmpString +"\n" + message.getContent() );
              break;
            case SUBASTACANCELADA:
                String tmpStringCancelada = subastador.getController().getVista().feedTextArea.getText();
                System.out.println("En al caja hay: " + subastador.getController().getVista().feedTextArea.getText());
                    subastador.getController().getVista().feedTextArea.setText( 
                            tmpStringCancelada + "\nSe ha cancelado la subasta");
                break;
          case SUBASTAFINALIZADA:
              String tmpStringFinalizada = subastador.getController().getVista().feedTextArea.getText();
              System.out.println("En al caja hay: " + subastador.getController().getVista().feedTextArea.getText());
                    subastador.getController().getVista().feedTextArea.setText( 
                            tmpStringFinalizada + "\nSe ha finalizado la subasta");
                break;
            default:
                System.out.println("No se reconocio el tipo de mensaje");                
                break;
        }
    }
    
}
