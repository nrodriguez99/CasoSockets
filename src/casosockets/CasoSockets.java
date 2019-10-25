/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casosockets;

import API.AbstractObservable;
import API.IObservable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author naty9
 */
public class CasoSockets  {
    public static ArrayList<Object> observadores;
    public static  HashMap<String, String> observables;
    public CasoSockets(){
        observadores = new ArrayList<>();
        observables = new HashMap<>();
        go();   
    }
    public static void main(String[] args) throws Exception {
        
        CasoSockets cs =new CasoSockets();
        

    }
    public void go(){
       try{
            ServerSocket server=new ServerSocket(8888);
            int counter=0;
            System.out.println("Server Started ....");
            while(true){
                counter++;
                Socket serverClient=server.accept();  //server accept the client connection request
                System.out.println(" >> " + "Client No:" + counter + " started!");
                HiloServidor sct = new HiloServidor(serverClient,counter); 
                sct.setOs(sct.os);
                //sct.addObserver(sct.os);
                //send  the request to a separate thread
                sct.start();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    
}
