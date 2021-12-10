package src.tmDat;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import java.io.*;

public class Main {

	public static void main(String[] args)  
			throws ParserConfigurationException, SAXException, IOException, 
			NullPointerException, InterruptedException {
		System.out.println("Start");
		Config config = new Config("tmdat.cnf.xml");
		config.print();

		Dim dim = new Dim("dimens.ion");
		//dim.printDim();
		
		ReadXML readxml = new ReadXML(config.input_file_dat_xml, config.output_file_number_name);
		readxml.load();
		readxml.print();
		System.out.println("Loading...");
		ReadTMI readTMI = new ReadTMI(config.input_file_TMI, config.output_file_TMI, readxml);
		readTMI.load();
		readTMI.print();
		
		System.out.println("Finish");
	}
}

