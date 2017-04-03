package backend.unit;

import backend.cell.CellInstance;
import backend.cell.Terrain;
import backend.grid.CoordinateTuple;
import backend.grid.MutableGrid;
import backend.player.Player;
import backend.player.Team;
import backend.unit.properties.*;
import backend.util.*;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Timmy
 *
 * @author Created by th174 on 3/27/2017.
 */
public class UnitInstance extends VoogaInstance<UnitTemplate> implements Unit {
    private final HitPoints hitPoints;
    private final MovePoints movePoints;
    private final GridPattern movePattern;
    private final Map<String, ActiveAbility<VoogaObject>> activeAbilities;
    private final Map<String, TriggeredAbility> triggeredAbilities;
    private final List<InteractionModifier<Double>> offensiveModifiers;
    private final List<InteractionModifier<Double>> defensiveModifiers;
    private final Map<Terrain, Integer> moveCosts;
    private final Faction faction;

    private Player ownerPlayer;
    private CellInstance currentCell;

    protected UnitInstance(String unitName, UnitTemplate unitTemplate, Player ownerPlayer, CellInstance startingCell) {
        super(unitName, unitTemplate);
        this.faction = unitTemplate.getFaction();
        this.hitPoints = unitTemplate.getHitPoints();
        this.movePoints = unitTemplate.getMovePoints();
        this.movePattern = unitTemplate.getMovePattern();
        this.moveCosts = new HashMap<>(unitTemplate.getTerrainMoveCosts());
        this.triggeredAbilities = new HashMap<>(unitTemplate.getTriggeredAbilities());
        this.activeAbilities = new HashMap<>(unitTemplate.getActiveAbilities());
        this.offensiveModifiers = new ArrayList<>(unitTemplate.getOffensiveModifiers());
        this.defensiveModifiers = new ArrayList<>(unitTemplate.getDefensiveModifiers());
        setOwner(ownerPlayer);
        setCurrentCell(startingCell);
    }

    public void moveTo(CellInstance cell, ImmutableGameState gameState) {
        movePoints.useMovePoints(moveCosts.get(cell.getTerrain()));
        currentCell = cell;
        processTriggers(Event.UNIT_MOVEMENT, gameState);
    }

    public void startTurn(GameState gameState) {
        processTriggers(Event.TURN_START, gameState);
    }

    public void endTurn(GameState gameState) {
        processTriggers(Event.TURN_END, gameState);
        movePoints.resetValue();
    }

    public void takeDamage(double damage) {
        getHitPoints().takeDamage(damage);
    }

    public void useActiveAbility(String activeAbilityName, VoogaInstance target, ImmutableGameState gameState) {
        useActiveAbility(getActiveAbilityByName(activeAbilityName), target, gameState);
    }

    public void useActiveAbility(ActiveAbility<VoogaObject> activeAbility, VoogaInstance target, ImmutableGameState gameState) {
        activeAbility.affect(this, target, gameState);
        processTriggers(Event.UNIT_ABILITY_USE, gameState);
    }

    private void processTriggers(Event event, ImmutableGameState gameState) {
        triggeredAbilities.values().forEach(e -> e.affect(this, event, gameState));
    }

    public Collection<CellInstance> getLegalMoves(MutableGrid grid) {
        return movePattern.getCoordinates().parallelStream()
                .map(e -> grid.get(e.sum(this.getLocation())))
                .filter(Objects::nonNull)
                .filter(e -> getMoveCostByTerrain(e.getTerrain()) < movePoints.getCurrentValue()).collect(Collectors.toSet());
    }

    public CellInstance getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(CellInstance currentCell) {
        this.currentCell = currentCell;
    }

    public Map<CoordinateTuple, Collection<UnitInstance>> getNeighboringUnits(MutableGrid grid) {
        Map<CoordinateTuple, Collection<UnitInstance>> neighbors = currentCell.getNeighbors(grid).entrySet().parallelStream()
                .map(e -> new Pair<>(e.getKey(), e.getValue().getOccupants()))
                .filter(e -> !e.getValue().isEmpty())
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        neighbors.put(CoordinateTuple.getOrigin(currentCell.dimension()), currentCell.getOccupants().parallelStream().filter(e -> !equals(e)).collect(Collectors.toSet()));
        return neighbors;
    }

    public Collection<UnitInstance> getAllNeighboringUnits(MutableGrid grid) {
        return getNeighboringUnits(grid).values().parallelStream().flatMap(Collection::stream).parallel().collect(Collectors.toSet());
    }

    public Map<CoordinateTuple, CellInstance> getNeighboringCells(MutableGrid grid) {
        return currentCell.getNeighbors(grid);
    }

    public CoordinateTuple getLocation() {
        return currentCell.getCoordinates();
    }

    public Player getOwner() {
        return ownerPlayer;
    }

    public void setOwner(Player p) {
        ownerPlayer = p;
    }

    public Team getTeam() {
        return ownerPlayer.getTeam();
    }

    @Override
    public List<InteractionModifier<Double>> getOffensiveModifiers() {
        return offensiveModifiers;
    }

    public double applyAllOffensiveModifiers(Double originalValue, UnitInstance target, final ImmutableGameState gameState) {
        return InteractionModifier.modifyAll(getOffensiveModifiers(), originalValue, this, target, gameState);
    }

    @Override
    public List<InteractionModifier<Double>> getDefensiveModifiers() {
        return defensiveModifiers;
    }

    public double applyAllDefensiveModifiers(Double originalValue, UnitInstance agent, final ImmutableGameState gameState) {
        return InteractionModifier.modifyAll(getDefensiveModifiers(), originalValue, agent, this, gameState);
    }

    @Override
    public Map<String, ActiveAbility<VoogaObject>> getActiveAbilities() {
        return activeAbilities;
    }

    @Override
    public Map<String, TriggeredAbility> getTriggeredAbilities() {
        return triggeredAbilities;
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
    public Map<Terrain, Integer> getTerrainMoveCosts() {
        return moveCosts;
    }

    public int movePointsTo(CoordinateTuple other) {
        throw new RuntimeException("Not Implemented Yet");
    }
}
