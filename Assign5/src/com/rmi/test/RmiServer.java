package com.rmi.test;

import java.rmi.registry.*;

public class RmiServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		Registry reg = LocateRegistry.getRegistry();
		reg.rebind("Fukker", new Hello("Hello world"));
		System.out.println("Hello server is ready");
	}

}
