package backend.util;

import backend.cell.CellTemplate;
import backend.cell.Terrain;
import backend.game_engine.ResultQuadPredicate;
import backend.grid.MutableGrid;
import backend.io.XMLSerializable;
import backend.player.Player;
import backend.player.Team;
import backend.unit.UnitTemplate;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * @author Created by th174 on 3/30/2017. Worked on by Alex
 */

// TODO: Implement getTurnNumber(), toXML() (Kinda Tavo's job),
// messagePlayer(Player from, Player to, String message), and endTurn();

public class GameState implements XMLSerializable, MutableGameState {

	private List<Player> playerList;
	private Player currentPlayer;
	private Collection<Team> teams;
	private MutableGrid gameGrid;

	private Collection<UnitTemplate> unitTemplates;
	private Collection<UnitTemplate> activeAbilities;
	private Collection<Terrain> terrains;
	private Collection<CellTemplate> cellTemplates;

	private Collection<ResultQuadPredicate> currentObjectives;
	private Map<Event, List<BiConsumer<Player, ImmutableGameState>>> turnActions;
	private Collection<BiPredicate<Player, ImmutableGameState>> turnRequirements;

	public GameState() {
		this(null);
	}

	public GameState(MutableGrid grid) {
		gameGrid = grid;

		playerList = new ArrayList<>();
		teams = new ArrayList<>();

		unitTemplates = new ArrayList<>();
		activeAbilities = new ArrayList<>();
		terrains = new ArrayList<>();
		cellTemplates = new ArrayList<>();

		currentObjectives = new ArrayList<>();
		turnActions = new HashMap<>();
		turnRequirements = new ArrayList<>();
	}

	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void endTurn() {
		if (playerList.indexOf(getCurrentPlayer()) == playerList.size()) {
			setCurrentPlayer(playerList.get(0));
			return;
		}
		setCurrentPlayer(playerList.get(playerList.indexOf(getCurrentPlayer()) + 1));
	}

	@Override
	public void messagePlayer(Player from, Player to, String message) {
		// TODO Auto-generated method stub

	}

	public Collection<Terrain> getTerrains() {
		return terrains;
	}

	@Override
	public void addObjective(ResultQuadPredicate winCondition) {
		currentObjectives.add(winCondition);
	}

	@Override
	public void addTurnRequirement(BiPredicate<Player, ImmutableGameState> requirement) {
		turnRequirements.add(requirement);
	}

	@Override
	public void addEventHandler(BiConsumer<Player, ImmutableGameState> eventListener, Event event) {
		turnActions.merge(event, new ArrayList<>(Collections.singletonList(eventListener)), (list, t) -> {
			list.addAll(t);
			return list;
		});
	}

	private void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	@Override
	public List<Player> getPlayers() {
		return playerList;
	}

	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Map<Event, List<BiConsumer<Player, ImmutableGameState>>> getTurnEvents() {
		return turnActions;
	}

	@Override
	public Collection<Team> getTeams() {
		return teams;
	}

	@Override
	public MutableGrid getGrid() {
		return gameGrid;
	}

	public void setGrid(MutableGrid grid) {
		gameGrid = grid;
	}

	public Collection<UnitTemplate> getUnitTemplates() {
		return unitTemplates;
	}

	public Collection<UnitTemplate> getActiveAbilities() {
		return activeAbilities;
	}

	public Collection<CellTemplate> getCellTemplates() {
		return cellTemplates;
	}

	@Override
	public Collection<ResultQuadPredicate> getObjectives() {
		return currentObjectives;
	}

	@Override
	public Collection<BiPredicate<Player, ImmutableGameState>> getTurnRequirements() {
		return turnRequirements;
	}

	@Override
	public int getTurnNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

}
