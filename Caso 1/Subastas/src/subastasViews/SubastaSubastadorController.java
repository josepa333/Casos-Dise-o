/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastasViews;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import subastas.Subastador;

/**
 *
 * @author jose pablo
 */
public class SubastaSubastadorController implements ActionListener { //Todo meterle observer 
    
    private Subastador subastador;
    private SubastaSubastador vista;

    public SubastaSubastadorController(String idSubastador,String fechaInicial,String nombreProducto, 
                                                                String precioInicial) {
        subastador = new Subastador(idSubastador,fechaInicial,nombreProducto,precioInicial,this);
        vista = new SubastaSubastador();
        vista.show();
        this.vista.cancelarSubastaButton.addActionListener(this);
        this.vista.finalizarSubastaButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
           case "Finalizar subasta":
               System.out.println("Finalizar subasta");
               subastador.finalizarSubasta();
               vista.feedTextArea.setText(subastador.getSubasta().addToFeed("Subasta finalizada") );
               break;
           case "Cancelar subasta":
               System.out.println("Cancelar subasta");
               subastador.cancelarSubasta();
               vista.feedTextArea.setText(subastador.getSubasta().addToFeed("Subasta cancelada") );
               break;
       }
    }

    public SubastaSubastador getVista() {
        return vista;
    }
    
    
    
    
    
    
    
    
}
