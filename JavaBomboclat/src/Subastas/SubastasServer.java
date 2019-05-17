/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Subastas;
import client_server_API.*;
import client_server_API.Server;
/**
 *
 * @author Ricardo Bonilla
 */
public class SubastasServer extends Server{
    public SubastasServer(int serverPort) throws Exception{
        super(serverPort);
    }
    
    public static void main(String ... args) throws Exception {
        Server server = Server.getInstance(9999);
    } // end of main
}
