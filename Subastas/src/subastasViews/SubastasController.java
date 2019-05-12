/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastasViews;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author jose pablo
 */
public class SubastasController implements MouseListener{

    
    private ListaSubastas vista;
    
    
    public SubastasController() {
        vista = new ListaSubastas();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        this.vista.TablaSubastas.addMouseListener(this);
        
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
                         //abrir la ventana de subasta  
                        break;
                }
                System.out.println(row);
            }
        }
    } 

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
