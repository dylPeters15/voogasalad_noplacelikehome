package backend.util;

/**
 * Timmy
 *
 * @author Created by th174 on 3/28/2017.
 */
public class GameQuantity<T extends Number> extends GameObjectImpl {
    private final T maxValue;
    private T currentValue;

    public GameQuantity(String name, T maxValue, String description, String imgPath) {
        this(name, maxValue, maxValue, description, imgPath);
    }

    public GameQuantity(String name, T currentValue, T maxValue, String description, String imgPath) {
        super(name, description, imgPath);
        this.currentValue = currentValue;
        this.maxValue = maxValue;
    }

    public void set(T newValue) {
        currentValue = newValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public T getCurrentValue() {
        return currentValue;
    }

    public void resetValue() {
        set(getMaxValue());
    }

    public boolean isFull() {
        return getCurrentValue().doubleValue() >= getMaxValue().doubleValue();
    }
}
