package com.ass.six;

import java.io.*;
import java.util.*;

public class HashJoin {
	private String dir1, dir2, outputFileName = "output/hashOutput.txt";
	private BufferedWriter bw;
	private int numOfBlocks;
	
	public HashJoin(String dir1, String dir2, int numOfBlocks) throws IOException{
		// TODO Auto-generated constructor stub
		this.dir1 = dir1;
		this.dir2 = dir2;
		this.numOfBlocks = numOfBlocks;
		bw = new BufferedWriter(new FileWriter(outputFileName));
	}
	
	public void run() throws IOException{
		for(int i = 0; i < this.numOfBlocks; i++){
			//	Create fileName
			String fileName = "";
			if(i < 10) fileName = "0" + i + ".txt";
			else fileName = i + ".txt";
//			System.out.println(fileName);
			//	Output the result
			outputResult(getData(dir1 + "/" + fileName, 1), getData(dir2 + "/" + fileName, 0));

		}
//		ArrayList<String> m = getData(dir1 + "/00.txt", 1);
//		Iterator<String> itr = m.iterator();
//		while(itr.hasNext()){
//			System.out.println(itr.next());
//		}
	}
	
	private TreeMap<Integer,ArrayList<String>> getData(String file, int keyIndex) throws IOException{
		TreeMap<Integer,ArrayList<String>> ret = new TreeMap<Integer, ArrayList<String>>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		while((line = br.readLine()) != null){
			Integer key = Integer.parseInt(line.split(" ")[keyIndex]);
			if(ret.containsKey(key)){
				ArrayList<String> temp = ret.get(key);
				temp.add(line);
				ret.put(key, temp);
			}else{
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(line);
				ret.put(key, temp);
			}
		}
		return ret;
	}
	
	private void outputResult(TreeMap<Integer,ArrayList<String>> one,
			TreeMap<Integer,ArrayList<String>> two) throws IOException{
		for(Map.Entry<Integer, ArrayList<String>> entry : one.entrySet()){
			Integer key = entry.getKey();
//			System.out.println(key);
			ArrayList<String> value = entry.getValue();
			if(two.containsKey(key)){
				ArrayList<String> valueTwo = two.get(key);
				cartProduct(value, valueTwo, bw);
			}
		}
		bw.flush();
	}
	
	private void cartProduct(ArrayList<String> one, ArrayList<String> two, BufferedWriter bw) throws IOException{
//		System.out.println(one.size()*two.size());
		for(int i = 0; i < one.size(); i++){
			for(int j = 0; j < two.size(); j++){
//				System.out.println(one.get(i) + " " + two.get(j).split(" ")[1]);
				bw.write(one.get(i) + " " + two.get(j).split(" ")[1] + "\n");
			}
		}
	}
	
	
	public void destructor() throws IOException{
		bw.close();
	}
}
