package client;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class subStub {

	Socket client = null;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	String signParam = "int a, String b, float c, double d, long e";
	
	/*public static void main(String args[]) throws ClassNotFoundException, IOException{
		addStub m = new addStub();
		m.add(1, "this", 1,1.2, new Long(4).longValue());
	}*/
	
	int sub(int a, String b, float c, double d, long e) throws ClassNotFoundException, IOException{
		String msg = "funcName:" + "sub" + ";"
		+ "returnDT:" + "int" + ";"
		+ "args:" + "Integer " + new Integer(a).toString() + "," + "String " + b + "," + "Float " + new Float(c).toString() + "," + "Double " + new Double(d).toString() + "," + "Long " + new Long(e).toString();
		
		run(msg);
		return 0;
	}
	
	void run(String msgObj) throws IOException, ClassNotFoundException{
		
		client = new Socket("localhost", 2004);
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
