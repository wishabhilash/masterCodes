package test;

import java.io.*;
import java.net.*;
import java.util.HashMap;

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
		myClient.run();
	}

	void clientStub(int a, String b){
		
	}
	
	void run() throws IOException, ClassNotFoundException{
		HashMap<String, String> m = parseFile("data/registry");
		
		String args[] = m.get("args").split(",");
		String message = "funcName : " + m.get("funcName") + " ";
		
		client = new Socket("localhost", 2004);
		out = new ObjectOutputStream(client.getOutputStream());
		
		sendMessage("This is my world!!!");
		
		in = new ObjectInputStream(client.getInputStream());
		readMessage();
		
		closeConnection();
	}
	
	
	static HashMap<String, String> parseFile(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		HashMap<String, String> ret = new HashMap<String, String>();
		
		String line = null;
		while((line = br.readLine()) != null){
			String output[] = line.split("[()]");
			String returnDT = output[0].split(" ")[0];
			String funcName = output[0].split(" ")[1];
			String args = output[1];
			ret.put("returnDT", returnDT);
			ret.put("funcName", funcName);
			ret.put("args", args);
		}
		return ret;
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
