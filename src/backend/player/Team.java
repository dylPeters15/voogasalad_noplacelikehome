package backend.player;

import backend.GameObjectSet;

import java.util.Collection;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class Team extends GameObjectSet<Player> {
    public Team(String name, String description, String imgPath) {
        super(name, description, imgPath);
    }

    public Team(String name, Collection<Player> gameObjects, String description, String imgPath) {
        super(name, gameObjects, description, imgPath);
    }

    @Override
    public void add(Player player) {
        super.add(player);
        player.setTeam(this);
    }

    @Override
    public void remove(String playerName) {
        get(playerName).setTeam(null);
        super.remove(playerName);
    }
}
