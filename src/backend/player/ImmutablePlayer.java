package backend.player;

import backend.cell.CellInstance;
import backend.grid.MutableGrid;
import backend.unit.UnitInstance;
import backend.unit.properties.Faction;

import java.util.Collection;

public interface ImmutablePlayer {
    Team getTeam();

    Faction getFaction();

    Collection<UnitInstance> getOwnedUnits(MutableGrid grid);

    Collection<CellInstance> getVisibleCells();

    Collection<CellInstance> getExploredCells();
}
