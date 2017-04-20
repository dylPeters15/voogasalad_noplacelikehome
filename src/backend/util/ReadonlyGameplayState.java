package backend.util;

import backend.game_engine.ResultQuadPredicate;
import backend.grid.GameBoard;
import backend.player.ImmutablePlayer;
import backend.player.Team;
import backend.unit.ModifiableUnit;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * @author Created by th174 on 4/11/2017.
 */
public interface ReadonlyGameplayState extends VoogaEntity {
	ImmutablePlayer getCurrentPlayer();

	ImmutablePlayer getPlayerByName(String name);

	List<? extends ImmutablePlayer> getAllPlayers();

	Team getTeamByName(String teamName);

	Collection<Team> getTeams();

	int getTurnNumber();

	GameBoard getGrid();

	double random();

	Collection<ResultQuadPredicate> getObjectives();

	Map<Event, Collection<BiConsumer<ImmutablePlayer, GameplayState>>> getTurnActions();

	Collection<BiPredicate<ImmutablePlayer, GameplayState>> getTurnRequirements();

	boolean turnRequirementsSatisfied();
	
	
	
	//Next four methods depricated...
	void addUnit(ModifiableUnit mUnit);
	Collection<ModifiableUnit> getUnits();	
	void addUnitTemplates(ModifiableUnit mUnit);
	Collection<ModifiableUnit> getUnitTemplates();	
	

	@Override
	ReadonlyGameplayState copy();

	boolean isAuthoringMode();
}
