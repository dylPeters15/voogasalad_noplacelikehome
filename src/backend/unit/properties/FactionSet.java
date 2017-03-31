package backend.unit.properties;

import backend.GameObjectSet;

import java.util.Collection;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class FactionSet extends GameObjectSet<Faction> {
    public FactionSet(String name, String description, String imgPath) {
        super(name, description, imgPath);
    }

    public FactionSet(String name, Collection<Faction> gameObjects, String description, String imgPath) {
        super(name, gameObjects, description, imgPath);
    }
}
