/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import redsocial.model.SeguidorConexion;
import redsocial.model.Vip;
import redsocial.model.VipConexion;

import redsocial.view.InicioView;
/**
 *
 * @author Angelo PC
 */
public class InicioController implements ActionListener{
    private InicioView vista;
    
    public InicioController(){
        vista = new InicioView();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        this.vista.buttonArtista.addActionListener(this);
        this.vista.buttonCliente.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    JButton button = (JButton) e.getSource();
    String user = vista.txtUsuario.getText();
    String ip = vista.txtIp.getText();

    if(user == null || user.isEmpty())
    {   
        JOptionPane.showMessageDialog(this.vista,"El usuario no puede ser nulo.");
        return;
    }
    
    if(ip == null || ip.isEmpty())
    {   
        JOptionPane.showMessageDialog(this.vista,"El ip del servidor no puede ser nulo.");
        return;
    }
    
    if (null != button) {
        if(button.getText().equals("Login Artista")){
            try {
                VipConexion vc = new VipConexion(ip,new Vip(user));
            } catch (Exception ex) {
                Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.vista.dispose();

        }
        else{
            try {
                SeguidorConexion sc = new SeguidorConexion(ip,user);
            } catch (Exception ex) {
                Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.vista.dispose();

        }
    }    
    }
}
