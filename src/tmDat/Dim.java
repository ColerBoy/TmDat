package src.tmDat;


import java.io.*;
import java.util.*;


public class Dim {
	public static TreeMap<Integer, String> arr = new TreeMap<Integer, String>();
	Dim(String namefiledim) throws FileNotFoundException, IOException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("dimens.ion"));
			String line;
			int num = 1;
			while((line = reader.readLine()) != null) { //читаем файл дименшен
				if(line.length()>1)
					arr.put(num, line); 
				num++;
			}
			reader.close();
			}
				finally {
				 //           System.out.println("End try");
			}
		}
		
	
	/*public void printDim() {
		for(Map.Entry<Integer, String> item : arr.entrySet())
			System.out.println(item.getKey() + " " + item.getValue());
	}*/
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Dim dim = new Dim("dimens.ion");
		//dim.printDim();
	}
}
