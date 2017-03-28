package backend.unit.properties;

import backend.unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * @author Created by th174 on 3/27/2017.
 */
public final class Attack extends Ability<Unit> {
    private final double damage;
    private final int numHits;
    private final List<UnaryOperator<Double>> damageModifiers;
    private final List<UnaryOperator<Double>> accuracyModifier;

    public Attack(String name, String description, Double damage, int numHits) {
        this(name, description, damage, numHits, new ArrayList<>());
    }

    public Attack(String name, String description, Double damage, int numHits, List<UnaryOperator<Double>> damageModifiers) {
        this(name, description, damage, numHits, damageModifiers, new ArrayList<>());
    }

    public Attack(String name, String description, Double damage, int numHits, List<UnaryOperator<Double>> damageModifiers, List<UnaryOperator<Double>> accuracyModifier) {
        super(name, description);
        this.damage = damage;
        this.numHits = numHits;
        this.damageModifiers = damageModifiers;
        this.accuracyModifier = accuracyModifier;
    }

    public int getNumHits() {
        return numHits;
    }

    public double getBaseDamage() {
        return damage;
    }

    @Override
    public void affect(Unit user, Unit target) {
        for (int i = 0; i < getNumHits(); i++) {
            if (Math.random() < getHitChance(target.getCurrentHitChance())) {
                target.getHitPoints().takeDamage(target.getDefenseModifier().apply(user.getAttackModifier().apply(getDamage())));
            }
        }
    }

    public double getHitChance(double baseChance) {
        for (UnaryOperator<Double> op : accuracyModifier) {
            baseChance = op.apply(baseChance);
        }
        return baseChance;
    }

    public double getDamage() {
        return getDamage(getBaseDamage());
    }

    public double getDamage(double baseDamage) {
        for (UnaryOperator<Double> op : damageModifiers) {
            baseDamage = op.apply(baseDamage);
        }
        return baseDamage;
    }

    @Override
    public String toXml() {
        return null;
    }
}
