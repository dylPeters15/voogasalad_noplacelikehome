package backend;

import backend.util.AuthoringGameState;

/**
 * Noah
 *
 * @author Dylan Peters
 */
public interface Model {
	//Is this class even necessary?? Our game state is literally the model
	AuthoringGameState getGameState();

	AuthoringGameState setGameState();
}
