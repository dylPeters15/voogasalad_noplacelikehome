package backend.util;

import java.util.List;
import java.util.function.BiConsumer;

import backend.grid.MutableGrid;
import backend.player.Player;

public interface ImmutableGameState {
	List<Player> getPlayers();

    MutableGrid getGrid();

    Player getCurrentPlayer();

    int getTurnNumber();

    void addEventHandler(BiConsumer<Player, GameState> eventListener, Event event);

    enum Event {
        TURN_START, TURN_END, UNIT_MOVEMENT, UNIT_ABILITY_USE,
    }
}
