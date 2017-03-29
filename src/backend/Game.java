/**
 *
 */
package backend;

import backend.grid.Grid;

import java.util.List;

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
}
