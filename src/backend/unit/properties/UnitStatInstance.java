package backend.unit.properties;

import backend.util.VoogaInstance;

/**
 * Timmy
 *
 * @author Created by th174 on 3/28/2017.
 */
public class UnitStatInstance<T extends Comparable<T>> extends VoogaInstance<UnitStatTemplate> implements Comparable<UnitStatInstance<T>> {
    private final T maxValue;
    private final T minValue;
    private T currentValue;

    protected UnitStatInstance(T initialValue, T minValue, T maxValue, UnitStatTemplate<T> template) {
        super(template.getName(), template);
        this.currentValue = initialValue;
        this.maxValue = maxValue;
        this.minValue = minValue;
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

    public T getMinValue() {
        return minValue;
    }

    public T getCurrentValue() {
        return currentValue;
    }

    public boolean isFull() {
        return currentValue.compareTo(maxValue) < 0;
    }

    public boolean isEmpty() {
        return currentValue.compareTo(minValue) > 0;
    }

    @Override
    public int compareTo(UnitStatInstance<T> o) {
        return this.currentValue.compareTo(o.currentValue);
    }
}
