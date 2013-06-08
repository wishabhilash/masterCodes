package com.ass.server;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.lang.reflect.*;
import java.util.*;

import org.w3c.dom.*;

import javax.xml.xpath.*;
import javax.xml.parsers.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

/**
 * Servlet implementation class Skeleton
 */

@WebServlet(urlPatterns={"/Skeleton"})
public class Skeleton extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	protected String msg = "", XSDFIlePath = System.getenv("HOME") + "/workspace/Assign3/misc/serverXMLValidation.xsd";
	
    public Skeleton() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		msg = request.getParameterValues("msg")[0];
		
		PrintWriter pw = response.getWriter();
		
		// Validate incoming XML
		try{
			if(validation()){
				// Get name and return type and arguments from XML
				String name = "", args = "";
				
				try{
					TreeMap<Integer, String> map = new TreeMap<Integer, String>();
					name = getValue("//name/text()").item(0).getNodeValue();
	
					// Extract arguments and create compatible string for service call
					NodeList argList = getValue("//arg/text()");
					for(int i = 0; i < argList.getLength(); i++){
						String value = argList.item(i).getNodeValue();
						String valueList[] = value.split(" ");
						map.put(Integer.parseInt(valueList[1]), valueList[0] + " " + valueList[2]);
					}
					for(int i = 0; i < map.size(); i++){
						args += map.get(i);
						if(map.size() - i > 1) args += ",";
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				// Invoke the service
				String output = "";
				try{
					Class cls = Class.forName("com.ass.services." + name + "Skel");
					Object clsObj = cls.newInstance();
					Method met = cls.getDeclaredMethod(name, new Class[]{String.class});
					output = (String)met.invoke(clsObj, new String(args));
				}catch(Exception e){
					e.printStackTrace();
				}
				
				pw.write(output);
	
			}else{
				pw.write("POST request not valid!!!");
			}
			pw.close();
		}catch(SAXException e){
			e.printStackTrace();
		}
	}
	
	NodeList getValue(String xpathexpr) throws ParserConfigurationException, IOException,
	SAXException, XPathExpressionException{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(this.msg)));
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression exp = xpath.compile(xpathexpr);
		
		return (NodeList)exp.evaluate(doc, XPathConstants.NODESET);
	}
	
	boolean validation() throws SAXException, IOException{
		File schemaFile = new File(XSDFIlePath);
		Source xmlFile = new StreamSource(new StringReader(this.msg));
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
