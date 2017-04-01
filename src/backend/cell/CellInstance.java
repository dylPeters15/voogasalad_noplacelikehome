package backend.cell;

import backend.grid.CoordinateTuple;
import backend.unit.UnitInstance;
import backend.util.GameState;
import backend.util.GameState.Event;
import backend.util.VoogaInstance;

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
    private final Collection<CellEffect> abilities;

    protected CellInstance(CoordinateTuple coordinateTuple, CellTemplate templateCell, Collection<CellEffect> cellAbilities, Collection<UnitInstance> initialOccupants, GameState game) {
        super(templateCell.getName() + "@" + coordinateTuple.toString(), templateCell, game);
        this.coordinates = coordinateTuple;
        this.abilities = new HashSet<>(cellAbilities);
        currentOccupants = new HashSet<>(initialOccupants);
    }

    public void startTurn() {
        applyAbilities(Event.TURN_START);
    }

    public void endTurn() {
        applyAbilities(Event.TURN_END);
    }

    @Override
    public CoordinateTuple getCoordinates() {
        return coordinates;
    }

    @Override
    public Shape getShape() {
        return getTemplate().getShape();
    }

    public Map<CoordinateTuple, CellInstance> getNeighbors() {
        return getGameState().getGrid().getNeighbors(this);
    }

    @Override
    public Terrain getTerrain() {
        return getTemplate().getTerrain();
    }

    public void applyAbilities(Event event) {
        currentOccupants.forEach(unit -> abilities.forEach(ability -> ability.affect(unit, event, getGameState())));
    }

    @Override
    public Collection<CellEffect> getAbilities() {
        return Collections.unmodifiableCollection(abilities);
    }

    @Override
    public Collection<UnitInstance> getOccupants() {
        return currentOccupants;
    }
}
