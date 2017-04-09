package backend.player;

import backend.cell.CellInstance;
import backend.grid.ModifiableGameBoard;
import backend.unit.UnitInstance;
import backend.unit.properties.Faction;
import backend.util.VoogaTemplate;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Alex
 *
 * @author Created by th174 on 3/28/2017.
 */
public class Player extends VoogaTemplate<Player> implements ImmutablePlayer {
	private Faction faction;
	private Team team;

	public Player(String name, Team team, Faction faction, String description, String imgPath) {
		super(name, description, imgPath);
		team.addAll(this);
		this.faction = faction;
	}

	@Override
	public Player copy() {
		return new Player(getName(), getTeam(), getFaction(), getDescription(), getImgPath());
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
	public Collection<UnitInstance> getOwnedUnits(ModifiableGameBoard grid) {
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

	public void setTeam(Team team) {
		this.team = team;
	}
}
