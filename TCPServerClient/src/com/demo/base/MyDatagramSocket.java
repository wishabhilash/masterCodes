package com.demo.base;

import java.io.IOException;
import java.net.*;

import com.demo.config.Config;

public class MyDatagramSocket
{
    
    /**
     * @param args
     */
    protected DatagramSocket mySocket;
    
    public static void main(String[] args) throws Exception
    {
        // TODO Auto-generated method stub
        Config config = new Config();
        new MyDatagramSocket();
    }
    
    public MyDatagramSocket() throws Exception{
        mySocket = new DatagramSocket();
    }
    
    public void sendMessage(String msg, String IPAddr, int port) throws IOException{
        InetAddress IPAddress = InetAddress.getByName(IPAddr);
        byte[] sendData = new byte[10];
        sendData = msg.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        mySocket.send(sendPacket);
    }
    
    public String[] recieveMessage() throws IOException{
        String retData[] = new String[3];
        byte[] receiveData = new byte[10];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        mySocket.receive(receivePacket);
        retData[0] = new String(receivePacket.getData());
        retData[1] = receivePacket.getAddress().toString().substring(1);
        retData[2] = new Integer(receivePacket.getPort()).toString();
        return retData;
    }
    
    public void close(){
        mySocket.close();
    }
    
}
