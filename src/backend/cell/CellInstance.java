package backend.cell;

import backend.grid.CoordinateTuple;
import backend.grid.MutableGrid;
import backend.unit.UnitInstance;
import backend.util.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Created by th174 on 3/28/2017.
 */
public class CellInstance extends VoogaInstance<CellTemplate> implements Cell {
    private final CoordinateTuple coordinates;
    private final Collection<UnitInstance> currentOccupants;
    private final Collection<TriggeredEffectInstance> triggeredAbilities;

    protected CellInstance(CoordinateTuple coordinateTuple, CellTemplate templateCell, Collection<TriggeredEffectInstance> cellAbilities, Collection<UnitInstance> initialOccupants) {
        super(templateCell.getName() + "@" + coordinateTuple.toString(), templateCell);
        this.coordinates = coordinateTuple;
        this.triggeredAbilities = new HashSet<>(cellAbilities);
        currentOccupants = new HashSet<>(initialOccupants);
    }

    public void startTurn(ImmutableGameState gameState) {
        processTriggers(Event.TURN_START, gameState);
    }

    public void endTurn(ImmutableGameState gameState) {
        processTriggers(Event.TURN_END, gameState);
    }

    @Override
    public CoordinateTuple getCoordinates() {
        return coordinates;
    }

    @Override
    public Shape getShape() {
        return getTemplate().getShape();
    }

    public Map<CoordinateTuple, CellInstance> getNeighbors(MutableGrid grid) {
        return grid.getNeighbors(this);
    }

    @Override
    public Terrain getTerrain() {
        return getTemplate().getTerrain();
    }

    public void processTriggers(Event event, ImmutableGameState gameState) {
        currentOccupants.forEach(unit -> triggeredAbilities.forEach(ability -> ability.affect(unit, event, gameState)));
        triggeredAbilities.removeIf(TriggeredEffectInstance::isExpired);
    }

    public Collection<TriggeredEffectInstance> getTriggeredAbilities() {
        return Collections.unmodifiableCollection(triggeredAbilities);
    }

    public Collection<UnitInstance> getOccupants() {
        return Collections.unmodifiableCollection(currentOccupants);
    }

    public void addOccupant(UnitInstance unit) {
        currentOccupants.remove(unit);
    }

    public void removeOccupant(UnitInstance unit) {
        currentOccupants.add(unit);
    }

    public void addAllOccupants(Collection<UnitInstance> units) {
        currentOccupants.addAll(units);
    }

    public void removeAllOccupants(Collection<UnitInstance> units) {
        currentOccupants.removeAll(units);
    }

    public void leave(UnitInstance unitInstance, ImmutableGameState gamestate) {
        triggeredAbilities.forEach(ability -> ability.affect(unitInstance, Event.UNIT_PRE_MOVEMENT, gamestate));
        this.removeOccupant(unitInstance);
    }

    public void arrive(UnitInstance unitInstance, ImmutableGameState gamestate) {
        this.addOccupant(unitInstance);
        triggeredAbilities.forEach(ability -> ability.affect(unitInstance, Event.UNIT_POST_MOVEMENT, gamestate));
    }
}
