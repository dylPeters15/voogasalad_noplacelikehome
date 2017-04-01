package backend.unit.properties;

import backend.util.VoogaCollection;

import java.util.Collection;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class FactionSet extends VoogaCollection<Faction> {
    private final AbilitySet abilitySet;

    public FactionSet(String name, String description, String imgPath, AbilitySet abilitySet, Faction... factions) {
        super(name, description, imgPath, factions);
        this.abilitySet = abilitySet;
    }

    public FactionSet(String name, String description, String imgPath, AbilitySet abilitySet, Collection<Faction> factions) {
        super(name, description, imgPath, factions);
        this.abilitySet = abilitySet;
    }

    public AbilitySet getAbilitySet() {
        return abilitySet;
    }

    public static Collection<FactionSet> getPredefinedFactionSets() {
        return getPredefined(FactionSet.class);
    }
}
