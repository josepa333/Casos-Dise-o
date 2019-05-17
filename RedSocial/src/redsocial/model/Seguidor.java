/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsocial.model;

import client_server_API.Client;
import client_server_API.Message;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

/**
 *
 * @author Angelo PC
 */
public class Seguidor extends Client{
    private String idSeguidor;

    public Seguidor(String idSeguidor,ObjectOutputStream output, InetAddress inetAddress) {
        super(output,inetAddress);
        this.idSeguidor = idSeguidor;
    }

    public String getIdSeguidor() {
        return idSeguidor;
    }

    public void setIdSeguidor(String idSeguidor) {
        this.idSeguidor = idSeguidor;
    }
}
