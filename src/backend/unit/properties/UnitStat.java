package backend.unit.properties;

import backend.util.VoogaObject;

/**
 * Timmy
 *
 * @author Created by th174 on 3/28/2017.
 */
public abstract class UnitStat<T extends Number> extends VoogaObject {
    private T maxValue;
    private T currentValue;

    public UnitStat(String name, T maxValue, String description, String imgPath) {
        this(name, maxValue, maxValue, description, imgPath);
    }

    public UnitStat(String name, T currentValue, T maxValue, String description, String imgPath) {
        super(name, description, imgPath);
        this.currentValue = currentValue;
        this.maxValue = maxValue;
    }

    public void set(T newValue) {
        currentValue = newValue;
    }

    public void resetValue() {
        set(getMaxValue());
    }

    public T getMaxValue() {
        return maxValue;
    }

    public T getCurrentValue() {
        return currentValue;
    }

    public boolean isFull() {
        return getCurrentValue().doubleValue() >= getMaxValue().doubleValue();
    }
}
