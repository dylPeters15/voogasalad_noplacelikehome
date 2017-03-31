package backend.cell;

import backend.game_engine.GameState;
import backend.unit.UnitInstance;

/**
 * Dylan
 * @author Created by th174 on 3/28/2017.
 */
public interface CellAbility {
    void apply(GameState game, UnitInstance occupant);
}
