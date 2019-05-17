/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_server_API;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Ricardo Bonilla
 */
public class Client implements IObserver{
    private ObjectOutputStream output;
    private InetAddress inetAddress;

    public Client(ObjectOutputStream output, InetAddress inetAddress) {
        this.output = output;
        this.inetAddress = inetAddress;
    }
    
    @Override
    public void notifyObserver(Message message) {
        System.out.println("Enviando Mensaje a: " + this.inetAddress +
                "\n" + "Mensaje: " + message.toString());
        sendMessage(message);
    }
    
    private void sendMessage(Message message) {
        try {
            this.output.writeObject(message);
            this.output.flush();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }
}
