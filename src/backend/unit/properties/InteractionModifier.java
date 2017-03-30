package backend.unit.properties;

import backend.game_engine.GameState;
import backend.unit.Unit;

import java.util.Collection;

/**
 * Timmy
 * @author Created by th174 on 3/28/2017.
 */
@FunctionalInterface
public interface InteractionModifier<T> {
    InteractionModifier NO_CHANGE = (baseValue, agent, target, game) -> baseValue;

    T modify(T baseValue, Unit agent, Unit target, GameState game);

    static <T> T modifyAll(Collection<InteractionModifier<T>> modifiers, T baseValue, Unit agent, Unit target, GameState game) {
        for (InteractionModifier<T> op : modifiers) {
            baseValue = op.modify(baseValue, agent, target, game);
        }
        return baseValue;
    }
}
