package backend.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

import backend.game_engine.ResultQuadPredicate;
import backend.grid.ModifiableGameBoard;
import backend.player.Player;
import backend.player.Team;

/**
 * @author Created by th174 on 3/30/2017. Worked on by Alex Zapata
 */
// TODO: Implement getTurnNumber() and getTurnState().
public class AuthorGameState implements AuthoringGameState {

	private Collection<Player> playerList;
	private Player currentPlayer;
	private Map<String, Team> teams;
	private ModifiableGameBoard gameGrid;
	private final Random random;

	private Collection<ResultQuadPredicate> currentObjectives;
	private Map<Event, Collection<BiConsumer<Player, NonAuthoringGameState>>> turnActions;
	private Collection<BiPredicate<Player, NonAuthoringGameState>> turnRequirements;

	public AuthorGameState() {
		this((ModifiableGameBoard) null);
	}

	public AuthorGameState(ModifiableGameBoard grid) {
		gameGrid = grid;
		playerList = new ArrayList<>();
		teams = new HashMap<>();
		currentObjectives = new ArrayList<>();
		turnActions = new HashMap<>();
		turnRequirements = new ArrayList<>();
		random = new Random();
		random.setSeed(7);
	}
	
	@SuppressWarnings("unchecked")
	private AuthorGameState(Object ... toBeCopied){
		playerList = (List<Player>) toBeCopied[0];
		currentPlayer = (Player) toBeCopied[1];
		teams = (Map<String, Team>) toBeCopied[2];
		gameGrid = (ModifiableGameBoard) toBeCopied[3];
		random = (Random) toBeCopied[4];
		currentObjectives = (Collection<ResultQuadPredicate>) toBeCopied[5];
		turnActions = (Map<Event, Collection<BiConsumer<Player, NonAuthoringGameState>>>) toBeCopied[6];
		turnRequirements = (Collection<BiPredicate<Player, NonAuthoringGameState>>) toBeCopied[7];
	}
	@Override
	public ModifiableGameBoard getGrid() {
		return gameGrid;
	}

	@Override
	public Team getTeamByName(String teamName) {
		return teams.get(teamName);
	}

	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public Player getPlayerByName(String playerName) {
		return (Player) teams.keySet().stream().map(e -> teams.get(e)).map(t -> {
			return t.get(playerName);
		});
	}

	@Override
	public int getTurnNumber() {
		return 0;
	}

	@Override
	public Collection<ResultQuadPredicate> getObjectives() {
		return currentObjectives;
	}

	@Override
	public Map<Event, Collection<BiConsumer<Player, NonAuthoringGameState>>> getTurnEvents() {
		return turnActions;
	}

	@Override
	public Collection<BiPredicate<Player, NonAuthoringGameState>> getTurnRequirements() {
		return turnRequirements;
	}

	@Override
	public double random() {
		return random.nextDouble();
	}

	@Override
	public AuthoringGameState addObjective(ResultQuadPredicate winCondition) {
		return this;
	}

	@Override
	public Collection<Team> getTeams() {
		return teams.values();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Player> getPlayers() {
		return (List<Player>) teams.values().stream().flatMap(t -> t.stream()).collect(Collectors.toList());
	}

	@Override
	public AuthoringGameState addTurnRequirement(BiPredicate<Player, NonAuthoringGameState> requirement) {
		turnRequirements.add(requirement);
		return this;
	}

	public void endTurn() {
		setCurrentPlayer(((ArrayList<Player>) playerList).get((((ArrayList<Player>) playerList).indexOf(getCurrentPlayer()) + 1) % playerList.size()));
	}

	@Override
	public NonAuthoringGameState addTeam(Team team) {
		teams.put(team.getName(), team);
		return this;
	}

	@Override
	public AuthoringGameState addEventHandler(BiConsumer<Player, NonAuthoringGameState> eventListener, Event event) {
		turnActions.merge(event, new ArrayList<>(Collections.singletonList(eventListener)), (list, t) -> {
			list.addAll(t);
			return list;
		});
		return this;
	}

	@Override
	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AuthorGameState copy() {
		return new AuthorGameState(playerList.stream().map(e -> e.copy()).collect(Collectors.toList()), 
				currentPlayer.copy(),
				teams.values().stream().map(t -> t.copy()).collect(Collectors.toMap(e -> e.getName(), e -> e)),
				gameGrid.copy(),
				random,
				currentObjectives.stream().collect(Collectors.toList()),
				turnActions.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue()))),
				turnRequirements.stream().collect(Collectors.toList()));
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImgPath() {
		// TODO Auto-generated method stub
		return null;
	}

}