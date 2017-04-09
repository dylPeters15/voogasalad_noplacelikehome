package backend.game_engine;

import backend.player.Player;

/**
 * @author Alexander Zapata
 */
public interface GameEngine {

	void messagePlayer(Player from, Player to, String message);

	void restart();

	void save();

	void load();
}