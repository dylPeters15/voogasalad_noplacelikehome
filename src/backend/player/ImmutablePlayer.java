package backend.player;

import java.util.Collection;

import backend.grid.MutableGrid;
import backend.unit.UnitInstance;

public interface ImmutablePlayer {
	Team getTeam();

	Collection<UnitInstance> getOwnedUnits(MutableGrid grid);
}
