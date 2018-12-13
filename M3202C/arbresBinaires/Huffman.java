import java.util.*;
/**
* @author hugo labbé D2
* @version 2018-12-13
* Classe permettant de crééer un arbre de Huffman
*/

public class Huffman{

	/**
	* HashMap qui associe une fréquence en Double à une chaine de caractère
	* */
	private HashMap<String, Double> map;

	/**
	* ArbreBinaire dans lequel l'abre de huffman sera construit
	* */
	private ArbreBinaire arbre;

	/**
	* Constructeur d'un arbre de Huffman
	* @param map hashmap pour associer une chaine de caractère à une fréquence
	* @return un objet Huffman
	* */
	public Huffman(HashMap<String, Double> map){
		this.map = map;
	}

	/**
	* Construit un arbre de huffman
	* */
	public void genererArbre(){
		List<ArbreBinaire> arbres = new ArrayList<>();
		Set<String> etiquettes = map.keySet();

		// étape 1
		ArbreBinaire temp;
		for(String etiquette: etiquettes)
		{
			temp = new ArbreBinaire(etiquette);
			temp.setFrequence(map.get(etiquette));

			arbres.add(temp);
		}

		// étape 2
		String etiquetteGauche, etiquetteDroite;
		int iMin;

		while(!arbres.isEmpty()){
			iMin = min(arbres);
			etiquetteGauche = arbres.get(iMin).getEtiquette();
			arbres.remove(iMin);

			iMin = min(arbres);
			etiquetteDroite = arbres.get(iMin).getEtiquette();
			arbres.remove(iMin);

			temp = new ArbreBinaire("");
			temp.setSag(new ArbreBinaire(etiquetteGauche));
			temp.setSad(new ArbreBinaire(etiquetteDroite));
		}

		// étape 3
	}

	/**
	* Méthode privée retournant l'indice de l'arbre ayant la plus petite fréquences dans une List
	* @param arbres une List d'ArbreBinaire
	* @return entier indice du plus petit élément dans la List
	* */
	private static int min(List<ArbreBinaire> arbres){
		int iMin = 0;
		double frequence =0.0;

		for(int i=0; i<arbres.size(); i++){
			if(frequence < arbres.get(i).getFrequence()){
				frequence = arbres.get(i).getFrequence();
				iMin = i;
			}
		}

		return iMin;
	}
}