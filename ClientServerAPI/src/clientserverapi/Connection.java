/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserverapi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Ricardo Bonilla
 */
public class Connection extends Thread{
    
    private static Connection client;
    private ObjectOutputStream output;
    private ObjectOutputStream output1;
    private ObjectInputStream input;
    private ServerSocket clientSocket;
    private Socket connection;
    private Socket connection1;
    private int clientPort;
    private String serverIPAddress;
    private int serverPort;
    
    public Connection(String serverIPAddress, int serverPort, int clientPort) {
        this.serverIPAddress = serverIPAddress;
        this.serverPort = serverPort;
        this.clientPort = clientPort;
        startServer();
    }
    
    private synchronized static void createInstance(String serverIPAddress, int serverPort, int clientPort) {
        if(client == null)
            client = new Connection(serverIPAddress, serverPort, clientPort);
    }
    
    public static Connection getInstance(String serverIPAddress, int serverPort, int clientPort) {
        if(client == null)
            createInstance(serverIPAddress, serverPort, clientPort);
        return client;
    }
    
    private void startServer() {
        try {
            this.clientSocket = new ServerSocket(this.clientPort, 1);
            System.out.println("El cliente está en el puerto: " + clientSocket.getLocalPort());
        }
        catch(IOException e) {
            System.out.println(e);   
        }
    }
    
    private void waitConnection() throws IOException {
        System.out.println("\n\n++++++++++++++++++++++++++++++++++++");
        System.out.println("Esperando una Conexión...");
        this.connection = clientSocket.accept();
        
        System.out.println("Conectado a :" + connection.getInetAddress().getHostName());
    }
    
    private void getStreams() throws IOException {
        this.output = new ObjectOutputStream(connection.getOutputStream());
        this.output.flush();
        this.input = new ObjectInputStream(connection.getInputStream());
    }
    
    private Message readMessage() throws IOException, ClassNotFoundException {
        Message message = (Message) input.readObject();
        System.out.println("leer " + message.toString()) ;
        return message;
    }
    
    private void closeConnection() throws IOException {
        this.input.close();
        this.output.close();
        this.connection.close();
    }
    
    public void endServer() throws IOException {
        this.clientSocket.close();
    }
    
    public void processConnection(Message message){
        switch (message.getType()) {
            case 1:
                System.out.println(message.getContent());
                break;
            default:
                System.out.println("No se reconocio el tipo de mensaje");
                break;
        }
    }
    
    public static void main(String[] args) {
        Connection client = Connection.getInstance("192.168.100.17", 3702, 5050);
        client.sendMessage(new Message(1, "Saludos", "BadBunny"));
    }
    
    @Override
    public void run() {
        try {
            while(true) {
                
                // Esperar Conexion
                System.out.println("Espera conexion");
                client.waitConnection();
                
                // Obtener Flujos
                System.out.println("Obtiene flujos");
                client.getStreams();
                
                // Enviar y escribir el mensaje
                System.out.println("Procesa conexion");
                client.processConnection(client.readMessage());
                
                // Liberar la conexion
               // System.out.println("Cierra la conexion");
                //client.closeConnection();
                
                try{
                    Thread.sleep(2000);
                }
                catch(InterruptedException e){}
                    
            }
        }
        catch(ClassNotFoundException | IOException e) {
            System.out.println(e);
        }
    }
    
    private void openConnection() {
        try {
            this.connection1 = new Socket(this.serverIPAddress, this.serverPort);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    private void closeConnetion1() {
        try {
            this.connection1.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    private void getStreams1() throws IOException {
        this.output1 = new ObjectOutputStream(this.connection1.getOutputStream());
        this.output1.flush();
    }
    
    private void sendMessage(Message message) {
        try {
            openConnection();
            getStreams1();
            System.out.println("Enviando Mensaje a: " + this.connection1.getInetAddress() +
                "\n" + "Mensaje: " + message.toString());
            this.output1.writeObject(message);
            System.out.println("writeObject");
            this.output1.flush();
            System.out.println("flush");
            closeConnetion1();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
