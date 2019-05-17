/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subastas; 

import client_server_API.Message;
import java.awt.Component;
import javax.swing.JOptionPane;
import static subastas.SubastasServer.xstream;
import subastasViews.InicioSesion;


/**
 *
 * @author jose pablo
 */
public class Subastas {

    public static void main(String[] argv){
        InicioSesion ventanaPrincipal = new InicioSesion();
        ventanaPrincipal.show();
    }
}
