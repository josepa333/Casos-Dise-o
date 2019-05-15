/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.model;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Angelo PC
 */
public class Vip extends IServidor{
    private ArrayList<MensajeVip> mensajes;
    private ArrayList<Seguidor> seguidores;
    private String id;

    public ArrayList<Seguidor> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(ArrayList<Seguidor> seguidores) {
        this.seguidores = seguidores;
    }
    
    public void addSeguidor(Seguidor seguidor){
        this.seguidores.add(seguidor);
    }

    public ArrayList<MensajeVip> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<MensajeVip> mensajes) {
        this.mensajes = mensajes;
    }

    public void addMensaje(MensajeVip mensaje){
        this.mensajes.add(mensaje);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
   
    
    public void darDeBaja(){
        closeConnection();
    }
    
    public void postearMensaje(String mensaje){
        mensajes.add(mensajes.size(),new MensajeVip(mensaje,mensajes.size()));
    }
       
    private void closeConnection(){
        //TODO
    }
    
    public void agregarSeguidor(){
        //TODO
    }
    
    public void likeMensaje(int idMensaje,String idUsuario){
        mensajes.get(idMensaje).agregarLike(idUsuario);
    }
    
    public void dislikeMensaje(int idMensaje,String idUsuario){
        mensajes.get(idMensaje).agregarDislike(idUsuario);
    }

    @Override
    public void procesarConexion() throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
