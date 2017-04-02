package backend.player;

import backend.grid.Grid;
import backend.unit.UnitInstance;
import backend.util.VoogaObject;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Alex
 *
 * @author Created by th174 on 3/28/2017.
 */
public class Player extends VoogaObject {
    private Team team;

    public Player(String name, String description, String imgPath) {
        this(name, new Team(name + "'s Team", description, imgPath), description, imgPath);
    }

    public Player(String name, Team team, String description, String imgPath) {
        super(name, description, imgPath);
        team.add(this);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Collection<UnitInstance> getOwnedUnits(Grid grid) {
        return grid.getUnits().parallelStream().filter(e -> e.getOwner().equals(this)).collect(Collectors.toSet());
    }
}
