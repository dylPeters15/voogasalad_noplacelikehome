package backend.util;

import backend.game_engine.ResultQuadPredicate;
import backend.grid.GameBoard;
import backend.player.ImmutablePlayer;
import backend.player.Player;
import backend.player.Team;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * @author Created by th174 on 4/11/2017.
 */
public interface ReadonlyGameplayState extends VoogaEntity {
	ImmutablePlayer getCurrentPlayer();

	ImmutablePlayer getPlayerByName(String name);

	Team getTeamByName(String teamName);

	Collection<Team> getTeams();

	int getTurnNumber();

	GameBoard getGrid();

	double random();

	Collection<ResultQuadPredicate> getObjectives();

	Map<Event, Collection<BiConsumer<Player, GameplayState>>> getTurnActions();

	Collection<BiPredicate<Player, GameplayState>> getTurnRequirements();

	boolean turnRequirementsSatisfied();

	@Override
	ReadonlyGameplayState copy();
}
