package backend.game_rules;

import backend.util.GameState;

public class GameRuleCreator {
	
	GameState gameState;
	String inputCode;
	String codeType;
	
	public void GameRuleCreator(GameState mGameState, String inputCode, String codeType)
	{
		this.gameState = mGameState;
		this.inputCode = inputCode;
		this.codeType = codeType;
	}

}
