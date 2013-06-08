package wikiparser;

import java.io.*;
import java.util.*;


public class MergeFiles {
	private TreeMap<String, TreeMap<String, ArrayList<Integer>>> treeMap 
	= new TreeMap<String, TreeMap<String, ArrayList<Integer>>>();
//	private static String dirName = "/media/SOUL/wikiFile/wiki_index";
	private FileReaderObj[] fileReaders;
	private int MAX;
	/**
	 * @param args
	 */
	public MergeFiles(String dirName, String outputFileName) throws IOException{
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
			Map.Entry<String, TreeMap<String, ArrayList<Integer>>> fentry = treeMap.firstEntry();
			if(fentry == null){
				for(int i = 0; i < fileReaders.length; i++){
					mapIteration(i);
				}
			} else{
				String key = fentry.getKey();
				TreeMap<String, ArrayList<Integer>> value = fentry.getValue();
				treeMap.remove(key);
				Map.Entry<String, ArrayList<Integer>> entry = value.firstEntry();
				String valueReal = sortPostings(entry.getKey());
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
		String newToken = token.nextToken();
		String key = extractTerm(newToken, "term");
		Integer df = Integer.parseInt(extractTerm(newToken, "df"));
		if(treeMap.containsKey(key)){
			TreeMap<String, ArrayList<Integer>> value = treeMap.get(key);
			treeMap.remove(key);
			Map.Entry<String, ArrayList<Integer>> entry = value.firstEntry();
			String line2 = entry.getKey();
			StringTokenizer token2 = new StringTokenizer(line2, "#");
			String newToken2 = token2.nextToken();
//			String key2 = extractTerm(newToken2, "term");
			Integer df2 = Integer.parseInt(extractTerm(newToken2, "df"));
			Integer totalDf = df + df2;
			String valueReal = key + totalDf.toString() + "#" + line2.substring(newToken2.length()+1) 
					+ line.substring(key.length(), line.length());
			
			ArrayList<Integer> valueId = entry.getValue();
			valueId.add(i);
			TreeMap<String, ArrayList<Integer>> tempValue = new TreeMap<String, ArrayList<Integer>>();
			tempValue.put(valueReal, valueId);
			treeMap.put(key, tempValue);
			
		}else{
			TreeMap<String, ArrayList<Integer>> value = new TreeMap<String, ArrayList<Integer>>();
			ArrayList<Integer> tempArray = new ArrayList<Integer>();
			tempArray.add(i);
			//System.out.println(line);
			value.put(line, tempArray);
			treeMap.put(key,value);
		}
		return true;
	}

	// SORT THE POSTINGS ACCORDING TO DOCID
	private static String sortPostings(String posting){
		TreeMap<Long, String> tempSortMap = new TreeMap<Long, String>();
		StringTokenizer token = new StringTokenizer(posting, "#");
		StringBuilder term = new StringBuilder(token.nextToken());
		Integer df = Integer.parseInt(extractTerm(term.toString(), "df"));
		if(df < 2) return null;
		while(token.hasMoreTokens()){
			String post = token.nextToken();
			StringTokenizer termToken = new StringTokenizer(post, "tciog");
			String docStr = termToken.nextToken();
			Long docId = Long.parseLong(docStr);
//			System.out.println(docId + " " + docStr.length() + " " + post);
			String sec = post.substring(docStr.length());
			tempSortMap.put(docId, sec);
		}
		Long lastDocId = new Long(0);
		for(Map.Entry<Long, String> entry : tempSortMap.entrySet()){
			Long newKey = entry.getKey() - lastDocId;
			term.append("#" + newKey.toString() + entry.getValue());
			lastDocId = entry.getKey();
		}
		return term.toString();
	}
	
	//	EXTRACT DOCID AND DF
	private static String extractTerm(String term, String retMode){
//		ArrayList<String> temp = new ArrayList<String>();
		StringBuilder num = new StringBuilder("");
		int len = term.length();
		while(true){
			len--;
			try{
				Integer.parseInt(term.substring(len));
				num.append(term.charAt(len));
			}catch(NumberFormatException e){break;}
		}
		if(retMode == "term") return term.substring(0, len+1);
		else return num.reverse().toString();
	}
}





class FileReaderObj {
	private BufferedReader br;
	public FileReaderObj(String fileName) throws IOException{
		// TODO Auto-generated constructor stub
		DataInputStream in = new DataInputStream(new FileInputStream(fileName));
		br = new BufferedReader(new InputStreamReader(in));
	}
	
	public FileReaderObj(){}
	
	public BufferedReader getBuffReader(){
		return br;
	}
	
	public void setBuffReader(BufferedReader reader){
		br = reader;
	}
}
