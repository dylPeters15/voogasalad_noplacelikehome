package backend.cell;

import backend.grid.CoordinateTuple;
import backend.unit.UnitInstance;
import backend.util.TriggeredEffectInstance;
import backend.util.TriggeredEffectTemplate;
import backend.util.VoogaObject;

import java.util.*;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class CellTemplate extends VoogaObject implements Cell {
    //TODO ResourceBundlify
    public static final CellTemplate BASIC_HEXAGONAL_EMPTY = new CellTemplate("Basic Hexagonal Empty Cell", Shape.HEXAGONAL, Terrain.EMPTY);
    public static final CellTemplate BASIC_HEXAGONAL_FLAT = new CellTemplate("Basic Hexagonal Flat Cell", Shape.HEXAGONAL, Terrain.FLAT);
    public static final CellTemplate BASIC_HEXAGONAL_FOREST = new CellTemplate("Basic Hexagonal Forest Cell", Shape.HEXAGONAL, Terrain.FOREST);
    public static final CellTemplate BASIC_HEXAGONAL_WATER = new CellTemplate("Basic Hexagonal Water Cell", Shape.HEXAGONAL, Terrain.WATER);
    public static final CellTemplate BASIC_HEXAGONAL_MOUNTAIN = new CellTemplate("Basic Hexagonal Mountain Cell", Shape.HEXAGONAL, Terrain.MOUNTAIN);
    public static final CellTemplate BASIC_HEXAGONAL_FORTIFIED = new CellTemplate("Basic Hexagonal Fortified Cell", Shape.HEXAGONAL, Terrain.FORTIFIED);
    public static final CellTemplate HEALING_HEXAGONAL_EMPTY = new CellTemplate("Healing Hexagonal Empty Cell", Shape.HEXAGONAL, Terrain.EMPTY, TriggeredEffectTemplate.FULL_HEAL.createInstance());
    public static final CellTemplate HEALING_HEXAGONAL_FLAT = new CellTemplate("Healing Hexagonal Flat Cell", Shape.HEXAGONAL, Terrain.FLAT, TriggeredEffectTemplate.FULL_HEAL.createInstance());
    public static final CellTemplate HEALING_HEXAGONAL_FOREST = new CellTemplate("Healing Hexagonal Forest Cell", Shape.HEXAGONAL, Terrain.FOREST, TriggeredEffectTemplate.FULL_HEAL.createInstance());
    public static final CellTemplate HEALING_HEXAGONAL_WATER = new CellTemplate("Healing Hexagonal Water Cell", Shape.HEXAGONAL, Terrain.WATER, TriggeredEffectTemplate.FULL_HEAL.createInstance());
    public static final CellTemplate HEALING_HEXAGONAL_MOUNTAIN = new CellTemplate("Healing Hexagonal Mountain Cell", Shape.HEXAGONAL, Terrain.MOUNTAIN, TriggeredEffectTemplate.FULL_HEAL.createInstance());
    public static final CellTemplate HEALING_HEXAGONAL_FORTIFIED = new CellTemplate("Healing Hexagonal Fortified Cell", Shape.HEXAGONAL, Terrain.FORTIFIED, TriggeredEffectTemplate.FULL_HEAL.createInstance());
    public static final CellTemplate BASIC_SQUARE_EMPTY = new CellTemplate("Basic Square Empty Cell", Shape.SQUARE, Terrain.EMPTY);
    public static final CellTemplate BASIC_SQUARE_FLAT = new CellTemplate("Basic Square Flat Cell", Shape.SQUARE, Terrain.FLAT);
    public static final CellTemplate BASIC_SQUARE_FOREST = new CellTemplate("Basic Square Forest Cell", Shape.SQUARE, Terrain.FOREST);
    public static final CellTemplate BASIC_SQUARE_WATER = new CellTemplate("Basic Square Water Cell", Shape.SQUARE, Terrain.WATER);
    public static final CellTemplate BASIC_SQUARE_MOUNTAIN = new CellTemplate("Basic Square Mountain Cell", Shape.SQUARE, Terrain.MOUNTAIN);
    public static final CellTemplate BASIC_SQUARE_FORTIFIED = new CellTemplate("Basic Square Fortified Cell", Shape.SQUARE, Terrain.FORTIFIED);
    public static final CellTemplate STRONG_ATTACK_SQUARE_MOUNTAIN = new CellTemplate("Strong Attack Square Cell", Shape.SQUARE, Terrain.MOUNTAIN, TriggeredEffectTemplate.STRONG_ATTACK.createInstance());
    public static final CellTemplate STRONG_ATTACK_HEXAGON_MOUNTAIN = new CellTemplate("Strong Attack Square Cell", Shape.HEXAGONAL, Terrain.MOUNTAIN, TriggeredEffectTemplate.STRONG_ATTACK.createInstance());

    private final List<TriggeredEffectInstance> abilities;
    private Shape shape;
    private Terrain terrain;

    public CellTemplate(String name, Shape shape, Terrain terrain, TriggeredEffectInstance... abilities) {
        this(name, shape, terrain, terrain.getImgPath(), abilities);
    }

    public CellTemplate(String name, Shape shape, Terrain terrain, Collection<TriggeredEffectInstance> abilities) {
        this(name, shape, terrain, terrain.getImgPath(), abilities);
    }

    public CellTemplate(String name, Shape shape, Terrain terrain, String imgPath, TriggeredEffectInstance... abilities) {
        this(name, shape, terrain, imgPath, Arrays.asList(abilities));
    }

    public CellTemplate(String name, Shape shape, Terrain terrain, String imgPath, Collection<TriggeredEffectInstance> abilities) {
        this(name, shape, terrain, name, imgPath, abilities);
    }

    public CellTemplate(String name, Shape shape, Terrain terrain, String description, String imgPath, Collection<TriggeredEffectInstance> abilities) {
        super(name, description, imgPath);
        this.shape = shape;
        this.terrain = terrain;
        this.abilities = new ArrayList<>(abilities);
    }

    public CellInstance createInstance(CoordinateTuple coordinateTuple) {
        return createInstance(coordinateTuple, Collections.emptyList());
    }

    public CellInstance createInstance(CoordinateTuple coordinateTuple, Collection<TriggeredEffectInstance> cellAbilities) {
        return createInstance(coordinateTuple, cellAbilities, Collections.emptyList());
    }

    public CellInstance createInstance(CoordinateTuple coordinateTuple, Collection<TriggeredEffectInstance> cellAbilities, Collection<UnitInstance> initialOccupants) {
        return new CellInstance(coordinateTuple, this, cellAbilities, initialOccupants);
    }

    @Override
    public CoordinateTuple getCoordinates() {
        return new CoordinateTuple(new int[getShape().getDimension()]);
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    @Override
    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    @Override
    public Collection<TriggeredEffectInstance> getTriggeredAbilities() {
        return abilities;
    }

    public Collection<CellTemplate> getPredefinedCellTemplates() {
        return getPredefined(CellTemplate.class);
    }
}
