/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas;

import java.util.Date;

/**
 *
 * @author jose pablo
 */
public class Subasta {
    
    private Date inicio;
    private Date finalProgramado;
    private Producto producto;
    private String status;
    private String feed;

    public Subasta(String finalProgramado, Producto producto) {
        //this.inicio = inicio;
        this.finalProgramado = new Date();
        this.producto = producto;
        this.status = "activa";
        this.feed = "";
    }
    
    public void pujar(int nuevoPrecio){
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
