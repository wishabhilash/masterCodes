package wikiparser;


import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.*;

//import wikiparser.WikiPage.*;

public class XMLParser extends DefaultHandler {
	private HashMap<Integer, String> typeMap = new HashMap<Integer, String>();
	//private OutputStreamWriter outFile;
	private Integer id;
	public HashSet<String> stopwordSet;
	
	//	<term ,<docId ,term_frequency >>
	public TreeMap<String, HashMap<Integer, Integer[]>> IndexMap = new TreeMap<String, HashMap<Integer, Integer[]>>();
	private WikiPage page;
	private int level = 0;
	private StringBuilder accumulator;
	private String tempStr;
	private String tokenStr = " ~?`–\n\t\r\f1234567890/.=!*-|_\"'[](){},:;<>@#$%^&+=\\";
	private TreeMap<Integer, String> forwardIndexMap = new TreeMap<Integer, String>();
	private Integer countDocWords = 0;
	
	/**
	 * @param args
	 */
	
	public XMLParser() throws FileNotFoundException{
		typeMap.put(0, "t");
		typeMap.put(1, "c");
		typeMap.put(2, "o");
		typeMap.put(3, "g");
		typeMap.put(4, "i");
	}
	
	public void startElement(String uri, String localName, String qName,
			Attributes attr) throws SAXException {
		level++;
		//System.out.println(level);
		if(qName.equalsIgnoreCase("page")) {
			page = new WikiPage();
			countDocWords = 0;
		}
		else if(qName.equalsIgnoreCase("text")){
			accumulator = new StringBuilder();
		}
		else if(qName.equalsIgnoreCase("id") && level == 3){
			accumulator = new StringBuilder();
		}
	}
		
	public void endElement(String uri, String localName, String qName) throws SAXException{
		if(qName.equalsIgnoreCase("page")){
			StringBuilder title = page.getTitle();
			id = Integer.parseInt(page.getId().toString());
			StringBuilder content = page.getContent();
			ArrayList<StringBuilder> outlink = page.getOutlink();
			ArrayList<StringBuilder> category = page.getCategory();
			ArrayList<StringBuilder> infobox = page.getInfobox();
			
			
			
			// BUILD STOPWORD SET
			try {
				this.buildStopwordHashSet();
			} catch(Exception e){ }
			
			//	TOKENIZATION
			this.tokenizeWord(title, 0);
			this.tokenizeWord(content, 1);
			this.tokenizeArray(outlink, 2);
			this.tokenizeArray(category, 3);
			this.tokenizeArray(infobox, 4);
			
			//	BUILD FORWARD INDEXMAP
			forwardIndexMap.put(id, countDocWords.toString() + "#" + title.toString());

		}
		else if(qName.equalsIgnoreCase("title")){
			page.setTitle(new StringBuilder(tempStr.replaceAll("[\\P{ASCII}]", " ")));
			//System.out.println(tempStr);
		}
		else if(qName.equalsIgnoreCase("id") && level == 3){
			page.setId(new StringBuilder(tempStr));
		}
		else if(qName.equalsIgnoreCase("text")){
			page.setContent(accumulator);
			//System.out.println(accumulator);
		}
		
		level--;
	}
	
	

	public void characters(char[] buffer, int start, int length) throws SAXException {
		tempStr = new String(buffer, start, length);
		if(accumulator == null) {
			accumulator = new StringBuilder();
		}
		accumulator.append(tempStr);
	}
	
	
	//	TOKENIZE WORD
	public void tokenizeWord(StringBuilder word, int type) {
		this.tokenizeIntemediate(word.toString(), type);
	}
	
	//	TOKENIZE ARRAY
	public void tokenizeArray(ArrayList<StringBuilder> data, int type) {
		Iterator <StringBuilder> itr = data.iterator();
		while(itr.hasNext()){
			this.tokenizeIntemediate(itr.next().toString(), type);
		}
	}
	
