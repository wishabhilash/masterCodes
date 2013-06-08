package com.rmi.test;

import java.util.Stack;

public class RandomTestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new RandomTestClass("aaabaaa");
	}
	
	public RandomTestClass(String str){
//		Stack<String> anagramStack = new Stack<String>();
//		anagramStack.add("mice");
//		anagramStack.add("mice2");
//		anagramStack.add("mice3");
//		System.out.println(anagramStack.lastElement());
		
		Integer res = validateAnagram(str);
		System.out.println(res);
	}
	
	Integer validateAnagram(String str){
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
