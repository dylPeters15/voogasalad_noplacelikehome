package controller;

import backend.grid.GameBoard;
import backend.util.AuthoringGameState;
import frontend.View;

/**
 * @author Created by ncp14 on 4/3/2017.
 */
public interface Controller {
	
	GameBoard getGrid();
	
	AuthoringGameState getGameState();
	
	public void setView(View view);

	Object getUnitTemplates();

	void setGameState(AuthoringGameState newGameState);


}
