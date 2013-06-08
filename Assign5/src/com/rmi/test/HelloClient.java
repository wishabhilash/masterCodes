package com.rmi.test;

import java.rmi.Naming;

public class HelloClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		HelloInterface hello = (HelloInterface) Naming.lookup("//localhost/Fukker");
		System.out.println(hello.say());
	}

}
