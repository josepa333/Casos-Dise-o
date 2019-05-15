/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Angelo PC
 */
public class MensajeVip {
    private Date fecha;
    private String contenido;
    private ArrayList<String> likes;
    private ArrayList<String> dislikes;
    private int idMensaje;
    
    public MensajeVip(String contenido, Integer id){
        this.fecha = Calendar.getInstance().getTime();
        this.contenido = contenido;
        likes = new ArrayList();
        dislikes = new ArrayList();
        idMensaje = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }
    

    public Date getInicio() {
        return fecha;
    }

    public void setInicio(Date fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getLikes() {
        return likes.size();
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }
    
    public void agregarLike(String seguidor){
        this.likes.add(seguidor);
        if(this.dislikes.contains(seguidor)){
            this.dislikes.remove(seguidor);
        }
    }
    public int getDislikes() {
        return dislikes.size();
    }

    public void setDislikes(ArrayList<String> dislikes) {
        this.dislikes = dislikes;
    }
    
    public void agregarDislike(String seguidor){
         this.dislikes.add(seguidor);
        if(this.likes.contains(seguidor)){
            this.likes.remove(seguidor);
        }
    }
}
