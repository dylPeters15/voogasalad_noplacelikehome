package backend.unit.properties;

import backend.unit.Unit;

import java.util.ResourceBundle;

/**
 * @author Dylan Peters,Timmy Huang
 */
public final class Attack extends Ability<Unit> {
    private final double damage;
    private final int numHits;

    Attack(ResourceBundle resourceBundle, double damage, int numHits) {
        super(resourceBundle);
        this.damage = damage;
        this.numHits = numHits;
    }

    public int getNumHits() {
        return numHits;
    }

    public double getBaseDamage() {
        return damage;
    }

    @Override
    public void affect(Unit user, Unit target) {
        for (int i = 0; i < numHits; i++) {
            if (Math.random() < target.getCurrentHitChance()) {
                target.getHitPoints().takeDamage(target.getDefenseModifier().apply(user.getAttackModifier().apply(getBaseDamage())));
            }
        }
    }

    @Override
    public String toXML(int indents) {
        return null;
    }
}
