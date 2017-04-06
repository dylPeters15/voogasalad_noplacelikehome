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
    public static final Faction UNDEAD = new Faction("Undead", "The reanimated corpses of the dead want no more than to slake their thirst with the fresh blood of the living","get_spooked.png", UnitTemplate.SKELETON_WARRIOR,UnitTemplate.SKELETON_ARCHER);

    public Faction(String name, String description, String imgPath, UnitTemplate... unitTypes) {
        super(name, description, imgPath, unitTypes);
    }

    public Faction(String name, String description, String imgPath, Collection<UnitTemplate> unitTypes) {
        super(name, description, imgPath, unitTypes);
    }

    public static Collection<Faction> getPredefinedFactions() {
        return getPredefined(Faction.class);
    }
}
