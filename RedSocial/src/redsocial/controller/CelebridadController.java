/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import redsocial.model.MensajeVip;
import redsocial.model.Vip;
import redsocial.view.CelebridadView;

/**
 *
 * @author Angelo PC
 */
public class CelebridadController implements ActionListener{
    private CelebridadView vista;
    private Vip user;
    
    public CelebridadController(String username, String ip){
        user = new Vip(username);
        vista = new CelebridadView();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        vista.labelNombre.setText(vista.labelNombre.getText() + " " + username);
        this.vista.BotonEnviarMasaje.addActionListener(this);
        this.vista.botonDarseBaja.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    JButton button = (JButton) e.getSource();
    
    if (null != button) {
        if(button.getText().equals("Darse de Baja")){
            InicioController ic = new InicioController();
            this.vista.dispose();
        }
        else{
            String mensaje = vista.textoCuerpoMensaje.getText();
            if(mensaje == null || mensaje.isEmpty()){
                JOptionPane.showMessageDialog(this.vista,"El mensaje no puede estar vacio.");
                return;
            }
            System.out.println("Enviando mensaje...");
            user.addMensaje(mensaje);
        }
    }        
    }
}
