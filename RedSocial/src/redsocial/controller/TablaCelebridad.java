package redsocial.controller;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import redsocial.model.Vip;

public class TablaCelebridad {
    
    public void ver_tabla(JTable pTabla,ArrayList<Vip> celebridades){
        
        pTabla.setDefaultRenderer(Object.class, new Render());
        DefaultTableModel tablaPredeterminada = new DefaultTableModel(){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        tablaPredeterminada.addColumn("Nombre Celebridad");
        tablaPredeterminada.addColumn("Cantidad Seguidores");
        tablaPredeterminada.addColumn("Seguir");
        tablaPredeterminada.addColumn("Ver Mensajes");
        JButton botonSeguir = new JButton("Seguir");
        JButton botonVerMensajes = new JButton("Ver");
        
        Object fila[] = new Object[4];
            
        if(celebridades.size() > 0){
            for(int i=0; i<celebridades.size(); i++){
                fila[0] = celebridades.get(i).getId();
                fila[1] = celebridades.get(i).getSeguidores().size();
                fila[2] = botonSeguir;
                fila[3] = botonVerMensajes;
                tablaPredeterminada.addRow(fila);
            }
        }
        else{
            fila[0] = "";
            fila[1] = "";
            fila[2] = botonSeguir;
            fila[3] = botonVerMensajes;
            tablaPredeterminada.addRow(fila);
        }
        pTabla.setModel(tablaPredeterminada);
        pTabla.setRowHeight(30);
        pTabla.repaint();
    }
}
