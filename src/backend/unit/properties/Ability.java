package backend.unit.properties;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Created by th174 on 3/31/2017.
 */
public interface Ability {
    static Collection<Ability> getPredefinedAbilities() {
        return Stream.of(
                TriggeredCellAbilityTemplate.getPredefinedCellEffects(),
                ActiveAbility.getPredefinedActiveAbilities(),
                InteractionModifier.getPredefinedInteractionModifiers(),
                TriggeredAbilityTemplate.getPredefinedTriggeredAbilities())
                .flatMap(Collection::stream).map(Ability.class::cast).collect(Collectors.toSet());
    }
}
