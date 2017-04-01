package backend.player;

import backend.unit.UnitInstance;
import backend.util.GameState;
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
    private GameState gameState;

    public Player(String name, String description, String imgPath, GameState currentGame) {
        this(name, new Team(name + "'s Team", description, imgPath), description, imgPath, currentGame);
    }

    public Player(String name, Team team, String description, String imgPath, GameState gameState) {
        super(name, description, imgPath);
        team.add(this);
        this.gameState = gameState;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Collection<UnitInstance> getOwnedUnits() {
        return gameState.getGrid().getUnits().parallelStream().filter(e -> e.getOwner().equals(this)).collect(Collectors.toSet());
    }
}
