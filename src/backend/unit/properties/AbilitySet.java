package backend.unit.properties;

import backend.util.VoogaCollection;

import java.util.Collection;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class AbilitySet extends VoogaCollection<Ability> {
    public AbilitySet(String name, String description, String imgPath, Ability... abilities) {
        super(name, description, imgPath, abilities);
    }

    public AbilitySet(String name, String description, String imgPath, Collection<Ability> abilities) {
        super(name, description, imgPath, abilities);
    }

    public static Collection<AbilitySet> getPredefinedAbilitySets() {
        return getPredefined(AbilitySet.class);
    }
}
