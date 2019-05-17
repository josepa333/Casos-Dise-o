package redsocial.controller;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import redsocial.model.MensajeVip;

public class TablaMensajes {
    
    public void ver_tabla(JTable pTabla,ArrayList<String> mensajes){
        
        pTabla.setDefaultRenderer(Object.class, new Render());
        DefaultTableModel tablaPredeterminada = new DefaultTableModel(){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        tablaPredeterminada.addColumn("Mensaje");
        tablaPredeterminada.addColumn("Like");
        tablaPredeterminada.addColumn("Dislike");
        JButton botonLike = new JButton("Like");
        JButton botonDislike = new JButton("Dislike");
        
        Object fila[] = new Object[3];
            
        if(mensajes.size() > 0){
            for(int i=0; i<mensajes.size(); i++){
                fila[0] = mensajes.get(i);
                fila[1] = botonLike;
                fila[2] = botonDislike;
                tablaPredeterminada.addRow(fila);
            }
        }
        else{
            fila[0] = "";
            fila[1] = botonLike;
            fila[2] = botonDislike;
            tablaPredeterminada.addRow(fila);
        }
        pTabla.setModel(tablaPredeterminada);
        pTabla.setRowHeight(30);
        pTabla.repaint();

    }
}
