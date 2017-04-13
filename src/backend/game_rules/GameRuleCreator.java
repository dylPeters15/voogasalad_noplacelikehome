package backend.game_rules;

import backend.util.GameplayState;

import java.util.List;

public class GameRuleCreator {
	GameplayState gameState;
	String inputCode;
	String codeType;

	public void GameRuleCreator(GameplayState mGameState, String inputCode, String codeType) {
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
