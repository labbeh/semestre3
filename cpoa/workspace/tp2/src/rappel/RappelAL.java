package rappel;

import java.util.ArrayList;
import java.util.Collections;

public class RappelAL
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ArrayList<String> alS = new ArrayList<String>();

		// add : ajout en fin d'AL
		alS.add("un");
		alS.add("deux");
		alS.add("trois");

		// parcours
		for (String elem : alS) {
			System.out.print(elem + " ");
		}

		System.out.println();
		
		// tri
		Collections.sort(alS);

		for (String elem : alS) {
			System.out.print(elem + " ");
		}
	}

}
