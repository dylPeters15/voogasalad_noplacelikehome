package backend;

/**
 * @author Dylan Peters
 */
public interface Model {
	
	Game getGame();

	void editGame(Game gameToEdit);

	void loadGame(Game gameToLoad);
	
}
