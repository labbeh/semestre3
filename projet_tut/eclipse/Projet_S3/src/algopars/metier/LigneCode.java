package algopars.metier;

/**
 * Classe permettant creer une instance de ligne de code contenant 
 * son numero et son contenu
 * @author Hugo Labbe, Titouan Cornilleau, Clement Baron, Sebastien Mande,Loan Cadorel 
 * @version 2018-1-7, 1.0
 * */
class LigneCode {
	
	/**
	 * Numero de la ligne de code dans le fichier
	 * */
	private int numLig;
	
	/**
	 * Contenu de la ligne de code
	 * */
	private String contenu;
	
	public LigneCode(int numLig, String contenu) {
		this.numLig = numLig;
		this.contenu = contenu.replaceFirst("\\s+", "");
	}
	
	/*------------*/
	/* ACCESSEURS */
	/*------------*/
	
	/**
	 * Retourne le numero de la ligne de code
	 * @return un int
	 * */
	public int getNumLig() {
		return numLig;
	}
	
	/**
	 * Retourne le contenu de la ligne de code
	 * @return un String
	 * */
	public String getContenu() {
		return contenu;
	}
	
	@Override
	public String toString() {
		return numLig +") " +contenu;
	}

	
	/*---------------*/
	/* MODIFICATEURS */
	/*---------------*/
	/**
	 * Permet de dfinir le numero de la ligne de code
	 * @param numLig numero de la ligne en int
	 * */
	public void setNumLig(int numLig) {
		this.numLig = numLig;
	}
	
	/**
	 * Permet de definir le contenu de la ligne de code
	 * @param contenu String contenant la ligne de code
	 * */
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
}
