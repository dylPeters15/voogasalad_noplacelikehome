package backend.cell;

import backend.GameObjectImpl;
import backend.game_engine.GameState;
import backend.grid.CoordinateTuple;
import backend.unit.Unit;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * Dylan
 *
 * @author Created by th174 on 3/28/2017.
 */
public class CellImpl extends GameObjectImpl implements Cell {
    private final CoordinateTuple coordinates;
    private final Terrain terrain;
    private final Collection<Unit> currentOccupants;
    private final Collection<CellAbility> abilities;

    public CellImpl(CoordinateTuple coordinates, Terrain terrain, Collection<CellAbility> abilities, GameState game) {
        this(coordinates, terrain, abilities, terrain.getImgPath(), game);
    }

    public CellImpl(CoordinateTuple coordinates, Terrain terrain, Collection<CellAbility> abilities, String imgPath, GameState game) {
        this(coordinates, terrain, abilities, Paths.get(imgPath), game);
    }

    public CellImpl(CoordinateTuple coordinates, Terrain terrain, Collection<CellAbility> abilities, Path imgPath, GameState game) {
        super(terrain.getName() + "@" + coordinates.toString(), imgPath, game);
        this.coordinates = coordinates;
        this.terrain = terrain;
        this.abilities = abilities;
        currentOccupants = new HashSet<>();
    }

    @Override
    public CoordinateTuple getCoordinates() {
        return coordinates;
    }

    @Override
    public Map<CoordinateTuple, Cell> getNeighbors() {
        return getGame().getGrid().getNeighbors(this);
    }

    @Override
    public Terrain getTerrain() {
        return terrain;
    }

    @Override
    public void applyAbilities() {
        currentOccupants.forEach(unit -> abilities.forEach(ability -> ability.apply(getGame(), unit)));
    }

    @Override
    public Collection<Unit> getOccupants() {
        return currentOccupants;
    }
}
