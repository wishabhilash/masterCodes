package com.query.opt;

import java.util.*;
import java.io.*;

public class RelationCreator {

	private int numOfRels, numOfLines = 100;
	
	public RelationCreator() {
		// TODO Auto-generated constructor stub
		this.numOfRels = 1;
	}
	
	public RelationCreator(int numOfRels){
		this.numOfRels = numOfRels;
	}
	
	public void generateFiles() throws IOException{
		Random rand = new Random();
		
		//	Permanent array.
		ArrayList<Integer> referenceKeySet = new ArrayList<Integer>();
		for(int i = 0; i < numOfRels; i++){
			
			//	Temporary array. It will be assigned to the permanent one at the end of a file.
			ArrayList<Integer> referenceKeySetTemp = new ArrayList<Integer>();
			BufferedWriter bw = new BufferedWriter(new FileWriter("relations/" + (char)(65+i)));
			int m = rand.nextInt(1000);
			for(int line = 0; line < m; line++){
				Integer a = 0,b;
				
				//	Generate random index for referenceKeySet to retrieve key.
				if(i == 0){
					a = rand.nextInt(1000);
				}else{
					Integer randIndex = rand.nextInt(referenceKeySet.size());
					a = referenceKeySet.get(randIndex);
				}

				//	Create referenceKeySet.
				b = rand.nextInt(1000);
				if(!referenceKeySetTemp.contains(b)) referenceKeySetTemp.add(b);
				
				bw.write(correctNum(a) + " " + correctNum(b) + "\n");
				referenceKeySet = referenceKeySetTemp;
			}
			bw.close();
		}
	}
	
	private String correctNum(Integer n){
		String num = n.toString();
		if(num.length() == 1){
			num = "00" + num;
		}else if(num.length() == 2){
			num = "0" + num;
		}
		return num;
	}
	
}
