package backend.unit;

import backend.grid.Cell;
import backend.grid.CoordinateTuple;
import backend.grid.Grid;
import backend.grid.Terrain;
import backend.unit.properties.Ability;
import backend.unit.properties.HitPoints;

import java.nio.file.Path;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * @author Created by th174 on 3/27/2017.
 */
public class UnitImpl implements Unit {
    private final String unitName;
    private final HitPoints hitPoints;
    private final Movement movement;
    private final MovementPattern movePattern;
    private final Map<String, Ability> abilities;
    private final Map<Terrain, Double> terrainDefenses;
    private final UnaryOperator<Double> attackDamageModifier = baseDamage -> baseDamage;
    private final UnaryOperator<Double> defenseDamageModifier = baseDamage -> baseDamage;

    private Cell currentCell;

    UnitImpl(double hitPoints, Movement movement, MovementPattern movePattern, Map<String, Ability> abilities, Map<Terrain, Double> terrainDefenses, String unitName) {
        this.unitName = unitName;
        this.hitPoints = new HitPoints(hitPoints);
        this.movement = movement;
        this.movePattern = movePattern;
        this.abilities = abilities;
        this.terrainDefenses = terrainDefenses;
    }


    @Override
    public Cell getCurrentCell() {
        return currentCell;
    }

    @Override
    public Grid getGrid() {
        return currentCell.getGrid();
    }

    @Override
    public Map<String, Ability> getAbilities() {
        return abilities;
    }

    @Override
    public Map<Terrain, Double> getHitChance() {
        return null;
    }

    @Override
    public UnaryOperator<Double> getAttackModifier() {
        return attackDamageModifier;
    }

    @Override
    public UnaryOperator<Double> getDefenseModifier() {
        return defenseDamageModifier;
    }

    @Override
    public void moveTo(Cell cell) {
        currentCell = cell;
    }

    @Override
    public HitPoints getHitPoints() {
        return hitPoints;
    }

    @Override
    public int movePointsTo(CoordinateTuple other) {
        return 0;
    }

    @Override
    public MovementPattern getMovementPattern() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Path imagePath() {
        return null;
    }

    @Override
    public String toXml() {
        return null;
    }
}
