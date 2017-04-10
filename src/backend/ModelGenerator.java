package backend;

import backend.util.GameState;
import controller.CommunicationController;

public class ModelGenerator implements Model {

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
