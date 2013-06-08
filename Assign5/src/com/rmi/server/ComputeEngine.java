package com.rmi.server;

import com.rmi.base.*;

public class ComputeEngine implements Compute{

	/**
	 * @param args
	 */
	
	@Override
	public <T> T executeTask(Task <T> task) throws Exception{
		return task.execute();
	}
}
