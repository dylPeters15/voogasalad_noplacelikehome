/**
 *
 */
package backend.game_engine;

import backend.cell.Terrain;
import backend.grid.Grid;
import backend.io.XMLsavable;
import backend.unit.Unit;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * Alex
 * @author Dylan Peters
 */
public interface GameEngine extends XMLsavable {
    List<Player> getPlayers();

    Grid getGrid();

    Player getCurrentPlayer();

    int getTurnNumber();

    int addObjective(BiPredicate<Player, GameEngine> winCondition);

    int addTrigger(BiConsumer<Player, GameEngine> betweenTurns);

    void start();

    void restart();

    void quit();

    void save();

    void load();

    void newUnit(Unit newUnit);

    void newTerrain(Terrain terrain);

}
