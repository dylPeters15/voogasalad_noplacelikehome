package backend.cell;

import backend.grid.CoordinateTuple;
import backend.grid.ModifiableGameBoard;
import backend.grid.Shape;
import backend.unit.Unit;
import backend.util.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class ModifiableCell extends ModifiableVoogaObject implements Cell {
	//TODO ResourceBundlify
	public static final ModifiableCell BASIC_HEXAGONAL_EMPTY = new ModifiableCell("Basic Hexagonal Empty Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.EMPTY);
	public static final ModifiableCell BASIC_HEXAGONAL_FLAT = new ModifiableCell("Basic Hexagonal Flat Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.FLAT);
	public static final ModifiableCell BASIC_HEXAGONAL_FOREST = new ModifiableCell("Basic Hexagonal Forest Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.FOREST);
	public static final ModifiableCell BASIC_HEXAGONAL_WATER = new ModifiableCell("Basic Hexagonal Water Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.WATER);
	public static final ModifiableCell BASIC_HEXAGONAL_MOUNTAIN = new ModifiableCell("Basic Hexagonal Mountain Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.MOUNTAIN);
	public static final ModifiableCell BASIC_HEXAGONAL_FORTIFIED = new ModifiableCell("Basic Hexagonal Fortified Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.FORTIFIED);
	public static final ModifiableCell HEALING_HEXAGONAL_EMPTY = new ModifiableCell("Healing Hexagonal Empty Cell")
			.setShape(Shape.HEXAGONAL).setTerrain(ModifiableTerrain.EMPTY)
			.addTriggeredAbility(ModifiableTriggeredEffect.FULL_HEAL);
	public static final ModifiableCell HEALING_HEXAGONAL_FLAT = new ModifiableCell("Healing Hexagonal Flat Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.FLAT)
			.addTriggeredAbility(ModifiableTriggeredEffect.FULL_HEAL);
	public static final ModifiableCell HEALING_HEXAGONAL_FOREST = new ModifiableCell("Healing Hexagonal Forest Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.FOREST)
			.addTriggeredAbility(ModifiableTriggeredEffect.FULL_HEAL);
	public static final ModifiableCell HEALING_HEXAGONAL_WATER = new ModifiableCell("Healing Hexagonal Water Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.WATER)
			.addTriggeredAbility(ModifiableTriggeredEffect.FULL_HEAL);
	public static final ModifiableCell HEALING_HEXAGONAL_MOUNTAIN = new ModifiableCell("Healing Hexagonal Mountain Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.MOUNTAIN)
			.addTriggeredAbility(ModifiableTriggeredEffect.FULL_HEAL);
	public static final ModifiableCell HEALING_HEXAGONAL_FORTIFIED = new ModifiableCell("Healing Hexagonal Fortified Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.FORTIFIED)
			.addTriggeredAbility(ModifiableTriggeredEffect.FULL_HEAL);
	public static final ModifiableCell BASIC_SQUARE_EMPTY = new ModifiableCell("Basic Square Empty Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.EMPTY);
	public static final ModifiableCell BASIC_SQUARE_FLAT = new ModifiableCell("Basic Square Flat Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.FLAT);
	public static final ModifiableCell BASIC_SQUARE_FOREST = new ModifiableCell("Basic Square Forest Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.FOREST);
	public static final ModifiableCell BASIC_SQUARE_WATER = new ModifiableCell("Basic Square Water Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.WATER);
	public static final ModifiableCell BASIC_SQUARE_MOUNTAIN = new ModifiableCell("Basic Square Mountain Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.MOUNTAIN);
	public static final ModifiableCell BASIC_SQUARE_FORTIFIED = new ModifiableCell("Basic Square Fortified Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.FORTIFIED);
	public static final ModifiableCell STRONG_ATTACK_SQUARE_MOUNTAIN = new ModifiableCell("Strong Attack Square Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.MOUNTAIN)
			.addTriggeredAbility(ModifiableTriggeredEffect.STRONG_ATTACK);
	public static final ModifiableCell STRONG_ATTACK_HEXAGON_MOUNTAIN = new ModifiableCell("Strong Attack Square Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.MOUNTAIN)
			.addTriggeredAbility(ModifiableTriggeredEffect.STRONG_ATTACK);
	private final List<ModifiableTriggeredEffect> abilities;
	private final Map<String, Unit> occupants;
	private Shape shape;
	private Terrain terrain;
	private CoordinateTuple coordinates;

	public ModifiableCell(String name) {
		this(name, null, null, null, "", "");
	}

	public ModifiableCell(String name, CoordinateTuple location, Shape shape, Terrain terrain, String description, String imgPath, ModifiableTriggeredEffect... abilities) {
		this(name, location, shape, terrain, description, imgPath, Arrays.asList(abilities));
	}

	public ModifiableCell(String name, CoordinateTuple location, Shape shape, Terrain terrain, String description, String imgPath, Collection<ModifiableTriggeredEffect> abilities) {
		super(name, description, imgPath);
		this.shape = shape;
		this.terrain = terrain;
		this.abilities = abilities.stream().map(ModifiableTriggeredEffect::copy).collect(Collectors.toList());
		this.coordinates = location;
		occupants = new HashMap<>();
	}

	public ModifiableCell addTriggeredAbility(ModifiableTriggeredEffect modifiableTriggeredEffect) {
		abilities.add(modifiableTriggeredEffect);
		return this;
	}

	public ModifiableCell removeTriggeredAbility(ModifiableTriggeredEffect modifiableTriggeredEffect) {
		abilities.remove(modifiableTriggeredEffect);
		return this;
	}

	public Collection<ModifiableCell> getPredefinedCellTemplates() {
		return getPredefined(ModifiableCell.class);
	}

	private void processTriggers(Event event, ImmutableGameState gameState) {
		occupants.values().forEach(unit -> abilities.forEach(ability -> ability.affect(unit, event, gameState)));
		abilities.removeIf(TriggeredEffect::isExpired);
	}

	public ModifiableCell addOccupants(Unit... units) {
		Arrays.stream(units).forEach(unit -> occupants.put(unit.getName(), unit));
		return this;
	}

	public ModifiableCell removeOccupants(Unit... units) {
		Arrays.stream(units).forEach(unit -> occupants.remove(unit.getName()));
		return this;
	}

	@Override
	public void startTurn(ImmutableGameState gameState) {
		processTriggers(Event.TURN_START, gameState);
	}

	@Override
	public void endTurn(ImmutableGameState gameState) {
		processTriggers(Event.TURN_END, gameState);
	}

	@Override
	public CoordinateTuple getLocation() {
		return coordinates;
	}

	public ModifiableCell setLocation(CoordinateTuple coordinates) {
		this.coordinates = coordinates;
		return this;
	}

	@Override
	public int dimension() {
		return getShape().getDimension();
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	public ModifiableCell setShape(Shape shape) {
		this.shape = shape;
		return this;
	}

	@Override
	public Map<CoordinateTuple, Cell> getNeighbors(ModifiableGameBoard grid) {
		return grid.getNeighbors(this);
	}

	@Override
	public Terrain getTerrain() {
		return terrain;
	}

	public ModifiableCell setTerrain(Terrain terrain) {
		this.terrain = terrain;
		return this;
	}

	@Override
	public Collection<ModifiableTriggeredEffect> getTriggeredAbilities() {
		return Collections.unmodifiableCollection(abilities);
	}

	@Override
	public ModifiableCell addTriggeredAbilities(ModifiableTriggeredEffect... cellAbilities) {
		Arrays.stream(cellAbilities).forEach(e -> abilities.add(e.copy()));
		return this;
	}

	@Override
	public ModifiableCell removeTriggeredAbilities(ModifiableTriggeredEffect... cellAbilities) {
		abilities.removeAll(Arrays.asList(cellAbilities));
		return this;
	}

	@Override
	public Collection<Unit> getOccupants() {
		return Collections.unmodifiableCollection(occupants.values());
	}

	@Override
	public void leave(Unit unit, ImmutableGameState gamestate) {
		abilities.forEach(ability -> ability.affect(unit, Event.UNIT_PRE_MOVEMENT, gamestate));
		this.removeOccupants(unit);
	}

	@Override
	public void arrive(Unit unit, ImmutableGameState gamestate) {
		this.addOccupants(unit);
		abilities.forEach(ability -> ability.affect(unit, Event.UNIT_POST_MOVEMENT, gamestate));
	}

	@Override
	public ModifiableCell copy() {
		return new ModifiableCell(getName(), getLocation(), getShape(), getTerrain(), getDescription(), getImgPath(), getTriggeredAbilities().stream().map(ModifiableTriggeredEffect::copy).collect(Collectors.toList()));
	}

	@Deprecated
	public Collection<ModifiableCell> getPredefinedCells() {
		return getPredefined(ModifiableCell.class);
	}

	@Override
	public String toString() {
		return "\n" + super.toString() + " @ " + getLocation();
	}
}
