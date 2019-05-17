// Chat Server runs at port no. 9999
package client_server_API;

import java.io.*;
import java.util.*;
import java.net.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    
    private static Server server;
    private int port;
    public ServerSocket serverSocket;
    public Socket connection;
    public ArrayList<AbstractObservable> observables;
    private int observableAmount;
  
    public Server(int port) throws Exception{
        this.port = port;
        this.observableAmount = 0;
        this.observables = new ArrayList<>();
        startServer();
        process();
    }
    
    public synchronized static void createInstance(int port) throws Exception {
        if(server == null)
            server = new Server(port);
    }
    
    public static Server getInstance(int port) throws Exception {
        if(server == null)
            createInstance(port);
        return server;
    }
    
    public void startServer() {
        try {
            this.serverSocket = new ServerSocket(this.port, 10);
            System.out.println("El servidor está en el puerto: " + serverSocket.getLocalPort());
        }
        catch(IOException e) {
            System.out.println(e);   
        }
    }
    
    public AbstractObservable findObservable(int idObservable) {
        for (int i = 0; i < Server.this.observables.size(); i++) {
            AbstractObservable observable = (AbstractObservable) Server.this.observables.get(i);
            if (observable.getIdObservable() == idObservable) {
                return observable;
            }
        }
        return null;
    }
    
    public void process() throws Exception  {
        System.out.println("\n\n++++++++++++++++++++++++++++++++++++");
        System.out.println("Esperando una Conexión...");
        while( true) {
           this.connection = this.serverSocket.accept();
           HandleClient c = new HandleClient(this.connection);
           System.out.println("Conectado a :" + connection.getInetAddress().getHostName());
        }
    }

    //Es como un notify
//    public void boradcast(String user, String message)  {
//        // send message to all connected users
//        for ( HandleClient c : clients )
//           if ( ! c.getUserName().equals(user) )
//              c.sendMessage(user,message);
//    }

    public class  HandleClient extends Thread {
        
        public ObjectInputStream input;
        public ObjectOutputStream output;
        public InetAddress clientInetAddress;

        public HandleClient(Socket client) throws Exception {
            // get input and output streams
           output = new ObjectOutputStream(client.getOutputStream());
           output.flush();
           input = new ObjectInputStream(client.getInputStream());
           clientInetAddress = client.getInetAddress();
           start();
        }
        
        public void addObservable(AbstractObservable observable) {
            observable.setIdObservable(Server.this.observableAmount);
            Server.this.observables.add(observable);
            addObserver(observable, new Client(this.output, this.clientInetAddress));
            Server.this.observableAmount = Server.this.observableAmount + 1;
        }

        public void addObserver(AbstractObservable observable, Client observer) {
            observable.addObserver(observer);
            //observable.notifyAllObservers(new Message(1, "Se ha agregado el observador " + observer.getInetAddress() + " al observable: " + observable.getIdObservable(), "server"));
        }
        
        public boolean processConnection(Message message){
            System.out.println("Proccess Normal");
            boolean flag = true;
            switch (message.getType()) {
                case 1: //Caso añadir Observable
                    XStream xstream = new XStream(new DomDriver());
                    //AbstractObservable observable =  (AbstractObservable)(xstream.fromXML(message.getContent()));
                    //addObservable(observable); 
                    break;
                case 2: //Caso añadir Observador a Observable
                    int idObservable = Integer.parseInt(message.getContent());
                    addObserver(findObservable(idObservable), new Client(this.output, this.clientInetAddress));
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    System.out.println("No se reconocio el tipo de mensaje");
                    break;
            }
            return flag;
        }
        
        public Message readMessage() {
            System.out.println("Leyendo...") ;
            try {
                Message message = (Message) input.readObject();
                System.out.println("leer:\n" + message.toString()) ;
                return message;
            }
            catch(ClassNotFoundException e1) {
                System.out.println("ClassNotFoundException");
                System.out.println(e1.toString());
            } catch (IOException ex) {
                System.out.println("IOException");
                System.out.println(ex.toString());
            }
            return null;
        }
        
        public void run()  {
             boolean run = true;
             try    {
                while(run)   {
                    run = processConnection(readMessage());
//                 line = input.readLine();
//                 if ( line.equals("end") ) {
//                   clients.remove(this);
//                   users.remove(name);
//                   break;
//                 }
//                 boradcast(name,line); // method  of outer class - send messages to all
               } // end of while
             } // try
             catch(Exception ex) {
               System.out.println(ex.getMessage());
             }
        } // end of run()
     } // end of inner class

} // end of Server
