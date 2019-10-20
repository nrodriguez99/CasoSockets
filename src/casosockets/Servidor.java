/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casosockets;

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
import java.io.Serializable;

/**
 *
 * @author maryp
 */
public class Servidor extends Thread implements Serializable, Runnable {
    Socket socket;
    ObjectInputStream is;
    ObjectOutputStream os;
    int clientNo;
    Servidor(Socket inSocket,int counter){
        socket = inSocket;
        clientNo=counter;
        crearFlujos();
    }
    public void crearFlujos(){
        try{
           is = new ObjectInputStream(socket.getInputStream());
           os= new ObjectOutputStream(socket.getOutputStream());

        }catch(IOException ex){
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public MensajeObject recibirObjeto(){
        MensajeObject mensaje=new MensajeObject();
        try{
            ObjectInputStream is1 = new ObjectInputStream(socket.getInputStream());
            mensaje = (MensajeObject) is1.readObject();
            System.out.println("Recibir mensaje: "+mensaje.getObjeto());
            return mensaje;
        }catch(IOException ex){
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje;
    }

    public void enviarMensaje(MensajeObject mensaje){
        try {
            os.writeObject(mensaje);
           // os.newLine();
            os.flush();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        while(true){
            MensajeObject mensaje = recibirObjeto();

            switch (mensaje.getNombreAplcacion()){
                case "Subasta":
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    ObservableSubasta v = ObservableSubasta.getInstance();
                    v.setHiloCliente(socket);
                    v.setHiloServidor(this);
                    v.evaluarInformacion(mensaje);  
                    System.out.println("Subasta creada");
                    break;

                case "RedSocial":
                default:
                    System.out.println("Si le llega los mensajes al servidor");
                    break;
                
                    
            }
        }
    }
}
