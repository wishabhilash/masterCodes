package com.rmi.test;

import java.rmi.*;
import java.rmi.server.*;

public class Hello extends UnicastRemoteObject implements HelloInterface{
	private String str;
	
	public Hello(String str) throws RemoteException{
		this.str = str;
	}
	
	public String say() throws RemoteException{
		return this.str;
	}
}
