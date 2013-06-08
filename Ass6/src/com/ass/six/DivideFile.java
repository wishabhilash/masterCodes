package com.ass.six;

import java.io.*;
import java.util.*;

public class DivideFile {
	
	private int	eachFileSize;
	private TreeMap<Integer, ArrayList<String>> buffer = new TreeMap<Integer, ArrayList<String>>();
	
	public DivideFile(int numOfBlocks, int blockSize) {
		// TODO Auto-generated constructor stub
		eachFileSize = numOfBlocks * blockSize;
	}
	
	public void divideFile(String dirName, String fileName, int keyIndex) throws FileNotFoundException, IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = "";
		int lineCount = 0, fileCount = 0;
		
		
		while((line = br.readLine()) != null){
			
			//	Put into buffer
			Integer key = Integer.parseInt(line.split(" ")[keyIndex]);
			ArrayList<String> temp = new ArrayList<String>();
			if(buffer.containsKey(key)) temp = buffer.get(key);
			else temp = new ArrayList<String>();
			temp.add(line);
			buffer.put(key, temp);

			lineCount++;
			
			if(lineCount % eachFileSize == 0 && lineCount!= 0){
//				System.out.println("file" + fileCount + ".txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(dirName + fileCount + ".txt"));
				
				//	Write into file
				for(Map.Entry<Integer, ArrayList<String>> entry : buffer.entrySet()){
					Iterator<String> itr = entry.getValue().iterator();
					while(itr.hasNext()) bw.write(itr.next() + "\n");
				}
				bw.flush();
				bw.close();
				
				//	Clear buffers
				buffer.clear();
				fileCount++;
			}
		}
		if(buffer.size() > 0){
			BufferedWriter bw = new BufferedWriter(new FileWriter(dirName + fileCount + ".txt"));
			
			//	Write into file
			for(Map.Entry<Integer, ArrayList<String>> entry : buffer.entrySet()){
				Iterator<String> itr = entry.getValue().iterator();
				while(itr.hasNext()) bw.write(itr.next() + "\n");
			}
			bw.flush();
			bw.close();
			
			//	Clear buffers
			buffer.clear();
		}
	}

	public void clearBuffer(){
		buffer.clear();
	}
}

