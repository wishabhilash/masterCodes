package sample;

import java.io.*;
import java.util.*;

public class FilterClaim {

	/**
	 * @param args
	 */
	
	private String stopWordsFile = "stopwords";
	private HashMap<String,String> stopwords = new HashMap<String, String>();
	private String tokenStr = " 1234567890:,;";
	private HashSet<String> claimWords = new HashSet<String>();
	private BufferedWriter bw;
	
	public FilterClaim() throws IOException{
		//	Create stopword MAP for constant access
		BufferedReader br = new BufferedReader(new FileReader(stopWordsFile));
		String word = "";
		while((word = br.readLine()) != null) stopwords.put(word, null);
		br.close();
		
		bw = new BufferedWriter(new FileWriter("/media/FUN/data_FILES/data.txt"));
	}
	
	public HashSet<String> getClaimWords(String fileName) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String claim = "";
		while((claim = br.readLine()) != null){
			if(claim.startsWith("<CLAIM>")) break;
		}
		br.close();
		claim = claim.replaceAll("<CLAIM>", "").replaceAll("</CLAIM>", "");

		HashMap<String, Double> wordFreq = new HashMap<String, Double>();
		ValueComparator bvc = new ValueComparator(wordFreq);
		TreeMap<String, Double> wordFreqTree = new TreeMap<String, Double>(bvc);
		
		StringTokenizer claimTokenizer = new StringTokenizer(claim, tokenStr);
		while(claimTokenizer.hasMoreTokens()){
			String token = claimTokenizer.nextToken();
			if(token.endsWith(".")) token = token.replace(".", "");
			token = token.toLowerCase();
			
//			if(!stopwords.containsKey(token) && token.length() > 0){
			if(token.length() > 0){
				if(wordFreq.containsKey(token)){
					Integer temp = wordFreq.get(token).intValue();
					wordFreq.put(token, new Integer(temp+1).doubleValue());
				}else{
					wordFreq.put(token, new Integer(1).doubleValue());
				}
				claimWords.add(token);
			}
		}
		wordFreqTree.putAll(wordFreq);
		
		
		bw.write(fileName + "\n" + "------------------------\n");
		for(Map.Entry<String, Double> entry : wordFreqTree.entrySet()){
			bw.write(entry.getKey() + " " + entry.getValue().intValue() + "\n");
		}
		bw.write("\n\n");
		return claimWords;
	}
	
	public void destructor() throws IOException{
		bw.close();
	}

}


class ValueComparator implements Comparator<String>{
	Map<String, Double> base;
	public ValueComparator(Map base){
		this.base = base;
	}
	
	@Override
	public int compare(String a, String b){
		if((Double)base.get(a) < (Double)base.get(b)){
			return 1;
		}
		else if((Double)base.get(a) == (Double)base.get(b)){
			return 0;
		}
		else return -1;
	}
}
