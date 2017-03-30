package backend.unit;

import backend.GameObject;
import backend.GameObjectImpl;
import backend.cell.Cell;
import backend.cell.Terrain;
import backend.game_engine.GameState;
import backend.game_engine.Player;
import backend.grid.CoordinateTuple;
import backend.unit.properties.*;
import javafx.util.Pair;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Timmy
 *
 * @author Created by th174 on 3/27/2017.
 */
public class UnitInstance extends GameObjectImpl implements GameObject, Unit {
    private final UnitTemplate unitType;
    private final HitPoints hitPoints;
    private final MovePoints movePoints;
    private final GridPattern movePattern;
    private final Map<String, ActiveAbility<GameObject>> activeAbilities;
    private final Map<String, PassiveAbility> passiveAbilties;
    private final List<InteractionModifier<Double>> offensiveModifiers;
    private final List<InteractionModifier<Double>> defensiveModifiers;
    private final Map<Terrain, Integer> moveCosts;
    private final Faction faction;

    private Player ownedBy;
    private Cell currentCell;

    //LOL 15 parameters and they're all necessary
    public UnitInstance(UnitTemplate unitType, String unitName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, Collection<ActiveAbility<GameObject>> activeAbilities, Collection<PassiveAbility> passiveAbilties, Collection<InteractionModifier<Double>> offensiveModifiers, Collection<InteractionModifier<Double>> defensiveModifiers, String unitDescription, Path imgPath, GameState game) {
        this(unitType,unitName,new HitPoints(hitPoints),new MovePoints(movePoints),faction,movePattern,moveCosts,activeAbilities,passiveAbilties,offensiveModifiers,defensiveModifiers,unitDescription,imgPath,game);
    }

    public UnitInstance(UnitTemplate unitType, String unitName, HitPoints hitPoints, MovePoints movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, Collection<ActiveAbility<GameObject>> activeAbilities, Collection<PassiveAbility> passiveAbilties, Collection<InteractionModifier<Double>> offensiveModifiers, Collection<InteractionModifier<Double>> defensiveModifiers, String unitDescription, Path imgPath, GameState game) {
        super(unitName, unitDescription, imgPath, game);
        this.unitType = unitType;
        this.faction = faction;
        this.moveCosts = new HashMap<>(moveCosts);
        this.hitPoints = hitPoints;
        this.movePoints = movePoints;
        this.movePattern = movePattern;
        this.passiveAbilties = passiveAbilties.stream().collect(Collectors.toMap(PassiveAbility::getName, a -> a));
        this.activeAbilities = activeAbilities.stream().collect(Collectors.toMap(ActiveAbility::getName, a -> a));
        this.offensiveModifiers = new ArrayList<>(offensiveModifiers);
        this.defensiveModifiers = new ArrayList<>(defensiveModifiers);
    }

    public void moveTo(Cell cell) {
        movePoints.useMovePoints(moveCosts.get(cell.getTerrain()));
        currentCell = cell;
        triggerPassives(PassiveAbility.TriggerEvent.ON_MOVE);
    }

    public void startTurn() {
        triggerPassives(PassiveAbility.TriggerEvent.ON_TURN_START);
    }

    public void endTurn() {
        triggerPassives(PassiveAbility.TriggerEvent.ON_TURN_END);
        movePoints.resetValue();
    }

    public void useActiveAbility(String activeAbilityName, GameObject target) {
        useActiveAbility(getActiveAbilityByName(activeAbilityName), target);
    }

    public void useActiveAbility(ActiveAbility<GameObject> activeAbility, GameObject target) {
        activeAbility.affect(this, target, getGame());
        triggerPassives(PassiveAbility.TriggerEvent.ON_ACTION);
    }

    private void triggerPassives(PassiveAbility.TriggerEvent event) {
        passiveAbilties.values().forEach(e -> e.affect(this, event, getGame()));
    }

    public Collection<Cell> getMoveOptions() {
        return movePattern.getLegalMoves().stream()
                .map(e -> getGame().getGrid().get(e.sum(this.getLocation())))
                .filter(Objects::nonNull)
                .filter(e -> getMoveCostByTerrain(e.getTerrain()) < movePoints.getCurrentValue()).collect(Collectors.toSet());
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public Map<CoordinateTuple, Collection<UnitInstance>> getNeighboringUnits() {
        Map<CoordinateTuple, Collection<UnitInstance>> neighbors = currentCell.getNeighbors().entrySet().stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue().getOccupants()))
                .filter(e -> !e.getValue().isEmpty())
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        neighbors.put(CoordinateTuple.getOrigin(currentCell.dimension()), currentCell.getOccupants().stream().filter(e -> !equals(e)).collect(Collectors.toSet()));
        return neighbors;
    }

    public Map<CoordinateTuple, Cell> getNeighboringCells() {
        return currentCell.getNeighbors();
    }

    public CoordinateTuple getLocation() {
        return currentCell.getCoordinates();
    }

    public Player getOwner() {
        return ownedBy;
    }

    public void setOwner(Player p) {
        ownedBy = p;
    }

    public UnitTemplate getUnitType() {
        return unitType;
    }

    public String getUnitTypeName() {
        return unitType.getName();
    }

    @Override
    public List<InteractionModifier<Double>> getOffensiveModifiers() {
        return offensiveModifiers;
    }

    @Override
    public List<InteractionModifier<Double>> getDefensiveModifiers() {
        return defensiveModifiers;
    }

    @Override
    public Map<String, ActiveAbility<GameObject>> getActiveAbilities() {
        return activeAbilities;
    }

    @Override
    public Map<String, PassiveAbility> getPassiveAbilities() {
        return passiveAbilties;
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
    public Faction getFaction() {
        return faction;
    }

    @Override
    public GridPattern getMovePattern() {
        return movePattern;
    }

    @Override
    public Map<Terrain, Integer> getMoveCosts() {
        return moveCosts;
    }

    public int movePointsTo(CoordinateTuple other) {
        throw new RuntimeException("Not Implemented Yet");
    }
}
