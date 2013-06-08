package test;

import org.w3c.dom.*;
import javax.xml.xpath.*;
import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.Vector;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

public class XPathTest {

	/**
	 * @param args
	 */
	String str = "misc/registry.xml";
	
	public static void main(String[] args)  throws ParserConfigurationException, IOException, SAXException, XPathExpressionException{
		// TODO Auto-generated method stub
		XPathTest xp = new XPathTest();
		xp.parse();
	}
	
	void parse() throws SAXException, ParserConfigurationException, IOException{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		
	}
	
	void Validation() throws SAXException, IOException{
		File schemaFile = new File("misc/clientXMLValidation.xsd");
//		URL schemaFile = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
		Source xmlFile = new StreamSource(new File(str));
		SchemaFactory schemaFactory = SchemaFactory
		    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		try {
		  validator.validate(xmlFile);
		  System.out.println(xmlFile.getSystemId() + " is valid");
		} catch (SAXException e) {
		  System.out.println(xmlFile.getSystemId() + " is NOT valid");
		  System.out.println("Reason: " + e.getLocalizedMessage());
		}
	}
	
	void run() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(new File(str));
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression exp = xpath.compile("//function");
		String line = "";
		NodeList nodes = (NodeList)exp.evaluate(doc, XPathConstants.NODESET);
		for(int i = 0; i < nodes.getLength(); i++){
			Node n = nodes.item(i);
//			NodeList childList = n.getChildNodes();
//			for(int j = 0; j < childList.getLength(); j++){
//				System.out.println(childList.item(j).getNodeName() + " " + childList.item(j).hasChildNodes());
//			}
			String data = n.getTextContent();
			Vector<String> vec = new Vector<String>();
			boolean flag = true;
			
			for(int j = 0; j < data.length(); j++){
				
				int m = (int)data.charAt(j);
				if(m != 9 || m != 10){
					line += Character.toString(data.charAt(j));
					System.out.println(line);
				}
				
			}
			
			System.out.println(vec.size());
			
		}
	}

}

