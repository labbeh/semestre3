import java.sql.*;
import java.util.ArrayList;

public class DB {
	private Connection connec;
	private static DB dbInstance;
	PreparedStatement psSelectP;

	private DB() {
		try {
			Class.forName("org.postgresql.Driver");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			connec = DriverManager.getConnection("jdbc:postgresql://woody/lh150094","lh150094","labbeh002"); // A MODIFIER !!
			psSelectP = connec.prepareStatement("SELECT * FROM pac_produit WHERE np = ?");
			/* A COMPLETER */
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DB getInstance() {
		if(dbInstance==null){dbInstance=new DB();}
		return dbInstance;
	}

	public Produit getProduit(int np) throws SQLException{
		Produit p = null;  
		psSelectP.setInt(1,np);
		ResultSet rsProd=psSelectP.executeQuery();
		if(rsProd.next()) 
			p = new Produit(rsProd.getInt("np"),rsProd.getString("lib"),rsProd.getString("coul"),rsProd.getInt("qs"));  
		rsProd.close();
		return p;
	  }
	  
	//Methode a n'utiliser que dans la classe DB
	//Le parametre req doit correspondre a une requete de la forme "SELECT * FROM produit WHERE ..."
	private ArrayList<Produit> getProduits(String req) throws SQLException {
		Statement selectNP=connec.createStatement();
		ArrayList<Produit> listeP=new ArrayList<Produit>();
			
		ResultSet rsP=selectNP.executeQuery(req);
		while(rsP.next()){			
			Produit p = new Produit(rsP.getInt("np"),rsP.getString("lib"),rsP.getString("coul"),rsP.getInt("qs"));
			listeP.add(p);
		  }
		rsP.close(); 
		return listeP;
	}

	public ArrayList<Produit> getProduits() throws SQLException {
		return getProduits("SELECT * FROM pac_produit");
	}

	public void insertProduit(Produit p) throws SQLException {
		/* A COMPLETER ! */
	}

	public void updateProduit(int np, String coul) throws SQLException {
		/* A COMPLETER ! */
	}

	public void deleteProduit(int np) throws SQLException {
		/* A COMPLETER ! */
	}

	public int getNbProduits() throws SQLException {
		return 0;
		/* A COMPLETER ! */
	}
	
	public static void main(String[] args)
	{
		System.out.println(DB.getInstance());
	}	
} //fin classe DB


