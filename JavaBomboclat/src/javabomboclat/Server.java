// Chat Server runs at port no. 9999
package javabomboclat;

import java.io.*;
import java.util.*;
import java.net.*;
import static java.lang.System.out;

public class Server{
    
    private static Server server;
    private int port;
    private ArrayList<IObservable> observables;
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

  public void process() throws Exception  {
      out.println("Server Started...");
      while(true) {
 		 Socket client = this.serverSocket.accept();
 		 ClientHandler c = new ClientHandler(client);
                 clients.add(c);
     }  // end of while
  }
  public static void main(String ... args) throws Exception {
      Server server = Server.getInstance(9999);
      server.process();
  } // end of main

  public void boradcast(String user, String message)  {
	    // send message to all connected users
	    for ( ClientHandler c : clients )
	       if ( ! c.getUserName().equals(user) )
	          c.sendMessage(user,message);
  }

   
    
    class  ClientHandler extends Thread implements IMessager { //TODO: Crear una interfaz compartida por Observador y Observable para interacciones con el hanlder
	ObjectInputStream input;
	ObjectOutputStream output;

	public ClientHandler(Observable  client) throws Exception {
         // get input and output streams
	 input = client.getInput();
	 output = client.getOutput();
         observables.add(client);
	 start();
        }

        public void run()  {
    	     String line;
	     try    {
                while(true)   {
		 line = input.readLine();
		 if ( line.equals("end") ) {
		   clients.remove(this);
		   users.remove(name);
		   break;
                 }
		 boradcast(name,line); // method  of outer class - send messages to all
	       } // end of while
	     } // try
	     catch(Exception ex) {
	       System.out.println(ex.getMessage());
	     }
        } // end of run()

        @Override
        public void sendMessage(Message message) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void receiveMessage() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
   } // end of inner clas
} // end of Server
