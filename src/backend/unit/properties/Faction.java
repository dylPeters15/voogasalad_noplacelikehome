package backend.unit.properties;

import backend.GameObjectSet;
import backend.unit.UnitTemplate;

import java.util.Collection;

/**
 * Timmy
 *
 * @author Created by th174 on 3/29/2017.
 */
public class Faction extends GameObjectSet<UnitTemplate> {
    public Faction(String name, String description, String imgPath) {
        super(name, description, imgPath);
    }

    public Faction(String name, Collection<UnitTemplate> unitTypes, String description, String imgPath) {
        super(name, unitTypes, description, imgPath);
    }
}
