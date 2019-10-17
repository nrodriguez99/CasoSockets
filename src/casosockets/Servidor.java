/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package casosockets;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author naty9
 */
public class Servidor extends Thread {
    
    Socket serverClient;
    int clientNo;
    int squre;
  
    Servidor(Socket inSocket,int counter){
        serverClient = inSocket;
        clientNo=counter;
    }
    public void run(){
        try{
            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
            String clientMessage="", serverMessage="";
        while(!clientMessage.equals("bye")){
            clientMessage=inStream.readUTF();
            System.out.println("From Client-" +clientNo+ ": Number is :"+clientMessage);
            squre = Integer.parseInt(clientMessage) * Integer.parseInt(clientMessage);
            serverMessage="From Server to ClienServert-" + clientNo + " Square of " + clientMessage + " is " +squre;
            outStream.writeUTF(serverMessage);
            outStream.flush();
        }
        inStream.close();
        outStream.close();
        serverClient.close();
        }catch(Exception ex){
            System.out.println(ex);
        }finally{
            System.out.println("Client -" + clientNo + " exit!! ");
        }
    }
}
