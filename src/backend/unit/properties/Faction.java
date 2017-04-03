package backend.unit.properties;

import backend.unit.UnitTemplate;
import backend.util.VoogaCollection;

import java.util.Collection;

/**
 * Timmy
 *
 * @author Created by th174 on 3/29/2017.
 */
public class Faction extends VoogaCollection<UnitTemplate> {
    public Faction(String name, String description, String imgPath) {
        super(name, description, imgPath);
    }

    public Faction(String name, Collection<UnitTemplate> unitTypes, String description, String imgPath) {
        super(name, description, imgPath, unitTypes);
    }

    public static Collection<Faction> getPredefinedFactions() {
        return getPredefined(Faction.class);
    }
}
