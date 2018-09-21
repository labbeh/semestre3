package gameOfLife;

import java.util.Set;
import java.util.HashSet;

import utilitaires.Clavier;

// une cellule aura un état de vie et des cellules voisines
/**
* @author : hugo labbé
* @version: 1.1, 2018-09-14
*/

class Cell
{
	private boolean isAlive; /** true = alive, false = dead */
	private boolean nextStep; // valeur tampon, prochain état de la cellule courante

	private Set<Cell> neighbours;


	/**
	* @constructor: initialise une cellule morte
	*/
	Cell()
	{
		this.isAlive 	= false;
		this.nextStep 	= false;

		this.neighbours = new HashSet<>();
	}

	/* ACCESSEURS */

	/**
	* @return: true if the current Cell is alive
	*/
	boolean isAlive(){ return this.isAlive; }

	/* MODIFICATEURS */

	/** 
	* Permet d'affecter une valeur à isAlive
	* @param: true = vivant, false = mort
	*/
	void setAlive(boolean isAlive){ this.isAlive = isAlive; }

	/**
	* Ajoute un voisin à la cellule courante, ne fait rien si paramètre null
	* @param: voisin de type Cell
	*/
	void addNeighbours(Cell neighbours)
	{
		// de 3 à 8 voisins
		if(neighbours != null) this.neighbours.add(neighbours);
	}

	void nextStep()
	{
		int counter   = 0;
		this.nextStep = this.isAlive;

		for(Cell cell: this.neighbours)
			if(cell.isAlive()) counter++;

		if 	   ((counter < 2) || (counter > 3)) this.nextStep = false;
		else if(counter == 3) 					this.nextStep = true ;
	}

	void update()
	{
		this.isAlive = this.nextStep;
	}

	public static void main(String[] args)
	{
		Cell cell = new Cell();

		cell.addNeighbours(new Cell());
		cell.addNeighbours(new Cell());
		cell.addNeighbours(new Cell());
		cell.addNeighbours(new Cell());
		cell.addNeighbours(new Cell());
		cell.addNeighbours(new Cell());
		cell.addNeighbours(new Cell());
	}
}