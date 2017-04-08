package backend.game_engine;

import java.io.Serializable;

import backend.player.Player;
import util.io.Unserializer;

/**
 * @author Alexander Zapata
 */
public interface GameEngine {

	void messagePlayer(Player from, Player to, String message);
	
    void restart();

    Serializable save(Object gameEngine);

    Object load(Object gameEngine);
}