/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casosockets;

import API.AbstractObservable;
import API.IObservable;
import API.IObserver;
import API.Json;
import API.MensajeObject;
import com.google.gson.Gson;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author maryp
 */
public class InformacionSubasta   {
    private  HashMap<String, String> observables;
    private static InformacionSubasta singletonInstance;
    private String subasta;
    private HiloServidor hiloServidor;
    private InformacionSubasta() {
    }
    public static InformacionSubasta getInstance()
    {
        if (singletonInstance == null)
            System.out.println("Instancia");
            singletonInstance = new InformacionSubasta();
            singletonInstance.observables = new HashMap<>();

            
        
        return singletonInstance;
    }

    public HiloServidor getHiloServidor() {
        return hiloServidor;
    }

    public void setHiloServidor(HiloServidor hiloServidor) {
        this.hiloServidor = hiloServidor;
    }


    
    public void evaluarInformacion(MensajeObject objeto){
            switch(objeto.getComando()){
                case "Agregar subasta":
                    System.out.println("Llave"+objeto.getKey());
                    observables.put(objeto.getKey(), objeto.getObjeto());
                    System.out.println("Subasta agregada con éxito");
                    this.hiloServidor.notifyAllObservers(objeto);
                    break;
                case "Colocar producto":
                case "Cancelar Subasta":
                case "Cerrar Subasta":
                   observables.replace(objeto.getKey(), objeto.getObjeto());
                    System.out.println("Accion realizada con éxito");
                    this.hiloServidor.notifyAllObservers(objeto);
                    
                    break;
                case "Aceptar oferta":
                    break;
                case "Enviar Subastas":
                    MensajeObject ms = new MensajeObject();
                    ms.setComando("Recibir subastas");
                    ms.setKey("todas las subastas");
                    final Gson gson = new Gson();
                    final String representacionJSON = gson.toJson(observables);
                    ms.setObjeto(representacionJSON);
                    this.hiloServidor.enviarMensaje(ms);
                    System.out.println("Subastas envíadas con éxito");
                    break;
                case "Enviar Subasta":
                    MensajeObject mso = new MensajeObject();
                    String json = objeto.getKey();
                    String key = objeto.getKey();
                    mso.setComando("Recibir Subasta");
                    mso.setObjeto(json);
                    mso.setKey(key);
                    this.hiloServidor.enviarMensaje(mso);
                    System.out.println("Subasta enviada con éxito");
                    break;
                case "Enviar Mensaje Ganador":
                    break;
                default:
                    System.out.println("Error de comando");
            }
    }






}
