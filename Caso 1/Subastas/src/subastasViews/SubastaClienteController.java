/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastasViews;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import subastas.Oferente;


public class SubastaClienteController implements ActionListener{

    private SubastaCliente vista;
    private Oferente modelo;
    private int idSubasta;
     
            
    public SubastaClienteController(String idCliente, Oferente oferente, int idSubasta) {
        vista = new SubastaCliente();
        vista.show();
        vista.ofertarButton.addActionListener(this);
        this.modelo = oferente;
        this.idSubasta = idSubasta;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
           case "Ofertar":
               System.out.println("Ofertar");
               modelo.enviarOferta(vista.valorOferta.getText(), idSubasta );
               break;
       }
    }

    public SubastaCliente getVista() {
        return vista;
    }

    public Oferente getModelo() {
        return modelo;
    }

    public int getIdSubasta() {
        return idSubasta;
    }
    
    
}
