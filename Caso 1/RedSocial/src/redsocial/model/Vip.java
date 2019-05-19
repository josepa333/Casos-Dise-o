/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.model;

import client_server_API.AbstractObservable;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Angelo PC
 */
public class Vip extends AbstractObservable implements Serializable{
    private ArrayList<MensajeVip> mensajes;
    private ArrayList<Seguidor> seguidores;
    private String user;
  
    public Vip(String id){
        this.user = id;
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
        for(Seguidor seg : seguidores){
            if(seg.getIdSeguidor().equals(seguidor.getIdSeguidor())){
                return;
            }
        }
        this.seguidores.add(seguidor);
    }

    public ArrayList<MensajeVip> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<MensajeVip> mensajes) {
        this.mensajes = mensajes;
    }

    public String addMensaje(String mensaje){
        this.mensajes.add(new MensajeVip(mensaje,this.mensajes.size()));
        return mensajes.get(mensajes.size() - 1).getContenido();
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String id) {
        this.user = id;
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
    
    @Override
    public ArrayList<String> getData(){
        ArrayList<String> data = new ArrayList();
        data.add(user);
        data.add(Integer.toString(getSeguidores().size()));
        return data;
    }
}
