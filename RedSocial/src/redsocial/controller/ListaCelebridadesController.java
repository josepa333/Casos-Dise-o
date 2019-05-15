/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import redsocial.view.ListaCelebridadesView;
import redsocial.model.Seguidor;
import redsocial.model.MensajeVip;
import redsocial.model.Vip;
/**
 *
 * @author Angelo PC
 */
public class ListaCelebridadesController implements MouseListener{
    
    private ListaCelebridadesView vista;
    private String idCliente;
    TablaCelebridad tablaCelebridades = new TablaCelebridad();
    TablaMensajes tablaMensajes = new TablaMensajes();

    
    public ListaCelebridadesController(String idCliente, String Ip) {
        this.idCliente = idCliente;
        vista = new ListaCelebridadesView();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        this.vista.TablaMensajes.addMouseListener(this);
        this.vista.TablaCelebridades.addMouseListener(this);
        cargarTabla(new ArrayList());
    }
    
    private void cargarTabla(ArrayList<Vip> datos){
        Vip tmpOferente = new Vip("Temporal");
        Vip tmpOferente2 = new Vip("Temporal 2");

        datos = new ArrayList(); //temporal
        datos.add(tmpOferente);
        datos.add(tmpOferente2);
        tablaCelebridades.ver_tabla(vista.TablaCelebridades, datos);
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
                    }
                    else{ //Dislike
                        System.out.println("dislike");
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
                    System.out.println("Siguiendo al artista :" + vista.TablaCelebridades.getValueAt(row, 0));}
                    else{
                        System.out.println("Viendo mensajes del artista :" + vista.TablaCelebridades.getValueAt(row, 0));
                        tablaMensajes.ver_tabla(vista.TablaMensajes, new ArrayList());
                    }
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