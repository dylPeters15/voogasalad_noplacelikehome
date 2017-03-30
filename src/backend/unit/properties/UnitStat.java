package backend.unit.properties;

import backend.GameObjectImpl;
import backend.game_engine.GameState;

/**
 * Timmy
 *
 * @author Created by th174 on 3/28/2017.
 */
public class UnitStat<T> extends GameObjectImpl {
    private final T initialValue;
    private T currentValue;

    public UnitStat(String name, T initialValue, String description, String imgPath, GameState game) {
        this(name, initialValue, initialValue, description, imgPath, game);
    }

    public UnitStat(String name, T currentValue, T initialValue, String description, String imgPath, GameState game) {
        super(name, description, imgPath, game);
        this.currentValue = currentValue;
        this.initialValue = initialValue;
    }

    public void set(T newValue) {
        currentValue = newValue;
    }

    public T getInitialValue() {
        return initialValue;
    }

    public T getCurrentValue() {
        return currentValue;
    }

    public void resetValue() {
        set(getInitialValue());
    }

    public boolean isFull() {
        return getCurrentValue().equals(getInitialValue());
    }
}
