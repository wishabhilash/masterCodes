package test;

import org.w3c.dom.*;
import javax.xml.xpath.*;
import javax.xml.parsers.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.*;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

public class XPathTest {

	/**
	 * @param args
	 */
	String str = "<parse><arg>3</arg><arg>4</arg></parse>";
	
	public static void main(String[] args)  throws ParserConfigurationException, IOException, SAXException, XPathExpressionException{
		// TODO Auto-generated method stub
		XPathTest xp = new XPathTest();
		xp.Validation();
	}
	
	void Validation() throws SAXException, IOException{
		File schemaFile = new File("misc/serverXMLValidation.xsd");
//		URL schemaFile = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
		Source xmlFile = new StreamSource(new StringReader(str));
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
		Document doc = builder.parse(new InputSource(new StringReader(str)));
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression exp = xpath.compile("//arg/text()");
		
		NodeList nodes = (NodeList)exp.evaluate(doc, XPathConstants.NODESET);
		for(int i = 0; i < nodes.getLength(); i++){
			System.out.println(nodes.getLength());
			System.out.println(nodes.item(i).getNodeValue());
		}
	}

}
