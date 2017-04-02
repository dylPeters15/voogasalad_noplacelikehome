package backend.unit.properties;

import backend.unit.UnitInstance;
import backend.util.GameState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Timmy
 *
 * @author Created by th174 on 3/27/2017.
 */
public final class Attack implements ActiveAbility.AbilityEffect<UnitInstance> {
    private final double damage;
    private final int numHits;
    private final List<InteractionModifier<Double>> damageModifiers;

    public Attack(double damage, int numHits) {
        this(damage, numHits, Collections.EMPTY_LIST);
    }

    public Attack(double damage, int numHits, Collection<InteractionModifier<Double>> damageModifiers) {
        this.damage = damage;
        this.numHits = numHits;
        this.damageModifiers = new ArrayList<>(damageModifiers);
    }

    public int getNumHits() {
        return numHits;
    }

    public double getBaseDamage() {
        return damage;
    }

    public double getDamage(UnitInstance user, UnitInstance target, GameState game) {
        return InteractionModifier.modifyAll(damageModifiers, getBaseDamage(), user, target, game);
    }

    @Override
    public void useAbility(UnitInstance user, UnitInstance target, GameState game) {
        IntStream.range(0, getNumHits()).forEach(i -> {
            double attackDamage = user.applyAllOffensiveModifiers(getDamage(user, target, game), target, game);
            double totalDamage = target.applyAllDefensiveModifiers(attackDamage, user, game);
            target.takeDamage(totalDamage);
        });
    }
}

