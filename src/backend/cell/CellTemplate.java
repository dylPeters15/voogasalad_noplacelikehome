package backend.cell;

import backend.grid.CoordinateTuple;
import backend.grid.ModifiableGameBoard;
import backend.grid.Shape;
import backend.unit.UnitInstance;
import backend.util.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class CellTemplate extends VoogaTemplate implements CellInstance {
	//TODO ResourceBundlify
	public static final CellTemplate BASIC_HEXAGONAL_EMPTY = new CellTemplate("Basic Hexagonal Empty Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.EMPTY);
	public static final CellTemplate BASIC_HEXAGONAL_FLAT = new CellTemplate("Basic Hexagonal Flat Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.FLAT);
	public static final CellTemplate BASIC_HEXAGONAL_FOREST = new CellTemplate("Basic Hexagonal Forest Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.FOREST);
	public static final CellTemplate BASIC_HEXAGONAL_WATER = new CellTemplate("Basic Hexagonal Water Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.WATER);
	public static final CellTemplate BASIC_HEXAGONAL_MOUNTAIN = new CellTemplate("Basic Hexagonal Mountain Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.MOUNTAIN);
	public static final CellTemplate BASIC_HEXAGONAL_FORTIFIED = new CellTemplate("Basic Hexagonal Fortified Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.FORTIFIED);
	public static final CellTemplate HEALING_HEXAGONAL_EMPTY = new CellTemplate("Healing Hexagonal Empty Cell")
			.setShape(Shape.HEXAGONAL).setTerrain(ModifiableTerrain.EMPTY)
			.addTriggeredAbility(TriggeredEffectTemplate.FULL_HEAL);
	public static final CellTemplate HEALING_HEXAGONAL_FLAT = new CellTemplate("Healing Hexagonal Flat Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.FLAT)
			.addTriggeredAbility(TriggeredEffectTemplate.FULL_HEAL);
	public static final CellTemplate HEALING_HEXAGONAL_FOREST = new CellTemplate("Healing Hexagonal Forest Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.FOREST)
			.addTriggeredAbility(TriggeredEffectTemplate.FULL_HEAL);
	public static final CellTemplate HEALING_HEXAGONAL_WATER = new CellTemplate("Healing Hexagonal Water Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.WATER)
			.addTriggeredAbility(TriggeredEffectTemplate.FULL_HEAL);
	public static final CellTemplate HEALING_HEXAGONAL_MOUNTAIN = new CellTemplate("Healing Hexagonal Mountain Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.MOUNTAIN)
			.addTriggeredAbility(TriggeredEffectTemplate.FULL_HEAL);
	public static final CellTemplate HEALING_HEXAGONAL_FORTIFIED = new CellTemplate("Healing Hexagonal Fortified Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.FORTIFIED)
			.addTriggeredAbility(TriggeredEffectTemplate.FULL_HEAL);
	public static final CellTemplate BASIC_SQUARE_EMPTY = new CellTemplate("Basic Square Empty Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.EMPTY);
	public static final CellTemplate BASIC_SQUARE_FLAT = new CellTemplate("Basic Square Flat Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.FLAT);
	public static final CellTemplate BASIC_SQUARE_FOREST = new CellTemplate("Basic Square Forest Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.FOREST);
	public static final CellTemplate BASIC_SQUARE_WATER = new CellTemplate("Basic Square Water Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.WATER);
	public static final CellTemplate BASIC_SQUARE_MOUNTAIN = new CellTemplate("Basic Square Mountain Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.MOUNTAIN);
	public static final CellTemplate BASIC_SQUARE_FORTIFIED = new CellTemplate("Basic Square Fortified Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.FORTIFIED);
	public static final CellTemplate STRONG_ATTACK_SQUARE_MOUNTAIN = new CellTemplate("Strong Attack Square Cell")
			.setShape(Shape.SQUARE)
			.setTerrain(ModifiableTerrain.MOUNTAIN)
			.addTriggeredAbility(TriggeredEffectTemplate.STRONG_ATTACK);
	public static final CellTemplate STRONG_ATTACK_HEXAGON_MOUNTAIN = new CellTemplate("Strong Attack Square Cell")
			.setShape(Shape.HEXAGONAL)
			.setTerrain(ModifiableTerrain.MOUNTAIN)
			.addTriggeredAbility(TriggeredEffectTemplate.STRONG_ATTACK);
	private final List<TriggeredEffectTemplate> abilities;
	private final Map<String, UnitInstance> occupants;
	private Shape shape;
	private TerrainInstance terrain;
	private CoordinateTuple coordinates;

	public CellTemplate(String name) {
		this(name, null, null, null, "", "");
	}

	public CellTemplate(String name, CoordinateTuple location, Shape shape, TerrainInstance terrain, String description, String imgPath, TriggeredEffectTemplate... abilities) {
		this(name, location, shape, terrain, description, imgPath, Arrays.asList(abilities));
	}

	public CellTemplate(String name, CoordinateTuple location, Shape shape, TerrainInstance terrain, String description, String imgPath, Collection<TriggeredEffectTemplate> abilities) {
		super(name, description, imgPath);
		this.shape = shape;
		this.terrain = terrain;
		this.abilities = abilities.stream().map(TriggeredEffectTemplate::copy).collect(Collectors.toList());
		occupants = null;
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

	private void processTriggers(Event event, ImmutableGameState gameState) {
		occupants.values().forEach(unit -> abilities.forEach(ability -> ability.affect(unit, event, gameState)));
		abilities.removeIf(TriggeredEffectInstance::isExpired);
	}

	public CellTemplate addOccupants(UnitInstance... units) {
		Arrays.stream(units).forEach(unit -> occupants.put(unit.getName(), unit));
		return this;
	}

	public CellTemplate removeOccupants(UnitInstance... units) {
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

	public CellTemplate setLocation(CoordinateTuple coordinates) {
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

	public CellTemplate setShape(Shape shape) {
		this.shape = shape;
		return this;
	}

	@Override
	public Map<CoordinateTuple, CellInstance> getNeighbors(ModifiableGameBoard grid) {
		return grid.getNeighbors(this);
	}

	@Override
	public TerrainInstance getTerrain() {
		return terrain;
	}

	public CellTemplate setTerrain(TerrainInstance terrain) {
		this.terrain = terrain;
		return this;
	}

	@Override
	public Collection<TriggeredEffectTemplate> getTriggeredAbilities() {
		return Collections.unmodifiableCollection(abilities);
	}

	@Override
	public CellTemplate addTriggeredAbilities(TriggeredEffectTemplate... cellAbilities) {
		Arrays.stream(cellAbilities).forEach(e -> abilities.add(e.copy()));
		return this;
	}

	@Override
	public CellTemplate removeTriggeredAbilities(TriggeredEffectTemplate... cellAbilities) {
		abilities.removeAll(Arrays.asList(cellAbilities));
		return this;
	}

	@Override
	public Collection<UnitInstance> getOccupants() {
		return Collections.unmodifiableCollection(occupants.values());
	}

	@Override
	public void leave(UnitInstance unitInstance, ImmutableGameState gamestate) {
		abilities.forEach(ability -> ability.affect(unitInstance, Event.UNIT_PRE_MOVEMENT, gamestate));
		this.removeOccupants(unitInstance);
	}

	@Override
	public void arrive(UnitInstance unitInstance, ImmutableGameState gamestate) {
		this.addOccupants(unitInstance);
		abilities.forEach(ability -> ability.affect(unitInstance, Event.UNIT_POST_MOVEMENT, gamestate));
	}

	@Override
	public CellTemplate copy() {
		return new CellTemplate(getName(), getLocation(), getShape(), getTerrain(), getDescription(), getImgPath(), getTriggeredAbilities());
	}
}
