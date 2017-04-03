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

    void addEventHandler(BiConsumer<Player, ImmutableGameState> eventListener, Event event);
}
