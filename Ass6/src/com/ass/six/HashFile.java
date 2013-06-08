package com.ass.six;

import java.io.*;

public class HashFile {
	private int numOfBlocks;
	private FileWriterObj[] fileWriters = new FileWriterObj[100];
	private String dirName = "";
	
	public HashFile(String dirName, int numOfBlocks) throws IOException{
		// TODO Auto-generated constructor stub
		this.numOfBlocks = numOfBlocks;
		this.dirName = dirName;
		
		for(int i = 0; i < this.numOfBlocks; i++){
			if(i < 10){
				fileWriters[i] = new FileWriterObj(dirName + "/0" + i + ".txt");
			}
			else{
				fileWriters[i] = new FileWriterObj(dirName + "/" + i + ".txt");
			}
		}
	}
	
	public void hashFile(String fileName, int keyIndex) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = "";
		while((line = br.readLine()) != null){
			Integer key = Integer.parseInt(line.split(" ")[keyIndex]);
			fileWriters[key % this.numOfBlocks].getBuffWriter().write(line + "\n");
		}
		
	}
	
	public void closeFiles() throws IOException{
		for(int i = 0; i < this.numOfBlocks; i++){
			fileWriters[i].getBuffWriter().close();
		}
	}
	
}

class FileWriterObj {
	private BufferedWriter bw;
	public FileWriterObj(String fileName) throws IOException{
		// TODO Auto-generated constructor stub
		bw = new BufferedWriter(new FileWriter(fileName));
	}
	
	public FileWriterObj(){}
	
	public BufferedWriter getBuffWriter(){
		return bw;
	}
}
