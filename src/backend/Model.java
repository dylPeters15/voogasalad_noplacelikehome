package backend;

import backend.game_engine.GameState;

/**
 * Noah
 * @author Dylan Peters
 */
public interface Model {
	
	GameState getGame();

	void editGame(GameState gameToEdit);

	void loadGame(GameState gameToLoad);
	
}
