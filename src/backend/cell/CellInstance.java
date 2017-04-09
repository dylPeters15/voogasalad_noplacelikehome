package backend.cell;

import backend.grid.CoordinateTuple;
import backend.grid.ModifiableGameBoard;
import backend.grid.Shape;
import backend.unit.UnitInstance;
import backend.util.ImmutableGameState;
import backend.util.TriggeredEffectInstance;
import backend.util.TriggeredEffectTemplate;
import backend.util.VoogaObject;

import java.util.Collection;
import java.util.Map;

/**
 * @author Created by th174 on 3/28/2017.
 */
public interface CellInstance extends VoogaObject {
	void startTurn(ImmutableGameState gameState);

	void endTurn(ImmutableGameState gameState);

	CoordinateTuple getLocation();

	default int dimension() {
		return getShape().getDimension();
	}

	Shape getShape();

	Map<CoordinateTuple, CellInstance> getNeighbors(ModifiableGameBoard grid);

	TerrainInstance getTerrain();

	Collection<? extends TriggeredEffectInstance> getTriggeredAbilities();

	CellInstance addTriggeredAbilities(TriggeredEffectTemplate... cellEffect);

	CellInstance removeTriggeredAbilities(TriggeredEffectTemplate... cellEffect);

	Collection<? extends UnitInstance> getOccupants();

	void leave(UnitInstance unitInstance, ImmutableGameState gamestate);

	void arrive(UnitInstance unitInstance, ImmutableGameState gamestate);
}
