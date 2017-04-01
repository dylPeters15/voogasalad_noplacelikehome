package backend.game_engine;

import backend.cell.Terrain;
import backend.io.XMLSavable;
import backend.player.Player;
import backend.unit.UnitInstance;
import backend.util.GameState;

import java.util.function.BiConsumer;

/**
 * @author Alexander Zapata
 */
public interface GameEngine extends XMLSavable {
    void addObjective(ResultQuadPredicate winCondition);

    void addTrigger(BiConsumer<Player, GameState> turnAction, GameState.Event when);

    void start();

    void restart();

    void quit();

    void save();

    void load();

    void newUnit(UnitInstance newUnit);

    void newTerrain(Terrain terrain);
}
