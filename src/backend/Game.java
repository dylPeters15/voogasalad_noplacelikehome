/**
 *
 */
package backend;

import backend.grid.Grid;
import backend.grid.Terrain;
import backend.unit.Unit;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * @author Dylan Peters
 */
public interface Game extends XMLsavable {
    List<Player> getPlayers();

    Grid getGrid();

    Player getCurrentPlayer();

    int getTurnNumber();

    int addObjective(BiPredicate<Player, Game> winCondition);

    int addTrigger(BiConsumer<Player, Game> betweenTurns);

    void start();

    void restart();

    void quit();

    void save();

    void load();

    void newUnit(Unit newUnit);

    void newTerrain(Terrain terrain);

}
