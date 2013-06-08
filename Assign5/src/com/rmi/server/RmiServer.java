package com.rmi.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.rmi.base.*;

public class RmiServer extends RmiStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new RmiServer();
	}
	
	public RmiServer(){
		super();
	}
	
	@Override
	public void doCustomCodeHere(){
		try{
			Compute engine = new ComputeEngine();
			Compute engineStub = (Compute) UnicastRemoteObject.exportObject(engine, 0);
			
			Registry reg = LocateRegistry.getRegistry();
			reg.rebind(Compute.SERVICE_NAME, engineStub);
			System.out.println("Server is ready!!!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
