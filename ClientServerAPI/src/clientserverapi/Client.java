/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserverapi;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Ricardo Bonilla
 */
public class Client implements IObserver{
    private String clientIPAddress;
    private int clientPort;
    private Socket connection;
    private ObjectOutputStream output;

    public Client(String clientIPAddress, int clientPort) {
        this.clientIPAddress = clientIPAddress;
        this.clientPort = clientPort;
    }
    
    @Override
    public void notifyObserver(Message message, Object object) {
        openConnection();
        try {
            getStreams();
        } catch(IOException e) {
            System.out.println(e);
        }
        System.out.println("Enviando Mensaje a: " + this.connection.getInetAddress() +
                "\n" + "Mensaje: " + message.toString());
        sendMessage(message);
        closeConnetion();
    }
    
    private void openConnection() {
        try {
            this.connection = new Socket(this.clientIPAddress, this.clientPort);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    private void closeConnetion() {
        try {
            this.connection.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    private void getStreams() throws IOException {
        this.output = new ObjectOutputStream(this.connection.getOutputStream());
        this.output.flush();
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
}
