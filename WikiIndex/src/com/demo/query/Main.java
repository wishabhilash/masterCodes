package com.demo.query;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.lang.Math;


public class Main {

	/**
	 * @param args
	 */
	private static String query;
	
	private static ArrayList<String> sparseArray = new ArrayList<String>();
	private static HashMap<String,String> sparseIndex = new HashMap<String,String>();
	
	private static HashMap<Integer,String> sparseDocIdIndex = new HashMap<Integer,String>();
	private static ArrayList<Integer> sparseDocIdArray = new ArrayList<Integer>();
	
	private static ArrayList<HashMap<Integer,Double>> postingArray = new ArrayList<HashMap<Integer,Double>>();

	//	ROOT PATH
	
	//	SET ROOT PATH FOR INDEXES
	private static String rootPath = "/media/SOUL/wikiFile/indices/";
//	private static String rootPath = "/media/SOUL/wikiFile/index/";
	
	private static String sparseIndexName = rootPath + "sparse.index";
	private static String denseIndexName = rootPath + "dense.index";
	private static String outputFileName = rootPath + "main.index";
	private static String frdSparseIndexName = rootPath + "frdsparse.index";
	private static String frdIndexName = rootPath + "frd.index";
	
//	private static HashMap<String, Integer> hashDocId = new HashMap<String, Integer>();
	private static HashMap<Integer, Integer> hashDocId = new HashMap<Integer, Integer>();
	
	static private int period = 100;
	private static Integer frdPeriod = 10;
	static private long totalDocs = 9000000;
	
	private static boolean titlePresent = false;
	private static boolean infoboxPresent = false;
	private static boolean categoryPresent = false;
	private static boolean outlinkPresent = false;
	private static boolean contentPresent = false;
	
	private static HashMap<String, String> frdResultMap = new HashMap<String, String>();
	
	private static String separator = "tciog";
	
	private static HashMap<String, Integer> typeWeightMap = new HashMap<String, Integer>(); 
	static{
		typeWeightMap.put("t", 5);
		typeWeightMap.put("i", 4);
		typeWeightMap.put("g", 3);
		typeWeightMap.put("c", 2);
		typeWeightMap.put("o", 1);
	}
	
	private static String inputFile = rootPath + "Input.txt"; 
	private static String outputFile = rootPath + "Output.txt"; 
	
	
//	private static OutputStreamWriter logger;
//
//	static {
//		try{
//			logger = new OutputStreamWriter(new FileOutputStream("/media/SOUL/wikiFile/log"));
//		}catch(FileNotFoundException e){e.printStackTrace();}
//	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RandomAccessFile randAccDense = new RandomAccessFile(denseIndexName, "r");
		RandomAccessFile randAccMain = new RandomAccessFile(outputFileName, "r");
		
		
		DataInputStream sin = new DataInputStream(new FileInputStream(sparseIndexName));
		BufferedReader siReader = new BufferedReader(new InputStreamReader(sin));
		
		DataInputStream sdin = new DataInputStream(new FileInputStream(frdSparseIndexName));
		BufferedReader sdiReader = new BufferedReader(new InputStreamReader(sdin));
		
		
		//	LOAD INDEX		========================================================
		String line = new String();
		System.out.println("Loading sparse index...");
		while((line = siReader.readLine()) != null){
			StringTokenizer token = new StringTokenizer(line, "#");
			String key = token.nextToken();
			sparseIndex.put(key, token.nextToken());
			sparseArray.add(key);
		}
		System.out.println("Index loaded!!!");

		
		System.out.println("Loading sparse docId index...");
		while((line = sdiReader.readLine()) != null){
			StringTokenizer token = new StringTokenizer(line, "#");
			Integer key = Integer.parseInt(token.nextToken());
			sparseDocIdIndex.put(key, token.nextToken());
			sparseDocIdArray.add(key);
		}
		System.out.println("Index loaded!!!");
		
		
		
//		System.out.println("Loading docIds...");
//		loadDocIds();
//		System.out.println("DocIds loaded!!!");
		
