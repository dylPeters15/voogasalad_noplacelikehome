package backend.unit.properties;

/**
 * @author Created by th174 on 3/27/2017.
 */
public class HitPoints extends UnitStat<Double> {
    public HitPoints(Double initialValue) {
        super(initialValue);
    }

    public HitPoints(Double currentValue, Double initialValue) {
        super(currentValue, initialValue);
    }

    public void takeDamage(double dmg) {
        set(getCurrentValue() - dmg);
    }
}
