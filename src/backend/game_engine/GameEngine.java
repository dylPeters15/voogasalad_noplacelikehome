package backend.game_engine;

import backend.io.XMLSerializable;
import backend.player.Player;

/**
 * @author Alexander Zapata
 */
public interface GameEngine extends XMLSerializable {

    //All the checkers.
    
    void restart();

    void save();

    void load();
}