/**
 *
 */
package backend;

import java.util.List;

import backend.grid.Grid;
import backend.grid.Terrain;
import backend.unit.Unit;

/**
 * @author Dylan Peters
 */
public interface Game extends XMLsavable {
	List<Player> getPlayers();

	Grid getGrid();

	Player getCurrentPlayer();

	int getTurnNumber();

	void start();

	void restart();

	void quit();

	void save();

	void load();

	void newUnit(Unit newUnit);

	void newTerrain(Terrain terrain);

}
