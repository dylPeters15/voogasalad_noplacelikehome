package controller;


import backend.ModelGenerator;
import backend.grid.GameBoard;
import backend.util.GameState;
import frontend.View;

/**
 * @author Created by ncp14
 * This class is the communication controller which communicates the information
 * in both directions.
 * 
 * myFrontBuffer passes from
 */
public class CommunicationController implements Controller {
	//front to back buffer for gamestate
	private Buffer<GameState> gameStateHistory;
	private GameState mGameState;
	private View mView;
	
	@Override
	public GameBoard getGrid() {
		return mGameState.getGrid();
	}


	@Override
	public Object getUnitTemplates() {
		return mGameState.getUnitTemplates();
	}
	
	public void setView(View view)
	{
		this.mView = view;
	}
	
	
	public void setGameState(GameState gameState)
	{
		this.mGameState = gameState;
	}

	
	
	
	
}