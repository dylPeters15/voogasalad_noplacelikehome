package backend.cell;

import backend.game_engine.GameEngine;
import backend.unit.Unit;

/**
 * Dylan
 * @author Created by th174 on 3/28/2017.
 */
public interface CellAbility {
    void apply(GameEngine game, Unit occupant);
}
