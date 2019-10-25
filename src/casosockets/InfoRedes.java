/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casosockets;

import API.MensajeObject;
import com.google.gson.Gson;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

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
    public void evaluarInformacion(MensajeObject objeto,ObjectOutputStream o){
        String nuevoObjeto;
        switch(objeto.getComando()){
            case "Crear VIP":
                CasoSockets.observables.put(objeto.getKey(), (String)objeto.getObjeto());
                objeto.setComando("notificacion observador");
                nuevoObjeto = jsonRedes();
                objeto.setObjeto(nuevoObjeto);
                this.hiloServidor.notifyAllObservers(objeto, CasoSockets.observadores);
                System.out.println("VIP agregado con éxito");
                break;
            case "Postear mensaje":
                CasoSockets.observables.replace(objeto.getKey(), (String)objeto.getObjeto());
                objeto.setComando("notificacion observador");
                nuevoObjeto = jsonRedes();
                objeto.setObjeto(nuevoObjeto);
                this.hiloServidor.notifyAllObservers(objeto, CasoSockets.observadores);
                System.out.println("Mensaje posteado!");
                break;
                
            case "Ver Mensajes":
                CasoSockets.observables.replace(objeto.getKey(), (String)objeto.getObjeto());
                objeto.setComando("notificacion observador");
                nuevoObjeto = jsonRedes();
                objeto.setObjeto(nuevoObjeto);
                this.hiloServidor.notifyAllObservers(objeto, CasoSockets.observadores);
                System.out.println("Notificado!");

                break;
            case "Seguir VIP":
                CasoSockets.observables.replace(objeto.getKey(), (String)objeto.getObjeto());
                objeto.setComando("notificacion observador");
                nuevoObjeto = jsonRedes();
                objeto.setObjeto(nuevoObjeto);
                this.hiloServidor.notifyAllObservers(objeto, CasoSockets.observadores);
                System.out.println("VIP seguido!");
                break;
                
            case "Reaccionar a mensaje":
                CasoSockets.observables.replace(objeto.getKey(), (String)objeto.getObjeto());
                objeto.setComando("notificacion observador");
                nuevoObjeto = jsonRedes();
                objeto.setObjeto(nuevoObjeto);
                this.hiloServidor.notifyAllObservers(objeto, CasoSockets.observadores);
                System.out.println("Reaccion guardada!");
                break;
            case "Crear Follower":
                objeto.setComando("notificacion observador");
                nuevoObjeto = jsonRedes();
                objeto.setObjeto(nuevoObjeto);
                this.hiloServidor.notifyAllObservers(objeto, CasoSockets.observadores);
                System.out.println("Follower agregado con éxito");
                break;
               
            default:
                System.out.println("Error de comando");
        }
       
    
    }
}
