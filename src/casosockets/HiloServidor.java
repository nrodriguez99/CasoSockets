/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casosockets;

import API.IObservable;
import API.MensajeObject;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author maryp
 */
public class HiloServidor extends Thread implements IObservable,Runnable {
    Socket socket;
    ObjectInputStream is;
    ObjectOutputStream os;

    int clientNo;
    public HiloServidor(Socket inSocket,int counter){
        socket = inSocket;
        clientNo=counter;
        crearFlujos();
    }
    public void crearFlujos(){
        try{
           is = new ObjectInputStream(socket.getInputStream());
           os= new ObjectOutputStream(socket.getOutputStream());
           

        }catch(IOException ex){
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public MensajeObject recibirObjeto(){
        MensajeObject mensaje=new MensajeObject();
        try{
            ObjectInputStream is1 = new ObjectInputStream(socket.getInputStream());
            mensaje = (MensajeObject) is1.readObject();
            return mensaje;
        }catch(IOException ex){
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

    public void enviarMensaje(MensajeObject mensaje){
        try {
            os.writeObject(mensaje);
           // os.newLine();
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        while(true){
            MensajeObject mensaje = recibirObjeto();

            switch (mensaje.getNombreAplcacion()){
                case "Subasta":
                    InformacionSubasta v = InformacionSubasta.getInstance();
                    v.setHiloServidor(this);
                    v.evaluarInformacion(mensaje);  
                    break;

                case "RedSocial":
                    InfoRedes r = InfoRedes.getInstance();
                    r.setHiloServidor(this);
                    r.evaluarInformacion(mensaje);
                    System.out.println("Solicitud recibida..");

                    break;
                default:
                    System.out.println("Error");

                    break;
                
                    
            }
        }
    }

    @Override
    public void addObserver(ObjectOutputStream observer) {
        CasoSockets.observadores.add(observer);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeObserver(ObjectOutputStream observer) {
        CasoSockets.observadores.remove(observer);
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyAllObservers(MensajeObject mensaje, ArrayList<ObjectOutputStream> observadores) {
        for(ObjectOutputStream o: observadores){
            try {
                o.writeObject(mensaje);
               // os.newLine();
                o.flush();
            } catch (IOException ex) {
                Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }     
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
