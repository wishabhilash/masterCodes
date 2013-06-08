package com.ass.six;

import java.io.*;
import java.util.ArrayList;

public class SplitMergeJoin {
	private String outputFileName = "output/splitJoinOutput.txt", dirAPath = "A_split_files", dirBPath = "B_split_files";
	private FileReaderObj[] fileReadersA, fileReadersB;
	private BufferedWriter bw;
	
	public SplitMergeJoin() throws IOException{
		// TODO Auto-generated constructor stub
		fileReadersA = createBufferArray(dirAPath);
		fileReadersB = createBufferArray(dirBPath);
		bw = new BufferedWriter(new FileWriter(outputFileName));
	}
	
	public void run() throws IOException{
		int count = 0;
		while(cartProduct(pickLines(fileReadersA, 1, count), pickLines(fileReadersB, 0, count), bw)){
			count++;
			if(count > 998) break;
		}
	}
	
	private boolean cartProduct(ArrayList<String> one, ArrayList<String> two, BufferedWriter bw) throws IOException{
//		System.out.println(one.size() + " " + two.size() + " " + one.size()*two.size());
		if(one.size() == 0 && two.size() == 0){
			return false;
		}
		for(int i = 0; i < one.size(); i++){
			for(int j = 0; j < two.size(); j++){
				String temp = one.get(i) + " " + two.get(j).split(" ")[1] + "\n";
				bw.write(temp);
//				System.out.println(temp);
			}
		}
		bw.flush();
		return true;
	}
	
	private ArrayList<String> pickLines(FileReaderObj[] fileReaders, int keyIndex, Integer key) throws IOException{
		String line = "";
//		Integer key = 0;
		
		//	Get the key
//		for(int i = 0; i < fileReaders.length; i++){
//			String keyLine = fileReaders[i].getNext();
////			System.out.println(keyLine);
//			if(keyLine == null) return new ArrayList<String>();
//			key = Integer.parseInt(keyLine.split(" ")[keyIndex]);
//			if(key != null) break;
//		}
//		fileReaders[0].retreat();
//		System.out.println(key);
		ArrayList<String> ret = new ArrayList<String>();
		for(int i = 0; i < fileReaders.length; i++){
			while(true){
				line = fileReaders[i].getNext();
//				System.out.println(line);
				if(line == null) break;
				Integer key2 = Integer.parseInt(line.split(" ")[keyIndex]);
//				System.out.println("Key: " + key + " " + key2);
				if(!key.equals(key2)){
					fileReaders[i].retreat();
					break;
				}
				ret.add(line);
			}
		}
		return ret;
	}

	private FileReaderObj[] createBufferArray(String dirPath) throws IOException{
		File dir = new File(dirPath);
		String[] fileNames = dir.list();
		FileReaderObj[] fileReaders = new FileReaderObj[fileNames.length];
		for(int i = 0; i < fileNames.length; i++){
			String fileName = dir + "/" + i + ".txt";
			FileReaderObj flr = new FileReaderObj(fileName);
			fileReaders[i] = flr;
		}
		return fileReaders;
	}
}




class FileReaderObj{
	private Integer ONEMB = 100, count = 0;
	private ArrayList<String> readBuffer;
	private BufferedReader br;
	private boolean linesNeeded = true, EOF = false;
	
	public FileReaderObj(String fileName) throws FileNotFoundException, IOException {
		// TODO Auto-generated constructor stub
		br = new BufferedReader(new FileReader(fileName));
	}
	
	public String getNext() throws IOException{
		String ret = "";
		if(linesNeeded){
			readBuffer = new ArrayList<String>();
			if(!fetchTuples()){
				EOF = true;
				close();
			}
			linesNeeded = false;
		}
		if(EOF)	return null;
		else{
			try{
				ret = readBuffer.get(count);
				count++;
			}catch(IndexOutOfBoundsException e){
				count = 0;
				linesNeeded = true;
				ret = getNext();
			}
//			System.out.println(count);
			return ret;
		}
	}
	
	public void retreat(){
		count--;
	}
	
	private void close() throws IOException{
		br.close();
	}
	
	private boolean fetchTuples() throws IOException{
		readBuffer.clear();
		int count = 0;
		while(count++ < ONEMB){
			try{
				String line = br.readLine();
				readBuffer.add(line);
			}catch(EOFException e){
				return false;
			}
		}
		return true;
	}
}