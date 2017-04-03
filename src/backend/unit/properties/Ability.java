package backend.unit.properties;

import backend.cell.CellEffect;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Created by th174 on 3/31/2017.
 */
public interface Ability {
    static Collection<Ability> getPredefinedAbilities() {
        return Stream.of(
                CellEffect.getPredefinedCellEffects(),
                ActiveAbility.getPredefinedActiveAbilities(),
                InteractionModifier.getPredefinedInteractionModifiers(),
                TriggeredAbility.getPredefinedTriggeredAbilities())
                .flatMap(Collection::stream).map(Ability.class::cast).collect(Collectors.toSet());
    }
}
