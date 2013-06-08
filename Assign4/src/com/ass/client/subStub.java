package com.ass.client;

import java.io.*;
import java.net.*;

public class subStub {

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
		subStub tc = new subStub();
		System.out.println(tc.sub());
	}*/
	
	public String sub(float a,float b) throws MalformedURLException, IOException{
		String urlAddress = "http://localhost:8080/Assign3/Skeleton";
		
		//	Create POST data
		postData = "msg=" + URLEncoder.encode("<post>" +
	"<name>" + "sub" + "</name>" +
	"<retType>" + "float" + "</retType>" +
	"<param>" +
		"<arg>" + "Float 0 " + new Float(a).toString() + "</arg>" + "<arg>" + "Float 1 " + new Float(b).toString() + "</arg>"  + 
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
