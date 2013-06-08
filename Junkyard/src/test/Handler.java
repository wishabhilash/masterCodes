package test;

import java.util.*;

import org.w3c.dom.*;


public class Handler {

	/**
	 * @param args
	 */
	Util utilFunc = new Util();
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Handler handler = new Handler();
		String data = "<post>" +
				"<name>" + "Add" + "</name>" +
				"<retType>" + "String" + "</retType>" +
				"<param>" +
					"<arg>Integer 0 1</arg>" + 
					"<arg>Integer 1 2</arg>" + 
				"</param>" +
			"</post>";
		handler.compute(data);

	}

	public String compute(String data) throws Exception{
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		String args = "";
		
		// Extract arguments and create compatible string for service call
		NodeList argList = utilFunc.getValue("//arg/text()", data);
		for(int i = 0; i < argList.getLength(); i++){
			String value = argList.item(i).getNodeValue();
			String valueList[] = value.split(" ");
			map.put(Integer.parseInt(valueList[1]), valueList[2]);
		}
		for(int i = 0; i < map.size(); i++){
			args += map.get(i);
			if(map.size() - i > 1) args += ",";
		}
		System.out.println(args);
//		return utilFunc.invokeMethod("com.ass.server.server1.services.Add", "compute", args);
		return "";
	}

}
