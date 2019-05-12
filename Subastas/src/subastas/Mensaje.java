package subastas;

import java.io.Serializable;

public class Mensaje  implements Serializable {
    /**
     * Atributos
     */
    private TipoMensaje tipo;
    private String cuerpo;
    private String usuario;
    
    // Constructor
    public Mensaje() {
        this.tipo = TipoMensaje.UNKNOWN;
    }
    /**
     * Constructor
     * @param tipo
     * @param usuario
     * @param cuerpo 
     */
    public Mensaje(TipoMensaje tipo,String usuario, String cuerpo) {
        this.tipo = tipo;
        this.usuario = usuario;
        this.cuerpo = cuerpo;
    }
    
    // Tipo de mensaje
    public void setTipo(TipoMensaje tipo) {
        this.tipo = tipo;
    }
    public TipoMensaje getTipo() {
        return this.tipo;
    }
    
    // Cuerpo del mensaje
    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
    public String getCuerpo() {
        return this.cuerpo;
    }
    
    // Usuario
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getUsuario() {
        return this.usuario;
    }
    
    @Override
    public String toString(){
        return "Correo: "  + usuario + "Cuerpo: "  + cuerpo;
    }
}
