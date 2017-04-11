package backend.game_rules;

import backend.util.AuthorGameState;

public class GameRuleCreator {
	
	AuthorGameState gameState;
	String inputCode;
	String codeType;
	
	public void GameRuleCreator(AuthorGameState mGameState, String inputCode, String codeType)
	{
		this.gameState = mGameState;
		this.inputCode = inputCode;
		this.codeType = codeType;
	}

}
