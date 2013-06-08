package com.rmi.client;

import java.rmi.registry.*;
import com.rmi.base.*;

public class ComputePalindrome extends RmiStarter{

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ComputePalindrome();
	}
	
	public ComputePalindrome(){
		super();
	}
	
	@Override
	public void doCustomCodeHere(){
		try{
			String anaStr = "ruchit";
			Registry reg = LocateRegistry.getRegistry();
			Compute compute = (Compute) reg.lookup(Compute.SERVICE_NAME);
			
			PalindromeFinder task = new PalindromeFinder(anaStr);
			Integer res = compute.executeTask(task);
			if(res == 1){
				System.out.println(anaStr + " is a Palindrome");
			}else{
				System.out.println(anaStr + " is not a Palindrome");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
