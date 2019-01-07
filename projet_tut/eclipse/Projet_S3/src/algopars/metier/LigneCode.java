package algopars.metier;

class LigneCode {
	
	/**
	 * Numéro de la ligne de code dans le fichier
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
	 * Retourne le numéro de la ligne de code
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
	 * Permet de définir le numéro de la ligne de code
	 * @param numLig numéro de la ligne en int
	 * */
	public void setNumLig(int numLig) {
		this.numLig = numLig;
	}
	
	/**
	 * Permet de définir le contenu de la ligne de code
	 * @param contenu String contenant la ligne de code
	 * */
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	
	
}
