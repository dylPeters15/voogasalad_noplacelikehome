package backend.util;

import backend.game_engine.ResultQuadPredicate;
import backend.grid.MutableGrid;
import backend.player.Player;
import backend.player.Team;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public interface ImmutableGameState {
    List<Player> getPlayers();

    MutableGrid getGrid();

    Player getCurrentPlayer();
    
    Collection<Team> getTeams();

    void messagePlayer(Player from, Player to, String message);

    default void messageAll(Player from, String message) {
        getPlayers().forEach(player -> messagePlayer(from, player, message));
    }

    default void messageTeam(Player from, String message) {
        from.getTeam().getAll().forEach(player -> messagePlayer(from, player, message));
    }

    int getTurnNumber();

    void addEventHandler(BiConsumer<Player, ImmutableGameState> eventListener, Event event);

	void addObjective(ResultQuadPredicate winCondition);

	void addTurnRequirements(Predicate<Player> requirement);
}
