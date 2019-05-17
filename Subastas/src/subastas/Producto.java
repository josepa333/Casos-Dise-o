/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas;

/**
 *
 * @author jose pablo
 */
public class Producto {
    
    private String nombre;
    private String precioInicial;
    private String precioFinal;

    public Producto(String nombre, String precioInicial) {
        this.nombre = nombre;
        this.precioInicial = precioInicial;
        this.precioFinal = "0";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecioInicial() {
        return precioInicial;
    }

    public void setPrecioInicial(String precioInicial) {
        this.precioInicial = precioInicial;
    }

    public String getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(String precioFinal) {
        this.precioFinal = precioFinal;
    }
    
    
    
}
