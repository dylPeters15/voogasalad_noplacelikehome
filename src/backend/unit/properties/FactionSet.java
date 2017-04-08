package backend.unit.properties;

import backend.util.VoogaCollection;

import java.util.Collection;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class FactionSet extends VoogaCollection<Faction, FactionSet> {
    public FactionSet(String name, String description, String imgPath, Faction... factions) {
        super(name, description, imgPath, factions);
    }

    public FactionSet(String name, String description, String imgPath, Collection<Faction> factions) {
        super(name, description, imgPath, factions);
    }

    @Override
    public FactionSet clone() {
        return new FactionSet(getName(), getDescription(), getImgPath(), getAll());
    }

    public static Collection<FactionSet> getPredefinedFactionSets() {
        return getPredefined(FactionSet.class);
    }
}
