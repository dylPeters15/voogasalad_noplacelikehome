package backend.cell;

import backend.grid.CoordinateTuple;
import backend.unit.UnitInstance;
import backend.util.TriggeredEffectInstance;
import backend.util.TriggeredEffectTemplate;
import backend.util.VoogaTemplate;

import java.util.*;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class CellTemplate extends VoogaTemplate<CellTemplate> {
    //TODO ResourceBundlify
    public static final CellTemplate BASIC_HEXAGONAL_EMPTY = new CellTemplate("Basic Hexagonal Empty Cell")
            .setShape(Shape.HEXAGONAL)
            .setTerrain(Terrain.EMPTY);
    public static final CellTemplate BASIC_HEXAGONAL_FLAT = new CellTemplate("Basic Hexagonal Flat Cell")
            .setShape(Shape.HEXAGONAL)
            .setTerrain(Terrain.FLAT);
    public static final CellTemplate BASIC_HEXAGONAL_FOREST = new CellTemplate("Basic Hexagonal Forest Cell")
            .setShape(Shape.HEXAGONAL)
            .setTerrain(Terrain.FOREST);
    public static final CellTemplate BASIC_HEXAGONAL_WATER = new CellTemplate("Basic Hexagonal Water Cell")
            .setShape(Shape.HEXAGONAL)
            .setTerrain(Terrain.WATER);
    public static final CellTemplate BASIC_HEXAGONAL_MOUNTAIN = new CellTemplate("Basic Hexagonal Mountain Cell")
            .setShape(Shape.HEXAGONAL)
            .setTerrain(Terrain.MOUNTAIN);
    public static final CellTemplate BASIC_HEXAGONAL_FORTIFIED = new CellTemplate("Basic Hexagonal Fortified Cell")
            .setShape(Shape.HEXAGONAL)
            .setTerrain(Terrain.FORTIFIED);
    public static final CellTemplate HEALING_HEXAGONAL_EMPTY = new CellTemplate("Healing Hexagonal Empty Cell")
            .setShape(Shape.HEXAGONAL).setTerrain(Terrain.EMPTY)
            .addTriggeredAbility(TriggeredEffectTemplate.FULL_HEAL);
    public static final CellTemplate HEALING_HEXAGONAL_FLAT = new CellTemplate("Healing Hexagonal Flat Cell")
            .setShape(Shape.HEXAGONAL)
            .setTerrain(Terrain.FLAT)
            .addTriggeredAbility(TriggeredEffectTemplate.FULL_HEAL);
    public static final CellTemplate HEALING_HEXAGONAL_FOREST = new CellTemplate("Healing Hexagonal Forest Cell")
            .setShape(Shape.HEXAGONAL)
            .setTerrain(Terrain.FOREST)
            .addTriggeredAbility(TriggeredEffectTemplate.FULL_HEAL);
    public static final CellTemplate HEALING_HEXAGONAL_WATER = new CellTemplate("Healing Hexagonal Water Cell")
            .setShape(Shape.HEXAGONAL)
            .setTerrain(Terrain.WATER)
            .addTriggeredAbility(TriggeredEffectTemplate.FULL_HEAL);
    public static final CellTemplate HEALING_HEXAGONAL_MOUNTAIN = new CellTemplate("Healing Hexagonal Mountain Cell")
            .setShape(Shape.HEXAGONAL)
            .setTerrain(Terrain.MOUNTAIN)
            .addTriggeredAbility(TriggeredEffectTemplate.FULL_HEAL);
    public static final CellTemplate HEALING_HEXAGONAL_FORTIFIED = new CellTemplate("Healing Hexagonal Fortified Cell")
            .setShape(Shape.HEXAGONAL)
            .setTerrain(Terrain.FORTIFIED)
            .addTriggeredAbility(TriggeredEffectTemplate.FULL_HEAL);
    public static final CellTemplate BASIC_SQUARE_EMPTY = new CellTemplate("Basic Square Empty Cell")
            .setShape(Shape.SQUARE)
            .setTerrain(Terrain.EMPTY);
    public static final CellTemplate BASIC_SQUARE_FLAT = new CellTemplate("Basic Square Flat Cell")
            .setShape(Shape.SQUARE)
            .setTerrain(Terrain.FLAT);
    public static final CellTemplate BASIC_SQUARE_FOREST = new CellTemplate("Basic Square Forest Cell")
            .setShape(Shape.SQUARE)
            .setTerrain(Terrain.FOREST);
    public static final CellTemplate BASIC_SQUARE_WATER = new CellTemplate("Basic Square Water Cell")
            .setShape(Shape.SQUARE)
            .setTerrain(Terrain.WATER);
    public static final CellTemplate BASIC_SQUARE_MOUNTAIN = new CellTemplate("Basic Square Mountain Cell")
            .setShape(Shape.SQUARE)
            .setTerrain(Terrain.MOUNTAIN);
    public static final CellTemplate BASIC_SQUARE_FORTIFIED = new CellTemplate("Basic Square Fortified Cell")
            .setShape(Shape.SQUARE)
            .setTerrain(Terrain.FORTIFIED);
    public static final CellTemplate STRONG_ATTACK_SQUARE_MOUNTAIN = new CellTemplate("Strong Attack Square Cell")
            .setShape(Shape.SQUARE)
            .setTerrain(Terrain.MOUNTAIN)
            .addTriggeredAbility(TriggeredEffectTemplate.STRONG_ATTACK);
    public static final CellTemplate STRONG_ATTACK_HEXAGON_MOUNTAIN = new CellTemplate("Strong Attack Square Cell")
            .setShape(Shape.HEXAGONAL)
            .setTerrain(Terrain.MOUNTAIN)
            .addTriggeredAbility(TriggeredEffectTemplate.STRONG_ATTACK);
    private final List<TriggeredEffectTemplate> abilities;
    private Shape shape;
    private Terrain terrain;

    public CellTemplate(String name) {
        this(name, null, null);
    }

    public CellTemplate(String name, Shape shape, Terrain terrain, TriggeredEffectTemplate... abilities) {
        this(name, shape, terrain, terrain.getImgPath(), abilities);
    }

    public CellTemplate(String name, Shape shape, Terrain terrain, Collection<TriggeredEffectTemplate> abilities) {
        this(name, shape, terrain, terrain.getImgPath(), abilities);
    }

    public CellTemplate(String name, Shape shape, Terrain terrain, String imgPath, TriggeredEffectTemplate... abilities) {
        this(name, shape, terrain, imgPath, Arrays.asList(abilities));
    }

    public CellTemplate(String name, Shape shape, Terrain terrain, String imgPath, Collection<TriggeredEffectTemplate> abilities) {
        this(name, shape, terrain, name, imgPath, abilities);
    }

    public CellTemplate(String name, Shape shape, Terrain terrain, String description, String imgPath, Collection<TriggeredEffectTemplate> abilities) {
        super(name, description, imgPath);
        this.shape = shape;
        this.terrain = terrain;
        this.abilities = new ArrayList<>(abilities);
    }

    @Override
    public CellTemplate copy() {
        return new CellTemplate(getName(), getShape(), getTerrain(), getDescription(), getImgPath(), getTriggeredAbilities());
    }

    public CellInstance createInstance(CoordinateTuple coordinateTuple) {
        return createInstance(coordinateTuple, Collections.emptyList());
    }

    public CellInstance createInstance(CoordinateTuple coordinateTuple, Collection<UnitInstance> initialOccupants) {
        return new CellInstance(coordinateTuple, this, initialOccupants);
    }

    public CellInstance createInstance(CoordinateTuple coordinateTuple, Collection<TriggeredEffectInstance> cellAbilities, Collection<UnitInstance> initialOccupants) {
        return new CellInstance(coordinateTuple, this, cellAbilities, initialOccupants);
    }

    public CoordinateTuple getCoordinates() {
        return new CoordinateTuple(new int[getShape().getDimension()]);
    }

    public int dimension() {
        return getShape().getDimension();
    }

    public Shape getShape() {
        return shape;
    }

    public CellTemplate setShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public CellTemplate setTerrain(Terrain terrain) {
        this.terrain = terrain;
        return this;
    }

    public Collection<TriggeredEffectTemplate> getTriggeredAbilities() {
        return Collections.unmodifiableCollection(abilities);
    }

    public CellTemplate addTriggeredAbility(TriggeredEffectTemplate triggeredEffectTemplate) {
        abilities.add(triggeredEffectTemplate);
        return this;
    }

    public CellTemplate removeTriggeredAbility(TriggeredEffectTemplate triggeredEffectTemplate) {
        abilities.remove(triggeredEffectTemplate);
        return this;
    }

    public Collection<CellTemplate> getPredefinedCellTemplates() {
        return getPredefined(CellTemplate.class);
    }
}
