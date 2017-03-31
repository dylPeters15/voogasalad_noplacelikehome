package backend.unit.properties;

/**
 * Timmy
 *
 * @author Created by th174 on 3/27/2017.
 */
public class HitPoints extends UnitStat<Double> {
    public static final String NAME = "HP";
    public static final String DESCRIPTION = "If 0 u ded";
    public static final String IMG_PATH = "<3.png";

    public HitPoints(double initialValue) {
        this(initialValue, initialValue);
    }

    public HitPoints(double currentValue, double initialValue) {
        super(NAME, currentValue, initialValue, DESCRIPTION, IMG_PATH);
    }

    public void takeDamage(double dmg) {
        set(getCurrentValue() - dmg);
    }
}
