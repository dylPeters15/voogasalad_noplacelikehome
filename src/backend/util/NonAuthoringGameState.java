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

public interface NonAuthoringGameState extends VoogaEntity{
	ModifiableGameBoard getGrid();

	void endTurn();

	default NonAuthoringGameState messageAll(String message, ImmutablePlayer sender) {
		ChatMessage chatMessage = new ChatMessage(ChatMessage.AccessLevel.ALL, sender, message);
		getTeams().forEach(team -> team.forEach(player -> player.receiveMessage(chatMessage)));
		return this;
	}

	default NonAuthoringGameState messagePlayer(String message, ImmutablePlayer sender, ImmutablePlayer recipient) {
		ChatMessage chatMessage = new ChatMessage(ChatMessage.AccessLevel.WHISPER, sender, message);
		sender.receiveMessage(chatMessage);
		recipient.receiveMessage(chatMessage);
		return this;
	}

	default NonAuthoringGameState messageTeam(String message, ImmutablePlayer sender) {
		ChatMessage chatMessage = new ChatMessage(ChatMessage.AccessLevel.TEAM, sender, message);
		sender.getTeam().forEach(player -> player.receiveMessage(chatMessage));
		System.out.println(sender.getTeam().size());
		return this;
	}
	
	default boolean canEndTurn() {
		return getTurnRequirements().parallelStream().anyMatch(e -> !e.test(getCurrentPlayer(), this));
	}

	NonAuthoringGameState addTeam(Team team);

	Collection<Team> getTeams();

	Team getTeamByName(String teamName);

	Collection<Player> getPlayers();
	
	Player getCurrentPlayer();
	
	void setCurrentPlayer(Player player);

	Player getPlayerByName(String playerName);

	int getTurnNumber();

	Collection<ResultQuadPredicate> getObjectives();
	
	Map<Event, Collection<BiConsumer<Player, NonAuthoringGameState>>> getTurnEvents();
	
	Collection<BiPredicate<Player, NonAuthoringGameState>> getTurnRequirements();
	
	double random();
}
