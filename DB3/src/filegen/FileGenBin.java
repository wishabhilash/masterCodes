package filegen;

import java.io.*;
import java.util.*;

public class FileGenBin {
	private String genFileName = "", copiedLine = "", otherLine = "";
	private Integer numOfLines, numOfDups;
	private HashSet<Integer> listOfCopies = new HashSet<Integer>();
	private ArrayList<String> strOfCopies = new ArrayList<String>();
	
	public FileGenBin(String genFileName, Integer numOfLines, Integer numOfDups) throws IOException{
		// TODO Auto-generated constructor stub
		this.genFileName = genFileName;
		this.numOfLines = numOfLines;
		this.numOfDups = numOfDups;
		
		createFile();
	}
	
	private void createFile() throws IOException{
//		BufferedWriter bw = new BufferedWriter(new FileWriter(this.genFileName));
		DataOutputStream bw = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(this.genFileName)));
		Random randInt = new Random();
		for(int i = 0; i < numOfLines; i++){
			if(i%100 == 0){
				listOfCopies.clear();
				while(listOfCopies.size() < numOfDups) listOfCopies.add(randInt.nextInt(100-numOfDups));
			}
			
			if(i%100 == (100-numOfDups)){
				for(int j = 0; j < strOfCopies.size(); j++) bw.writeInt(Integer.parseInt(strOfCopies.get(j)));
				strOfCopies.clear();
				i += numOfDups - 1;
				continue;
			}
			
			for(int j = 0; j < 4; j++){
				Integer temp = randomIntInRange(10, 99, new Random());
				
				if(listOfCopies.contains(i%100)){
					copiedLine += temp;
				}
				otherLine += temp;
			}
			bw.writeInt(Integer.parseInt(otherLine));
			otherLine = "";
			if(listOfCopies.contains(i%100)){
				strOfCopies.add(copiedLine);
				copiedLine = "";
			}
			
		}
		bw.close();
	}
	
	private Integer randomIntInRange(int aStart, int aEnd, Random aRandom){
		if ( aStart > aEnd ) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		//get the range, casting to long to avoid overflow problems
		long range = (long)aEnd - (long)aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long)(range * aRandom.nextDouble());
		return (int)(fraction + aStart);
	}
	
}
