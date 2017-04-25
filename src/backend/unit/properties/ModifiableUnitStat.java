package backend.unit.properties;

import backend.util.ModifiableVoogaObject;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Created by th174 on 4/6/2017.
 */
public class ModifiableUnitStat<T extends Number & Comparable<T>> extends ModifiableVoogaObject<ModifiableUnitStat<T>> implements UnitStat<T>, Serializable {
	//TODO ResourceBundlify
	//For units
	public transient static final ModifiableUnitStat<Double> HITPOINTS = new ModifiableUnitStat<Double>("Hit Points")
			.setMinValue(0.0)
			.setMaxValue(50.0)
			.setDescription("Units lose HP when taking damage. When a unit's hitpoints reach 0, the unit dies.")
			.setImgPath("resources/images/heart.png");
	public transient static final ModifiableUnitStat<Integer> MOVEPOINTS = new ModifiableUnitStat<Integer>("Move Points")
			.setMinValue(0)
			.setMaxValue(5)
			.setDescription("Movepoints are consumed by moving on the map. Difficult terrain costs more movepoints, while more mobile units have more to spare.")
			.setImgPath("resources/images/timbs.png");
	public transient static final ModifiableUnitStat<Double> ABILITYPOINTS = new ModifiableUnitStat<Double>("Ability Points")
			.setMinValue(0.0)
			.setMaxValue(1.0)
			.setDescription("Most active abilities use ability points. They limit the number of actions a unit can do each turn.")
			.setImgPath("resources/images/ability_points.png");
	public transient static final ModifiableUnitStat<Double> ENERGY = new ModifiableUnitStat<Double>("ENERGY")
			.setMinValue(0.0)
			.setMaxValue(100.0)
			.setDescription("Energy points are required for and consumed by some powerful abilities.")
			.setImgPath("resources/images/energy.png");
	//For players
	public transient static final ModifiableUnitStat<Double> GOLD = new ModifiableUnitStat<Double>("Gold")
			.setMinValue(0.0)
			.setMaxValue(Double.MAX_VALUE)
			.setDescription("Players have a limited amount of gold to spend on their units.")
			.setImgPath("resources/images/gold.png");

	private T maxValue;
	private T minValue;
	private T currentValue;

	public ModifiableUnitStat(String name) {
		this(name, "", "");
	}

	public ModifiableUnitStat(String name, String description, String imgPath) {
		this(name, null, null, null, description, imgPath);
	}

	public ModifiableUnitStat(String name, T currentValue, T minValue, T maxValue, String description, String imgPath) {
		super(name, description, imgPath);
		setMaxValue(maxValue);
		setMinValue(minValue);
		setCurrentValue(currentValue);
	}

	@Deprecated
	public static Collection<ModifiableUnitStat> getPredefinedUnitStats() {
		return getPredefined(ModifiableUnitStat.class);
	}

	@Override
	public ModifiableUnitStat<T> copy() {
		return new ModifiableUnitStat<>(getName(), currentValue, minValue, maxValue, getDescription(), getImgPath());
	}

	@Override
	public T getMaxValue() {
		return maxValue;
	}

	public ModifiableUnitStat<T> setMaxValue(T maxValue) {
		this.maxValue = maxValue;
		this.currentValue = maxValue;
		return this;
	}

	@Override
	public T getCurrentValue() {
		return currentValue;
	}

	@Override
	public ModifiableUnitStat<T> setCurrentValue(T currentValue) {
		this.currentValue = currentValue;
		return this;
	}

	@Override
	public T getMinValue() {
		return minValue;
	}

	public ModifiableUnitStat<T> setMinValue(T minValue) {
		this.minValue = minValue;
		return this;
	}
}
