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
		
	private CommunicationController myController;

	public ModelGenerator(CommunicationController myController) {
		this.myController=myController;
		generateGamestate();
	}

	public void generateGameState()
	{
		//GameRuleCreator. Pass in gameState
			//Translate/parse/etc
		//This is basically just XML/JSON
	}
	
	public void setGameState(GameState mGameState)
	{
		this.gameState = mGameState;
	}
	
	
	
	
	
	
	
	
	
=======









>>>>>>> 47c3f2afac411cab22c43c48263a62e6ed3af7e3
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
