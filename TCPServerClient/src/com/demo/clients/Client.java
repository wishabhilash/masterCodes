package com.demo.clients;

import java.io.*;
import java.net.*;
import com.demo.config.*;

public class Client {

	/**
	 * @param args
	 */
	Socket client = null;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		// TODO Auto-generated method stub
		Client myClient = new Client();
		myClient.run(args[0]);
	}

	void clientStub(int a, String b){
		
	}
	
	void run(String msg) throws IOException, ClassNotFoundException{
		Config config = new Config();
		client = new Socket(config.IPAddr, config.port);
		
		out = new ObjectOutputStream(client.getOutputStream());
		
		sendMessage(msg);
		
		in = new ObjectInputStream(client.getInputStream());
		readMessage();
		
		closeConnection();
	}
	
	void sendMessage(String msg) throws ClassNotFoundException, IOException{
		out.writeObject(msg);
		out.flush();
	}

	void readMessage() throws IOException, ClassNotFoundException{
		System.out.println((String)in.readObject());
	}
	
	void closeConnection() throws IOException{
		in.close();
		out.close();
		client.close();
	}
}
