package test;

import java.io.*;
import java.lang.reflect.*;

public class RandTest {

	/**
	 * @param args
	 */
	public static void main(String[] argv)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		// TODO Auto-generated method stub
		String name = "add", args = "this is args";
		
		Class cls = Class.forName("services." + name + "Skel");
		Object clsObj = cls.newInstance();
		Method met = cls.getDeclaredMethod(name, new Class[]{String.class});
		String output = (String)met.invoke(clsObj, new String(args));
		System.out.println(output);
	}
	
	

}

class DemoClass{
	public DemoClass(){
		System.out.println("Working!!!");
	}
	
	public String DemoMethod(String ret){
		return ret;
	}
	
}