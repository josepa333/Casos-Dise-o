/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.controller;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author jose pablo
 * @author ruben
 */
public class Render extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        if(value instanceof JButton){
            JButton boton = (JButton) value;
            if(isSelected){
                boton.setForeground(table.getSelectionForeground());
                boton.setBackground(table.getSelectionBackground());
            }else{
                boton.setForeground(table.getForeground());
                boton.setBackground(UIManager.getColor("Button.background"));
            }
            return boton;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }
}
