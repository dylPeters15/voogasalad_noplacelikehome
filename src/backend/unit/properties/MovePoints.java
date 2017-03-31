package backend.unit.properties;

import backend.util.GameQuantity;

/**
 * Timmy
 *
 * @author Created by th174 on 3/28/2017.
 */
public class MovePoints extends GameQuantity<Integer> {
    public static final String NAME = "MP";
    public static final String DESCRIPTION = "Remaining movepoints this turn";
    public static final String IMG_PATH = "boot.png";

    public MovePoints(int initialValue) {
        this(initialValue, initialValue);
    }

    public MovePoints(int currentValue, int initialValue) {
        super(NAME, currentValue, initialValue, DESCRIPTION, IMG_PATH);
    }

    public void useMovePoints(int moveCost) {
        set(getCurrentValue() - moveCost);
    }
}
