package backend.unit;

import backend.cell.CellInstance;
import backend.cell.Terrain;
import backend.util.GameState;
import backend.util.GameState.Event;
import backend.grid.CoordinateTuple;
import backend.player.Player;
import backend.player.Team;
import backend.unit.properties.*;
import backend.util.VoogaInstance;
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
    private final Map<String, ActiveAbility<VoogaInstance>> activeAbilities;
    private final Map<String, TriggeredAbility> triggeredAbilities;
    private final List<InteractionModifier<Double>> offensiveModifiers;
    private final List<InteractionModifier<Double>> defensiveModifiers;
    private final Map<Terrain, Integer> moveCosts;
    private final Faction faction;

    private Player ownerPlayer;
    private CellInstance currentCell;

    protected UnitInstance(String unitName, UnitTemplate unitTemplate, Player ownerPlayer, CellInstance startingCell, GameState game) {
        super(unitName, unitTemplate, game);
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

    public void moveTo(CellInstance cell) {
        movePoints.useMovePoints(moveCosts.get(cell.getTerrain()));
        currentCell = cell;
        processTriggers(Event.UNIT_MOVEMENT);
    }

    public void startTurn() {
        processTriggers(Event.TURN_START);
    }

    public void endTurn() {
        processTriggers(Event.TURN_END);
        movePoints.resetValue();
    }

    public void takeDamage(double damage) {
        getHitPoints().takeDamage(damage);
    }

    public void useActiveAbility(String activeAbilityName, VoogaInstance target) {
        useActiveAbility(getActiveAbilityByName(activeAbilityName), target);
    }

    public void useActiveAbility(ActiveAbility<VoogaInstance> activeAbility, VoogaInstance target) {
        activeAbility.affect(this, target, getGameState());
        processTriggers(Event.UNIT_ABILITY_USE);
    }

    private void processTriggers(Event event) {
        triggeredAbilities.values().forEach(e -> e.affect(this, event, getGameState()));
    }

    public Collection<CellInstance> getLegalMoves() {
        return movePattern.getLegalMoves().parallelStream()
                .map(e -> getGameState().getGrid().get(e.sum(this.getLocation())))
                .filter(Objects::nonNull)
                .filter(e -> getMoveCostByTerrain(e.getTerrain()) < movePoints.getCurrentValue()).collect(Collectors.toSet());
    }

    public void setCurrentCell(CellInstance currentCell) {
        this.currentCell = currentCell;
    }

    public CellInstance getCurrentCell() {
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

    public Map<CoordinateTuple, CellInstance> getNeighboringCells() {
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

    @Override
    public List<InteractionModifier<Double>> getOffensiveModifiers() {
        return offensiveModifiers;
    }

    public double applyAllOffensiveModifiers(Double originalValue, UnitInstance target) {
        return InteractionModifier.modifyAll(getOffensiveModifiers(), originalValue, this, target, getGameState());
    }

    @Override
    public List<InteractionModifier<Double>> getDefensiveModifiers() {
        return defensiveModifiers;
    }

    public double applyAllDefensiveModifiers(Double originalValue, UnitInstance agent) {
        return InteractionModifier.modifyAll(getDefensiveModifiers(), originalValue, agent, this, getGameState());
    }

    @Override
    public Map<String, ActiveAbility<VoogaInstance>> getActiveAbilities() {
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
