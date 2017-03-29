package backend.unit;

import backend.Game;
import backend.GameObject;
import backend.grid.Cell;
import backend.grid.CoordinateTuple;
import backend.unit.properties.Ability;
import backend.unit.properties.HitPoints;
import backend.unit.properties.MovePoints;

import java.util.Map;

/**
 * @author Created by th174 on 3/27/2017.
 */
public class UnitImpl extends GameObject implements Unit {
    private final HitPoints hitPoints;
    private final MovePoints movePoints;
    private final MovementPattern movePattern;
    private final Map<String, Ability> abilities;

    private Cell currentCell;

    //TODO: Make actually usable constructor
    public UnitImpl(String unitName, double hitPoints, int movePoints, MovementPattern movePattern, Map<String, Ability> abilities, String unitDescription, String imgPath, Game game) {
        super(unitName, unitDescription, imgPath, game);
        this.hitPoints = new HitPoints(hitPoints, game);
        this.movePoints = new MovePoints(movePoints, game);
        this.movePattern = movePattern;
        this.abilities = abilities;
    }

    @Override
    public Cell getCurrentCell() {
        return currentCell;
    }

    @Override
    public Map<String, Ability> getAbilities() {
        return abilities;
    }

    @Override
    public InteractionModifier<Double> getAttackModifier() {
        //TODO
        return InteractionModifier.NO_CHANGE;
    }

    @Override
    public InteractionModifier<Double> getDefenseModifier() {
        //TODO
        return InteractionModifier.NO_CHANGE;
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
    public MovePoints getMovePoints() {
        return movePoints;
    }

    @Override
    public int movePointsTo(CoordinateTuple other) {
        throw new RuntimeException("Not Implemented Yet");
    }

    @Override
    public MovementPattern getMovementPattern() {
        return movePattern;
    }
}
