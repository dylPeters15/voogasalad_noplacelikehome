package backend.cell;

import backend.game_engine.GameState;
import backend.unit.Unit;

/**
 * Dylan
 * @author Created by th174 on 3/28/2017.
 */
public interface CellAbility {
    void apply(GameState game, Unit occupant);
}
