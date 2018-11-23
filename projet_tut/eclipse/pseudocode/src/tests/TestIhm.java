package tests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import ihm.IHMCui;

public class TestIhm {

	public static void main(String[] args)
	{
		IHMCui ihm = new IHMCui();
		
		String[] code;
		String[] trace;
		
		code = lireCode();
		trace = new String[code.length];
		
		// remplacement des tabulations par 4 espaces
		for(int i=0; i<code.length; i++)
			code[i] = code[i].replaceAll("\t", "    ");
		
		for(int i=0; i<trace.length; i++)
		{
			if(code[i].contains("écrire"))
				trace[i] = "value";
			else
				trace[i] = "";
		}
		
		ihm.afficher(code, trace);
	}

	private static String[] lireCode()
	{
		ArrayList<String> al = new ArrayList<>();
		String[] code = null;
		
		try {
			Scanner sc = new Scanner( new FileReader("unCode.txt") );
			
			while(sc.hasNextLine()) al.add(sc.nextLine());
			sc.close();
			
			code = new String[al.size()];
			al.toArray(code);
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return code;
	}

}
