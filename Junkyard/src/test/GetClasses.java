package test;

import java.io.*;

public class GetClasses {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cls = "AddHandler.java".split("\\.")[0];
		System.out.println(cls);
	}
	
	public String listFiles(String packageName){
		File file = new File("src/" + packageName.replaceAll("\\.", "/"));
		String files[] = file.list();
		for(int i = 0; i < files.length; i++) System.out.println(files[i]);
		return "";
	}

}
