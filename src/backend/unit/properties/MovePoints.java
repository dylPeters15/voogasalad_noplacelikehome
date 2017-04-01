package backend.unit.properties;

/**
 * Timmy
 *
 * @author Created by th174 on 3/28/2017.
 */
public class MovePoints extends UnitStat<Integer> {
    public static final String NAME = "MP";
    public static final String DESCRIPTION = "Remaining move points this turn";
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
