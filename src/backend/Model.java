package backend;

import backend.game_engine.GameEngine;

/**
 * Noah
 * @author Dylan Peters
 */
public interface Model {
	
	GameEngine getGame();

	void editGame(GameEngine gameToEdit);

	void loadGame(GameEngine gameToLoad);
	
}
