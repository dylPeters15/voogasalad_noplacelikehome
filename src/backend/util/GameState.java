package backend.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import backend.cell.ModifiableCell;
import backend.cell.ModifiableTerrain;
import backend.game_engine.ResultQuadPredicate;
import backend.game_rules.GameRule;
import backend.grid.ModifiableGameBoard;
import backend.player.Player;
import backend.player.Team;
import backend.unit.ModifiableUnit;
import backend.unit.properties.ActiveAbility;

/**
 * @author Created by th174 on 3/30/2017. Worked on by Alex and Noah (ncp14)
 */
//TODO: Implement getTurnNumber(), messagePlayer(Player from, Player to, String message);
public class GameState implements MutableGameState {
	private List<Player> playerList;
	private Player currentPlayer;
	private Map<String, Team> teams;
	private ModifiableGameBoard gameGrid;
	private Collection<ModifiableUnit> modifiableUnits;
	private Collection<ActiveAbility<?>> activeAbilities;
	private Collection<ModifiableTerrain> terrains;
	private Collection<ModifiableCell> modifiableCells;

	private final Random random;

	private Collection<ResultQuadPredicate> currentObjectives;
	private Map<Event, List<BiConsumer<Player, ImmutableGameState>>> turnActions;
	private Collection<BiPredicate<Player, ImmutableGameState>> turnRequirements;

	public GameState() {
		this(null);
	}

	public GameState(ModifiableGameBoard grid) {
		gameGrid = grid;
		playerList = new ArrayList<>();
		teams = new HashMap<>();
		modifiableUnits = new ArrayList<>();
		activeAbilities = new ArrayList<>(ActiveAbility.getPredefinedActiveAbilities());
		terrains = new ArrayList<>(ModifiableTerrain.getPredefinedTerrain());
		modifiableCells = new ArrayList<>();
		currentObjectives = new ArrayList<>();
		turnActions = new HashMap<>();
		turnRequirements = new ArrayList<>();
		random = new Random();
		random.setSeed(12012);
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

	public Collection<ActiveAbility<?>> getActiveAbilities() {
		return activeAbilities;
	}

	public Collection<ModifiableCell> getModifiableCells() {
		return modifiableCells;
	}
	
	public List<GameRule> getRules()
	{
		return gameRules;
	}
	
	public void updateGameRules(List<GameRule> mRules)
	{
		gameRules = mRules;
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

	@Override
	public double random() {
		return random.nextDouble();
	}

	private void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	public void setGrid(ModifiableGameBoard grid) {
		gameGrid = grid;
	}
}
