package server;

import java.io.*;
import java.net.*;
import java.lang.reflect.*;;

public class Skeleton {

	/**
	 * @param args
	 */
	
	static ServerSocket server = null;
	static Socket connection = null;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException,
	IllegalAccessException, InstantiationException, InvocationTargetException{
		// TODO Auto-generated method stub
		Skeleton myServer = new Skeleton();
		myServer.run();
	}
	
	void run() throws IOException, ClassNotFoundException, NoSuchMethodException,
	IllegalAccessException, InstantiationException, InvocationTargetException{
		server = new ServerSocket(9090, 10);
		
		try{
			while(true){
				// Wait for connections
				connection = server.accept();
				
				// Reading the stream
				in = new ObjectInputStream(connection.getInputStream());
				out = new ObjectOutputStream(connection.getOutputStream());
				
				
				System.out.println("Server: Recieving msg");
				String input = readMessage();
				String inputDiv[] = input.split(";");
				String name = inputDiv[0].split(":")[1];
				String retType = inputDiv[1].split(":")[1];
				String args = inputDiv[2].split(":")[1];
				
				Class cls = Class.forName("services." + name + "Skel");
				Object clsObj = cls.newInstance();
				Method met = cls.getDeclaredMethod(name, new Class[]{String.class});
				String output = (String)met.invoke(clsObj, new String(args));
//				// Writing to the stream
				
				out.flush();
				
				System.out.println("Server: Sending msg");
				sendMessage(output);
			}
		}finally{
//			closeConnections();
		}
	}

	void sendMessage(String msg) throws IOException, ClassNotFoundException{
		out.writeObject(msg);
		out.flush();
	}
	
	String readMessage() throws IOException, ClassNotFoundException{
		return (String)in.readObject();
	}
	
	void closeConnections()throws IOException{
		in.close();
		out.close();
		server.close();
	}
}
