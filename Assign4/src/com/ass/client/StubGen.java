package com.ass.client;

import java.io.*;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;


public class StubGen {

	/**
	 * @param args
	 */
	StringBuilder template = new StringBuilder(), postDataTmpl = new StringBuilder();
	String registryPath = "misc/registry.xml", stubTemplatePath = "misc/clientStub.tmpl",
			postDataTmplPath = "misc/postDataXml.tmpl", newStubPath = "src/com/ass/client/",
//			XSDFilePath = System.getenv("HOME") + "/workspace/src/misc/clientXMLValidation.xsd";
			XSDFilePath = "misc/clientXMLValidation.xsd";
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		StubGen stubGen = new StubGen();
		stubGen.run();
	
		
		
	}
	
	public void run() throws Exception{
		if(validation()){
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			
			DefaultHandler handler = new MyParser();
			parser.parse(registryPath, handler);
		}else{
			System.out.println("ValidationError: Registry structure is not valid.");
		}
	}
	
	// Generate the stubs
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
		
		
		// Write onto file
		BufferedWriter br = new BufferedWriter(new FileWriter(newStubPath + fileMap.get("funcName") + "Stub.java"));
		br.write(templateTemp.toString());
		br.close();
	}
	
	
	// Replace placeholders in template
	void replaceAll(StringBuilder templateStr, String tag, String str){
		while(templateStr.indexOf(tag) != -1){
			templateStr.replace(templateStr.indexOf(tag), templateStr.indexOf(tag) 
					+ tag.length(), str);
		}
	}
	
	// Read stubTemplate file
	StringBuilder readStubTemplate(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		StringBuilder retStr = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null){
			retStr.append(line + "\n");
		}
		return retStr;
	}
	
	
	// Get the parsed line from registry
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
	
	// Validate registry
	boolean validation() throws SAXException, IOException{
		File schemaFile = new File(XSDFilePath);
		Source xmlFile = new StreamSource(new File(registryPath));
		SchemaFactory schemaFactory = SchemaFactory
		    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		try {
		  validator.validate(xmlFile);
		  return true;
		} catch (SAXException e) {
		  return false;
		}
	}

}




class MyParser extends DefaultHandler{
	boolean name = false;
	boolean returnDT = false;
	boolean argType = false;
	boolean argIdent = false;
	
	String nameStr = "";
	String returnDTStr = "";
	Vector<String> argTypeList = new Vector<String>();
	Vector<String> argIdentList = new Vector<String>();
	
	Vector<String> functionList = new Vector<String>();
	
	StubGen stubGen = new StubGen();
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
		if(qName.equalsIgnoreCase("name")){
			name = true;
		}

		if(qName.equalsIgnoreCase("returnDT")){
			returnDT = true;
		}
		
		if(qName.equalsIgnoreCase("argType")){
			argType = true;
		}
		
		if(qName.equalsIgnoreCase("argIdent")){
			argIdent = true;
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException{
		if(qName.equalsIgnoreCase("function")){
			String paramStr = "";
			for(int i = 0; i < argIdentList.size(); i++){
				paramStr += argTypeList.get(i) + " " + argIdentList.get(i);
				if((argIdentList.size() - i) > 1) paramStr += ",";
			}
			try{
				stubGen.generateStubs(stubGen.parseLine(returnDTStr + " " + nameStr + "(" + paramStr + ")"));
			}catch(IOException e){
				e.printStackTrace();
			}
//			functionList.add(returnDTStr + " " + nameStr + "(" + paramStr + ")");
			argIdentList.clear();
			argTypeList.clear();
		}
	}
	
	public void characters(char ch[], int start, int length) throws SAXException{
		if(name){
			nameStr = new String(ch, start, length);
			name = false;
		}
		
		if(returnDT){
			returnDTStr = new String(ch, start, length);
			returnDT = false;
		}

		if(argType){
			argTypeList.add(new String(ch, start, length));
			argType = false;
		}
		if(argIdent){
			argIdentList.add(new String(ch, start, length));
			argIdent = false;
		}
	}
	
	public Vector<String> getFunctionSigns(){
		return functionList;
	}
}