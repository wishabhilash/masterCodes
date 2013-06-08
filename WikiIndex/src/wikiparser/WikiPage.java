package wikiparser;

import java.util.*;
import java.util.regex.*;

public class WikiPage {
	private StringBuilder title;
	private StringBuilder id;
	private StringBuilder content = new StringBuilder();
	private ArrayList<StringBuilder> outlink = new ArrayList<StringBuilder>();
	private ArrayList<StringBuilder> category = new ArrayList<StringBuilder>();
	private ArrayList<StringBuilder> infobox = new ArrayList<StringBuilder>();
	
	//private String removeTagPat = "</?[^>]*>";
	private Pattern removeTagPat = Pattern.compile("</?[^>]*>");
	//private String removeUnicodePat = "[\\P{ASCII}]";
	private Pattern removeUnicodePat = Pattern.compile("[\\P{ASCII}]");
	
	
	private void parse() {
		//		REMOVE COMMENTS
		while(true) {
			StringBuilder temp = extractData("<!--", "-->","<!--", true);
			if(temp.length() == 0) break;
		}
		
		//		REMOVE UNICODE CHARS
		Matcher removeUnicode = removeUnicodePat.matcher(this.content);
		this.content = new StringBuilder(removeUnicode.replaceAll(" "));
		
		//	APPEND AND CLEAN CATEGORY
		while(true) {
			StringBuilder temp = extractData("[[Category:", "]]",content, true);
			if(temp.length() == 0) break;
			//System.out.println(temp + "\n");
			this.addInfobox(temp);
		}
		
		//		REMOVE EVERYTHING AFTER SEE ALSO IF PRESENT		
		int refIndex;
		if((refIndex = this.content.indexOf("==See also")) != -1){
			this.content.replace(refIndex, this.content.length(), "");
		}
		//		REMOVE EVERYTHING AFTER REFERENCE IF PRESENT
		else if((refIndex = this.content.indexOf("==Reference")) != -1){
			this.content.replace(refIndex, this.content.length(), "");
		}
		
		//		APPEND INFOBOXES
		while(true) {
			StringBuilder temp = extractData("{{Infobox", "}}","{{", true);
			if(temp.length() == 0) break;
			this.addInfobox(temp);
		}

		//		REMOVE OUTLINKS
		while(true) {
			StringBuilder temp = extractData("[[", "]]",this.content, true);
			if(temp.length() == 0) break;
			this.addOutlink(temp);
		}
		
		
		//		REMOVE {{ }}
		while(true) {
			StringBuilder temp = extractData("{{", "}}",this.content, true);
			//System.out.println(temp + "\n");
			if(temp.length() == 0) break;
		}
		
		
		
		//		REMOVE [[File: ]]
		while(true) {
			StringBuilder temp = extractData("[[File:", "]]","[[", true);
			if(temp.length() == 0) break;
		}

		//		REMOVE [[Image: ]]
		while(true) {
			StringBuilder temp = extractData("[[Image:", "]]","[[", true);
			if(temp.length() == 0) break;
		}
		//		REMOVE [[Image: ]]
		while(true) {
			StringBuilder temp = extractData("[http://", "]","[", true);
			if(temp.length() == 0) break;
		}
		
		
		//		REMOVE TAGS
//		this.content = new StringBuilder(this.content.toString()
//				.replaceAll(removeTagPat, ""));
		Matcher matchRemoveTag = removeTagPat.matcher(this.content);
		this.content = new StringBuilder(matchRemoveTag.replaceAll(" "));
		
		
//		try{
//			OutputStreamWriter outFile = new OutputStreamWriter(new FileOutputStream("/media/SOUL/wikiFile/data.txt"));
//			outFile.write(content.toString());
//			outFile.close();
//		}catch(FileNotFoundException e){
//			e.printStackTrace();
//		}catch( IOException e){
//			e.printStackTrace();
//		}
		
	}
	
	WikiPage() {
		
	}
	
