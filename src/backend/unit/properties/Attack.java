package backend.unit.properties;

import backend.Game;
import backend.unit.InteractionModifier;
import backend.unit.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by th174 on 3/27/2017.
 */
public final class Attack extends Ability<Unit> {
    private final double damage;
    private final int numHits;
    private final List<InteractionModifier<Double>> damageModifiers;

    public Attack(String name, String description, String imgPath, Double damage, int numHits, Game game) {
        this(name, description, imgPath, damage, numHits, new ArrayList<>(), game);
    }

    public Attack(String name, String description, String imgPath, Double damage, int numHits, List<InteractionModifier<Double>> damageModifiers, Game game) {
        super(name, description, imgPath, game);
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

    public double getDamage(Unit user, Unit target) {
        return InteractionModifier.modifyAll(damageModifiers, getBaseDamage(), user, target, getGame());
    }

    @Override
    public void affect(Unit user, Unit target) {
        for (int i = 0; i < getNumHits(); i++) {
            double attackDamage = user.getAttackModifier().modify(getDamage(user, target), user, target, getGame());
            double totalDamage = target.getDefenseModifier().modify(attackDamage, user, target, getGame());
            target.getHitPoints().takeDamage(totalDamage);
        }
    }
}
