package exercice2.metier;

import java.io.Serializable;

public class Territoire implements Serializable
{
   private String nom;

   private Integer valMinX;
   private Integer valMaxX;
   private Integer valMinY;
   private Integer valMaxY;

	public Territoire(String nom, Integer valMinX, Integer valMaxX, Integer valMinY, Integer valMaxY)
    {
		this.nom = nom;

		this.valMinX = valMinX;
		this.valMaxX = valMaxX;
		this.valMinY = valMinY;
		this.valMaxY = valMaxY;
	}

	public Territoire(String nom)
	{
		this(nom, null, null, null, null);
	}

	public String getNom(){ return this.nom; }

	public int getMinX(){ return this.valMinX; }
	public int getMaxX(){ return this.valMaxX; }
	public int getMinY(){ return this.valMinY; }
	public int getMaxY(){ return this.valMaxY; }

	@Override
	public String toString()
	{
		if(this.valMinX == null) return "[" +this.nom+ "]";

		return this.nom +": ["
						+ this.valMinX + ";"
						+ this.valMaxX + ";"
						+ this.valMinY + ";"
						+ this.valMaxY + "]";
	}
}
