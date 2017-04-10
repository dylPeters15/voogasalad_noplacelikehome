package backend;

import backend.util.GameState;
import controller.CommunicationController;

public class ModelGenerator implements Model {

	private CommunicationController controller;
	private GameState gameState;
	
	public ModelGenerator(CommunicationController mController, GameState mGameState) {
		this.controller=mController;
		this.gameState = mGameState;
	}
		
	public void generateGameState()
	{
		//GameRuleCreator. Pass in gameState
			//Translate/parse/etc
		
	}
	
	public void setGameState(GameState mGameState)
	{
		this.gameState = mGameState;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public GameState getGameState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameState setGameState() {
		// TODO Auto-generated method stub
		return null;
	}

}
