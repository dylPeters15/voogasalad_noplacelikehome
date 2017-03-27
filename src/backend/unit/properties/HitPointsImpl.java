package backend.unit.properties;

/**
 * @author Created by th174 on 3/27/2017.
 */
public class HitPointsImpl implements HitPoints {
    private final double maxHP;
    private double currentHP;

    public HitPointsImpl(double maxHP) {
        this.maxHP = maxHP;
    }

    @Override
    public double getCurrentHP() {
        return currentHP;
    }

    @Override
    public double getMaxHP() {
        return maxHP;
    }

    @Override
    public void setHP(double numPoints) {
        currentHP = numPoints;
    }

    @Override
    public String toXML(int indents) {
        return null;
    }
}
