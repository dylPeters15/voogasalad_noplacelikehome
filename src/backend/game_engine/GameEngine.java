package backend.game_engine;

import backend.cell.Terrain;
import backend.io.XMLsavable;
import backend.player.Player;
import backend.unit.UnitInstance;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * Alex
 *
 * @author Dylan Peters
 */
public interface GameEngine extends XMLsavable {
    int addObjective(BiPredicate<Player, GameEngine> winCondition);

    int addTrigger(BiConsumer<Player, GameEngine> betweenTurns);

    void start();

    void restart();

    void quit();

    void save();

    void load();

    void newUnit(UnitInstance newUnit);

    void newTerrain(Terrain terrain);

}
