package backend.unit.properties;

import backend.Game;
import backend.grid.CoordinateTuple;
import backend.unit.InteractionModifier;
import backend.unit.Unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Created by th174 on 3/27/2017.
 */
public final class Attack extends ActiveAbility<Unit> {
    private final Collection<CoordinateTuple> attackPattern;
    private final double damage;
    private final int numHits;
    private final List<InteractionModifier<Double>> damageModifiers;

    public Attack(String name, String description, String imgPath, Double damage, int numHits, Game game) {
        this(name, description, imgPath, damage, numHits, CoordinateTuple.getOrigin(game.getGrid().dimension()).getNeighbors(), game);
    }

    public Attack(String name, String description, String imgPath, Double damage, int numHits, Collection<CoordinateTuple> attackPattern, Game game) {
        this(name, description, imgPath, damage, numHits, attackPattern, new ArrayList<>(), game);
    }

    //Uses CoordinateTuple.getNeighbors() as default attack pattern
    public Attack(String name, String description, String imgPath, Double damage, int numHits, List<InteractionModifier<Double>> damageModifiers, Game game) {
        this(name, description, imgPath, damage, numHits, CoordinateTuple.getOrigin(game.getGrid().dimension()).getNeighbors(), damageModifiers, game);
    }

    public Attack(String name, String description, String imgPath, Double damage, int numHits, Collection<CoordinateTuple> attackPattern, List<InteractionModifier<Double>> damageModifiers, Game game) {
        super(name, description, imgPath, game);
        this.damage = damage;
        this.numHits = numHits;
        this.damageModifiers = damageModifiers;
        this.attackPattern = attackPattern;
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

    public Collection<CoordinateTuple> getAttackPattern() {
        return attackPattern;
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