	WikiPage(StringBuilder title){
		this.title = title;
	}
	
	public void setTitle(StringBuilder title) {
		this.title = title;
	}
	
	public StringBuilder getTitle() {
		return this.title;
	}
	
	public void setId(StringBuilder id){
		this.id = id;
	}
	
	public StringBuilder getId(){
		return this.id;
	}
	
	public void setContent(StringBuilder content){
		this.content = content;
		this.parse();
		//System.out.println(this.content);
	}
	
	public StringBuilder getContent(){
		return this.content;
	}
	
	public void addInfobox(StringBuilder infobox){
		this.infobox.add(infobox);	
	}
	
	public void setInfobox(ArrayList<StringBuilder> infobox){
		this.infobox = infobox;
	}
	
	public ArrayList<StringBuilder> getInfobox(){
		return this.infobox;
	}
	
	public void setOutlink(ArrayList<StringBuilder> outlink){
		this.outlink = outlink;
		
	}
	
	public void addOutlink(StringBuilder outlink){
		this.outlink.add(outlink);
		
	}
	
	public ArrayList<StringBuilder> getOutlink(){
		return this.outlink;
	}
	
	public void setCategory(ArrayList<StringBuilder> category){
		this.outlink = category;
		
	}
	
	public void addCategory(StringBuilder category){
		while(true) {
			StringBuilder temp = extractData("[[", "]]", category, false);
			if(temp.length() == 0) break;
			this.addOutlink(temp);
		}
		this.category.add(category);
		
	}
	
	public ArrayList<StringBuilder> getCategory(){
		return this.category;
	}
	
	
	public String toString(){
		StringBuffer retStr = new StringBuffer();
		retStr.append("Page details:\n");
		retStr.append("Title: " + getTitle()+"\n");
		retStr.append("Id: " + getId()+"\n");
		//retStr.append("Content: " + getContent()+"\n");
		return retStr.toString();
	}
	
	
	public StringBuilder extractData(String start, String end, StringBuilder data, boolean remove) {
		int iterStart = data.indexOf(start);
		int iterClose = data.indexOf(end, iterStart+1);
		if(iterStart < 0 || iterClose < 0){
			return new StringBuilder();
		}
		String ret;
		ret = data.substring(iterStart, iterClose+end.length());
		if(remove) data.replace(iterStart, iterClose+end.length(), " ");
		return new StringBuilder(ret);
	}
	
	
	
	public StringBuilder extractData(String start, String end, String problem, boolean remove) {
		int iterClosePrev = 0;
		int level = 0;
		int iterStart = content.indexOf(start);
		int tempiterClose = content.indexOf(end, iterStart+1);
		int tempiterTemp = content.indexOf(problem, iterStart+1);
		if(iterStart < 0 || tempiterClose < 0 || tempiterTemp < 0) {
			return new StringBuilder();
		}
		int iterOpen = iterStart;
		int iterClose = iterStart;
		while(iterClose > 0) {
			iterClose = content.indexOf(end, iterClose+1);
			if(iterClose != -1) iterClosePrev = iterClose;
			while(iterOpen < iterClose) {
				level++;
				int iterTemp = content.indexOf(problem, iterOpen+1);
				if(iterTemp == -1 || iterTemp == iterOpen) break;
				
				iterOpen = iterTemp;
			}
			level--;
//			System.out.println(iterStart + " " + iterClosePrev);
			if(level < 1) break;
		}
		StringBuilder ret;
		if(iterClose != -1) {
			ret = new StringBuilder(content.substring(iterStart, iterClose+end.length()));
			if(remove) content.replace(iterStart,iterClose+end.length()," ");
		}
		else {
			//System.out.println(iterStart + " " + iterClosePrev);
			ret = new StringBuilder(content.substring(iterStart, iterClosePrev+end.length()));
			if(remove) content.replace(iterStart,iterClosePrev+end.length()," ");
		}
		return ret;
	}
}
