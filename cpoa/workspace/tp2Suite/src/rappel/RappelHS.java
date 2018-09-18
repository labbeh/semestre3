package rappel;

import java.util.HashSet;

public class RappelHS {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		HashSet <String> hsS = new HashSet <String>();

		// add : ajout à l'ensemble
		hsS.add("un");
		hsS.add("deux");
		hsS.add("trois");

		// parcours
		for (String elem : hsS) {
			System.out.print(elem + " ");
		}

		System.out.println();

		// un élément est-il présent ?
		System.out.println("un appartient-il ? " + hsS.contains("un"));
		System.out.println("dix appartient-il ? " + hsS.contains("dix"));

	}

}
