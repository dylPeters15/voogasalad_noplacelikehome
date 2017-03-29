package backend.unit.properties;

import backend.Game;

/**
 * @author Created by th174 on 3/27/2017.
 */
public class HitPoints extends UnitStat<Double> {
    public static final String NAME = "HP";
    public static final String DESCRIPTION = "If 0 u ded";
    public static final String IMG_PATH = "<3.png";

    public HitPoints(double initialValue, Game game) {
        this(initialValue,initialValue,game);
    }

    public HitPoints(double currentValue, double initialValue, Game game) {
        super(NAME, currentValue, initialValue, DESCRIPTION, IMG_PATH, game);
    }

    public void takeDamage(double dmg) {
        set(getCurrentValue() - dmg);
    }
}
