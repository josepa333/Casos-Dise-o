/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas;

import java.io.IOException;

/**
 *
 * @author jose pablo
 */
public interface IServidor {
    
     public void iniciarServidor();
     public void esperarConexion() throws IOException;
     public void obtenerFlujos() throws IOException;
     public void cerrarConexion() throws IOException;
     public void terminarServidor() throws IOException;
     public void procesarConexion() throws IOException, ClassNotFoundException;
     
}
