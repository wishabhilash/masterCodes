package com.query.opt;

import java.io.*;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
//		RelationCreator rel = new RelationCreator(5);
//		rel.generateFiles();

		System.out.println("Find:");
		System.out.println("1) T(R) - Number of tuples in relation R.");
		System.out.println("2) V(R,a) - Value count for attribute a of Relation R.");
		System.out.println("3) Cost for all the possible (valid)parse trees (left , right and  bushy).");
		System.out.println("4) Cost for Optimal Parse Tree.");
		System.out.println("5) Output of the Query.");
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		
		String input = inReader.readLine();
		if(input == "1"){
			
		}else if(input == "2"){
			
		}else if(input == "3"){
			
		}else if(input == "4"){
			
		}else if(input == "5"){
			
		}
	}

}
