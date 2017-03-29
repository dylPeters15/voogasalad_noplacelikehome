package backend.grid;

import backend.Game;
import backend.unit.Unit;

/**
 * @author Created by th174 on 3/28/2017.
 */
public interface CellAbility {
    void apply(Game game, Unit occupant);
}
