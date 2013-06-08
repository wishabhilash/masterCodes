package com.ass.six;

import java.io.*;
import java.util.*;

public class CreateFile {

	private BufferedWriter bw;
	private Random rand = new Random();
	
	public CreateFile(){
		// TODO Auto-generated constructor stub
		
	}
	
	public void writeFile(String fileName, int num) throws IOException{
		bw = new BufferedWriter(new FileWriter(fileName));
		for(int i = 0; i < num; i++) bw.write(rand.nextInt(999) + " " + rand.nextInt(999) + "\n");
		bw.flush();
		bw.close();
	}
	
}
