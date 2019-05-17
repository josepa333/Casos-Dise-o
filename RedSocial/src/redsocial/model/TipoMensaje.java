package redsocial.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lpsotoHP
 */
public enum TipoMensaje {
    SEGUIRVIP(1), DEJARSEGUIRVIP(2), LIKEMENSAJE(3), DISLIKEMENSAJE(4), DARSEBAJA(5), 
    POSTEARMENSAJE(6), NOTIFICACIONNIVEL(7), NOTIFICACIONMENSAJE(8);
    
    
    private int value;
    private static final Map map = new HashMap<>();

    private TipoMensaje(int value) {
        this.value = value;
    }

    static {
        for (TipoMensaje pageType : TipoMensaje.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static TipoMensaje valueOf(int pageType) {
        return (TipoMensaje) map.get(pageType);
    }

    public int getValue() {
        return value;
    }
}