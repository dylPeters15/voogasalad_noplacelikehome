package backend.unit.properties;

import backend.GameObjectImpl;
import backend.unit.UnitInstance;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Timmy
 * @author Created by th174 on 3/29/2017.
 */
public class Faction extends GameObjectImpl {
    private Map<String, UnitInstance> units;

    public Faction(String name, String description, String imgPath, UnitInstance... units) {
        super(name, description, imgPath);
        this.units = new HashMap<>();
        this.units = Arrays.stream(units).collect(Collectors.toMap(UnitInstance::getName, u -> u));
    }

    public Collection<UnitInstance> getAllUnits() {
        return units.values();
    }

    public UnitInstance getUnitByName(String name) {
        return units.get(name);
    }

    public void addUnit(UnitInstance u) {
        units.put(u.getName(), u);
    }

    public void removeUnit(UnitInstance u) {
        units.remove(u.getName(), u);
    }

    public void removeUnit(String s) {
        units.remove(s);
    }
}
