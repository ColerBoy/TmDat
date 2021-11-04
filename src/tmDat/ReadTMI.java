package src.tmDat;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.FileWriter;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.io.*;

public class ReadTMI {
	int TM=0; //подсчет всех тм файлов
	int TM0 = 0; //подсчет типа 1 LONG и т.д....
	int TM1 = 0;
	int TM2 = 0;
	int TM3 = 0;
	String f_input;
	String f_output;
	
	ReadXML readxml;	
	TreeSet<TmDat> arr = new TreeSet<TmDat>();
	
	ReadTMI(String inputStream, String out, ReadXML readxml) throws ParserConfigurationException, SAXException, IOException {
		f_input = inputStream;
		f_output = out;
		this.readxml = readxml;
	}
	public void load()  throws IOException {
		byte [] buff = new byte[90000];	
		try {			
			FileInputStream f_input = new FileInputStream(this.f_input);
			f_input.read(buff,0,32);
			while(f_input.read(buff,0,16)>0) {
				TmDat td = createTmDat(buff, f_input);
				if(td!=null)
					arr.add(td);
			}
			f_input.close();					
		}
		catch(FileNotFoundException e) {
			System.out.println("Error!!!");
		}
		catch(IOException ex) {
			System.out.println("Error2!!!");
		}	
	}
	TmDat create0(byte[] buff) {
		TmLong td = new TmLong();
		long lng = ((buff[12] << 24) & 0xFF000000) | ((buff[13]<<16) & 0xFF0000) | 
				((buff[14] << 8) & 0xFF00) | (buff[15] & 0xFF);
		((TmLong)td).value = (Long)(lng);
		return td;
	}
	TmDat create1(byte[] buff) {
		TmDouble td = new TmDouble();
		byte[] b= {buff[8], buff[9], buff[10], buff[11], buff[12], buff[13], buff[14], buff[15]};
		double dbl = ByteBuffer.wrap(b).getDouble();
		((TmDouble)td).value = dbl;
		return td;
	}
	TmDat create2(byte[] buff) {
		TmCode td = new TmCode();
		int length = buff[10] << 8 & 0xFF00 | buff[11] & 0xFF;
		String s = "";
		int numByte = (length-1)/8;
		int numBit = (length-1)%8;
		while(true) {
			s = s + ((buff[15-numByte] >> numBit) & 0x1);
			numBit--;
			if(numBit <0) {
				numBit+=8;
				numByte--;
			}
			if(numByte < 0)
				break;
		}
		((TmCode)td).len = length;
		((TmCode)td).value = s;
		return td;
	}
	TmDat create3(byte[] buff, FileInputStream f_input) throws IOException {
		TmPoint td = new TmPoint();
		int length = buff[10] << 8 & 0xFF00 | buff[11] & 0xFF;
		if(length > 4) {
			f_input.read(buff,0,length-4);
		}
		((TmPoint)td).len = length;
		return td;
	}
	public TmDat createTmDat(byte[] buff, FileInputStream f_input) throws IOException {
		int type;
		int number;	
		String dim, name;
		long time;    
		name = "";
		TmDat td = null;
		number = (buff[0]<<8 & 0xFF00 | buff[1]&0xFF);
		if(number == 0xFFFF)
			return null;
		time = (long)(buff[2]<<24 & 0xFF000000 | buff[3]<<16 & 0xFF0000 | buff[4]<<8 & 0xFF00 | buff[5] & 0xFF);
		time = time - (3*60*60*1000);
		dim = Dim.arr.get((int)(buff[6]));
		if(dim==null)
			dim=new String("dim=" + buff[6]);
		type = buff[7]&0xF;
		name = readxml.arr.get(number);		
		switch(type) {
		case 0:	
			td = create0(buff);
			++TM;
			++TM0;
			break;
		case 1:
			td = create1(buff);
			++TM;
			++TM1;
			break;
		case 2:
			td = create2(buff);
			++TM;
			++TM2;
			break;
		case 3:
			td = create3(buff, f_input);
			++TM;
			++TM3;
			break;
		default:
			System.out.println("Error!!! Type!!!");	
		}
		td.add(name, number, time, dim, type);
		return td;
	}
	public void print() {
		
	try {
			FileWriter f_output = new FileWriter(this.f_output);
			TmDat temp = null;
			for(TmDat item : arr) {		 
				if((temp == null) || (temp.num != item.num)) {
					item.print(f_output, 0);
					temp = item;
				}
				else
					item.print(f_output, 1);
		    }
			f_output.close();
		}
		catch(Exception e) {
			System.out.println(e.toString()); 
		} 
		 System.out.println("TM: " +  TM);
		 System.out.println("TM Long: " +   TM0);
		 System.out.println("TM Double: " +  TM1);
		 System.out.println("TM Code: " +  TM2);
		 System.out.println("TM Point: " +  TM3);
	}
}
