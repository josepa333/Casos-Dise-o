package redsocial.controller;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import redsocial.model.MensajeVip;

public class TablaMensajes {
    
    public void ver_tabla(JTable pTabla,ArrayList<MensajeVip> mensajes){
        
        pTabla.setDefaultRenderer(Object.class, new Render());
        DefaultTableModel tablaPredeterminada = new DefaultTableModel(){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        tablaPredeterminada.addColumn("Fecha");
        tablaPredeterminada.addColumn("Mensaje");
        tablaPredeterminada.addColumn("Likes");
        tablaPredeterminada.addColumn("Dislikes");
        tablaPredeterminada.addColumn("Like");
        tablaPredeterminada.addColumn("Dislike");
        JButton botonLike = new JButton("Like");
        JButton botonDislike = new JButton("Dislike");
        
        Object fila[] = new Object[6];
            
        if(mensajes.size() > 0){
            for(int i=0; i<mensajes.size(); i++){
                fila[0] = mensajes.get(i).getFecha().toString();
                fila[1] = mensajes.get(i).getContenido();
                fila[2] = mensajes.get(i).getLikes();
                fila[3] = mensajes.get(i).getDislikes();
                fila[4] = botonLike;
                fila[5] = botonDislike;
                tablaPredeterminada.addRow(fila);
            }
        }
        else{
            fila[0] = "";
            fila[1] = "";
            fila[2] = "";
            fila[3] = "";
            fila[4] = botonLike;
            fila[5] = botonDislike;
            tablaPredeterminada.addRow(fila);
        }
        pTabla.setModel(tablaPredeterminada);
        pTabla.setRowHeight(30);
        pTabla.repaint();

    }
}
