package gameOfLife;

import java.util.Set;
import java.util.HashSet;

// Grille de cellules
/**
* @author : hugo labbé
* @version: 1.1, 2018-09-21
*/

public class Grid
{
	private Set<Cell> cells;

	Grid(Cell[][] board)
	{
		this.cells = new HashSet<>();

		for(int i=1; i<=board.length; i++)
			for(int j=1; j<=board[i].length; j++)
				this.cells.add(board[i][j]);
	}

	void nextStep()
	{
		for(Cell cell: this.cells) cell.nextStep();
		for(Cell cell: this.cells) cell.update	();
	}
}