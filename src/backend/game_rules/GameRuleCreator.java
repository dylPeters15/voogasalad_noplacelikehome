package backend.game_rules;

import backend.util.GameState;

import java.util.List;

public class GameRuleCreator {

	GameState gameState;
	String inputCode;
	String codeType;

	public void GameRuleCreator(GameState mGameState, String inputCode, String codeType) {
		this.gameState = mGameState;
		this.inputCode = inputCode;
		this.codeType = codeType;
		performTranslation();
	}

	public void performTranslation() {
		Translate mTranslate = new Translate();
		List<GameRule> newRules = mTranslate.performTranslation();
	}

}
