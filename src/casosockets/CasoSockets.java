/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casosockets;

import API.IObservable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author naty9
 */
public class CasoSockets {
    public static HashMap<String, String> observables;
    public static HashMap<String, Socket> observer;
    
    public CasoSockets(){
        observables = new HashMap<>();
        observer = new HashMap<>();
    }
    public static void main(String[] args) throws Exception {
        new CasoSockets();
        try{
            ServerSocket server=new ServerSocket(8888);
            int counter=0;
            System.out.println("Server Started ....");
            ObservableSubasta.getInstance();
            while(true){
                counter++;
                Socket serverClient=server.accept();  //server accept the client connection request
                System.out.println(" >> " + "Client No:" + counter + " started!");
                Servidor sct = new Servidor(serverClient,counter); //send  the request to a separate thread
                sct.start();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
}
