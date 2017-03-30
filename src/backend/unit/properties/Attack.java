package backend.unit.properties;

import backend.GameObjectImpl;
import backend.game_engine.GameState;
import backend.grid.CoordinateTuple;
import backend.unit.Unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Timmy
 *
 * @author Created by th174 on 3/27/2017.
 */
public final class Attack extends GameObjectImpl implements ActiveAbility<Unit> {
    private final Collection<CoordinateTuple> attackPattern;
    private final double damage;
    private final int numHits;
    private final List<InteractionModifier<Double>> damageModifiers;

    public Attack(String name, String description, String imgPath, Double damage, int numHits, GameState game) {
        this(name, description, imgPath, damage, numHits, CoordinateTuple.getOrigin(game.getGrid().dimension()).getNeighbors());
    }

    public Attack(String name, String description, String imgPath, Double damage, int numHits, Collection<CoordinateTuple> attackPattern) {
        this(name, description, imgPath, damage, numHits, attackPattern, new ArrayList<>());
    }

    //Uses CoordinateTuple.getNeighbors() as default attack pattern
    public Attack(String name, String description, String imgPath, Double damage, int numHits, GameState game, List<InteractionModifier<Double>> damageModifiers) {
        this(name, description, imgPath, damage, numHits, CoordinateTuple.getOrigin(game.getGrid().dimension()).getNeighbors(), damageModifiers);
    }

    public Attack(String name, String description, String imgPath, Double damage, int numHits, Collection<CoordinateTuple> attackPattern, List<InteractionModifier<Double>> damageModifiers) {
        super(name, description, imgPath, null);
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

    public double getDamage(Unit user, Unit target, GameState game) {
        return InteractionModifier.modifyAll(damageModifiers, getBaseDamage(), user, target, game);
    }

    public Collection<CoordinateTuple> getAttackPattern() {
        return attackPattern;
    }

    @Override
    public void affect(Unit user, Unit target, GameState game) {
        for (int i = 0; i < getNumHits(); i++) {
            double attackDamage = InteractionModifier.modifyAll(user.getAttackModifier(), getDamage(user, target, game), user, target, game);
            double totalDamage = InteractionModifier.modifyAll(target.getDefenseModifier(), attackDamage, user, target, game);
            target.getHitPoints().takeDamage(totalDamage);
        }
    }
}
