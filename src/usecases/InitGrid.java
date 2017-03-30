package usecases;

import backend.grid.GameBoard;

public class InitGrid {
	private int x;
	private int y;
	private GameBoard gameBoard;
	
	public InitGrid(int x, int y){
		//game board will be initialized from GameBoard.java and Grid.java classes/interfaces
		this.gameBoard = new GameBoard(x, y);
	}
	
}
