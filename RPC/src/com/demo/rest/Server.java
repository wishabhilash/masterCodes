package com.demo.rest;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import java.lang.reflect.*;

public class Server {

	/**
	 * @param args
	 */
	
	private static Pattern paramPat = Pattern.compile("(?<=\\<param\\>)\\d+(?=\\</param\\>)");
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String fromClient;
		ServerSocket server = new ServerSocket(5000);
		while(true){
			Socket connected = server.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader (connected.getInputStream()));
			PrintWriter outToClient = new PrintWriter(connected.getOutputStream(),true);
			fromClient = inFromClient.readLine();
			

			
			Class<?> aClass = Class.forName("com.demo.rest." + getService(fromClient));
			ArrayList<Integer> params = getParams(fromClient);
			
			Class[] parTypes = new Class[2];
			Object[] argList = new Object[2];
			
			for(int i = 0; i < params.size(); i++){
				parTypes[i] = Integer.TYPE;
				argList[i] = params.get(i);
			}
			Method aMethod = aClass.getMethod("result", parTypes);
			Add obj = new Add();
			Object ret = aMethod.invoke(obj, argList);
			outToClient.write(ret.toString());
			
		}
	}
	
	private static String getService(String data){
		StringTokenizer token = new StringTokenizer(data,"/");
		return token.nextToken();
	}

	private static ArrayList<Integer> getParams(String data){
		Matcher match = paramPat.matcher(data);
		ArrayList<Integer> ret = new ArrayList<Integer>();
		while(match.find()){
			ret.add(Integer.parseInt(match.group()));
		}
		return ret;
	}
}
