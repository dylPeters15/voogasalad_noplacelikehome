package backend;

import backend.game_engine.GameState;

/**
 * Noah
 * @author Dylan Peters
 */
public interface Model {
	
	GameState getGameState();
	
	GameState setGameState();
	
}
