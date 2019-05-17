package client_server_API;


import java.io.*;
import java.net.*;



public class  Connection{
    public ObjectOutputStream output;
    public ObjectInputStream input;
    public  Socket clientSocket;
    
    public Connection(String servername, int serverport) throws Exception {
        clientSocket  = new Socket(servername, serverport);
        this.output = new ObjectOutputStream(this.clientSocket.getOutputStream());
        this.output.flush();
        this.input = new ObjectInputStream(this.clientSocket.getInputStream());
        new ConnectionThread().start();  // create thread for listening for messages
    }
    
    
    public void sendMessage(Message message) {
        try {
            System.out.println("Enviando Mensaje a: " + clientSocket.getInetAddress() +
                "\n" + "Mensaje: " + message.toString());
            output.writeObject(message);
            System.out.println("writeObject");
            output.flush();
            System.out.println("flush");
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void processConnection(Message message){
        switch (message.getType()) {
            case 1:
                System.out.println("Confirmacion:\n" + message.getContent());
                break;
            case 2:
                System.out.println("Confirmacion:\n" + message.getContent());
                break;
            case 3:
                System.out.println("Confirmacion:\n" + message.getContent());
                break;
            default:
                System.out.println("No se reconocio el tipo de mensaje");
                break;
        }
    }
    
    // inner class for Messages Thread
    class  ConnectionThread extends Thread {
        
        private Message readMessage() throws IOException, ClassNotFoundException {
            Message message = (Message) input.readObject();
            System.out.println("leer " + message.toString()) ;
            return message;
        }
        
        public void run() {
            String line;
            try {
                while(true) {
                    processConnection(readMessage());
                } // end of while
            } catch(IOException | ClassNotFoundException ex) {}
        }
    }
} //  end of client
