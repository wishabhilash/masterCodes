package wikiparser;

import java.io.*;
import java.util.*;


public class MergeFilesFrd {
	private TreeMap<Long, TreeMap<String, ArrayList<Integer>>> treeMap 
	= new TreeMap<Long, TreeMap<String, ArrayList<Integer>>>();
//	private static String dirName = "/media/SOUL/wikiFile/wiki_index";
	private FileReaderObj[] fileReaders;
	private int MAX;
	/**
	 * @param args
	 */
	public MergeFilesFrd(String dirName, String outputFileName) throws IOException{
		// TODO Auto-generated method stub
		
		//	GET ALL FILES IN THE DIRECTORY
		File dir = new File(dirName);
		String[] fileNames = dir.list();
		MAX = fileNames.length;
		int validFiles = 0;
		for(int i = 0; i < fileNames.length; i++){
			if(!fileNames[i].startsWith(".")){
				validFiles++;
			}
		}
		
		//	LOAD FILEREADEROBJECT IN ITS ARRAY
		fileReaders = new FileReaderObj[validFiles];
		validFiles = 0;
		for(int i = 0; i < MAX; i++){
			System.out.println(fileNames[i]);
			if(!fileNames[i].startsWith(".")){
				String fileName = dirName + "/" + fileNames[i];
				FileReaderObj flr = new FileReaderObj(fileName);
				fileReaders[validFiles] = flr;
				validFiles++;
			}
		}
		
		//	WRITING PART
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outputFileName));
		int endCount = 0;
		while(endCount < fileReaders.length){
			Map.Entry<Long, TreeMap<String, ArrayList<Integer>>> fentry = treeMap.firstEntry();
			if(fentry == null){
				for(int i = 0; i < fileReaders.length; i++){
					mapIteration(i);
				}
			} else{
				Long key = fentry.getKey();
				TreeMap<String, ArrayList<Integer>> value = fentry.getValue();
				treeMap.remove(key);
				Map.Entry<String, ArrayList<Integer>> entry = value.firstEntry();
				String valueReal = entry.getKey();
				ArrayList<Integer> valueId = entry.getValue();
				if(valueReal != null) out.write(valueReal + "\n");
				for(int i = 0; i < valueId.size(); i++){
					if(!mapIteration(valueId.get(i))) endCount++;
				}
			}
			
		}
		out.close();
	}
	
	//	FILL RESPECTIVE FILEBUFFERS THAT ARE EMPTY
	private boolean mapIteration(int i) throws IOException{
		BufferedReader tempBr = fileReaders[i].getBuffReader();
		String line = tempBr.readLine();
		if(line == null) return false;
		StringTokenizer token = new StringTokenizer(line, "#");
		Long key = Long.parseLong(token.nextToken());
		TreeMap<String, ArrayList<Integer>> value = new TreeMap<String, ArrayList<Integer>>();
		ArrayList<Integer> tempArray = new ArrayList<Integer>();
		tempArray.add(i);
		//System.out.println(line);
		value.put(line, tempArray);
		treeMap.put(key,value);
		return true;
	}

}
