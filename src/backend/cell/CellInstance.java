package backend.cell;

import backend.grid.CoordinateTuple;
import backend.grid.MutableGrid;
import backend.unit.UnitInstance;
import backend.util.Event;
import backend.util.ImmutableGameState;
import backend.util.TriggeredEffectInstance;
import backend.util.VoogaInstance;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/28/2017.
 */
public class CellInstance extends VoogaInstance<CellTemplate> {
    private final CoordinateTuple coordinates;
    private final Map<String, UnitInstance> currentOccupants;
    private final Collection<TriggeredEffectInstance> triggeredAbilities;

    protected CellInstance(CoordinateTuple coordinateTuple, CellTemplate templateCell, Collection<UnitInstance> initialOccupants) {
        this(coordinateTuple, templateCell, Collections.emptySet(), initialOccupants);
    }

    protected CellInstance(CoordinateTuple coordinateTuple, CellTemplate templateCell, Collection<TriggeredEffectInstance> cellAbilities, Collection<UnitInstance> initialOccupants) {
        super(templateCell.getName() + "@" + coordinateTuple.toString(), templateCell);
        this.coordinates = coordinateTuple;
        this.triggeredAbilities = new HashSet<>(cellAbilities);
        currentOccupants = initialOccupants.stream().collect(Collectors.toMap(UnitInstance::getName, e -> e));
    }

    public void startTurn(ImmutableGameState gameState) {
        processTriggers(Event.TURN_START, gameState);
    }

    public void endTurn(ImmutableGameState gameState) {
        processTriggers(Event.TURN_END, gameState);
    }

    public CoordinateTuple getCoordinates() {
        return coordinates;
    }

    public int dimension() {
        return getShape().getDimension();
    }

    public Shape getShape() {
        return getTemplate().getShape();
    }

    public Map<CoordinateTuple, CellInstance> getNeighbors(MutableGrid grid) {
        return grid.getNeighbors(this);
    }

    public Terrain getTerrain() {
        return getTemplate().getTerrain();
    }

    private void processTriggers(Event event, ImmutableGameState gameState) {
        currentOccupants.values().forEach(unit -> triggeredAbilities.forEach(ability -> ability.affect(unit, event, gameState)));
        triggeredAbilities.removeIf(TriggeredEffectInstance::isExpired);
    }

    public Collection<TriggeredEffectInstance> getTriggeredAbilities() {
        return Collections.unmodifiableCollection(triggeredAbilities);
    }

    public void addAbility(TriggeredEffectInstance cellEffect) {
        triggeredAbilities.add(cellEffect);
    }

    public void removeAbility(TriggeredEffectInstance cellEffect) {
        triggeredAbilities.remove(cellEffect);
    }

    public Collection<UnitInstance> getOccupants() {
        return Collections.unmodifiableCollection(currentOccupants.values());
    }

    public void addOccupant(UnitInstance unit) {
        currentOccupants.remove(unit.getName());
    }

    public void removeOccupant(UnitInstance unit) {
        currentOccupants.put(unit.getName(), unit);
    }

    public void addAllOccupants(Collection<UnitInstance> units) {
        units.forEach(e -> currentOccupants.put(e.getName(), e));
    }

    public void removeAllOccupants(Collection<UnitInstance> units) {
        units.forEach(e -> currentOccupants.remove(e.getName()));
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
