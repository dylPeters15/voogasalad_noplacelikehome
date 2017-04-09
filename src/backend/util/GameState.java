package backend.util;

import backend.cell.ModifiableCell;
import backend.cell.ModifiableTerrain;
import backend.game_engine.ResultQuadPredicate;
import backend.grid.ModifiableGameBoard;
import backend.player.Player;
import backend.player.Team;
import backend.unit.ModifiableUnit;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * @author Created by th174 on 3/30/2017. Worked on by Alex
 */

//TODO: Implement getTurnNumber(), toXML() (Kinda Tavo's job), messagePlayer(Player from, Player to, String message);
public class GameState implements MutableGameState {
	private List<Player> playerList;
	private Player currentPlayer;
	private Map<String, Team> teams;
	private ModifiableGameBoard gameGrid;
	private Collection<ModifiableUnit> modifiableUnits;
	private Collection<ModifiableUnit> activeAbilities;
	private Collection<ModifiableTerrain> terrains;
	private Collection<ModifiableCell> modifiableCells;

	private Collection<ResultQuadPredicate> currentObjectives;
	private Map<Event, List<BiConsumer<Player, ImmutableGameState>>> turnActions;
	private Collection<BiPredicate<Player, ImmutableGameState>> turnRequirements;

	public GameState() {
		this(null);
	}

	public GameState(ModifiableGameBoard grid) {
//		gameGrid = grid;
		playerList = new ArrayList<>();
		teams = new HashMap<>();
		modifiableUnits = new ArrayList<>();
		activeAbilities = new ArrayList<>();
		terrains = new ArrayList<>();
		modifiableCells = new ArrayList<>();
		currentObjectives = new ArrayList<>();
		turnActions = new HashMap<>();
		turnRequirements = new ArrayList<>();
	}

	public void endTurn(Player player) {
		setCurrentPlayer(playerList.get((playerList.indexOf(player) + 1) % playerList.size()));
	}

	public Collection<ModifiableTerrain> getTerrains() {
		return terrains;
	}

	public Collection<ModifiableUnit> getUnitTemplates() {
		return modifiableUnits;
	}

	public Collection<ModifiableUnit> getActiveAbilities() {
		return activeAbilities;
	}

	public Collection<ModifiableCell> getModifiableCells() {
		return modifiableCells;
	}

	@Override
	public ModifiableGameBoard getGrid() {
		return gameGrid;
	}

	@Override
	public ImmutableGameState endTurn() {
		if (playerList.indexOf(getCurrentPlayer()) == playerList.size() || playerList.size() == 0) {
			setCurrentPlayer(playerList.get(0));
			return this;
		}
		setCurrentPlayer(playerList.get(playerList.indexOf(getCurrentPlayer()) + 1));
		return this;
	}

	@Override
	public Collection<Team> getTeams() {
		return teams.values();
	}

	@Override
	public ImmutableGameState addTeam(Team team) {
		teams.put(team.getName(), team);
		return this;
	}

	@Override
	public Team getTeamByName(String teamName) {
		return teams.get(teamName);
	}

	@Override
	public List<Player> getPlayers() {
		return playerList;
	}

	@Override
	public Player getPlayerByName(String playerName) {
		return null;
	}

	@Override
	public int getTurnNumber() {
		return 0;
	}

	@Override
	public ImmutableGameState addEventHandler(BiConsumer<Player, ImmutableGameState> eventListener, Event event) {
		turnActions.merge(event, new ArrayList<>(Collections.singletonList(eventListener)), (list, t) -> {
			list.addAll(t);
			return list;
		});
		return this;
	}

	@Override
	public Collection<ResultQuadPredicate> getObjectives() {
		return currentObjectives;
	}

	@Override
	public ImmutableGameState addObjective(ResultQuadPredicate winCondition) {
		currentObjectives.add(winCondition);
		return this;
	}

	@Override
	public Map<Event, List<BiConsumer<Player, ImmutableGameState>>> getTurnEvents() {
		return turnActions;
	}

	@Override
	public ImmutableGameState addTurnRequirement(BiPredicate<Player, ImmutableGameState> requirement) {
		turnRequirements.add(requirement);
		return this;
	}

	@Override
	public Collection<BiPredicate<Player, ImmutableGameState>> getTurnRequirements() {
		return turnRequirements;
	}

	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	private void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	public void setGrid(ModifiableGameBoard grid) {
		gameGrid = grid;
	}
}
