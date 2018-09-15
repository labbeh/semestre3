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

	private Set<Cell> neighbours;


	/**
	* @constructor: initialise une cellule morte
	*/
	Cell()
	{
		this.isAlive 	= false			 ;
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
	void addNeighbours(Cell neighbours){ this.neighbours.add(neighbours); }

	void nextStep()
	{
		int counter = 0;

		for(Cell cell: this.neighbours)
			if(cell.isAlive()) counter++;

		if 	   ((counter < 2) || (counter > 3)) this.isAlive = false;
		else if(counter == 3) 					this.isAlive = true ;
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