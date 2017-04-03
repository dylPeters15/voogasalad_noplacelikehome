package backend;

import backend.util.GameState;

/**
 * Noah
 *
 * @author Dylan Peters
 */
public interface Model {
    GameState getGameState();

    GameState setGameState();
}
