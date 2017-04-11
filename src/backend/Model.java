package backend;

import backend.util.AuthorGameState;

/**
 * Noah
 *
 * @author Dylan Peters
 */
public interface Model {
	//Is this class even necessary?? Our game state is literally the model
	AuthorGameState getGameState();

	AuthorGameState setGameState();
}
