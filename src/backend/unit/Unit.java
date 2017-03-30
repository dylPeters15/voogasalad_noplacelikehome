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

import java.util.*;
import java.util.stream.Collectors;

/**
 * Timmy
 *
 * @author Created by th174 on 3/27/2017.
 */
public class Unit extends GameObjectImpl implements GameObject {
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

    public Unit(String unitName, double hitPoints, int movePoints, Faction faction, String unitDescription, String imgPath, GameState game) {
        this(unitName, hitPoints, movePoints, faction, Collections.EMPTY_MAP, unitDescription, imgPath, game);
    }

    public Unit(String unitName, double hitPoints, int movePoints, Faction faction, Map<Terrain, Integer> moveCosts, String unitDescription, String imgPath, GameState game) {
        this(unitName, hitPoints, movePoints, faction, GridPattern.getNeighborPattern(game.getGrid().dimension()), moveCosts, Collections.EMPTY_SET, Collections.EMPTY_SET, Collections.EMPTY_LIST, Collections.EMPTY_LIST, unitDescription, imgPath, game);
    }

    public Unit(String unitName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, Collection<ActiveAbility<GameObject>> activeAbilities, Collection<PassiveAbility> passiveAbilties, Collection<InteractionModifier> offensiveModifiers, Collection<InteractionModifier> defensiveModifiers, String unitDescription, String imgPath, GameState game) {
        super(unitName, unitDescription, imgPath, game);
        this.faction = faction;
        this.moveCosts = new HashMap<>(moveCosts);
        this.hitPoints = new HitPoints(hitPoints, game);
        this.movePoints = new MovePoints(movePoints, game);
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
        useActiveAbility(activeAbilities.get(activeAbilityName), target);
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
                .filter(e -> getTerrainMoveCost(e.getTerrain()) < movePoints.getCurrentValue()).collect(Collectors.toSet());
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public Map<CoordinateTuple, Collection<Unit>> getNeighboringUnits() {
        Map<CoordinateTuple, Collection<Unit>> neighbors = currentCell.getNeighbors().entrySet().stream()
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

    public Faction getFaction() {
        return faction;
    }

    public Player getOwner() {
        return ownedBy;
    }

    public void setOwner(Player p) {
        ownedBy = p;
    }

    public ActiveAbility<GameObject> getAbilityByName(String s) {
        return activeAbilities.get(s);
    }

    public Collection<ActiveAbility<GameObject>> getActives() {
        return activeAbilities.values();
    }

    public Collection<PassiveAbility> getPassives() {
        return passiveAbilties.values();
    }

    public List<InteractionModifier<Double>> getOffenseModifiers() {
        return offensiveModifiers;
    }

    public void addOffenseModifier(InteractionModifier<Double> offenseModifier) {
        offensiveModifiers.add(offenseModifier);
    }

    public List<InteractionModifier<Double>> getDefenseModifiers() {
        return defensiveModifiers;
    }

    public void addDefenseModifier(InteractionModifier<Double> defensiveModifier) {
        defensiveModifiers.add(defensiveModifier);
    }

    public int getTerrainMoveCost(Terrain terrain) {
        return moveCosts.getOrDefault(terrain, terrain.getDefaultMoveCost());
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

    public GridPattern getMovementPattern() {
        return movePattern;
    }
}
