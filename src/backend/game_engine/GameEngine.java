package backend.game_engine;

import backend.cell.Terrain;
import backend.io.XMLSerializable;
import backend.player.Player;
import backend.unit.UnitInstance;
import backend.util.Event;
import backend.util.ImmutableGameState;

import java.util.function.BiConsumer;

/**
 * @author Alexander Zapata
 */
public interface GameEngine extends XMLSerializable {
    void addObjective(ResultQuadPredicate winCondition);

    void addTrigger(BiConsumer<Player, ImmutableGameState> turnAction, Event when);

    void start();

    void restart();

    void quit();

    void save();

    void load();

    void newUnit(UnitInstance newUnit);

    void newTerrain(Terrain terrain);
}