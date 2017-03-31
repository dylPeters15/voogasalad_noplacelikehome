package backend.unit.properties;

import backend.util.GameObjectImpl;

import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class Ability extends GameObjectImpl {
    public Ability(String name, String description, String imgPath) {
        super(name, description, imgPath);
    }

    public Ability(String name, String description, Path imgPath) {
        super(name, description, imgPath);
    }

    public static Collection<Ability> getPredefinedAbilities() {
        return Stream.of(Ability.getPredefined(Ability.class), ActiveAbility.getPredefinedActiveAbilities(), InteractionModifier.getPredefinedInteractionModifiers(), PassiveAbility.getPredefinedPassiveAbilities()).flatMap(Collection::stream).map(Ability.class::cast).collect(Collectors.toSet());
    }
}
