package backend.unit;

import backend.cell.Cell;
import backend.cell.Terrain;
import backend.grid.GridPattern;
import backend.player.ImmutablePlayer;
import backend.unit.properties.*;
import backend.util.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class ModifiableUnit extends ModifiableVoogaObject<ModifiableUnit> implements Unit {
	//TODO ResourceBundlify
	public transient static final Unit SKELETON_WARRIOR = new ModifiableUnit("Skeleton Warrior")
			.addUnitStats(ModifiableUnitStat.HITPOINTS.setMaxValue(39.0), ModifiableUnitStat.MOVEPOINTS.setMaxValue(5))
			.setDescription("Once a noble knight in service of its kingdom, it once again takes up the blade for the lich king.")
			.setMovePattern(GridPattern.SQUARE_ADJACENT)
			.setImgPath("resources/images/skeletonWarrior.png")
			.addActiveAbilities(ActiveAbility.SWORD)
			.addTriggeredAbilities(ModifiableTriggeredEffect.RESET_MOVE_POINTS, ModifiableTriggeredEffect.RESET_ABILITY_POINTS)
			.addOffensiveModifiers(InteractionModifier.CHAOTIC);
	public transient static final Unit SKELETON_ARCHER = new ModifiableUnit("Skeleton Archer")
			.addUnitStats(ModifiableUnitStat.HITPOINTS.setMaxValue(34.0))
			.addUnitStats(ModifiableUnitStat.MOVEPOINTS.setMaxValue(6))
			.setMovePattern(GridPattern.SQUARE_ADJACENT)
			.setImgPath("resources/images/skeletonArcher.png")
			.setDescription("The skeletal corpse of an impoverished serf left to starve, reanimated by necromancy. Now, bow and arrow in hand, he pursues his revenge on the living.")
			.addOffensiveModifiers(InteractionModifier.CHAOTIC)
			.addTriggeredAbilities(ModifiableTriggeredEffect.RESET_MOVE_POINTS, ModifiableTriggeredEffect.RESET_ABILITY_POINTS)
			.addActiveAbilities(ActiveAbility.BOW);
	private transient static final Pattern MAGIC = Pattern.compile("_(\\d{2,})$");

	private final ActiveAbilitySet activeAbilities;
	private final TriggeredAbilitySet triggeredAbilities;
	private final ModifierSet offensiveModifiers;
	private final ModifierSet defensiveModifiers;
	private final Map<String, Integer> terrainMoveCosts;
	private final UnitStats stats;
	private GridPattern movePattern;
	private Faction faction;
	private ImmutablePlayer owner;
	private Cell currentCell;
	private boolean isVisible;

	public ModifiableUnit(String unitName) {
		this(unitName, Collections.emptySet(), null, null, Collections.emptyMap(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), "", "");
	}

	public ModifiableUnit(String unitName, Collection<? extends UnitStat> unitStats, Faction faction, GridPattern movePattern, Map<? extends String, Integer> moveCosts, Collection<? extends ActiveAbility> activeAbilities, Collection<? extends TriggeredEffect> triggeredAbilities, Collection<? extends InteractionModifier<Double>> offensiveModifiers, Collection<? extends InteractionModifier<Double>> defensiveModifiers, String unitDescription, String imgPath) {
		super(unitName, unitDescription, imgPath);
		this.faction = faction;
		this.terrainMoveCosts = new HashMap<>(moveCosts);
		this.stats = new UnitStats(unitStats);
		this.movePattern = movePattern;
		this.triggeredAbilities = new TriggeredAbilitySet(triggeredAbilities);
		this.activeAbilities = new ActiveAbilitySet(activeAbilities);
		this.offensiveModifiers = new ModifierSet("Offensive Modifiers", "Each unit has a set of offensive modifiers that can change the amount of damage the unit deals under different conditions.", "", offensiveModifiers);
		this.defensiveModifiers = new ModifierSet("Defensive modifiers", "Each unit has a set of defensive modifiers that can change the amount of damage the unit receives under different conditions.", "", defensiveModifiers);
	}

	@Deprecated
	public static Collection<ModifiableUnit> getPredefinedUnits() {
		return getPredefined(ModifiableUnit.class);
	}

	private void processTriggers(Event event, GameplayState gameState) {
		triggeredAbilities.forEach(e -> e.affect(this, event, gameState));
		triggeredAbilities.removeIf(TriggeredEffect::isExpired);
	}

	@Override
	public String getFormattedName() {
		return getName().split("_")[0];
	}

	@Override
	public ModifiableUnit copy() {
		Matcher m = MAGIC.matcher(getName());
		int id = m.find() ? Integer.parseInt(m.group(1)) : 0;
		setName(String.format("%s_%02d", m.replaceAll(""), id + 1));
		if (Objects.isNull(getHitPoints()) || Objects.isNull(getMovePoints()) || Objects.isNull(getMovePattern()) || getName().length() < 1) {
			throw new IncompleteUnitException();
		}
		if (Objects.isNull(getAbilityPoints())) {
			addUnitStats(ModifiableUnitStat.ABILITYPOINTS);
		}
		return new ModifiableUnit(getName(), getUnitStats(), getFaction(), getMovePattern(), getTerrainMoveCosts(), getActiveAbilities(), getTriggeredAbilities(), getOffensiveModifiers(), getDefensiveModifiers(), getDescription(), getImgPath());
	}

	@Override
	public void moveTo(Cell destinationCell, GameplayState gameState) {
		processTriggers(Event.UNIT_PRE_MOVEMENT, gameState);
		currentCell.leave(this, gameState);
		getMovePoints().setCurrentValue(getMovePoints().getCurrentValue() - getMoveCostByTerrain(destinationCell.getTerrain()));
		destinationCell.arrive(this, gameState);
		processTriggers(Event.UNIT_POST_MOVEMENT, gameState);
	}

	@Override
	public void startTurn(GameplayState gameState) {
		processTriggers(Event.TURN_START, gameState);
	}

	@Override
	public void endTurn(GameplayState gameState) {
		processTriggers(Event.TURN_END, gameState);
	}

	@Override
	public void takeDamage(double damage) {
		getHitPoints().setCurrentValue(getHitPoints().getCurrentValue() - damage);
		if (getHitPoints().isEmpty()) {
			getCurrentCell().removeOccupants(this);
		}
	}

	@Override
	public void useActiveAbility(ActiveAbility<VoogaEntity> activeAbility, VoogaEntity target, GameplayState gameState) {
		processTriggers(Event.UNIT_PRE_ABILITY_USE, gameState);
		activeAbility.affect(this, target, gameState);
		processTriggers(Event.UNIT_POST_ABILITY_USE, gameState);
	}

	@Override
	public final ActiveAbility getActiveAbilityByName(String name) {
		return activeAbilities.getByName(name);
	}

	@Override
	public final GridPattern getMovePattern() {
		return movePattern;
	}

	public final ModifiableUnit setMovePattern(GridPattern movePattern) {
		this.movePattern = movePattern;
		return this;
	}

	@Override
	public final Cell getCurrentCell() {
		return currentCell;
	}

	@Override
	public final ModifiableUnit setCurrentCell(Cell currentCell) {
		this.currentCell = currentCell;
		return this;
	}

	@Override
	public final Map<String, Integer> getTerrainMoveCosts() {
		return Collections.unmodifiableMap(terrainMoveCosts);
	}

	public final ModifiableUnit setTerrainMoveCosts(Map<Terrain, Integer> terrainMoveCosts) {
		this.terrainMoveCosts.clear();
		terrainMoveCosts.keySet().forEach(terrain -> this.terrainMoveCosts.put(terrain.getName(), terrainMoveCosts.get(terrain)));
		return this;
	}

	@Override
	@SafeVarargs
	public final ModifiableUnit addOffensiveModifiers(InteractionModifier<Double>... modifiers) {
		return (ModifiableUnit) Unit.super.addOffensiveModifiers(modifiers);
	}

	@Override
	public final ModifiableUnit addOffensiveModifiers(Collection<InteractionModifier<Double>> modifiers) {
		offensiveModifiers.addAll(modifiers);
		return this;
	}

	@SafeVarargs
	@Override
	public final ModifiableUnit removeOffensiveModifiers(InteractionModifier<Double>... modifiers) {
		return (ModifiableUnit) Unit.super.removeOffensiveModifiers(modifiers);
	}

	@Override
	public final ModifiableUnit removeOffensiveModifiers(Collection<InteractionModifier<Double>> modifiers) {
		defensiveModifiers.removeAll(modifiers);
		return this;
	}

	@Override
	public final List<InteractionModifier<Double>> getOffensiveModifiers() {
		return Collections.unmodifiableList(offensiveModifiers.getAll());
	}

	@SafeVarargs
	@Override
	public final ModifiableUnit addDefensiveModifiers(InteractionModifier<Double>... modifiers) {
		return (ModifiableUnit) Unit.super.removeDefensiveModifiers(modifiers);
	}

	@Override
	public final ModifiableUnit addDefensiveModifiers(Collection<InteractionModifier<Double>> modifiers) {
		defensiveModifiers.addAll(modifiers);
		return this;
	}

	@SafeVarargs
	@Override
	public final ModifiableUnit removeDefensiveModifiers(InteractionModifier<Double>... modifiers) {
		return (ModifiableUnit) Unit.super.removeDefensiveModifiers(modifiers);
	}

	@Override
	public final ModifiableUnit removeDefensiveModifiers(Collection<InteractionModifier<Double>> modifiers) {
		defensiveModifiers.removeAll(modifiers);
		return this;
	}

	@Override
	public final List<InteractionModifier<Double>> getDefensiveModifiers() {
		return Collections.unmodifiableList(defensiveModifiers.getAll());
	}

	@Override
	public final Collection<ActiveAbility> getActiveAbilities() {
		return Collections.unmodifiableCollection(activeAbilities.getAll());
	}

	@Override
	public ModifiableUnit addActiveAbilities(ActiveAbility... abilities) {
		return (ModifiableUnit) Unit.super.addActiveAbilities(abilities);
	}

	@Override
	public final ModifiableUnit addActiveAbilities(Collection<ActiveAbility> abilities) {
		activeAbilities.addAll(abilities);
		return this;
	}

	@Override
	public ModifiableUnit removeActiveAbilities(ActiveAbility... abilities) {
		activeAbilities.removeAll(abilities);
		return this;
	}

	@Override
	public final ModifiableUnit removeActiveAbilities(Collection<ActiveAbility> abilities) {
		activeAbilities.removeAll(abilities);
		return this;
	}

	@Override
	public final Collection<TriggeredEffect> getTriggeredAbilities() {
		return Collections.unmodifiableCollection(triggeredAbilities.getAll());
	}

	@Override
	public ModifiableUnit addTriggeredAbilities(TriggeredEffect... abilities) {
		return (ModifiableUnit) Unit.super.addTriggeredAbilities(abilities);
	}

	@Override
	public final ModifiableUnit addTriggeredAbilities(Collection<TriggeredEffect> abilities) {
		triggeredAbilities.addAll(abilities);
		return this;
	}

	@Override
	public ModifiableUnit removeTriggeredAbilities(TriggeredEffect... abilities) {
		return (ModifiableUnit) Unit.super.removeTriggeredAbilities(abilities);
	}

	@Override
	public final ModifiableUnit removeTriggeredAbilities(Collection<TriggeredEffect> abilities) {
		triggeredAbilities.removeAll(abilities);
		return this;
	}

	@Override
	public UnitStat getUnitStat(String name) {
		return stats.getByName(name);
	}

	@Override
	public final Faction getFaction() {
		return faction;
	}

	public final ModifiableUnit setFaction(Faction faction) {
		this.faction = faction;
		return this;
	}

	@Override
	public final boolean isVisible() {
		return isVisible;
	}

	@Override
	public final void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	@Override
	public ModifiableUnit setOwner(ImmutablePlayer owner) {
		this.owner = owner;
		return this;
	}

	@Override
	public Optional<ImmutablePlayer> getOwner() {
		return Optional.ofNullable(owner);
	}

	@Override
	public final Collection<? extends UnitStat> getUnitStats() {
		return stats.getAll();
	}

	public final ModifiableUnit addUnitStats(Collection<UnitStat> unitStats) {
		stats.addAll(unitStats);
		return this;
	}

	public final ModifiableUnit addUnitStats(UnitStat... unitStats) {
		stats.addAll(unitStats);
		return this;
	}

	public final ModifiableUnit removeUnitStats(Collection<UnitStat> unitStat) {
		stats.removeAll(unitStat);
		return this;
	}

	static class IncompleteUnitException extends RuntimeException {
		IncompleteUnitException() {
			super("Incomplete Unit");
		}
	}
}