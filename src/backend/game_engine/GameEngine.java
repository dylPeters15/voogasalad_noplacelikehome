package backend.game_engine;

import backend.util.AuthoringGameState;
import backend.util.GameplayState;

import java.io.File;

/**
 * @author Alexander Zapata
 */
public interface GameEngine {
	
	void save(GameplayState gameState);

	AuthoringGameState load(File gameStateFile);
	
}