package com.demo.clients;

import java.net.*;
import com.demo.config.*;
import com.demo.base.MyDatagramSocket;

public class UDPClient extends MyDatagramSocket implements Runnable
{
    
    /**
     * @param args
     */
    String message = "01234567890";
    int port;
    
    public static void main(String[] args) throws Exception
    {
        // TODO Auto-generated method stub
        
        Thread client1 = new Thread(new UDPClient(9000));
//        Thread client1 = new Thread(new UDPClient(9090));
        client1.start();
//        client2.start();
    }
    
    
    public UDPClient(int port) throws Exception{
        this.port = port;
    }
    
    public void run(){
        Config config = new Config();
        try{
            mySocket = new DatagramSocket();
            while(true){
                this.sendMessage(this.message,config.IPAddr, port);
                String recievedMessage[] = this.recieveMessage();
                System.out.println("From server: " + recievedMessage[0] + " Port: " + port);
                Thread.sleep(3000);

                
                //  Uncomment to stop sending message to more than 1 recievers. 
//                this.sendMessage(this.message,config.IPAddr, port+90);
//                recievedMessage = this.recieveMessage();
//                System.out.println("From server: " + recievedMessage[0] + " Port: " + (port+90));
//                Thread.sleep(3000);
            }
        }catch (Exception e) {
            // TODO: handle exception
            this.close();
            e.printStackTrace();
        }
    }
    
}