		RandomAccessFile randAccDocIdWords = new RandomAccessFile(frdIndexName, "r");
		BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFile));

		//	ASK FOR SEARCH QUERY	==============================================
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		
		while((query = br.readLine()) != null){
			TreeMap<Integer, Double> resultMap = new TreeMap<Integer, Double>();
			long t1 = System.currentTimeMillis();
			StringTokenizer token = new StringTokenizer(query, " :\t");
//			System.out.println(query);
			//	CREATE POSTINGMAP	===============================================
			while(token.hasMoreTokens()){
				String word = token.nextToken().toLowerCase();
				if(word.equalsIgnoreCase("t")){
					turnOff();titlePresent = true;continue;
				}
				else if(word.equalsIgnoreCase("b")){
					turnOff();contentPresent = true;continue;
				}
				else if(word.equalsIgnoreCase("i")){
					turnOff();infoboxPresent = true;continue; 
				}
				else if(word.equalsIgnoreCase("o")){
					turnOff();outlinkPresent = true;continue; 
				}
				else if(word.equalsIgnoreCase("c")){
					turnOff();categoryPresent = true;continue;
				}

				// GET POSTING
				String postings = getPosting(word, randAccDense, randAccMain);
//				outputWriter.write(postings + "\n");	
				if(titlePresent) addToPostingList(postings, "t", outputWriter);
				else if(contentPresent)	addToPostingList(postings, "c", outputWriter);
				else if(infoboxPresent) addToPostingList(postings, "i", outputWriter);
				else if(outlinkPresent) addToPostingList(postings, "o", outputWriter);
				else if(categoryPresent) addToPostingList(postings, "g", outputWriter);
				else addToPostingList(postings, "a", outputWriter);
			}


//			ValueComparator bvc = new ValueComparator(universal);
//			TreeMap<String, Double> refMap = new TreeMap<String, Double>(bvc);
//			refMap.putAll(universal);
//			resultMap = refMap;
			//	K-WAY INTERSECTION ==========================================
			if(postingArray.size() > 1){
				HashMap<Integer,Double> map = new HashMap<Integer, Double>();
				ValueComparator bvc = new ValueComparator(map);
				TreeMap<Integer, Double> refMap = new TreeMap<Integer, Double>(bvc);

				int size = 0, index = 0;
				for(int i = 0; i < postingArray.size(); i++){
					if(postingArray.get(i).size() > size) index = i;
				}
				HashMap<Integer,Double> reference = postingArray.get(index);
				postingArray.remove(index);
				int arrlen = postingArray.size();

				Iterator itr = reference.keySet().iterator();
				while(itr.hasNext()){
					boolean interCheck = true;
					Integer key = (Integer)itr.next();
//					outputWriter.write(key + "\n");
					Double tempScore = reference.get(key);
					for(int i = 0; i < arrlen; i++){
						HashMap<Integer, Double> tempMap = postingArray.get(i);
						if(tempMap.containsKey(key)){
							
							tempScore += tempMap.get(key);
						}
						else interCheck = false;
					}
					if(interCheck) map.put(key, tempScore);
					
				}
				if(map.size() < 10){
					itr = reference.keySet().iterator();
					while(itr.hasNext()){
						Integer key = (Integer)itr.next();
//						outputWriter.write(key + "\n");
						Double tempScore = reference.get(key);
						for(int i = 0; i < arrlen; i++){
							HashMap<Integer, Double> tempMap = postingArray.get(i);
							if(tempMap.containsKey(key)){
								
								tempScore += tempMap.get(key);
							}
						}
						map.put(key, tempScore);
					}
				}
				
//				System.out.println(count);
				refMap.putAll(map);
				resultMap = refMap;
			}else{
				HashMap<Integer,Double> reference = postingArray.get(0);
				ValueComparator bvc = new ValueComparator(reference);
				TreeMap<Integer, Double> tempMap = new TreeMap<Integer, Double>(bvc);
				tempMap.putAll(reference);
				resultMap = tempMap;
			}
			
			long t2 = System.currentTimeMillis()-t1;
			outputWriter.write("Time: " + t2 + "ms\n");
			int outputCount = 0;
			for(Map.Entry<Integer, Double> entry : resultMap.entrySet()){
				if(outputCount == 10) break;
				Integer docId = entry.getKey();
				String docName = processFrdPost(docId, randAccDocIdWords);
//				outputWriter.write(docId + "\t" + docName + "\t" + entry.getValue() + "\n");
				outputWriter.write(docId + "\t" + docName + "\n");
//				System.out.println(docId + " " + docName + "\n");
				outputCount++;
			}
			outputWriter.write("\n");
			resultMap.clear();
//			universal.clear();
		}
		
		outputWriter.close();
		
	}
	
	
	
	
	private static void addToPostingList(String postings, String type, BufferedWriter outputWriter) throws IOException{
		//System.out.println(postings);
		Long prevDocId = new Long(0);
		StringTokenizer aToken = new StringTokenizer(postings, "#");
		HashMap<Integer, Double> temp = new HashMap<Integer, Double>();
		// get IDF value here
		String term = aToken.nextToken();
//		System.out.println(term + " " + type);
		Double df = Double.parseDouble(extractTerm(term, "df"));
		Double IDF = Math.log10(totalDocs/df);
//		System.out.println(Math.log10(IDF));
		while(aToken.hasMoreTokens()){
			Double score;
			String docToken = aToken.nextToken();
			
			StringTokenizer token = new StringTokenizer(docToken, separator);
			
			//	get docId
			String docId = token.nextToken();

			//	get rest of tf
			String sec = docToken.substring(docId.length());
			
			//	CALCULATE THE DOCID OR DECOMPRESS DELTA COMPRESSION
			Long tempDocId = Long.parseLong(docId);
			tempDocId += prevDocId;
			docId = tempDocId.toString();
//			outputWriter.write(docId + " " + prevDocId + "\n");

			prevDocId = tempDocId;
			
//			System.out.println(docId);
			Double tfCalc = new Double(0);
			int index;
			if(type == "a"){
				index = sec.indexOf("t");
				if(index != -1)	tfCalc = Double.parseDouble(sec.substring(index+1, index+2)) * typeWeightMap.get("t");
				index = sec.indexOf("c");
				if(index != -1)	tfCalc = Double.parseDouble(sec.substring(index+1, index+2)) * typeWeightMap.get("c");
				index = sec.indexOf("i");
				if(index != -1)	tfCalc = Double.parseDouble(sec.substring(index+1, index+2)) * typeWeightMap.get("i");
				index = sec.indexOf("o");
				if(index != -1)	tfCalc = Double.parseDouble(sec.substring(index+1, index+2)) * typeWeightMap.get("o");
				index = sec.indexOf("g");
				if(index != -1)	tfCalc = Double.parseDouble(sec.substring(index+1, index+2)) * typeWeightMap.get("g");
			}else{
				index = sec.indexOf(type);
				if(index != -1) tfCalc = Double.parseDouble(sec.substring(index+1, index+2));// * typeWeightMap.get(type);
				else tfCalc = 0.0;
			}
			score = new Double(tfCalc * IDF);
			
			if(score != 0.0) temp.put(Integer.parseInt(docId), score);
//			if(universal.containsKey(docId)){
//				Double prevScore = universal.get(docId);
//				universal.put(docId, prevScore+score);
//			}else{
//				universal.put(docId, score);
//			}
		}
		
		postingArray.add(temp);
	}
	
