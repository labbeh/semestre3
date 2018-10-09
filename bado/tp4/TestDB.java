import java.util.ArrayList;
import java.sql.SQLException;

public class TestDB {	

	public static void afficherProduits(ArrayList<Produit> alp) {
		for (Produit p : alp) {
			System.out.println(p);
		}
	}

	public static void main (String args [])
	{
		//DB mondb = DB.getInstance();
		DB2 mondb = DB2.getInstance();
		Produit p;
		ArrayList<Produit> alp;
		int nb;

		try {
			p = mondb.getProduit(5);
			System.out.println("Produit 5 : "+p);
		}
		catch (SQLException e) { 
			System.out.println(e);
		}
		
		try {
			nb = mondb.getNbProduits();						
			System.out.println("-----> Nombre de produits = "+nb);
		}
		catch (SQLException e) { 
			System.out.println(e);
		}

		try {
			alp = mondb.getProduits();
			System.out.println("----- Liste des produits -----");
			afficherProduits(alp);
		}
		catch (SQLException e) { 
			System.out.println(e);
		}
		

		try {
			Produit unProduit = new Produit(200,"CLASSEUR","BLEU",100);
			mondb.insertProduit(unProduit);
			alp = mondb.getProduits();						
			System.out.println("----- Liste des produits apres insertion -----");
			afficherProduits(alp);
		}
		catch (SQLException e) { 
			System.out.println(e);
		}

		try {
			mondb.updateProduit(200,"VERT");
			alp = mondb.getProduits();						
			System.out.println("----- Liste des produits apres modification -----");
			afficherProduits(alp);
		}
		catch (SQLException e) { 
			System.out.println(e);
		}

		try {
			mondb.deleteProduit(200);
			alp = mondb.getProduits();						
			System.out.println("----- Liste des produits apres suppression -----");
			afficherProduits(alp);
		}
		catch (SQLException e) { 
			System.out.println(e);
		}

		try {
			nb = mondb.getNbProduits();						
			System.out.println("-----> Nombre de produits = "+nb);
		}
		catch (SQLException e) { 
			System.out.println(e);
		}
	} //fin du main

}

