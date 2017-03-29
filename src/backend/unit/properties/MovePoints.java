package backend.unit.properties;

import backend.Game;

/**
 * @author Created by th174 on 3/28/2017.
 */
public class MovePoints extends UnitStat<Integer> {
    public static final String NAME = "MP";
    public static final String DESCRIPTION = "Remaining movepoints this turn";
    public static final String IMG_PATH = "boot.png";

    public MovePoints(int initialValue, Game game) {
        this(initialValue, initialValue, game);
    }

    public MovePoints(int currentValue, int initialValue, Game game) {
        super(NAME, currentValue, initialValue, DESCRIPTION, IMG_PATH, game);
    }

    public void useMovePoints(int moveCost) {
        set(getCurrentValue() - moveCost);
    }
}
