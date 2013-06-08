package com.ass.services;

public class addSkel {
	public String add(String args){
		String argList[] = args.split(",");
		Integer retInt = new Integer(0);
		for (String i : argList){
			retInt += Integer.parseInt(i.split(" ")[1]);
		}
		System.out.println(retInt);
		return retInt.toString();
	}
}
