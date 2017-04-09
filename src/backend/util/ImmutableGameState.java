package backend.util;

import backend.game_engine.ResultQuadPredicate;
import backend.grid.ModifiableGameBoard;
import backend.player.ChatMessage;
import backend.player.ImmutablePlayer;
import backend.player.Player;
import backend.player.Team;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public interface ImmutableGameState {
	ModifiableGameBoard getGrid();

	ImmutableGameState endTurn();

	default ImmutableGameState messageAll(String message, ImmutablePlayer sender) {
		ChatMessage chatMessage = new ChatMessage(ChatMessage.AccessLevel.ALL, sender, message);
		getTeams().forEach(team -> team.forEach(player -> player.receiveMessage(chatMessage)));
		return this;
	}

	default ImmutableGameState messagePlayer(String message, ImmutablePlayer sender, ImmutablePlayer recipient) {
		ChatMessage chatMessage = new ChatMessage(ChatMessage.AccessLevel.WHISPER, sender, message);
		sender.receiveMessage(chatMessage);
		recipient.receiveMessage(chatMessage);
		return this;
	}

	default ImmutableGameState messageTeam(String message, ImmutablePlayer sender) {
		ChatMessage chatMessage = new ChatMessage(ChatMessage.AccessLevel.TEAM, sender, message);
		sender.getTeam().forEach(player -> player.receiveMessage(chatMessage));
		System.out.println(sender.getTeam().size());
		return this;
	}

	Collection<Team> getTeams();

	ImmutableGameState addTeam(Team team);

	Team getTeamByName(String teamName);

	List<Player> getPlayers();

	Player getPlayerByName(String playerName);

	int getTurnNumber();

	ImmutableGameState addEventHandler(BiConsumer<Player, ImmutableGameState> eventListener, Event event);

	Collection<ResultQuadPredicate> getObjectives();

	ImmutableGameState addObjective(ResultQuadPredicate winCondition);

	Map<Event, List<BiConsumer<Player, ImmutableGameState>>> getTurnEvents();

	ImmutableGameState addTurnRequirement(BiPredicate<Player, ImmutableGameState> requirement);

	default boolean canEndTurn() {
		return getTurnRequirements().parallelStream().anyMatch(e -> !e.test(getCurrentPlayer(), this));
	}

	Collection<BiPredicate<Player, ImmutableGameState>> getTurnRequirements();

	Player getCurrentPlayer();
}
