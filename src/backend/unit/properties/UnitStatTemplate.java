package backend.unit.properties;

import backend.util.VoogaTemplate;

import java.util.Collection;

/**
 * @author Created by th174 on 4/6/2017.
 */
public class UnitStatTemplate<T extends Comparable<T>> extends VoogaTemplate<UnitStatTemplate<T>> {
    //TODO ResourceBundlify
    //For units
    public static final UnitStatTemplate<Double> HITPOINTS = new UnitStatTemplate<>("Hitpoints", 0.0, 50.0, "Units lose HP when taking damage. When a unit's hitpoints reach 0, the unit dies.", "<3.png");
    public static final UnitStatTemplate<Integer> MOVEPOINTS = new UnitStatTemplate<>("Movepoints", 0, 5, "Movepoints are consumed by moving on the map. Difficult terrain costs more movepoints, while more mobile units have more to spare.", "Boot.png");
    public static final UnitStatTemplate<Double> ENERGY = new UnitStatTemplate<>("Energy", 0.0, 100.0, "Energy points are required for and consumed by some powerful abilities.", "NRG.png");
    //For players
    public static final UnitStatTemplate<Double> GOLD = new UnitStatTemplate<>("Gold", 0.0, Double.MAX_VALUE, "Players have a limited amount of gold to spend on their units.", "SHINY~~~.png");
    private T maxValue;
    private T minValue;

    public UnitStatTemplate(String name) {
        this(name, "", "");
    }

    public UnitStatTemplate(String name, String description, String imgPath) {
        this(name, null, null, description, imgPath);
    }

    public UnitStatTemplate(String name, T minValue, T maxValue, String description, String imgPath) {
        super(name, description, imgPath);
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    @Override
    public UnitStatTemplate<T> copy() {
        return new UnitStatTemplate<>(getName(), minValue, maxValue, getDescription(), getImgPath());
    }

    public UnitStatInstance<T> createInstance(){
        return createInstance(maxValue);
    }

    public UnitStatInstance<T> createInstance(T maxValue) {
        return createInstance(maxValue, maxValue);
    }

    public UnitStatInstance<T> createInstance(T initialValue, T maxValue) {
        return createInstance(initialValue, this.getMinValue(), maxValue);
    }

    public UnitStatInstance<T> createInstance(T initialvalue, T minValue, T maxValue) {
        return new UnitStatInstance<>(initialvalue, minValue, maxValue, this);
    }

    public UnitStatTemplate<T> setMaxValue(T maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public UnitStatTemplate<T> setMinValue(T minValue) {
        this.minValue = minValue;
        return this;
    }

    public T getMinValue() {
        return minValue;
    }

    public static Collection<UnitStatTemplate> getPredefinedUnitStatTemplates() {
        return getPredefined(UnitStatTemplate.class);
    }
}
