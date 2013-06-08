package com.demo.rest;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Client {

	/**
	 * @param args
	 */
	static private String host = "localhost";
	static private int port = 5000;
	static private String url = "";
	static private ArrayList<String> services= new ArrayList<String>();
	static{
		services.add("Add");
		services.add("Sub");
	}
	
	public static void main(String[] args) throws IOException, UnknownHostException{
		// TODO Auto-generated method stub
		
		Socket client = new Socket(host, port);
		System.out.println("Connected!!!");
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));    
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader (client.getInputStream()));
		PrintWriter outToServer = new PrintWriter(client.getOutputStream(),true);
		
		while(true){
			help();
			System.out.println("Choose your service:");
			url = inFromUser.readLine();
			if(!services.contains(url)) System.out.println("Typo!!! Please enter correctly!!!");
			else if(url.length() > 0) break;
		}
		System.out.println("Enter data (space in between):");
		String params = createXML(inFromUser.readLine());
		outToServer.println(url + "/" + params);
		inFromServer.read();
	}

	private static void help(){
		System.out.println("Services available:");
		for(int i = 0; i < services.size(); i++){
			System.out.println(i+1 + " " + services.get(i));
		}
	}
	
	private static String createXML(String params){
		StringTokenizer token = new StringTokenizer(params);
		String ret = "<data>";
		while(token.hasMoreTokens()){
			ret += "<param>" + token.nextToken()+ "</param>";
		}
		ret += "</data>";
		return ret;
	}
}
