package src.tmDat;

import java.io.FileWriter;

public class TmLong extends TmDat {

	long value;
	TmLong() {
		
	}
	TmLong(String name, int num, long time, String dim, int type) {
		super(name, num, time, dim, type);
	}
	public void add(String name, int num, long time, String dim, int type) {
		super.add(name, num, time, dim, type);
	}
	public void print(FileWriter f_output, int pr) {
		try {
			super.print(f_output, pr);
			f_output.write(" " + value+ " " + razm + "\n");
		}
		catch(Exception e) {
			System.out.println(e.toString()); 
		} 
	}
}

