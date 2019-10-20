/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casosockets;

import API.IObservable;
import API.IObserver;
import API.MensajeObject;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author maryp
 */
public class ObservableSubasta implements IAplicacion {
    private static ObservableSubasta singletonInstance;

    private Servidor hiloServidor;
    private Socket hiloCliente;
    private ObservableSubasta() {
    }
    public static ObservableSubasta getInstance()
    {
        if (singletonInstance == null)
            System.out.println("Instancia");
            singletonInstance = new ObservableSubasta();

            
        
        return singletonInstance;
    }

    public Servidor getHiloServidor() {
        return hiloServidor;
    }

    public void setHiloServidor(Servidor hiloServidor) {
        this.hiloServidor = hiloServidor;
    }

    public Socket getHiloCliente() {
        return hiloCliente;
    }

    public void setHiloCliente(Socket hiloCliente) {
        this.hiloCliente = hiloCliente;
    }
    
    public void evaluarInformacion(MensajeObject objeto){
            switch(objeto.getComando()){
                case "Agregar subasta":
                    CasoSockets.observables.put(objeto.getKey(), objeto.getObjeto());
                    System.out.println("....."+CasoSockets.observables.get(objeto.getKey()));
                    break;
                case "Colocar producto":
                case "Cancelar subasta":
                case "Cerrar subasta":
                    CasoSockets.observables.replace(objeto.getKey(), objeto.getObjeto());
                    System.out.println("Producto agregado con exitoso");
                    break;
                case "Aceptar oferta":
                    break;
                case "Enviar Subastas":
                    MensajeObject ms = new MensajeObject();
                    ms.setComando("Recibir subastas");
                   // ms.setObjeto(this.observables);
                    this.hiloServidor.enviarMensaje(objeto);
                    break;
                case "Enviar Subasta":
                    System.out.println("1");

                    MensajeObject mso = new MensajeObject();
                    String json =CasoSockets.observables.get(objeto.getKey());
                    System.out.println("2");

                    String key = objeto.getKey();
                    System.out.println("3");
                    mso.setComando("Recibir Subasta");
                    System.out.println("4");

                    System.out.println("Objeto"+objeto.getKey());
                    System.out.println("Observables"+CasoSockets.observables.containsKey(objeto.getKey()));
                    System.out.println("5");

                    mso.setObjeto(json);
                    System.out.println("6");

                    
                    mso.setKey(key);
                    System.out.println("7");
                    this.hiloServidor.enviarMensaje(mso);
                    System.out.println("8");
                    break;
                case "Enviar Mensaje Ganador":
                    break;
                default:
                    System.out.println("Error de comando");
            }
    }

}
