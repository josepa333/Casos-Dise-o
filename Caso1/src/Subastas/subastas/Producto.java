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
    private double precioInicial;
    private double precioFinal;

    public Producto(String nombre, String precioInicial) {
        this.nombre = nombre;
        this.precioInicial = Double.parseDouble(precioInicial);
        this.precioFinal = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioInicial() {
        return precioInicial;
    }

    public void setPrecioInicial(double precioInicial) {
        this.precioInicial = precioInicial;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }
    
    
    
}
