package backend.unit.properties;

import backend.GameObject;
import backend.game_engine.GameState;
import backend.unit.Unit;

/**
 * Timmy
 *
 * @author Created by th174 on 3/29/2017.
 */
public interface ActiveAbility<T extends GameObject> extends GameObject {
    void affect(Unit user, T target, GameState game);
}
