package backend.util;

import backend.cell.CellTemplate;
import backend.game_engine.ResultQuadPredicate;
import backend.grid.ModifiableGameBoard;
import backend.player.Player;
import backend.player.Team;
import backend.unit.UnitTemplate;
import com.thoughtworks.xstream.XStream;
import util.io.Serializer;
import util.io.Unserializer;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * @author Created by th174 on 3/30/2017. Worked on by Alex
 */

//TODO: Implement getTurnNumber(), toXML() (Kinda Tavo's job), messagePlayer(Player from, Player to, String message), and endTurn();
public class GameState implements MutableGameState {
	private List<Player> playerList;
	private Player currentPlayer;
	private Collection<Team> teams;
	private ModifiableGameBoard gameGrid;
	private Collection<UnitTemplate> unitTemplates;
	private Collection<CellTemplate> cellTemplates;

	private Collection<ResultQuadPredicate> currentObjectives;
	private Map<Event, List<BiConsumer<Player, ImmutableGameState>>> turnActions;
	private List<BiPredicate<Player, ImmutableGameState>> turnRequirements;

	public Collection<UnitTemplate> getUnitTemplates() {
		return unitTemplates;
	}

	public Collection<CellTemplate> getCellTemplates() {
		return cellTemplates;
	}

	public void endTurn(Player player) {
		if (playerList.indexOf(player) == playerList.size()) {
			setCurrentPlayer(playerList.get(0));
			return;
		}
		setCurrentPlayer(playerList.get(playerList.indexOf(player) + 1));
	}

	@Override
	public ModifiableGameBoard getGrid() {
		return gameGrid;
	}

	@Override
	public Collection<Team> getTeams() {
		return teams;
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Player> getPlayers() {
		return playerList;
	}

	@Override
	public void messagePlayer(Player from, Player to, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getTurnNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addEventHandler(BiConsumer<Player, ImmutableGameState> eventListener, Event event) {
		turnActions.merge(event, new ArrayList<>(Collections.singletonList(eventListener)), (list, t) -> {
			list.addAll(t);
			return list;
		});
	}

	@Override
	public Collection<ResultQuadPredicate> getObjectives() {
		return currentObjectives;
	}

	@Override
	public void addObjective(ResultQuadPredicate winCondition) {
		currentObjectives.add(winCondition);
	}

	@Override
	public Map<Event, List<BiConsumer<Player, ImmutableGameState>>> getTurnEvents() {
		return turnActions;
	}

	@Override
	public void addTurnRequirement(BiPredicate<Player, ImmutableGameState> requirement) {
		turnRequirements.add(requirement);
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

}
