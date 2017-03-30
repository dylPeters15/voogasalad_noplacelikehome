package backend.unit.properties;

import backend.GameObject;
import backend.unit.Unit;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/29/2017.
 */
public class Faction extends GameObject {
    private Map<String, Unit> units;

    public Faction(String name, String description, String imgPath, Unit... units) {
        super(name, description, imgPath, null);
        this.units = new HashMap<>();
        this.units = Arrays.stream(units).collect(Collectors.toMap(Unit::getName, u -> u));
    }

    public Collection<Unit> getAllUnits() {
        return units.values();
    }

    public Unit getUnitByName(String name) {
        return units.get(name);
    }

    public void addUnit(Unit u) {
        units.put(u.getName(), u);
    }

    public void removeUnit(Unit u) {
        units.remove(u.getName(), u);
    }

    public void removeUnit(String s) {
        units.remove(s);
    }
}