//	private static void loadDocIds() throws FileNotFoundException, IOException{
//		DataInputStream in = new DataInputStream(new FileInputStream(frdIndexName));
//		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//		String line;
////		Integer[] value = new Integer[2];
////		String docId;
//		while((line = bufferedReader.readLine()) != null){
//			StringTokenizer token = new StringTokenizer(line, "#");
//			hashDocId.put(Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken()));
//		}
//	}
	
	private static String processFrdPost(Integer docId, RandomAccessFile randAccDocIdWords) throws IOException{
		long docIdPointer = binSearchInt(docId, sparseDocIdArray, sparseDocIdIndex);
		randAccDocIdWords.seek(docIdPointer);
		BufferedReader docIdReader = new BufferedReader(new FileReader(randAccDocIdWords.getFD()));
		String[] strArray = new String[3];
		for(int i = 0; i < frdPeriod; i++){
			String temp = docIdReader.readLine();
			strArray = temp.split("#");
		}
		return strArray[2];
	}
	
	private static String getPosting(String word, RandomAccessFile randAccDense
			, RandomAccessFile randAccMain) throws IOException{
		//		SEARCH IN SPARSE INDEX
		String stemmedWord = stemWord(word);
		long diPointer = binSearch(stemmedWord, sparseArray, sparseIndex);
		
		//	LOAD REQUIRED DENSE INDEX
		randAccDense.seek(diPointer);
		BufferedReader diReader = new BufferedReader(new FileReader(randAccDense.getFD()));
		int lineCount = 0;
		
		String temp = new String();
		while(lineCount < period){
			String tempR = diReader.readLine();
//			System.out.println(temp);
			StringTokenizer st = new StringTokenizer(tempR,"#");
			if(stemmedWord.equals(st.nextToken())){
				temp = st.nextToken();
				break;
			}
			lineCount++;
		}
		
		//	RETRIVE FROM MAIN INDEX
		if(temp != null){
			long mainPointer = Long.parseLong(temp,16);
			randAccMain.seek(mainPointer);
			BufferedReader mainReader = new BufferedReader(new FileReader(randAccMain.getFD()));
			return mainReader.readLine();
		}
		
		return null;
	}
	
	private static Long binSearch(String S, ArrayList<String> array, HashMap<String,String> index){
		int beg = 0, end = array.size(), mid = (beg+end)/2, lastMid = 0;
		while((end-beg) > 0){
			int temp = array.get(mid).compareTo(S);
			if(temp < 0){
				beg = mid;
				mid = (beg+end)/2;
			}
			else if(temp > 0){
				end = mid;
				mid = (beg+end)/2;
			}
			else break;
			if(mid == lastMid) break;
			lastMid = mid;
		}
//		System.out.println(index.get(array.get(mid)));
//		System.out.println("In bin search: " + S + " " + array.get(mid));
		return Long.parseLong(index.get(array.get(mid)), 16);
	}
	
	private static Long binSearchInt(Integer S, ArrayList<Integer> array, HashMap<Integer,String> index){
		int beg = 0, end = array.size(), mid = (beg+end)/2, lastMid = 0;
		while((end-beg) > 0){
//			int temp = array.get(mid).compareTo(S);
			if(array.get(mid) < S){
				beg = mid;
				mid = (beg+end)/2;
			}
			else if(array.get(mid) > S){
				end = mid;
				mid = (beg+end)/2;
			}
			else break;
			if(mid == lastMid) break;
			lastMid = mid;
		}
//		System.out.println(index.get(array.get(mid)));
//		System.out.println("In bin search: " + S + " " + array.get(beg));
//		try{
//			logger.write("In bin search: " + S + " " + array.get(beg) + "\n");
//		}catch(IOException e){}
		return Long.parseLong(index.get(array.get(mid)), 16);
	}
	
	private static String stemWord(String word){
		Stemmer stemmer = new Stemmer();
		stemmer.add(word,word.length());
		stemmer.stem();
		return stemmer.toString();
	}
	
	private static void turnOff(){
		titlePresent = false;
		infoboxPresent = false;
		categoryPresent = false;
		outlinkPresent = false;
		contentPresent = false;
	}
	
	private static String extractTerm(String term, String type){
		StringTokenizer token = new StringTokenizer(term, "0123456789");
		String word = token.nextToken();
		int len = word.length();
		String df = term.substring(len);
		if(type == "term") return word;
		else return df;
	}
	
}



class ValueComparator implements Comparator<Integer>{
	Map<String, Double> base;
	public ValueComparator(Map base){
		this.base = base;
	}
	
	@Override
	public int compare(Integer a, Integer b){
		if((Double)base.get(a) < (Double)base.get(b)){
			return 1;
		}
		else if((Double)base.get(a) == (Double)base.get(b)){
			return 0;
		}
		else return -1;
	}
}
