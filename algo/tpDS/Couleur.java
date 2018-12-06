public class Couleur implements Comparable<Couleur>
{
	private String nom;
	private int    longueurOnde;
	private int    nbOccurence;

	// Qte RGB
	private int    rouge;
	private int    vert;
	private int    bleu;

	public Couleur ( String nom, int longueurOnde, int r, int v, int b )
	{
		this.nom          = nom;
		this.longueurOnde = longueurOnde;
		this.nbOccurence  = 0;

		this.rouge        = r;
		this.vert         = v;
		this.bleu         = b;
	}

	public String getNom         () { return this.nom;                                        }
	public int    getLongueurOnde() { return this.longueurOnde;                               }
	public int    getNbOccurence () { return this.nbOccurence;                                }

	public int    getRVB         () { return this.rouge*65536 + this.vert*256 + this.bleu*1 ; }

	public void   ajouterOccurence(){ this.nbOccurence++;                                     }

	@Override
	public int compareTo(Couleur autreCoul){
		return Integer.compare(this.longueurOnde, autreCoul.longueurOnde);
	}

}