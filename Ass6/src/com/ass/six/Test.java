package com.ass.six;

import java.io.*;
import java.util.*;


public class Test {

	/**
	 * @param args
	 */
	private static String
		aSplitDir = "A_split_files/",
		bSplitDir = "B_split_files/",
		fileNameA = "A.txt", 
		fileNameB = "B.txt";

	private static int
		numOfBlocks = 60,
		blockSize = 100,
		fileASize = 100000,
		fileBSize = 60000;

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
//		DivideFile m = new DivideFile(numOfBlocks, blockSize);
//		m.divideFile(aSplitDir, fileNameA, 1);
//		m.divideFile(bSplitDir, fileNameB, 0);
//
//		SplitMergeJoin p = new SplitMergeJoin();
//		p.run();
		
//		while((line = f.getNext()) != null){
//			count++;
////			if(line == "") System.out.println(count);
//		}
		
		HashFile t = new HashFile(aSplitDir, numOfBlocks);
		t.hashFile(fileNameA, 1);
		t.closeFiles();
		
		HashFile m = new HashFile(bSplitDir, numOfBlocks);
		m.hashFile(fileNameB, 0);
		m.closeFiles();
		
		HashJoin n = new HashJoin(aSplitDir, bSplitDir, numOfBlocks);
		n.run();
		n.destructor();
	}

}
