package backend.grid;

import backend.Game;
import backend.GameObject;
import backend.unit.Unit;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Created by th174 on 3/28/2017.
 */
public class CellImpl extends GameObject implements Cell {
    private final CoordinateTuple coordinates;
    private final Terrain terrain;
    private final Collection<Unit> currentOccupants;
    private final Collection<CellAbility> abilities;

    public CellImpl(CoordinateTuple coordinates, Terrain terrain, Collection<CellAbility> abilities, Game game) {
        this(coordinates, terrain, abilities, terrain.getImgPath(), game);
    }

    public CellImpl(CoordinateTuple coordinates, Terrain terrain, Collection<CellAbility> abilities, String imgPath, Game game) {
        this(coordinates, terrain, abilities, Paths.get(imgPath), game);
    }

    public CellImpl(CoordinateTuple coordinates, Terrain terrain, Collection<CellAbility> abilities, Path imgPath, Game game) {
        super("Cell@" + coordinates.toString(), imgPath, game);
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
