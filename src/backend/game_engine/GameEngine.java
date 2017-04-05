package backend.game_engine;

/**
 * @author Alexander Zapata
 */
public interface GameEngine {

	void messagePlayer(Player from, Player to, String message);
	
    void restart();

    void save();

    void load();
}