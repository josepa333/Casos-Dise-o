/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastasViews;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import subastas.Oferente;
import subastas.Subastador;


public class SubastasController implements MouseListener{
    
    private ListaSubastas vista;
    private String idCliente;
    Tabla tablaBase = new Tabla();
    private ArrayList<Subastador> subastadores;
    
    public SubastasController(String idCliente) {
        this.idCliente = idCliente;
        vista = new ListaSubastas();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        this.vista.TablaSubastas.addMouseListener(this);
        vista.idUsuario.setText(idCliente);
        cargarTabla();
    }
    
    private void cargarTabla(){
        Oferente tmpOferente = new Oferente("Temporal", null);
        subastadores = tmpOferente.cargarSubastadores();
        tablaBase.ver_tabla(vista.TablaSubastas, subastadores);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        int clic_tabla = vista.TablaSubastas.rowAtPoint(e.getPoint());
        String codigo = (String)vista.TablaSubastas.getValueAt(clic_tabla, 1);
        System.out.println(codigo);
        
        int column  = vista.TablaSubastas.getColumnModel().getColumnIndexAtX(e.getX());
        int row = e.getY()/vista.TablaSubastas.getRowHeight();
        
        if(row < vista.TablaSubastas.getRowCount() && row >= 0 && column<vista.TablaSubastas.getColumnCount() &&
                column>=0){
            Object value  = vista.TablaSubastas.getValueAt(row,column);
            if(value instanceof JButton){
                ((JButton) value).doClick();
                JButton boton = (JButton) value;
                switch (column) {
                    case 4:
                        System.out.println("Print heehee"); 
                         SubastaClienteController controler = new SubastaClienteController(idCliente,subastadores.get(row).getIdSubastador() );
                        break; 
                }
                System.out.println(row);
            }
        }
    } 

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("No tiene que ser implementado");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("No tiene que ser implementado");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("No tiene que ser implementado");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("No tiene que ser implementado");
    }
}