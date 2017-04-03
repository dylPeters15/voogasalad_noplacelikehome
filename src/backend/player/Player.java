package backend.player;

import java.util.Collection;
import java.util.stream.Collectors;

import backend.grid.MutableGrid;
import backend.unit.UnitInstance;
import backend.util.VoogaObject;

/**
 * Alex
 *
 * @author Created by th174 on 3/28/2017.
 */
public class Player extends VoogaObject implements MutablePlayer {
	private Team team;

	public Player(String name, String description, String imgPath) {
		this(name, new Team(name + "'s Team", description, imgPath), description, imgPath);
	}

	public Player(String name, Team team, String description, String imgPath) {
		super(name, description, imgPath);
		team.add(this);
	}

	@Override
	public Team getTeam() {
		return team;
	}

	@Override
	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public Collection<UnitInstance> getOwnedUnits(MutableGrid grid) {
		return grid.getUnits().parallelStream().filter(e -> e.getOwner().equals(this)).collect(Collectors.toSet());
	}
}
