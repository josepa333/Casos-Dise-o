package subastasViews;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import subastas.Subastador;

public class Tabla {
    
    public void ver_tabla(JTable pTabla,ArrayList<Subastador> subastadores){
        
        pTabla.setDefaultRenderer(Object.class, new Render());
        DefaultTableModel tablaPredeterminada = new DefaultTableModel(){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        tablaPredeterminada.addColumn("Subastador");
        tablaPredeterminada.addColumn("Producto");
        tablaPredeterminada.addColumn("Precio");
        tablaPredeterminada.addColumn("Estado");
        tablaPredeterminada.addColumn("Unirse");

        JButton botonUnirse = new JButton("Unirse");

        
        Object fila[] = new Object[5];
        
            
        if(subastadores.size() > 0){
            for(int i=0; i<subastadores.size(); i++){
                fila[0] = subastadores.get(i).getIdSubastador();
                fila[1] = subastadores.get(i).getSubasta().getProducto().getNombre();
                fila[2] = subastadores.get(i).getSubasta().getProducto().getPrecioFinal();
                fila[3] = subastadores.get(i).getSubasta().getStatus();
                fila[4] = botonUnirse;
                tablaPredeterminada.addRow(fila);
            }
        }
        else{
            fila[0] = "";
            fila[1] = "";
            fila[2] = "";
            fila[3] = "";
            fila[4] = botonUnirse;
            tablaPredeterminada.addRow(fila);
        }
        pTabla.setModel(tablaPredeterminada);
        pTabla.setRowHeight(30);
    }
}
