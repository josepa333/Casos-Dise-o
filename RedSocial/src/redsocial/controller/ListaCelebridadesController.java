/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.controller;

import client_server_API.Message;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import redsocial.view.ListaCelebridadesView;
import redsocial.model.Seguidor;
import redsocial.model.MensajeVip;
import redsocial.model.SeguidorConexion;
import redsocial.model.Vip;
/**
 *
 * @author Angelo PC
 */
public class ListaCelebridadesController implements MouseListener{
    
    public ListaCelebridadesView vista;
    private String idCliente;
    public TablaCelebridad tablaCelebridades = new TablaCelebridad();
    public TablaMensajes tablaMensajes = new TablaMensajes();
    public SeguidorConexion sc;
    private Integer observableActual = 0;

    
    public ListaCelebridadesController(String idCliente,SeguidorConexion sc) {
        this.idCliente = idCliente;
        vista = new ListaCelebridadesView();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        this.vista.TablaMensajes.addMouseListener(this);
        this.vista.TablaCelebridades.addMouseListener(this);
        this.sc = sc;
        sc.sendMessage(new Message(10,"",""));

    }

    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getSource() == vista.TablaMensajes){ //Click en la tabla de mensajes
        
            int column  = vista.TablaMensajes.getColumnModel().getColumnIndexAtX(e.getX());
            int row = e.getY()/vista.TablaMensajes.getRowHeight();

            if(row < vista.TablaMensajes.getRowCount() && row >= 0 && column<vista.TablaMensajes.getColumnCount() &&
                    column>=0){
                Object value  = vista.TablaMensajes.getValueAt(row,column);
                if(value instanceof JButton){
                    JButton boton = (JButton) value;
                    boton.doClick();
                    if(boton.getText().equals("Like")){ //Like
                        System.out.println("like");
                        ArrayList<String> datos = new ArrayList();
                        datos.add(String.valueOf(observableActual));
                        datos.add(String.valueOf(row));
                        XStream xml = new XStream(new DomDriver());
                        sc.sendMessage(new Message(3, xml.toXML(datos), idCliente));
                    }
                    else{ //Dislike
                        System.out.println("dislike");
                        ArrayList<String> datos = new ArrayList();
                        datos.add(String.valueOf(observableActual));
                        datos.add(String.valueOf(row));
                        XStream xml = new XStream(new DomDriver());
                        sc.sendMessage(new Message(4, xml.toXML(datos), idCliente));
                    }
                }
            }
        }
        else{ // Click en la tabla de celebridades
        
            int column  = vista.TablaCelebridades.getColumnModel().getColumnIndexAtX(e.getX());
            int row = e.getY()/vista.TablaCelebridades.getRowHeight();

            if(row < vista.TablaCelebridades.getRowCount() && row >= 0 && column<vista.TablaCelebridades.getColumnCount() &&
                    column>=0){
                Object value  = vista.TablaCelebridades.getValueAt(row,column);
                if(value instanceof JButton){
                    JButton boton = (JButton) value;
                    boton.doClick();
                    if(boton.getText().equals("Seguir")){
                    System.out.println("Siguiendo al artista :" + vista.TablaCelebridades.getValueAt(row, 0));
                    ArrayList<String> datos = new ArrayList();
                    datos.add((String)vista.TablaCelebridades.getValueAt(row, 2));
                    datos.add((String)vista.TablaCelebridades.getValueAt(row, 0));
                    XStream xml = new XStream(new DomDriver());
                    sc.sendMessage(new Message(1, xml.toXML(datos), idCliente));
                    }
                    else{
                        System.out.println("Viendo mensajes del artista :" + vista.TablaCelebridades.getValueAt(row, 0));
                        sc.sendMessage(new Message(9, (String)vista.TablaCelebridades.getValueAt(row, 2), idCliente));
                    }
                    observableActual = Integer.parseInt((String)vista.TablaCelebridades.getValueAt(row, 2));
                }
            }
        }
    } 

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}