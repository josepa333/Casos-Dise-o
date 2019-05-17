/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.controller;

import client_server_API.Message;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import redsocial.model.MensajeVip;
import redsocial.model.Vip;
import redsocial.model.VipConexion;
import redsocial.view.CelebridadView;

/**
 *
 * @author Angelo PC
 */
public class CelebridadController implements ActionListener{
    private CelebridadView vista;   
    public VipConexion vc;
    public CelebridadController(String idCliente,VipConexion vc){
        this.vc = vc;
        vista = new CelebridadView();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        vista.labelNombre.setText(vista.labelNombre.getText() + " " + idCliente);
        this.vista.BotonEnviarMasaje.addActionListener(this);
        this.vista.botonDarseBaja.addActionListener(this);
        XStream xml = new XStream(new DomDriver());
        vc.sendMessage(new Message(2, xml.toXML(vc.vip),idCliente));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    JButton button = (JButton) e.getSource();
    
    if (null != button) {
        if(button.getText().equals("Darse de Baja")){
            vc.sendMessage(new Message(5, String.valueOf(vc.vip.getIdObservable()),vc.vip.getUser()));
        }
        else{
            String mensaje = vista.textoCuerpoMensaje.getText();
            if(mensaje == null || mensaje.isEmpty()){
                JOptionPane.showMessageDialog(this.vista,"El mensaje no puede estar vacio.");
                return;
            }
            System.out.println("Enviando mensaje...");
            ArrayList<String> datos = new ArrayList<>();
            datos.add(String.valueOf(vc.vip.getIdObservable()));
            datos.add(vc.vip.getUser() + ": "+vc.vip.addMensaje(mensaje));
            XStream xml = new XStream(new DomDriver());

            vc.sendMessage(new Message(6,xml.toXML(datos),vc.vip.getUser()));

        }
    }        
    }
}
