package gameOfLife;

public class GridFactory
{
	public static Grid createGrid( int rows, int cols ) throws IllegalArgumentException
	{
		if(rows < 2 || cols < 2) throw new IllegalArgumentException();

		Cell[][] board = new Cell[rows+2][cols+2]; // dans le sens "plateau de jeu"

		for(int i=1; i<=rows; i++)
			for(int j=1; j<=cols; j++) board[i][j] = new Cell();

		for(int i=1; i<=rows; i++)
			for(int j=1; j<=cols; j++)
			{
				board[i][j].addNeighbours(board[i-1][j-1]);
				board[i][j].addNeighbours(board[i  ][j-1]);
				board[i][j].addNeighbours(board[i-1][j  ]);
				board[i][j].addNeighbours(board[i+1][j+1]);
				board[i][j].addNeighbours(board[i  ][j+1]);
				board[i][j].addNeighbours(board[i+1][j  ]);
				board[i][j].addNeighbours(board[i+1][j-1]);
				board[i][j].addNeighbours(board[i-1][j+1]);
			}
		return new Grid(board);
	}

	private GridFactory(){}
}