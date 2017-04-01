/**
 *
 */
package backend.util;

import backend.grid.Grid;
import backend.io.XMLsavable;
import backend.player.Player;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author Created by th174 on 3/30/2017.
 */
public interface GameState extends XMLsavable {
    List<Player> getPlayers();

    Grid getGrid();

    Player getCurrentPlayer();

    int getTurnNumber();

    void addEventHandler(BiConsumer<Player, GameState> eventListener, Event event);

    enum Event {
        TURN_START, TURN_END, UNIT_MOVEMENT, UNIT_ABILITY_USE,
    }
}
