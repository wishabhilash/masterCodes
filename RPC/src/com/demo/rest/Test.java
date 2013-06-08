package com.demo.rest;

import java.lang.reflect.*;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException,
	InvocationTargetException, IllegalAccessException{
		// TODO Auto-generated method stub
		Class<?> cls = Class.forName("com.demo.rest.Test");
		Method met = cls.getMethod("doit");
		Test obj = new Test();
		Object argList[] = new Object[1];
		met.invoke(obj);
	}
	
	public void doit(){
		System.out.println("working");
	}

}

class TestRefl{
	
	public void doit(Integer n){
		System.out.println("working");
	}
	
}