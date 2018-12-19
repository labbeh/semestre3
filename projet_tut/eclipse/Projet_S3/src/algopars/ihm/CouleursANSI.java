package algopars.ihm;

public enum CouleursANSI {
	
	NOIR("\033[30m", "\033[40m"), 
	ROUGE("\033[31m", "\033[41m"), 
	VERT("\033[32m", "\033[42m"), 
	JAUNE("\033[33m", "\033[43m"), 
	BLEU("\033[34m", "\033[44m"), 
	MAUVE("\033[35m", "\033[45m"), 
	CYAN("\033[36m", "\033[46m"), 
	BLANC("\033[37m", "\033[47m"),
	NORMAL("\033[0m", "\033[0m");
	  
	String couleurTexte;
	String couleurFond;
	  
	private CouleursANSI(String couleurTexte, String couleurFond) {
		this.couleurTexte = couleurTexte;
	    this.couleurFond  = couleurFond;
	}
	  
	public String getCouleurTexte() {
		return couleurTexte;
	}
	  
	public String getCouleurFond(){
		return couleurFond;
	}
}
