package test;

import java.io.*;
import java.lang.reflect.*;

import org.w3c.dom.*;

import javax.xml.xpath.*;
import javax.xml.parsers.*;

import org.xml.sax.*;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

public class Util {
	
	public String invokeMethod(String className, String methodName, String args) throws Exception{
		Class cls = Class.forName(className);
		Object clsObj = cls.newInstance();
		Method met = cls.getDeclaredMethod(methodName, new Class[]{String.class});
		return (String)met.invoke(clsObj, args);
	}
	
	public NodeList getValue(String xpathexpr, String msg) throws ParserConfigurationException, IOException,
	SAXException, XPathExpressionException{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(msg)));
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression exp = xpath.compile(xpathexpr);
		
		return (NodeList)exp.evaluate(doc, XPathConstants.NODESET);
	}
	
	public boolean validation(String XSDFIlePath, String msg) throws SAXException, IOException{
		File schemaFile = new File(XSDFIlePath);
		Source xmlFile = new StreamSource(new StringReader(msg));
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
