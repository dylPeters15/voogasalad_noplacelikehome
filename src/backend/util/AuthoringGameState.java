package backend.util;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import backend.game_engine.ResultQuadPredicate;
import backend.player.Player;
import backend.player.Team;

public interface AuthoringGameState extends NonAuthoringGameState, VoogaEntity {

	AuthoringGameState addEventHandler(BiConsumer<Player, NonAuthoringGameState> eventListener, Event event);
	
	AuthoringGameState addObjective(ResultQuadPredicate winCondition);
	
	Collection<Team> getTeams();

	Collection<Player> getPlayers();

	AuthoringGameState addTurnRequirement(BiPredicate<Player, NonAuthoringGameState> requirement);

}
