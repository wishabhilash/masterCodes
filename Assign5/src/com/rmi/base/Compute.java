package com.rmi.base;

import java.rmi.*;

public interface Compute extends Remote{
	public static final String SERVICE_NAME = "ComputeEngine";
	
	<T> T executeTask(Task<T> task) throws Exception;
}
