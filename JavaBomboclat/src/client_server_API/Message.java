/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client_server_API;

import java.io.Serializable;

/**
 *
 * @author Ricardo Bonilla
 */
public class Message  implements Serializable {
    private int type;
    private String content;
    private String user;

    public Message(int type, String content, String user) {
        this.type = type;
        this.content = content;
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "Correo: "  + this.user + "\tCuerpo: "  + this.content;
    }
}
