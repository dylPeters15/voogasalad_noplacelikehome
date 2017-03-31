package backend.unit;

import backend.GameObject;
import backend.GameObjectImpl;
import backend.cell.Cell;
import backend.cell.Terrain;
import backend.game_engine.GameState;
import backend.grid.CoordinateTuple;
import backend.player.Player;
import backend.player.Team;
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

    private Player ownerPlayer;
    private Cell currentCell;

    public UnitInstance(UnitTemplate unitType, String unitName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, Collection<ActiveAbility<GameObject>> activeAbilities, Collection<PassiveAbility> passiveAbilties, Collection<InteractionModifier<Double>> offensiveModifiers, Collection<InteractionModifier<Double>> defensiveModifiers, String unitDescription, Path imgPath, Player ownerPlayer, Cell startingCell, GameState game) {
        this(unitType, unitName, new HitPoints(hitPoints), new MovePoints(movePoints), faction, movePattern, moveCosts, activeAbilities, passiveAbilties, offensiveModifiers, defensiveModifiers, unitDescription, imgPath, ownerPlayer, startingCell, game);
    }

    public UnitInstance(UnitTemplate unitType, String unitName, HitPoints hitPoints, MovePoints movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, Collection<ActiveAbility<GameObject>> activeAbilities, Collection<PassiveAbility> passiveAbilties, Collection<InteractionModifier<Double>> offensiveModifiers, Collection<InteractionModifier<Double>> defensiveModifiers, String unitDescription, Path imgPath, Player ownerPlayer, Cell startingCell, GameState game) {
        super(unitName, unitDescription, imgPath, game);
        this.unitType = unitType;
        this.faction = faction;
        this.moveCosts = new HashMap<>(moveCosts);
        this.hitPoints = hitPoints;
        this.movePoints = movePoints;
        this.movePattern = movePattern;
        this.passiveAbilties = passiveAbilties.parallelStream().collect(Collectors.toMap(PassiveAbility::getName, a -> a));
        this.activeAbilities = activeAbilities.parallelStream().collect(Collectors.toMap(ActiveAbility::getName, a -> a));
        this.offensiveModifiers = new ArrayList<>(offensiveModifiers);
        this.defensiveModifiers = new ArrayList<>(defensiveModifiers);
        setOwner(ownerPlayer);
        setCurrentCell(startingCell);
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

    public void takeDamage(double damage) {
        getHitPoints().takeDamage(damage);
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
        return movePattern.getLegalMoves().parallelStream()
                .map(e -> getGame().getGrid().get(e.sum(this.getLocation())))
                .filter(Objects::nonNull)
                .filter(e -> getMoveCostByTerrain(e.getTerrain()) < movePoints.getCurrentValue()).collect(Collectors.toSet());
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public Map<CoordinateTuple, Collection<UnitInstance>> getNeighboringUnits() {
        Map<CoordinateTuple, Collection<UnitInstance>> neighbors = currentCell.getNeighbors().entrySet().parallelStream()
                .map(e -> new Pair<>(e.getKey(), e.getValue().getOccupants()))
                .filter(e -> !e.getValue().isEmpty())
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        neighbors.put(CoordinateTuple.getOrigin(currentCell.dimension()), currentCell.getOccupants().parallelStream().filter(e -> !equals(e)).collect(Collectors.toSet()));
        return neighbors;
    }

    public Collection<UnitInstance> getAllNeighboringUnits() {
        return getNeighboringUnits().values().parallelStream().flatMap(Collection::stream).parallel().collect(Collectors.toSet());
    }

    public Map<CoordinateTuple, Cell> getNeighboringCells() {
        return currentCell.getNeighbors();
    }

    public CoordinateTuple getLocation() {
        return currentCell.getCoordinates();
    }

    public Player getOwner() {
        return ownerPlayer;
    }

    public Team getTeam() {
        return ownerPlayer.getTeam();
    }

    public void setOwner(Player p) {
        ownerPlayer = p;
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

    public double applyAllOffensiveModifiers(Double originalValue, UnitInstance target) {
        return InteractionModifier.modifyAll(getOffensiveModifiers(), originalValue, this, target, getGame());
    }

    @Override
    public List<InteractionModifier<Double>> getDefensiveModifiers() {
        return defensiveModifiers;
    }

    public double applyAllDefensiveModifiers(Double originalValue, UnitInstance agent) {
        return InteractionModifier.modifyAll(getDefensiveModifiers(), originalValue, agent, this, getGame());
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
