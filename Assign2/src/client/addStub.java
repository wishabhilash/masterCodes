package client;

import java.io.*;
import java.net.*;

public class addStub {

	Socket client = null;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	String signParam = "int a, int b";
	
	public static void main(String args[]) throws ClassNotFoundException, IOException{
		addStub m = new addStub();
		System.out.println(m.add(1,10));
	}
	
	float add(int a, int b) throws ClassNotFoundException, IOException{
		String msg = "funcName:" + "add" + ";"
		+ "returnDT:" + "float" + ";"
		+ "args:" + "Integer " + new Integer(a).toString() + "," + "Integer " + new Integer(b).toString();
		
		run(msg);
		return 0;
	}
	
	void run(String msgObj) throws IOException, ClassNotFoundException{
		
		client = new Socket("localhost", 9090);
		out = new ObjectOutputStream(client.getOutputStream());
		
		sendMessage(msgObj);
		
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
