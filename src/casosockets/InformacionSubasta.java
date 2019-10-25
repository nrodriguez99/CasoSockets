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
 * @author maryp
 */
public class InformacionSubasta   {
    
    private static InformacionSubasta singletonInstance;
    private String subasta;
    private HiloServidor hiloServidor;
    private InformacionSubasta() {
    }
    public static InformacionSubasta getInstance()
    {
        if (singletonInstance == null)
            singletonInstance = new InformacionSubasta();

            
        
        return singletonInstance;
    }

    public HiloServidor getHiloServidor() {
        return hiloServidor;
    }

    public void setHiloServidor(HiloServidor hiloServidor) {
        this.hiloServidor = hiloServidor;
    }


    public String jsonSubastas(){
         final Gson gson = new Gson();
         final String representacionJSON = gson.toJson(CasoSockets.observables);
         return representacionJSON;
    }
    public void evaluarInformacion(MensajeObject objeto){
        System.out.println(objeto.getComando());
            switch(objeto.getComando()){
                case "Agregar subasta":
                    CasoSockets.observables.put(objeto.getKey(), (String)objeto.getObjeto());
                    objeto.setComando("notificacion observador");
                    String nuevoObjeto = jsonSubastas();
                    objeto.setObjeto(nuevoObjeto);
                    this.hiloServidor.notifyAllObservers(objeto, CasoSockets.observadores);
                    System.out.println("Subasta agregada con éxito");
                    break;
                case "Colocar producto":
                case "Cancelar Subasta":
                case "Cerrar Subasta":
                   CasoSockets.observables.replace(objeto.getKey(),(String) objeto.getObjeto());
                    System.out.println("Accion realizada con éxito");
                    objeto.setComando("notificacion observador");
                    String nuevoObjeto1 = jsonSubastas();
                    objeto.setObjeto(nuevoObjeto1);
                    this.hiloServidor.notifyAllObservers(objeto,CasoSockets.observadores);                  
                    break;
                case "Aceptar oferta":
                case "Rechazar oferta":
                    CasoSockets.observables.replace(objeto.getKey(), (String)objeto.getObjeto());
                    objeto.setComando("notificacion observador");
                    String nuevoObjeto4 = jsonSubastas();
                    objeto.setObjeto(nuevoObjeto4);
                    this.hiloServidor.notifyAllObservers(objeto,CasoSockets.observadores);
                    System.out.println("Accion realizada con éxito 2");
                    break;
                case "Enviar Mensaje Ganador":
                    objeto.setComando("enviar mensaje ganador");
                    System.out.println("Enviar mensaje");
                    this.hiloServidor.notifyAllObservers(objeto,CasoSockets.observadores);
                    break;
                case "Conectar Oferente":
                    String nuevoObjeto2 = jsonSubastas();
                    objeto.setObjeto(nuevoObjeto2);
                    objeto.setComando("notificacion observador");
                    this.hiloServidor.notifyAllObservers(objeto,CasoSockets.observadores);
                    System.out.println("Conectar oferente");
                    break;
                case "Añadir oferta":
                    CasoSockets.observables.replace(objeto.getKey(),(String)objeto.getObjeto());
                    objeto.setComando("notificacion observador");
                    String nuevoObjeto3 = jsonSubastas();
                    objeto.setObjeto(nuevoObjeto3);
                    this.hiloServidor.notifyAllObservers(objeto,CasoSockets.observadores);
                    break;
                default:
                    System.out.println("Error de comando");
            }
    }






}
