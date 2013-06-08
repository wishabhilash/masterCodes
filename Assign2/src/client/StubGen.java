package client;

import java.io.*;
import java.util.HashMap;

public class StubGen {
	
	static StringBuilder template = new StringBuilder(""); 
	static String registryPath = "data/registry", stubTemplatePath = "data/stubTemplete.txt";
	
	public static void main(String args[]) throws IOException{
		
		// Read the stub template file
		readStubTemplate();
		
		BufferedReader br = new BufferedReader(new FileReader(registryPath));
		String line = null;
		while((line = br.readLine()) != null) generateStubs(parseLine(line));
		
	}
	
	static void generateStubs(HashMap<String, String> fileMap) throws IOException{
		
		
		StringBuilder temp = new StringBuilder(template.toString());
		
		// Create the agrs message
		String argList[] = fileMap.get("args").split(",");
		StringBuilder msgString = new StringBuilder();
		for(String i : argList){
			String tempStr[] = i.replaceFirst("^ *|$ *", "").split(" ");

			if(tempStr[0].equals("int") || tempStr[0].equals("Integer")){
				tempStr[0] = "\"Integer \"";
				tempStr[1] = "new Integer(" + tempStr[1] + ").toString()";
			}
			else if(tempStr[0].equals("float") || tempStr[0].equals("Float")){
				tempStr[0] = "\"Float \"";
				tempStr[1] = "new Float(" + tempStr[1] + ").toString()";
			}
			else if(tempStr[0].equals("long") || tempStr[0].equals("Long")){
				tempStr[0] = "\"Long \"";
				tempStr[1] = "new Long(" + tempStr[1] + ").toString()";
			}
			else if(tempStr[0].equals("double") || tempStr[0].equals("Double")){
				tempStr[0] = "\"Double \"";
				tempStr[1] = "new Double(" + tempStr[1] + ").toString()";
			}
			else if(tempStr[0].equals("String")) tempStr[0] = "\"String \"";
			
			msgString.append(tempStr[0] + " + " + tempStr[1] + " + \",\" + ");
		}
		msgString.replace(msgString.lastIndexOf(" + \",\" + "), msgString.lastIndexOf(",") + " + \",\" + ".length(), "");
		System.out.println(msgString.toString());

		// Replace placeholders in temp
		replaceAll(temp, "[[ funcName ]]", fileMap.get("funcName"));
		replaceAll(temp, "[[ returnDT ]]", fileMap.get("returnDT"));
		replaceAll(temp, "[[ args ]]", fileMap.get("args"));
		replaceAll(temp, "[[ signParam ]]", "\"" + fileMap.get("args") + "\"");
		replaceAll(temp, "[[ argsFormated ]]", msgString.toString());

		// Create stub file and write into it
		BufferedWriter bw = new BufferedWriter(new FileWriter("src/client/" + fileMap.get("funcName")+"Stub.java"));
		bw.write(temp.toString());
		bw.flush();
		bw.close();
	}
	
	static void replaceAll(StringBuilder templateStr, String tag, String str){
		while(templateStr.indexOf(tag) != -1){
			templateStr.replace(templateStr.indexOf(tag), templateStr.indexOf(tag) 
					+ tag.length(), str);
		}
	}
	
	static void readStubTemplate() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(stubTemplatePath));
		String line = null;
		while((line = br.readLine()) != null){
			template.append(line + "\n");
		}
	}

	static HashMap<String, String> parseLine(String line) throws IOException{
		
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
