package com.ass.client;

import java.io.*;
import java.util.HashMap;

public class StubGen {

	/**
	 * @param args
	 */
	static StringBuilder template = new StringBuilder(), postDataTmpl = new StringBuilder();
	static String registryPath = "misc/registry", stubTemplatePath = "misc/clientStub.tmpl",
			postDataTmplPath = "misc/postDataXml.tmpl", newStubPath = "src/com/ass/client/";
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		StubGen stubGen = new StubGen();
		stubGen.run();
			
	}
	
	public void run() throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(registryPath));
		String line = null;
		while((line = reader.readLine()) != null) generateStubs(parseLine(line));
		
	}
	
	void generateStubs(HashMap<String, String> fileMap) throws IOException{
		template = readStubTemplate(stubTemplatePath);
		postDataTmpl = readStubTemplate(postDataTmplPath);
		
		StringBuilder templateTemp = new StringBuilder(template.toString());
		StringBuilder postDataTmplTemp = new StringBuilder(postDataTmpl.toString());
		
		StringBuilder msgString = new StringBuilder();
		String argList[] = fileMap.get("args").split(",");
		
		int count = 0;
		for(String i : argList){
			String tempStr[] = i.replaceFirst("^ *|$ *", "").split(" ");
			
			if(tempStr[0].equals("int") || tempStr[0].equals("Integer")){
				tempStr[0] = "Integer";
				tempStr[1] = "new Integer(" + tempStr[1] + ").toString()";
			}
			else if(tempStr[0].equals("float") || tempStr[0].equals("Float")){
				tempStr[0] = "Float";
				tempStr[1] = "new Float(" + tempStr[1] + ").toString()";
			}
			else if(tempStr[0].equals("long") || tempStr[0].equals("Long")){
				tempStr[0] = "Long";
				tempStr[1] = "new Long(" + tempStr[1] + ").toString()";
			}
			else if(tempStr[0].equals("double") || tempStr[0].equals("Double")){
				tempStr[0] = "Double";
				tempStr[1] = "new Double(" + tempStr[1] + ").toString()";
			}
			else if(tempStr[0].equals("String")) tempStr[0] = "String";
			
			msgString.append("\"" + "<arg>" + "\" + " + "\"" + tempStr[0] + " " + count + " \" + " 
			+  tempStr[1].toString() + " + \"" + "</arg>" + "\" + ");
			
			
			count++;
		}
		msgString.replace(msgString.lastIndexOf(" +"), msgString.lastIndexOf(" +") + " +".length(), "");
		System.out.println(msgString.toString());
		
		// Create XML
		replaceAll(postDataTmplTemp, "[[ funcName ]]", fileMap.get("funcName"));
		replaceAll(postDataTmplTemp, "[[ returnDT ]]", fileMap.get("returnDT"));
		replaceAll(postDataTmplTemp, "[[ params ]]", msgString.toString());
		
		// Replace template
		replaceAll(templateTemp, "[[ funcName ]]", fileMap.get("funcName"));
		replaceAll(templateTemp, "[[ returnDT ]]", fileMap.get("returnDT"));
		replaceAll(templateTemp, "[[ postData ]]", postDataTmplTemp.toString());
		replaceAll(templateTemp, "[[ params ]]", fileMap.get("args"));
		replaceAll(templateTemp, "[[ className ]]", fileMap.get("funcName") + "Stub");
		
//		if(fileMap.get("returnDT").equals("int") || fileMap.get("returnDT").equals("Integer")){
//			replaceAll(templateTemp, "[[ returnStat ]]", "Integer.parseInt(msg.toString())");
//		}else if(fileMap.get("returnDT").equals("float") || fileMap.get("returnDT").equals("Float")){
//			replaceAll(templateTemp, "[[ returnStat ]]", "Float.parseFloat(msg.toString())");
//		}else if(fileMap.get("returnDT").equals("double") || fileMap.get("returnDT").equals("Double")){
//			replaceAll(templateTemp, "[[ returnStat ]]", "Double.parseDouble(msg.toString())");
//		}else if(fileMap.get("returnDT").equals("long") || fileMap.get("returnDT").equals("Long")){
//			replaceAll(templateTemp, "[[ returnStat ]]", "Long.parseLong(msg.toString())");
//		}else if(fileMap.get("returnDT").equals("String")){
//			replaceAll(templateTemp, "[[ returnStat ]]", "msg.toString()");
//		}
		
		// Write onto file
		BufferedWriter br = new BufferedWriter(new FileWriter(newStubPath + fileMap.get("funcName") + "Stub.java"));
		br.write(templateTemp.toString());
		br.close();
	}
	
	void replaceAll(StringBuilder templateStr, String tag, String str){
		while(templateStr.indexOf(tag) != -1){
			templateStr.replace(templateStr.indexOf(tag), templateStr.indexOf(tag) 
					+ tag.length(), str);
		}
	}
	
	
	StringBuilder readStubTemplate(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		StringBuilder retStr = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null){
			retStr.append(line + "\n");
		}
		return retStr;
	}
	
	HashMap<String, String> parseLine(String line) throws IOException{
		
		HashMap<String, String> ret = new HashMap<String, String>();
		
		String output[] = line.split("[()]");
		String returnDT = output[0].split(" ")[0];
		String funcName = output[0].split(" ")[1];
		String args = output[1];
		ret.put("returnDT", returnDT);
		ret.put("funcName", funcName);
		ret.put("args", args);
		return ret;
	}
	

}
