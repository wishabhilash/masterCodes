package com.ass.client;

import java.io.*;
import java.net.*;

public class addStub {

	/**
	 * @param args
	 */
	URL url = null;
	String postData = "";
	HttpURLConnection conn = null;
	BufferedReader reader = null;
	DataOutputStream writer = null;
	
	/*public static void main(String[] args) throws MalformedURLException, IOException{
		// TODO Auto-generated method stub
		addStub tc = new addStub();
		System.out.println(tc.add());
	}*/
	
	public String add(int a, int b) throws MalformedURLException, IOException{
		String urlAddress = "http://localhost:8080/Assign3/Skeleton";
		
		//	Create POST data
		postData = "msg=" + URLEncoder.encode("<post>" +
	"<name>" + "add" + "</name>" +
	"<retType>" + "int" + "</retType>" +
	"<param>" +
		"<arg>" + "Integer 0 " + new Integer(a).toString() + "</arg>" + "<arg>" + "Integer 1 " + new Integer(b).toString() + "</arg>"  + 
	"</param>" +
"</post>"
, "UTF-8");
		
		
		StringBuilder msg = new StringBuilder();
		try{
			
			// Connect to the URL for transaction
			connectToUrl(urlAddress, "POST");
			
			// Write message
			sendMessage(postData);
			
			// Recieve message
			msg = recieveMessage();
			
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			closeAll();
		}
		
		return msg.toString();
		
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
