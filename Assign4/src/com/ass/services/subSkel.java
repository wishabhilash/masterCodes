package com.ass.services;

public class subSkel {

	public String sub(String args){
		String argList[] = args.split(",");
		Integer retInt = new Integer(0);
		Float result = Float.parseFloat(argList[0]) - Float.parseFloat(argList[1]);
		return result.toString();
	}

}
