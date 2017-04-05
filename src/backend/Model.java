package backend;

import backend.util.GameState;

/**
 * Noah
 *
 * @author Dylan Peters
 */
public interface Model {
    //Is this class even necessary?? Our game state is literally the model
    GameState getGameState();

    GameState setGameState();
}
