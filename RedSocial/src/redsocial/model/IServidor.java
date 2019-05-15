/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author jose pablo
 */
public abstract class IServidor {
    
    private int puerto;
    private ServerSocket servidor;
    private Socket conexion;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    
    public IServidor(){
        this.puerto = 6565;
        iniciarServidor();
    }
    
     public Mensaje leerMensaje() throws IOException, ClassNotFoundException {
        Mensaje mensaje = (Mensaje) entrada.readObject();
        System.out.println("leer " + mensaje.toString()) ;
        return mensaje;
    }
    
    public void enviarMensaje(Mensaje mensaje) throws IOException {
        System.out.println("Enviando Mensaje a: " + conexion.getInetAddress() +
                "\n" + "Mensaje: " + mensaje.toString());
        salida.writeObject(mensaje);
        salida.flush();
    }
    
    public void iniciarServidor() {
        try{
            this.servidor = new ServerSocket(this.puerto, 10);
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }

    public void esperarConexion() throws IOException {
        System.out.println("\n\n++++++++++++++++++++++++++++++++++++");
        System.out.println("Esperando una Conexión...");
        conexion = servidor.accept();
        System.out.println("Conectado a :" + conexion.getInetAddress().getHostName());
    }

    public void obtenerFlujos() throws IOException {
        salida = new ObjectOutputStream(conexion.getOutputStream());
        salida.flush();
        entrada = new ObjectInputStream(conexion.getInputStream());    
    }

    public void cerrarConexion() throws IOException {
        entrada.close();
        salida.close();
        conexion.close();
    }

    public void terminarServidor() throws IOException {
        servidor.close();
    }
    
   public void whileTrue(){
       Mensaje mensajeRecibido;
        try {
            while(true) {
                // Esperar Conexion 
                System.out.println("Espera conexion");
                esperarConexion();
                
                // Obtener Flujos 
                System.out.println("Obtiene flujos");
                obtenerFlujos();
                
                // Enviar y escribir el mensaje 
                System.out.println("procesa conexion");
                procesarConexion();
                
                // Liberar la conexion 
                System.out.println("Libera");
                cerrarConexion();
            }
        }
        catch(ClassNotFoundException | IOException e) {
            System.out.println(e);
        }
   }
    
    public abstract void procesarConexion() throws IOException, ClassNotFoundException;
     
}
