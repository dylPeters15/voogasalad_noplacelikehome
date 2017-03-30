package backend.unit;

import backend.GameObject;
import backend.cell.Terrain;
import backend.unit.properties.*;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Created by th174 on 3/30/2017.
 */
public interface Unit extends GameObject {

    UnitTemplate getUnitType();

    default String getUnitTypeName() {
        return getUnitType().getName();
    }

    HitPoints getHitPoints();

    MovePoints getMovePoints();

    Faction getFaction();

    GridPattern getMovePattern();

    Map<Terrain, Integer> getMoveCosts();

    default int getMoveCostByTerrain(Terrain terrain) {
        return getMoveCosts().get(terrain);
    }

    List<InteractionModifier<Double>> getOffensiveModifiers();

    default void addOffensiveModifier(InteractionModifier<Double> OffensiveModifier) {
        getOffensiveModifiers().add(OffensiveModifier);
    }

    default void removeOffensiveModifier(InteractionModifier<Double> OffensiveModifier) {
        getOffensiveModifiers().remove(OffensiveModifier);
    }

    List<InteractionModifier<Double>> getDefensiveModifiers();

    default void addDefensiveModifier(InteractionModifier<Double> defensiveModifier) {
        getDefensiveModifiers().add(defensiveModifier);
    }

    default void removeDefensiveModifier(InteractionModifier<Double> defensiveModifier) {
        getDefensiveModifiers().remove(defensiveModifier);
    }

    Map<String, ActiveAbility<GameObject>> getActiveAbilities();

    default Collection<ActiveAbility<GameObject>> getAllActiveAbilities() {
        return Collections.unmodifiableCollection(getActiveAbilities().values());
    }

    default ActiveAbility<GameObject> getActiveAbilityByName(String name) {
        return getActiveAbilities().get(name);
    }

    default void addActiveAbility(ActiveAbility<GameObject> ability) {
        getActiveAbilities().put(ability.getName(), ability);
    }

    default void removeActiveAbility(ActiveAbility<GameObject> ability) {
        getActiveAbilities().remove(ability.getName());
    }

    default void removeActiveAbility(String abilityName) {
        getActiveAbilities().remove(abilityName);
    }

    Map<String, PassiveAbility> getPassiveAbilities();

    default Collection<PassiveAbility> getAllPassiveAbilities() {
        return Collections.unmodifiableCollection(getPassiveAbilities().values());
    }

    default PassiveAbility getPassiveAbilityByName(String name) {
        return getPassiveAbilities().get(name);
    }

    default void addPassiveAbility(PassiveAbility ability) {
        getPassiveAbilities().put(ability.getName(), ability);
    }

    default void removePassiveAbility(PassiveAbility ability) {
        getPassiveAbilities().remove(ability.getName());
    }

    default void removePassiveAbility(String abilityName) {
        getPassiveAbilities().remove(abilityName);
    }
}
