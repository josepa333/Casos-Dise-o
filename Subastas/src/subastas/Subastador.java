/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas;

import client_server_API.AbstractObservable;
import client_server_API.Message;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.ArrayList; 
import subastasViews.SubastaSubastadorController;

/**
 *
 * @author jose pablo
 */
public class Subastador extends AbstractObservable{ //TODO Hacerlo observer 
    
    private String idSubastador;
    private Subasta subasta;
    private SubastaSubastadorController controller;
    //Servidor
     private String ipServerServer;
     private SubastaConnection connection;
     
     //XML
     static XStream xstream;
    
    public Subastador(String idSubastador,String fechaInicial,String nombreProducto,
            String precioInicial, SubastaSubastadorController controller) { //Crear hilo para la interfaz
        ipServerServer = "localhost"; //"172.19.51.112";
        this.idSubastador = idSubastador;
        this.controller = controller;
        this.subasta = new Subasta(fechaInicial, new Producto(nombreProducto, precioInicial));
        xstream = new XStream(new DomDriver());
        try{
            this.connection = new SubastaConnection(ipServerServer,this);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        crearSubasta();
    }
    
    public void finalizarSubasta(){
        subasta.setStatus("Finzalizada");
        connection.sendMessage(new Message(8,Integer.toString(this.getIdObservable()),subasta.getProducto().getPrecioFinal()));
    }
    public void cancelarSubasta(){
        subasta.setStatus("Cancelada");
        connection.sendMessage(new Message(7,Integer.toString(this.getIdObservable()),subasta.getProducto().getPrecioFinal()));

    }
    //Sets

    public String getIdSubastador() {
        return idSubastador;
    }

    public Subasta getSubasta() {
        return subasta;
    }
    
    public void crearSubasta(){
        ArrayList<String> data = new ArrayList();
        data.add(Integer.toString(this.getIdObservable()));
        data.add(idSubastador  );
        data.add( subasta.getProducto().getNombre() );
        data.add( subasta.getProducto().getPrecioFinal());
        data.add(subasta.getStatus());
        connection.sendMessage(new Message(1,  xstream.toXML( data  ) ,idSubastador));
    }

    public SubastaSubastadorController getController( ) {
        return this.controller;
    }    
}
