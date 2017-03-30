package backend.unit;

import backend.Game;
import backend.GameObject;
import backend.Player;
import backend.grid.Cell;
import backend.grid.CoordinateTuple;
import backend.unit.properties.ActiveAbility;
import backend.unit.properties.HitPoints;
import backend.unit.properties.MovePoints;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/27/2017.
 */
public class Unit extends GameObject {
    private final HitPoints hitPoints;
    private final MovePoints movePoints;
    private final MovementPattern movePattern;
    private final Map<String, ActiveAbility> abilities;
    private final Faction faction;

    //TODO: Passive abilities how?
    private Player ownedBy;
    private Cell currentCell;

    //TODO: Make actually usable constructor
    public Unit(String unitName, double hitPoints, int movePoints, Faction faction, MovementPattern movePattern, Collection<ActiveAbility> abilities, String unitDescription, String imgPath, Game game) {
        super(unitName, unitDescription, imgPath, game);
        this.faction = faction;
        this.hitPoints = new HitPoints(hitPoints, game);
        this.movePoints = new MovePoints(movePoints, game);
        this.movePattern = movePattern;
        this.abilities = abilities.stream().collect(Collectors.toMap(ActiveAbility::getName, a -> a));
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public Faction getFaction() {
        return faction;
    }

    public Player getOwner() {
        return ownedBy;
    }

    public void setOwner(Player p) {
        ownedBy = p;
    }

    public ActiveAbility getAbilityByName(String s) {
        return abilities.get(s);
    }

    public Collection<ActiveAbility> getAllAbilities() {
        return abilities.values();
    }

    public InteractionModifier<Double> getAttackModifier() {
        //TODO
        return InteractionModifier.NO_CHANGE;
    }

    public InteractionModifier<Double> getDefenseModifier() {
        //TODO
        return InteractionModifier.NO_CHANGE;
    }

    public void moveTo(Cell cell) {
        currentCell = cell;
    }

    public HitPoints getHitPoints() {
        return hitPoints;
    }

    public MovePoints getMovePoints() {
        return movePoints;
    }

    public int movePointsTo(CoordinateTuple other) {
        throw new RuntimeException("Not Implemented Yet");
    }

    public MovementPattern getMovementPattern() {
        return movePattern;
    }
}
