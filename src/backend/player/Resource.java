package backend.player;

import backend.unit.properties.UnitStat;

import java.util.Collection;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class Resource extends UnitStat<Double> {
    //TODO ResourceBundlify this
    public static final Resource GOLD = new Resource("Gold", "Gold allows you to purchase and upgrade units. Earn gold by capturing mines.", "ooo_shiny.png");

    public static final double DEFAULT_MAX_VALUE = Double.MAX_VALUE;

    public Resource(String name, String description, String imgPath) {
        this(name, 0, description, imgPath);
    }

    public Resource(String name, double currentValue, String description, String imgPath) {
        super(name, currentValue, DEFAULT_MAX_VALUE, description, imgPath);
    }

    public Resource(String name, double currentValue, double maxValue, String description, String imgPath) {
        super(name, currentValue, maxValue, description, imgPath);
    }

    public void gain(double value) {
        set(getCurrentValue() + value);
    }

    public void use(double value) {
        set(getCurrentValue() - value);
    }

    public static Collection<Resource> getPredefinedResources() {
        return getPredefined(Resource.class);
    }
}
