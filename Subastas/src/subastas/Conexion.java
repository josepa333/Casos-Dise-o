package subastas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Conexion {
    private String servidorIP;
    private int servidorPuerto;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Mensaje mensajeES;
    private Socket cliente;
    
    // Constructor
    /**
     * @param pDireccionIP  donde se establece la conexion
     * @param pPuerto el puerto donde se establace la conexion
     */
    public Conexion(String pDireccionIP, int pPuerto) {
        this.servidorIP = pDireccionIP;
        this.servidorPuerto = pPuerto;
    }
    
    // Abrir
    /**
     * Establece la conexion en el puerto
     */
    public void abrirConexion() {
        try {
            cliente = new Socket(this.servidorIP, this.servidorPuerto);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    // Cerrar
    /**
     * Cierra la conexion en el socket
     */
    public void cerrarConexion() {
        try {
            cliente.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    // Obtener
    /**
     * Abre los flujos para la entrada de objetos
     * @throws IOException 
     */
    public void obtenerFlujos() throws IOException {
        this.salida = new ObjectOutputStream(cliente.getOutputStream());
        this.salida.flush();
        this.entrada = new ObjectInputStream(cliente.getInputStream());
    }
    
    // Enviar
    /**
     * envia el mensaje al servidor
     * @param miMensaje 
     */
    public void enviarMensaje(Mensaje miMensaje) {
        try {
            salida.writeObject(miMensaje);
            salida.flush();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    // Recibir
    /**
     * Recibe el mensaje del servidor
     * @return retorna el mensaje para su uso posterior
     */
    public Mensaje recibirMensaje() {
        Mensaje mensajeRecib = new Mensaje();
        
        try {
            mensajeRecib = (Mensaje) entrada.readObject();
        }
        catch (ClassNotFoundException e) {System.out.println(e);}
        catch (Exception e) {System.out.println(e);}
        
        finally {
            return mensajeRecib;
        }
    }
}