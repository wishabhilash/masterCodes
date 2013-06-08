package filegen;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

	/**
	 * @param args
	 */
	private static ArrayList<Integer> outputBuffer;
	private static Integer ONEMB = 65536, numOfLines, numOfDups, outputBufferSize;
	private static String genFileName, outputFileName, configFileName = "config";
	private static boolean useHash;
	private static HashTab hashTab;
	private static BPlusTree bpTree;
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		BufferedReader configFileReader = new BufferedReader(new FileReader(configFileName));
		
		String line;
		int countLine = 0;
		while((line = configFileReader.readLine()) != null){
//			System.out.println(countLine + " " + line);
			if(countLine == 1) numOfLines = Integer.parseInt(line) * ONEMB;
			else if(countLine == 4) numOfDups = Integer.parseInt(line);
			else if(countLine == 7){
				if(line.equals("true")) useHash = true;
				else useHash = false;
			}
			else if(countLine == 10) genFileName = line;
			else if(countLine == 13) outputFileName = line;
			else if(countLine == 16) outputBufferSize = Integer.parseInt(line) - 1;
			countLine++;
		}
		
		//	Generate the file.
		FileGenBin fileGen = new FileGenBin(genFileName, numOfLines, numOfDups);
		
		
		
		//	Initialize reader and writer
//		BufferedReader dupFileReader = new BufferedReader(new FileReader(genFileName));
		BufferedWriter outputFileWriter = new BufferedWriter(new FileWriter(outputFileName));
		outputBuffer = new ArrayList<Integer>(outputBufferSize*ONEMB);
		FileIterator fileItr = new FileIterator(genFileName);
		
		
		if(useHash) hashTab = new HashTab();
		else bpTree = new BPlusTree();
		
		int countBufferLine = 0;
		long t1 = System.currentTimeMillis();
		while(true){
			Integer temp = fileItr.getNext();
			if(temp == -1) break;
			
			boolean flag;
			if(useHash) flag = hashTab.put(temp);
			else flag = bpTree.put(temp);
			
			if(flag){
				if(countBufferLine == (outputBufferSize * ONEMB)){
					Iterator iter = outputBuffer.iterator();
					while(iter.hasNext()) outputFileWriter.write(intToStr((int)iter.next()));
					countBufferLine = 0;
					outputBuffer = new ArrayList<Integer>(outputBufferSize*ONEMB);
				}
				outputBuffer.add(temp);
				countBufferLine++;
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
		//	Close readers and writers
		
		Iterator iter = outputBuffer.iterator();
		while(iter.hasNext()) outputFileWriter.write(intToStr((int)iter.next()));
		outputFileWriter.close();
	}
	
	
	private static String intToStr(Integer n){
		String m = n.toString(), p = "";
		for(int i = 0; i < 8; i = i + 2){
			if(i < 6) p += m.substring(i, i + 2) + " ";
			else p += m.substring(i, i + 2) + "\n";
		}
		return p;
	}
	
}


class FileIterator{
	private Integer ONEMB = 65536, count = 0;
	private ArrayList<Integer> readBuffer;
	DataInputStream br;
	private boolean linesNeeded = true, EOF = false;;
	
	public FileIterator(String fileName) throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		open(fileName);
	}
	
	private void open(String fileName) throws FileNotFoundException{
		br = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
	}
	
	public Integer getNext() throws IOException{
		Integer ret = -1;
		if(linesNeeded){
			readBuffer = new ArrayList<Integer>();
			if(!fetchTuples()){
				EOF = true;
				close();
			}
			linesNeeded = false;
		}
		if(EOF)	return -1;
		else{
			try{
				ret = readBuffer.get(count);
				count++;
			}catch(IndexOutOfBoundsException e){
				count = 0;
				linesNeeded = true;
				getNext();
			}
			return ret;
		}
	}
	
	private void close() throws IOException{
		br.close();
	}
	
	private boolean fetchTuples() throws IOException{
		readBuffer.clear();
		int count = 0;
		while(count++ < ONEMB){
			try{
				Integer line = br.readInt();
				readBuffer.add(line);
			}catch(EOFException e){
				return false;
			}
		}
		return true;
	}
}