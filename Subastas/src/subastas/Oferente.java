/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas;

import client_server_API.Client;
import client_server_API.Message;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.ArrayList;
import subastasViews.SubastaClienteController;
import subastasViews.SubastasController;

/**
 *
 * @author jose pablo
 */
public class Oferente extends Client {
    
    private ArrayList<SubastaClienteController> subastas;
    private String idOferente;
    private OferenteConnection miConexion; 
    private SubastasController controller;
    
    static XStream xstream;
    static String xml;
    
    public Oferente(String idOferente, SubastasController controller) {
        super(null, null);
        String ipServerServer = "localhost"; //"172.19.51.112";
        this.idOferente = idOferente;
        this.controller = controller;
        this.subastas = new ArrayList();
        xstream = new XStream(new DomDriver());
        try{
            miConexion = new OferenteConnection(ipServerServer,controller);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String getIdOferente() {
        return idOferente;
    }

    public void setIdOferente(String idOferente) {
        this.idOferente = idOferente;
    }
    
    public void enviarOferta(String valor, int idSubasta){
        ArrayList<String> data = new ArrayList();
        data.add(Integer.toString(idSubasta));
        data.add(valor);
        miConexion.sendMessage(new Message(3,xstream.toXML(data),idOferente));
    }
    public void addSubasta(SubastaClienteController subasta){
        subastas.add(subasta);
        miConexion.sendMessage(new Message(6, Integer.toString(subasta.getIdSubasta()),idOferente)); //Fijo lo termino usando 
    }

    public ArrayList<SubastaClienteController> getSubastas() {
        return subastas;
    }

    public void setSubastas(ArrayList<SubastaClienteController> subastas) {
        this.subastas = subastas;
    }
    
    
    
 
}