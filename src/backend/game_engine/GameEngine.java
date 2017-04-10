package backend.game_engine;

import backend.player.Player;

import java.io.Serializable;

/**
 * @author Alexander Zapata
 */
public interface GameEngine {
	void messagePlayer(Player from, Player to, String message);

	Serializable save(Object gameEngine);

	Object load(Object gameEngine);
}