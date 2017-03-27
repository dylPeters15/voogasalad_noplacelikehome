/**
 * 
 */
package backend;

import java.util.Collection;

import backend.grid.Grid;

/**
 * @author Dylan Peters
 *
 */
public interface Game {

	Time getTime();

	Collection<Player> getPlayers();

	Grid getGrid();

	Player getCurrentPlayer();

	int getTurnNumber();

	void start();

	void restart();

	void quit();

	void save();

	void load();

}
