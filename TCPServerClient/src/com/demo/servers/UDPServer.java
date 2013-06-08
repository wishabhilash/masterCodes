package com.demo.servers;

import java.net.*;

import com.demo.base.MyDatagramSocket;
import com.demo.config.Config;

public class UDPServer extends MyDatagramSocket implements Runnable
{
    
    /**
     * @param args
     */
    String message = "abcdefghij", recievedMessage[];
    int port;
    
    public static void main(String[] args) throws Exception
    {
        // TODO Auto-generated method stub
        Config config = new Config();
        Thread server1 = new Thread(new UDPServer(9000));
        Thread server2 = new Thread(new UDPServer(9090));
        
        server1.start();
        server2.start();
    }
    
    public UDPServer(int port) throws Exception{   
        this.port = port;
    }
    
    public void run(){
        Config config = new Config();
        try{
            mySocket = new DatagramSocket(this.port);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        //  TIMEOUT
//        mySocket.setSoTimeout(config.timeout);

        //  NON-INFINITE
//        try{
//            recievedMessage = this.recieveMessage();
//            System.out.println("From client: " + recievedMessage[0] + " " + recievedMessage[1]);
//            this.sendMessage(this.message, recievedMessage[1], Integer.parseInt(recievedMessage[2]));
//            this.close();
//        }catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }

        //  INFINITE LOOP
        try{
            while(true){
                recievedMessage = this.recieveMessage();
                System.out.println("From client: " + recievedMessage[0] + " " + recievedMessage[1]);
                this.sendMessage(this.message, recievedMessage[1], Integer.parseInt(recievedMessage[2]));
            }
        }catch (Exception e) {
            // TODO: handle exception
            this.close();
            e.printStackTrace();
        }
    }
}
