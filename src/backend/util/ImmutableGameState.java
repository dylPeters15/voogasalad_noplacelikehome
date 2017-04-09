package backend.util;

import backend.game_engine.ResultQuadPredicate;
import backend.grid.ModifiableGameBoard;
import backend.player.Player;
import backend.player.Team;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public interface ImmutableGameState {
	ModifiableGameBoard getGrid();

	void endTurn();

	default void messageAll(Player from, String message) {
		getPlayers().forEach(player -> messagePlayer(from, player, message));
	}
    Collection<Team> getTeams();

	List<Player> getPlayers();

	void messagePlayer(Player from, Player to, String message);

	default void messageTeam(Player from, String message) {
		from.getTeam().getAll().forEach(player -> messagePlayer(from, player, message));
	}

	int getTurnNumber();

	void addEventHandler(BiConsumer<Player, ImmutableGameState> eventListener, Event event);

	Collection<ResultQuadPredicate> getObjectives();

	void addObjective(ResultQuadPredicate winCondition);

	Map<Event, List<BiConsumer<Player, ImmutableGameState>>> getTurnEvents();

	void addTurnRequirement(BiPredicate<Player, ImmutableGameState> requirement);

	default boolean canEndTurn() {
		return getTurnRequirements().parallelStream().anyMatch(e -> !e.test(getCurrentPlayer(), this));
	}

	Collection<BiPredicate<Player, ImmutableGameState>> getTurnRequirements();

	Player getCurrentPlayer();
}
