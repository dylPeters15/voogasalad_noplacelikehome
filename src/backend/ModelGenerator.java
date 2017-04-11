package backend;

import backend.util.AuthorGameState;
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
	public AuthorGameState getGameState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthorGameState setGameState() {
		// TODO Auto-generated method stub
		return null;
	}

}
