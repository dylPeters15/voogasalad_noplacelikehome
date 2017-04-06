package backend.player;

import backend.cell.CellInstance;
import backend.grid.MutableGrid;
import backend.unit.UnitInstance;
import backend.unit.properties.Faction;
import backend.util.VoogaObject;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Alex
 *
 * @author Created by th174 on 3/28/2017.
 */
public class Player extends VoogaObject implements MutablePlayer {
    private Faction faction;
    private Team team;

    public Player(String name, Team team, Faction faction, String description, String imgPath) {
        super(name, description, imgPath);
        team.add(this);
        this.faction = faction;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public Faction getFaction() {
        return faction;
    }

    @Override
    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public Collection<UnitInstance> getOwnedUnits(MutableGrid grid) {
        return grid.getUnits().parallelStream().filter(e -> e.getOwner().equals(this)).collect(Collectors.toSet());
    }

    @Override
    public Collection<CellInstance> getVisibleCells() {
        return null;
    }

    @Override
    public Collection<CellInstance> getExploredCells() {
        return null;
    }
}
