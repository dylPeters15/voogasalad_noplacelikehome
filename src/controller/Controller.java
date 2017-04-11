package controller;

import backend.grid.GameBoard;
import backend.util.GameState;

/**
 * @author Created by ncp14 on 4/3/2017.
 */
public interface Controller {
	
	GameBoard getGrid();
	
	GameState getGameState();
	
	

	Object getUnitTemplates();


}
