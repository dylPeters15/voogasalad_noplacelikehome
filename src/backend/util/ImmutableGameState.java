package backend.util;

import backend.grid.MutableGrid;
import backend.player.Player;

import java.util.List;
import java.util.function.BiConsumer;

public interface ImmutableGameState {
    List<Player> getPlayers();

    MutableGrid getGrid();

    Player getCurrentPlayer();

    void messagePlayer(Player from, Player to, String message);

    default void messageAll(Player from, String message) {
        getPlayers().forEach(player -> messagePlayer(from, player, message));
    }

    default void messageTeam(Player from, String message) {
        from.getTeam().getAll().forEach(player -> messagePlayer(from, player, message));
    }

    int getTurnNumber();

    void addEventHandler(BiConsumer<Player, ImmutableGameState> eventListener, Event event);
}
