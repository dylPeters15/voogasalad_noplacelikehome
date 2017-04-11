package backend.game_engine;

import backend.player.Player;
import backend.util.AuthorGameState;

import java.io.File;
import java.io.Serializable;

/**
 * @author Alexander Zapata
 */
public interface GameEngine {
	
	void save(AuthorGameState gameState);

	AuthorGameState load(File gameStateFile);
	
}