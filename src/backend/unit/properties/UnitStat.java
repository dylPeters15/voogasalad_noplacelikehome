package backend.unit.properties;

import backend.util.VoogaEntity;

/**
 * Timmy
 *
 * @author Created by th174 on 3/28/2017.
 */
public interface UnitStat<T extends Comparable<T>> extends VoogaEntity, Comparable<UnitStat<T>> {
	@Override
	UnitStat<T> copy();

	default void resetValue() {
		setCurrentValue(getMaxValue());
	}

	T getMaxValue();

	default boolean isFull() {
		return getCurrentValue().compareTo(getMaxValue()) < 0;
	}

	T getCurrentValue();

	UnitStat<T> setCurrentValue(T newValue);

	default boolean isEmpty() {
		return getCurrentValue().compareTo(getMinValue()) > 0;
	}

	T getMinValue();

	@Override
	default int compareTo(UnitStat<T> o) {
		return this.getCurrentValue().compareTo(o.getCurrentValue());
	}
}
