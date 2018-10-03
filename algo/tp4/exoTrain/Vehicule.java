public class Vehicule
{
	private String couleur;

	public Vehicule( String couleur )
	{
		this.couleur = couleur.toUpperCase();
	}

	public String getCouleur(){ return this.couleur; }
}