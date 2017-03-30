/**
 *
 */
package backend.game_engine;

import backend.grid.Grid;
import backend.io.XMLsavable;

import java.util.List;

/**
 * Alex
 *
 * @author Created by th174 on 3/30/2017.
 */
public interface GameState extends XMLsavable {
    List<Player> getPlayers();

    Grid getGrid();

    Player getCurrentPlayer();

    int getTurnNumber();
}
