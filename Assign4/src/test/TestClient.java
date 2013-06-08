package test;

import java.io.*;
import java.net.*;

public class TestClient {

	/**
	 * @param args
	 */
	URL url = null;
	String postData = "";
	HttpURLConnection conn = null;
	BufferedReader reader = null;
	DataOutputStream writer = null;
	
	public static void main(String[] args) throws MalformedURLException, IOException{
		// TODO Auto-generated method stub
		TestClient tc = new TestClient();
		tc.run();
	}
	
	public void run() throws MalformedURLException, IOException{
		String urlAddress = "http://localhost:8080/Assign3/TestServer";
		
		//	Create POST data
		postData = "msg=" + URLEncoder.encode("Client: Hello server", "UTF-8");
		
		try{
			
			// Connect to the URL for transaction
			connectToUrl(urlAddress, "GET");
			
			// Write message
			sendMessage(postData);
			
			// Recieve message
			StringBuilder msg = recieveMessage();
			System.out.println(msg.toString());
			
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			closeAll();
		}
		
	}
	
	void closeAll() throws IOException{
		conn.disconnect();
	}
	
	void connectToUrl(String urlAddr, String method) throws MalformedURLException, IOException{
		url = new URL(urlAddr);
		conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod(method);
		conn.setDoOutput(true);
		conn.connect();
	}
	
	
	void sendMessage(String msg) throws IOException{
		writer = new DataOutputStream(conn.getOutputStream());
		writer.writeBytes(msg);
		writer.flush();
	}
	
	StringBuilder recieveMessage() throws IOException{
		reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder str = new StringBuilder();
		String line = "";
		while((line = reader.readLine()) != null) str.append(line);
		return str;
	}

}
