package test;

import java.io.*;
import java.net.*;

public class Server {

	/**
	 * @param args
	 */
	
	static ServerSocket server = null;
	static Socket connection = null;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		// TODO Auto-generated method stub
		Server myServer = new Server();
		myServer.run();
	}
	
	void run() throws IOException, ClassNotFoundException{
		server = new ServerSocket(2004, 10);
		try{
			while(true){
				// Wait for connections
				connection = server.accept();
				
				// Reading the stream
				in = new ObjectInputStream(connection.getInputStream());
				readMessage();
				
				// Writing to the stream
				out = new ObjectOutputStream(connection.getOutputStream());
				out.flush();
				
				sendMessage("Yes it is urs");
			}
		}finally{
			closeConnections();
		}
	}

	void sendMessage(String msg) throws IOException, ClassNotFoundException{
		out.writeObject(msg);
		out.flush();
	}
	
	void readMessage() throws IOException, ClassNotFoundException{
		System.out.println((String)in.readObject());
	}
	
	void closeConnections()throws IOException{
		in.close();
		out.close();
		server.close();
	}
}
