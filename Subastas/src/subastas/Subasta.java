/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas;

import client_server_API.AbstractObservable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jose pablo
 */

public class Subasta{
    
    private Date inicio;
    private Date finalProgramado;
    private Producto producto;
    private String status;
    private String feed;

    public Subasta(String finalProgramado, Producto producto){
        this.inicio = new Date();
        this.producto = producto;
        this.status = "Activa";
        this.feed = "";
        try{
        this.finalProgramado = new SimpleDateFormat("dd/MM/yyyy").parse(finalProgramado);
        }
        catch(ParseException e){
            System.out.println("La fecha no funciona :(");
        }
    }
    
    public void pujar(String nuevoPrecio){
        producto.setPrecioFinal(nuevoPrecio);
    }
    
    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFinalProgramado() {
        return finalProgramado;
    }

    public void setFinalProgramado(Date finalProgramado) {
        this.finalProgramado = finalProgramado;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }
    
    public String addToFeed(String newSection){
        return feed +=  "\t" + newSection;
    }
}
