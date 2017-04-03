package backend.game_engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import backend.cell.Terrain;
import backend.grid.Grid;
import backend.player.Player;
import backend.unit.UnitInstance;

public class DieselEngine implements GameEngine, GameState{

	private List<ResultQuadPredicate> currentObjectives;
	private Map<TurnTrigger, List<BiConsumer<Player, GameState>>> turnActions;
	private Optional<List<Predicate<Player>>> turnRequirements; 
	
	public DieselEngine(){}

	@Override
	public void canEndTurn() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addObjective(ResultQuadPredicate winCondition) {
		currentObjectives.add(winCondition);
	}

	@Override
	public void addTrigger(BiConsumer<Player, GameState> turnAction, TurnTrigger when) {
		turnActions.merge(when, new ArrayList<>(Collections.singletonList(turnAction)), (list, t) -> {
			list.addAll(t);
			return list;
		});
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newUnit(UnitInstance newUnit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newTerrain(Terrain terrain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Grid getGrid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTurnNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

}