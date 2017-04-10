package backend.cell;

import backend.grid.CoordinateTuple;
import backend.grid.ModifiableGameBoard;
import backend.grid.Shape;
import backend.unit.Unit;
import backend.util.ImmutableGameState;
import backend.util.TriggeredEffect;
import backend.util.ModifiableTriggeredEffect;
import backend.util.VoogaEntity;

import java.util.Collection;
import java.util.Map;

/**
 * @author Created by th174 on 3/28/2017.
 */
public interface Cell extends VoogaEntity {
	void startTurn(ImmutableGameState gameState);

	void endTurn(ImmutableGameState gameState);

	CoordinateTuple getLocation();

	default int dimension() {
		return getShape().getDimension();
	}

	Shape getShape();

	Map<CoordinateTuple, Cell> getNeighbors(ModifiableGameBoard grid);

	Terrain getTerrain();

	Collection<? extends TriggeredEffect> getTriggeredAbilities();

	Cell addTriggeredAbilities(ModifiableTriggeredEffect... cellEffect);

	Cell removeTriggeredAbilities(ModifiableTriggeredEffect... cellEffect);

	Collection<? extends Unit> getOccupants();

	void leave(Unit unit, ImmutableGameState gamestate);

	void arrive(Unit unit, ImmutableGameState gamestate);
}
