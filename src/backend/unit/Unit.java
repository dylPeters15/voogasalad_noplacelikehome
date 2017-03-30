package backend.unit;

import backend.Game;
import backend.GameObject;
import backend.Player;
import backend.grid.Cell;
import backend.grid.CoordinateTuple;
import backend.grid.Terrain;
import backend.unit.properties.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/27/2017.
 */
public class Unit extends GameObject {
    private final HitPoints hitPoints;
    private final MovePoints movePoints;
    private final MovementPattern movePattern;
    private final Map<String, ActiveAbility> activeAbilities;
    private final Map<String, PassiveAbility> passiveAbilties;
    private final Map<Terrain, Integer> moveCosts;
    private final Faction faction;

    private Player ownedBy;
    private Cell currentCell;

    public Unit(String unitName, double hitPoints, int movePoints, Faction faction, MovementPattern movePattern, Map<Terrain, Integer> moveCosts, Collection<ActiveAbility> activeAbilities, Collection<PassiveAbility> passiveAbilties, String unitDescription, String imgPath, Game game) {
        super(unitName, unitDescription, imgPath, game);
        this.faction = faction;
        this.moveCosts = moveCosts;
        this.hitPoints = new HitPoints(hitPoints, game);
        this.movePoints = new MovePoints(movePoints, game);
        this.movePattern = movePattern;
        this.passiveAbilties = passiveAbilties.stream().collect(Collectors.toMap(PassiveAbility::getName, a -> a));
        this.activeAbilities = activeAbilities.stream().collect(Collectors.toMap(ActiveAbility::getName, a -> a));
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
        return activeAbilities.get(s);
    }

    public Collection<ActiveAbility> getActives() {
        return activeAbilities.values();
    }

    public Collection<PassiveAbility> getPassives() {
        return passiveAbilties.values();
    }

    public List<InteractionModifier<Double>> getAttackModifier() {
        //TODO
        return Collections.singletonList(InteractionModifier.NO_CHANGE);
    }

    public List<InteractionModifier<Double>> getDefenseModifier() {
        //TODO
        return Collections.singletonList(InteractionModifier.NO_CHANGE);
    }

    public void moveTo(Cell cell) {
        movePoints.useMovePoints(moveCosts.get(cell.getTerrain()));
        currentCell = cell;
    }

    public Collection<Cell> getMoveOptions(){
        //TODO:
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
