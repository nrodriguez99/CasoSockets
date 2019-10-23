/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casosockets;

import API.MensajeObject;
import com.google.gson.Gson;

/**
 *
 * @author derec
 */
public class InfoRedes{
    
    private static InfoRedes singleton;
    private String vip;
    private HiloServidor hiloServidor;
    private InfoRedes() {
    }
    public static InfoRedes getInstance()
    {
        if (singleton == null)
            singleton = new InfoRedes();

            
        
        return singleton;
    }

    public HiloServidor getHiloServidor() {
        return hiloServidor;
    }

    public void setHiloServidor(HiloServidor hiloServidor) {
        this.hiloServidor = hiloServidor;
    }


    public String jsonRedes(){
        final Gson gson = new Gson();
        final String representacionJSON = gson.toJson(CasoSockets.observables);
        return representacionJSON;
    }
    public void evaluarInformacion(MensajeObject objeto){
        switch(objeto.getComando()){
            case "Crear VIP":
                CasoSockets.observables.put(objeto.getKey(), objeto.getObjeto());
                objeto.setComando("notificacion observador");
                String nuevoObjeto = jsonRedes();
                objeto.setObjeto(nuevoObjeto);
                this.hiloServidor.notifyAllObservers(objeto, CasoSockets.observadores);
                System.out.println("VIP agregado con éxito");
                break;
            case "Postear mensaje":
                break;
            case "Ver Mensajes":
                break;
            case "Seguir VIP":
                break;
            case "Ver actualizaciones":
                break;
            case "Reaccionar a mensaje":
                break;
            case "Conectar Oferente":
                String nuevoObjeto2 = jsonRedes();
                objeto.setObjeto(nuevoObjeto2);
                objeto.setComando("notificacion observador");
                this.hiloServidor.notifyAllObservers(objeto,CasoSockets.observadores);
                break;
            default:
                System.out.println("Error de comando");
        }
    
    }
}
