package com.ass.six;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

	/**
	 * @param args
	 */
	
	private static String
		aSplitDir = "A_split_files/",
		outputDir = "output/",
		bSplitDir = "B_split_files/",
		fileNameA = "A.txt", 
		fileNameB = "B.txt";
	
	private static int 
		blockSize = 100,
		fileASize = 100000,
		fileBSize = 60000;
	
	private static ArrayList<Integer> blocks = new ArrayList<Integer>();
	static{
		blocks.add(50);
		blocks.add(60);
		blocks.add(70);
		blocks.add(80);
		blocks.add(90);
		blocks.add(100);
	}
	
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		//	Create the files with 100000 lines and 60000 lines
		CreateFile create = new CreateFile();
		create.writeFile(fileNameA, fileASize);
		create.writeFile(fileNameB, fileBSize);
		
		
		Iterator<Integer> itr = blocks.iterator();
		while(itr.hasNext()){
			int numOfBlocks = itr.next();
			//	Clear the directories
			clearDir(aSplitDir);
			clearDir(bSplitDir);
			clearDir(outputDir);

			//		Divide file A.txt into A_split_files and B.txt into B_split_files
			DivideFile m = new DivideFile(numOfBlocks, blockSize);
			m.divideFile(aSplitDir, fileNameA, 1);
			m.divideFile(bSplitDir, fileNameB, 0);

			long t1 = System.currentTimeMillis();
			SplitMergeJoin p = new SplitMergeJoin();
			p.run();
			long t2 = System.currentTimeMillis();
			System.out.println("SMJ: " + numOfBlocks + " " + (t2-t1) + "sec");

			//	Clear the directories
			clearDir(aSplitDir);
			clearDir(bSplitDir);
			clearDir(outputDir);

//			Hash file A.txt into A_split_files and B.txt into B_split_files
			HashFile t = new HashFile(aSplitDir, numOfBlocks);
			t.hashFile(fileNameA, 1);
			t.closeFiles();

			HashFile u = new HashFile(bSplitDir, numOfBlocks);
			u.hashFile(fileNameB, 0);
			u.closeFiles();

			t1 = System.currentTimeMillis();
			HashJoin q = new HashJoin(aSplitDir, bSplitDir, numOfBlocks);
			q.run();
			q.destructor();
			t2 = System.currentTimeMillis();
			System.out.println("HJ: " + numOfBlocks + " " + (t2-t1) + "sec");
		}
		
	}
	
	private static void clearDir(String dirName){
		File f = new File(dirName);
		String[] list = f.list();
		for(int i = 0; i < list.length; i++){
			File temp = new File(dirName + "/" + list[i]);
			temp.delete();
		}
	}

}