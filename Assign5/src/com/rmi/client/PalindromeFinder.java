package com.rmi.client;

import java.io.*;
import java.util.Stack;

import com.rmi.base.*;

public class PalindromeFinder implements Task<Integer>, Serializable {

	/**
	 * @param args
	 */
	
	private static final long serialVersionUID = 3942967283733335029L;
	
	String string = "";
	
	public PalindromeFinder(String str){
		this.string = str;
	}

	public Integer execute(){
		return validatePalindrome(this.string);
	}
	
	Integer validatePalindrome(String str){
		int lastIndex = str.length()-1;
		boolean flag = true;
		for(int i = 0; i < str.length(); i++){
			if(i > lastIndex-i) break;
			if(str.charAt(i) != str.charAt(lastIndex - i)){
				flag = false;
			}
		}
		if(flag) return 1;
		else return 0;
	}
}
