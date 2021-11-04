package src.tmDat;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Config {

	String input_file_dat_xml;
	String output_file_number_name;
	String input_file_TMI;
	String output_file_TMI;
	
//	public static TreeMap<Integer, String> arr = new TreeMap<Integer, String>();
	
	Config(String nameinputfile) throws ParserConfigurationException, SAXException, IOException {
		load(new FileInputStream(nameinputfile));
	}
	private void load(InputStream inputStream) 
            throws ParserConfigurationException, SAXException, IOException {
		Document doc;
        try {
            DocumentBuilder parser = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            doc = parser.parse(inputStream);
	        final NodeList topNodes = doc.getElementsByTagName("xmlfile");    
	        for (int i = 0; i < topNodes.getLength(); i++) {
	            final Node node = topNodes.item(i);
	            if (!(node instanceof Element))
	                continue;
	            final Element element = (Element)node;
	            if ("xmlfile".equals(element.getTagName())) {
	            	input_file_dat_xml = element.getAttribute("inputFile");
	            	output_file_number_name = element.getAttribute("outputFile");
	            
	            	
	            }
	        }
	        final NodeList topNodes2 = doc.getElementsByTagName("tmfile");    
	        for (int i = 0; i < topNodes2.getLength(); i++) {
	            final Node node2 = topNodes2.item(i);
	            if (!(node2 instanceof Element))
	                continue;
	            final Element element2 = (Element)node2;
	            if ("tmfile".equals(element2.getTagName())) {
	            	input_file_TMI = element2.getAttribute("inputFile");
	            	output_file_TMI = element2.getAttribute("outputFile");
	            		            }
	        }

        } 
        finally {
            inputStream.close();
        }
	}
	public void print() {
		System.out.println("Input TMI file: " + input_file_TMI);
		
		System.out.println("Output TMI file: " + output_file_TMI);
	
	}
	
	
}
