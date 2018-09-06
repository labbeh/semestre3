package irb;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ClassementIRB {

	private ArrayList<String> alS;
	
	public ClassementIRB(String nomFich)
	{
		this.alS = new ArrayList<String>();
		
		try
		{
			InputStream ips = this.getClass().getResourceAsStream("/" +nomFich);
			InputStreamReader ipsr = new InputStreamReader(ips);
			//FileReader fr = new FileReader(ipsr);
			Scanner    sc = new Scanner(ipsr);
			
			while(sc.hasNextLine()) this.alS.add(sc.nextLine());
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for(String line: this.alS) sb.append(line);
		
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ClassementIRB irb2007 = new ClassementIRB("i2007.txt");
		ClassementIRB irb2008 = new ClassementIRB("i2008.txt");
		ClassementIRB irb2009 = new ClassementIRB("i2009.txt");
		ClassementIRB irb2010 = new ClassementIRB("i2010.txt");
		ClassementIRB irb2011 = new ClassementIRB("i2011.txt");
		
		System.out.println("2007 " + irb2007);
		System.out.println("2008 " + irb2008);
		System.out.println("2009 " + irb2009);
		System.out.println("2010 " + irb2010);
		System.out.println("2011 " + irb2011);

	}

}
