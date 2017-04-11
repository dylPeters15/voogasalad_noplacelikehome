package controller;

import backend.grid.GameBoard;
import backend.util.AuthoringGameState;

/**
 * @author Created by ncp14 on 4/3/2017.
 */
public interface Controller {
	
	GameBoard getGrid();
	
	AuthoringGameState getGameState();
	
	

	Object getUnitTemplates();


}
