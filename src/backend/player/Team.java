package backend.player;

import backend.util.VoogaCollection;

import java.util.Collection;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class Team extends VoogaCollection<Player, Team> {
    public Team(String name, String description, String imgPath) {
        super(name, description, imgPath);
    }

    public Team(String name, Collection<Player> gameObjects, String description, String imgPath) {
        super(name, description, imgPath, gameObjects);
    }

    @Override
    public Team copy() {
        return new Team(getName(), getAll(), getDescription(), getImgPath());
    }

    @Override
    public Team add(Player player) {
        super.add(player);
        player.setTeam(this);
        return this;
    }

    @Override
    public Team remove(String playerName) {
        get(playerName).setTeam(null);
        super.remove(playerName);
        return this;
    }
}
