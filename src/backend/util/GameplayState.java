package backend.util;

import backend.game_engine.ResultQuadPredicate;
import backend.grid.GameBoard;
import backend.player.ChatMessage;
import backend.player.ImmutablePlayer;
import backend.player.Player;
import backend.player.Team;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 4/10/2017.
 */
public class GameplayState extends ImmutableVoogaObject {
	private final Random random;
	private int turnNumber;
	private int currentPlayerNumber;
	private List<String> playerNames;
	private Map<String, Player> playerList;
	private Map<String, Team> teams;
	private GameBoard grid;

	private Collection<ResultQuadPredicate> currentObjectives;
	private Map<Event, Collection<BiConsumer<Player, GameplayState>>> turnActions;
	private Collection<BiPredicate<Player, GameplayState>> turnRequirements;

	public GameplayState(String name, GameBoard grid, String description, String imgPath) {
		super(name, description, imgPath);
		teams = new HashMap<>();
		this.grid = grid;
		this.random = new Random(7);
		this.turnNumber = 0;
	}

	private GameplayState(String name, GameBoard grid, int turnNumber, Collection<Team> teams,
	                      Collection<ResultQuadPredicate> currentObjectives,
	                      Map<Event, Collection<BiConsumer<Player, GameplayState>>> turnActions,
	                      Collection<BiPredicate<Player, GameplayState>> turnRequirements,
	                      String description, String imgPath, Random random) {
		super(name, description, imgPath);
		this.grid = grid.copy();
		this.teams = teams.stream().map(Team::copy).collect(Collectors.toMap(Team::getName, e -> e));
		this.random = random;
		this.turnNumber = turnNumber;
		this.turnActions = turnActions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue())));
		this.currentObjectives = new HashSet<>(currentObjectives);
		this.turnRequirements = new HashSet<>(turnRequirements);
	}

	public Player getCurrentPlayer() {
		return playerList.get(playerNames.get(currentPlayerNumber));
	}

	public Team getTeamByName(String teamName) {
		return teams.get(teamName);
	}

	public Collection<Team> getTeams() {
		return Collections.unmodifiableCollection(teams.values());
	}

	public GameplayState endTurn() {
		currentPlayerNumber = (currentPlayerNumber + 1) % playerNames.size();
		turnNumber++;
		return this;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public GameplayState addPlayer(Player newPlayer) {
		return addPlayer(newPlayer, new Team(newPlayer.getName() + "'s Team", "", ""));
	}

	public GameplayState addPlayer(Player newPlayer, Team team) {
		playerNames.add(newPlayer.getName());
		playerList.putIfAbsent(newPlayer.getName(), newPlayer);
		team.addAll(newPlayer);
		return this;
	}

	public GameBoard getGrid() {
		return grid;
	}

	public double random() {
		return random.nextDouble();
	}

	public Collection<ResultQuadPredicate> getCurrentObjectives() {
		return Collections.unmodifiableCollection(currentObjectives);
	}

	public Map<Event, Collection<BiConsumer<Player, GameplayState>>> getTurnActions() {
		return Collections.unmodifiableMap(turnActions);
	}

	public Collection<BiPredicate<Player, GameplayState>> getTurnRequirements() {
		return Collections.unmodifiableCollection(turnRequirements);
	}

	public boolean turnRequirementsSatisfied() {
		return turnRequirements.stream().allMatch(e -> e.test(getCurrentPlayer(), this));
	}

	public GameplayState messageAll(String message, ImmutablePlayer sender) {
		ChatMessage chatMessage = new ChatMessage(ChatMessage.AccessLevel.ALL, sender, message);
		getTeams().forEach(team -> team.forEach(player -> player.receiveMessage(chatMessage)));
		return this;
	}

	public GameplayState messagePlayer(String message, ImmutablePlayer sender, ImmutablePlayer recipient) {
		ChatMessage chatMessage = new ChatMessage(ChatMessage.AccessLevel.WHISPER, sender, message);
		sender.receiveMessage(chatMessage);
		recipient.receiveMessage(chatMessage);
		return this;
	}

	public GameplayState messageTeam(String message, ImmutablePlayer sender) {
		ChatMessage chatMessage = new ChatMessage(ChatMessage.AccessLevel.TEAM, sender, message);
		sender.getTeam().forEach(player -> player.receiveMessage(chatMessage));
		System.out.println(sender.getTeam().size());
		return this;
	}

	public GameplayState addTeam(Team team) {
		teams.put(team.getName(), team);
		return this;
	}

	@Override
	public GameplayState copy() {
		return new GameplayState(getName(), getGrid(), turnNumber, getTeams(), currentObjectives, turnActions, turnRequirements, getDescription(), getImgPath(), random);
	}
}
