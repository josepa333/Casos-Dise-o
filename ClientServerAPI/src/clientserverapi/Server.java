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
import java.util.ArrayList;

/**
 *
 * @author Ricardo Bonilla
 */
public class Server{
    
    private static Server server;
    private int port;
    private ServerSocket serverSocket;
    private Socket connection;
    private ArrayList<IObservable> observables;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    
    private Server(int port){
        this.port = port;
        this.observables = new ArrayList<>();
        startServer();
    }
    
    private synchronized static void createInstance(int port) {
        if(server == null)
            server = new Server(port);
    }
    
    private void addObservable(IObservable observable) {
        this.observables.add(observable);
    }
    
    public static Server getInstance(int port) {
        if(server == null)
            createInstance(port);
        return server;
    }
    
    private void startServer() {
        try {
            this.serverSocket = new ServerSocket(this.port, 10);
            System.out.println("El servidor está en el puerto: " + serverSocket.getLocalPort());
        }
        catch(IOException e) {
            System.out.println(e);   
        }
    }
    
    private void waitConnection() throws IOException {
        System.out.println("\n\n++++++++++++++++++++++++++++++++++++");
        System.out.println("Esperando una Conexión...");
        this.connection = serverSocket.accept();
        
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
        this.serverSocket.close();
    }
    
    public void processConnection(Message message){
        switch (message.getType()) {
            case 1:
                System.out.println(message.getUser());
                IObservable subasta = new Subasta(1);
                subasta.addObserver(new Client("192.168.100.17", 5050));
                addObservable(subasta);
                subasta.notifyAllObservers(new Message(1, "saludos BadBunny", "Maluma"), output);
                break;
            default:
                System.out.println("No se reconocio el tipo de mensaje");
                break;
        }
    }
    
    public static void main(String[] args) {
        
        Server server = Server.getInstance(3702);
        try {
            while(true) {
                
                // Esperar Conexion
                System.out.println("Espera conexion");
                server.waitConnection();
                
                // Obtener Flujos
                System.out.println("Obtiene flujos");
                server.getStreams();
                
                // Enviar y escribir el mensaje
                System.out.println("Procesa conexion");
                server.processConnection(server.readMessage());
                
                // Liberar la conexion
                //System.out.println("Cierra la conexion");
                //server.closeConnection();
            }
        }
        catch(ClassNotFoundException | IOException e) {
            System.out.println(e);
        }
    }
}
