package backend.unit.properties;

import backend.game_engine.GameState;
import backend.unit.Unit;

import java.util.Collections;
import java.util.List;

/**
 * Timmy
 *
 * @author Created by th174 on 3/27/2017.
 */
public final class Attack implements ActiveAbility.AbilityEffect<Unit> {
    private final double damage;
    private final int numHits;
    private final List<InteractionModifier<Double>> damageModifiers;

    public Attack(double damage, int numHits) {
        this(damage, numHits, Collections.EMPTY_LIST);
    }

    public Attack(double damage, int numHits, List<InteractionModifier<Double>> damageModifiers) {
        this.damage = damage;
        this.numHits = numHits;
        this.damageModifiers = damageModifiers;
    }

    public int getNumHits() {
        return numHits;
    }

    public double getBaseDamage() {
        return damage;
    }

    public double getDamage(Unit user, Unit target, GameState game) {
        return InteractionModifier.modifyAll(damageModifiers, getBaseDamage(), user, target, game);
    }

    @Override
    public void useAbility(Unit user, Unit target, GameState game) {
        for (int i = 0; i < getNumHits(); i++) {
            double attackDamage = InteractionModifier.modifyAll(user.getOffenseModifiers(), getDamage(user, target, game), user, target, game);
            double totalDamage = InteractionModifier.modifyAll(target.getDefenseModifiers(), attackDamage, user, target, game);
            target.getHitPoints().takeDamage(totalDamage);
        }
    }
}