	//	COMMON TOKENIZER
	private void tokenizeIntemediate(String data, int type){
		StringTokenizer st = new StringTokenizer(data,this.tokenStr, false);
		while(st.hasMoreTokens()) {
			countDocWords++;
			String token = st.nextToken();
			token = token.toLowerCase();
			if(token.length() > 0) {
				if(!stopwordSet.contains(token)){
					String stemmedWord = this.stemWord(token);
					if(stemmedWord != "") {
						this.insertIntoIndexMap(stemmedWord, type);
//						try{
//							outFile.write(stemmedWord + "\n");
//						}catch(FileNotFoundException e){
//							e.printStackTrace();
//						}catch( IOException e){
//							e.printStackTrace();
//						}
					}
				}
			}
		}
	}
	
	//	INSERT INTO THE MAP OF INDEX
	private void insertIntoIndexMap(String s, int type){
		if(this.IndexMap.containsKey(s)){
			HashMap<Integer, Integer[]> tempTreeMap = this.IndexMap.get(s);
			if(tempTreeMap.containsKey(this.id)){
				//this.IndexMap.remove(s);
				Integer[] val = tempTreeMap.get(this.id);
				val[type] += 1;
				tempTreeMap.put(this.id, val);
				this.IndexMap.put(s,tempTreeMap);
			} else{
				HashMap<Integer, Integer[]> anotherOne = this.IndexMap.get(s);
				Integer[] val = {0,0,0,0,0};
				val[type] += 1;
				anotherOne.put(this.id,val);
				this.IndexMap.put(s, anotherOne);
			}
		}
		else{
			Integer[] val = {0,0,0,0,0};
			val[type] += 1;
			HashMap<Integer, Integer[]> tempTreeMap = new HashMap<Integer, Integer[]>(); 
			tempTreeMap.put(this.id, val);
			this.IndexMap.put(s, tempTreeMap);
		}
	}
	
	//	BUILD HASHSET FOR STOPWORDS
	private void buildStopwordHashSet() throws FileNotFoundException, IOException{
		this.stopwordSet = new HashSet<String>();
		DataInputStream in = new DataInputStream(new FileInputStream("data/stopwords"));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
		String stopword;
		while((stopword = bufferedReader.readLine()) != null) {
			this.stopwordSet.add(stopword);
		}
		in.close();
	}
	
	//	STEM THE WORDS
	private String stemWord(String word){
		Stemmer stemmer = new Stemmer();
		stemmer.add(word,word.length());
		stemmer.stem();
		return stemmer.toString();
	}
	
	
	public void createIndex(String fileName) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileName));
		for (Map.Entry<String,HashMap<Integer, Integer[]>> entry : this.IndexMap.entrySet()) {
			String term = entry.getKey();
			if(term != ""){
				HashMap<Integer, Integer[]> value = entry.getValue();
				out.write(term + value.size());
				for (Map.Entry<Integer, Integer[]> innerEntry : value.entrySet()) {
					Integer docId = innerEntry.getKey();
					Integer[] freq = innerEntry.getValue();
					out.write("#" + docId.toString());
					
					//	HERE NORMALIZATION OF tf
					for(int i = 0; i < 5; i++){
						if(freq[i] != 0) out.write(typeMap.get(i) + freq[i].toString());
					}
				}
				
				//System.out.println(line.toString()+"\n");
			}
			out.write("\n");
		}
		out.close();
	}

	public void createForwardIndex(String fileName) throws IOException{
		OutputStreamWriter forwardIndex = new OutputStreamWriter(new FileOutputStream(fileName));
		for(Map.Entry<Integer, String> entry : forwardIndexMap.entrySet()){
			forwardIndex.write(entry.getKey().toString() + "#" + entry.getValue() + "\n");
		}
		forwardIndex.close();
	}

	
	public void flushData(){
		this.IndexMap.clear();
		this.forwardIndexMap.clear();
	}
}
