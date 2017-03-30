package backend.unit.properties;

import backend.GameObjectImpl;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class Era extends GameObjectImpl {
    private Collection<Faction> factions;

    public Era(String name, String description, String imgPath) {
        this(name, new HashSet<>(), description, imgPath);
    }

    public Era(String name, Collection<Faction> factions, String description, String imgPath) {
        super(name, description, imgPath);
    }
}
