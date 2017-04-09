package backend.unit.properties;

import backend.util.VoogaObject;

/**
 * Timmy
 *
 * @author Created by th174 on 3/28/2017.
 */
public interface UnitStatInstance<T extends Comparable<T>> extends VoogaObject, Comparable<UnitStatInstance<T>> {
	default void resetValue() {
		setCurrentValue(getMaxValue());
	}

	T getMaxValue();

	default boolean isFull() {
		return getCurrentValue().compareTo(getMaxValue()) < 0;
	}

	T getCurrentValue();

	UnitStatInstance<T> setCurrentValue(T newValue);

	default boolean isEmpty() {
		return getCurrentValue().compareTo(getMinValue()) > 0;
	}

	T getMinValue();

	@Override
	default int compareTo(UnitStatInstance<T> o) {
		return this.getCurrentValue().compareTo(o.getCurrentValue());
	}
}
