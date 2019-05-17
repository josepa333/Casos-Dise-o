/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.model;

import client_server_API.AbstractObservable;
import java.util.ArrayList;

/**
 *
 * @author Angelo PC
 */
public class Vip extends AbstractObservable{
    private ArrayList<MensajeVip> mensajes;
    private ArrayList<Seguidor> seguidores;
    private String id;
    
    public Vip(String id){
        this.id = id;
        this.seguidores = new ArrayList();
        this.mensajes = new ArrayList();
    }
    
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

    public void addMensaje(String mensaje){
        this.mensajes.add(new MensajeVip(mensaje,this.mensajes.size()));
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
   
    
    public void darDeBaja(){
    }
    
    public void postearMensaje(String mensaje){
        mensajes.add(mensajes.size(),new MensajeVip(mensaje,mensajes.size()));
    }

    public void likeMensaje(int idMensaje,String idUsuario){
        mensajes.get(idMensaje).agregarLike(idUsuario);
    }
    
    public void dislikeMensaje(int idMensaje,String idUsuario){
        mensajes.get(idMensaje).agregarDislike(idUsuario);
    }
    
}
