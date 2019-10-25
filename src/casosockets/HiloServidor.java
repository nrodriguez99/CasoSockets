/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casosockets;

import API.IObservable;
import API.IObserver;
import API.MensajeObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import jdk.internal.org.objectweb.asm.util.Printer;

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
    public void setOs(ObjectOutputStream o){
        this.os = o;
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
                    addObserver(os);
                    InformacionSubasta v = InformacionSubasta.getInstance();
                    v.setHiloServidor(this);
                    v.evaluarInformacion(mensaje);  
                    System.out.println("Solicitud recibida..");
                    break;

                case "RedSocial":
                    System.out.println("Solicitud recibida..");
                    InfoRedes r = InfoRedes.getInstance();
                    r.setHiloServidor(this);
                    if(mensaje.getComando().equals("Crear Follower")||mensaje.getComando().equals("Crear VIP")){addObserver(os);}
                    r.evaluarInformacion(mensaje,os);
                    
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
    public void notifyAllObservers(MensajeObject mensaje, ArrayList<Object> observadores) {
        for(Object o: observadores){
            try {
                ObjectOutputStream obj = (ObjectOutputStream)o;
                obj.writeObject(mensaje);
               // os.newLine();
                obj.flush();
            } catch (IOException ex) {
                Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }     
    }
    
    public void sendObjectStream(ObjectOutputStream obj){
        try{
            os.writeObject(obj);
            os.flush();
        } catch (IOException ex) {
                Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
    }



}
