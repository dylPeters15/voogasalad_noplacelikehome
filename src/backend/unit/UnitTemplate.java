package backend.unit;

import backend.cell.CellInstance;
import backend.cell.TerrainInstance;
import backend.grid.GridPattern;
import backend.player.Player;
import backend.unit.properties.*;
import backend.util.*;

import java.util.*;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class UnitTemplate extends VoogaTemplate<UnitTemplate> implements UnitInstance {
	//TODO ResourceBundlify
	public static final UnitTemplate SKELETON_WARRIOR = new UnitTemplate("Skeleton Warrior")
			.setFaction(Faction.UNDEAD)
			.addUnitStats(UnitStatTemplate.HITPOINTS.copy().setMaxValue(39.0), UnitStatTemplate.MOVEPOINTS.copy().setMaxValue(5))
			.setDescription("Once a noble knight in service of his kingdom, the skeleton warrior once again takes up the blade for the lich king.")
			.setImgPath("spooky1.png")
			.setMovePattern(GridPattern.HEXAGONAL_ADJACENT)
			.addActiveAbilities(ActiveAbility.SWORD)
			.addOffensiveModifiers(InteractionModifier.CHAOTIC);
	public static final UnitTemplate SKELETON_ARCHER = new UnitTemplate("Skeleton Archer")
			.setFaction(Faction.UNDEAD)
			.addUnitStats(UnitStatTemplate.HITPOINTS.copy().setMaxValue(34.0))
			.addUnitStats(UnitStatTemplate.MOVEPOINTS.copy().setMaxValue(6))
			.setMovePattern(GridPattern.HEXAGONAL_ADJACENT)
			.setImgPath("spooky2.png")
			.setDescription("The skeletal corpse of an impoverished serf left to starve, reanimated by necromancy. Now, bow and arrow in hand, he enacts his revenge on the living.")
			.addOffensiveModifiers(InteractionModifier.CHAOTIC)
			.addActiveAbilities(ActiveAbility.BOW);
	private final ActiveAbilitySet activeAbilities;
	private final TriggeredAbilitySet triggeredAbilities;
	private final OffensiveModifierSet offensiveModifiers;
	private final DefensiveModifierSet defensiveModifiers;
	private final Map<TerrainInstance, Integer> terrainMoveCosts;
	private final UnitStats stats;
	private GridPattern movePattern;
	private Faction faction;
	private Player ownerPlayer;
	private CellInstance currentCell;
	private boolean isVisible;

	public UnitTemplate(String unitTemplateName) {
		this(unitTemplateName, Collections.emptySet(), null, null, Collections.emptyMap(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), "", "");
	}

	public UnitTemplate(String unitTemplateName, Collection<? extends UnitStatInstance> unitStats, Faction faction, GridPattern movePattern, Map<? extends TerrainInstance, Integer> moveCosts, Collection<? extends ActiveAbility> activeAbilities, Collection<? extends TriggeredEffectInstance> triggeredAbilities, Collection<? extends InteractionModifier<Double>> offensiveModifiers, Collection<? extends InteractionModifier<Double>> defensiveModifiers, String unitDescription, String imgPath) {
		super(unitTemplateName, unitDescription, imgPath);
		this.faction = faction;
		this.terrainMoveCosts = new HashMap<>(moveCosts);
		this.stats = new UnitStats(unitStats);
		this.movePattern = movePattern;
		this.triggeredAbilities = new TriggeredAbilitySet(triggeredAbilities);
		this.activeAbilities = new ActiveAbilitySet(activeAbilities);
		this.offensiveModifiers = new OffensiveModifierSet(offensiveModifiers);
		this.defensiveModifiers = new DefensiveModifierSet(defensiveModifiers);
	}

	private void processTriggers(Event event, ImmutableGameState gameState) {
		triggeredAbilities.forEach(e -> e.affect(this, event, gameState));
		triggeredAbilities.removeIf(TriggeredEffectInstance::isExpired);
	}

	@Override
	public UnitTemplate copy() {
		return new UnitTemplate(getName(), getUnitStats(), getFaction(), getMovePattern(), getTerrainMoveCosts(), getActiveAbilities(), getTriggeredAbilities(), getOffensiveModifiers(), getDefensiveModifiers(), getDescription(), getImgPath());
	}

	@Override
	public void moveTo(CellInstance destinationCell, ImmutableGameState gameState) {
		processTriggers(Event.UNIT_PRE_MOVEMENT, gameState);
		currentCell.leave(this, gameState);
		getMovePoints().setCurrentValue(getMovePoints().getCurrentValue() - getTerrainMoveCosts().get(destinationCell.getTerrain()));
		currentCell = destinationCell;
		currentCell.arrive(this, gameState);
		processTriggers(Event.UNIT_POST_MOVEMENT, gameState);
	}

	@Override
	public void startTurn(GameState gameState) {
		processTriggers(Event.TURN_START, gameState);
	}

	@Override
	public void endTurn(GameState gameState) {
		processTriggers(Event.TURN_END, gameState);
		getMovePoints().resetValue();
	}

	@Override
	public void takeDamage(double damage) {
		getHitPoints().setCurrentValue(getHitPoints().getCurrentValue() - damage);
	}

	@Override
	public void useActiveAbility(ActiveAbility activeAbility, VoogaObject target, ImmutableGameState gameState) {
		processTriggers(Event.UNIT_PRE_ABILITY_USE, gameState);
		activeAbility.affect(this, target, gameState);
		processTriggers(Event.UNIT_POST_ABILITY_USE, gameState);
	}

	@Override
	public final ActiveAbility getActiveAbilityByName(String name) {
		return null;
	}

	@Override
	public final GridPattern getMovePattern() {
		return movePattern;
	}

	public final UnitTemplate setMovePattern(GridPattern movePattern) {
		this.movePattern = movePattern;
		return this;
	}

	@Override
	public final CellInstance getCurrentCell() {
		return currentCell;
	}

	@Override
	public final Map<TerrainInstance, Integer> getTerrainMoveCosts() {
		return Collections.unmodifiableMap(terrainMoveCosts);
	}

	@Override
	public final UnitStatInstance<Integer> getMovePoints() {
		return stats.get("Movepoints");
	}

	@Override
	@SafeVarargs
	public final UnitTemplate addOffensiveModifiers(InteractionModifier<Double>... modifiers) {
		return (UnitTemplate) UnitInstance.super.addOffensiveModifiers(modifiers);
	}

	@Override
	public final UnitTemplate addOffensiveModifiers(Collection<InteractionModifier<Double>> modifiers) {
		offensiveModifiers.addAll(modifiers);
		return this;
	}

	@SafeVarargs
	@Override
	public final UnitTemplate removeOffensiveModifiers(InteractionModifier<Double>... modifiers) {
		return (UnitTemplate) UnitInstance.super.removeOffensiveModifiers(modifiers);
	}

	@Override
	public final UnitTemplate removeOffensiveModifiers(Collection<InteractionModifier<Double>> modifiers) {
		defensiveModifiers.removeAll(modifiers);
		return this;
	}

	@Override
	public final List<InteractionModifier<Double>> getOffensiveModifiers() {
		return Collections.unmodifiableList(offensiveModifiers.getAll());
	}

	@SafeVarargs
	@Override
	public final UnitTemplate addDefensiveModifiers(InteractionModifier<Double>... modifiers) {
		return (UnitTemplate) UnitInstance.super.removeDefensiveModifiers(modifiers);
	}

	@Override
	public final UnitTemplate addDefensiveModifiers(Collection<InteractionModifier<Double>> modifiers) {
		defensiveModifiers.addAll(modifiers);
		return this;
	}

	@SafeVarargs
	@Override
	public final UnitTemplate removeDefensiveModifiers(InteractionModifier<Double>... modifiers) {
		return (UnitTemplate) UnitInstance.super.removeDefensiveModifiers(modifiers);
	}

	@Override
	public final UnitTemplate removeDefensiveModifiers(Collection<InteractionModifier<Double>> modifiers) {
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
	public UnitTemplate addActiveAbilities(ActiveAbility... abilities) {
		return (UnitTemplate) UnitInstance.super.addActiveAbilities(abilities);
	}

	@Override
	public final UnitTemplate addActiveAbilities(Collection<ActiveAbility> abilities) {
		activeAbilities.addAll(abilities);
		return this;
	}

	@Override
	public UnitTemplate removeActiveAbilities(ActiveAbility... abilities) {
		return null;
	}

	@Override
	public final UnitTemplate removeActiveAbilities(Collection<ActiveAbility> abilities) {
		activeAbilities.removeAll(abilities);
		return this;
	}

	@Override
	public final Collection<TriggeredEffectInstance> getTriggeredAbilities() {
		return Collections.unmodifiableCollection(triggeredAbilities.getAll());
	}

	@Override
	public UnitTemplate addTriggeredAbilities(TriggeredEffectInstance... abilities) {
		return (UnitTemplate) UnitInstance.super.addTriggeredAbilities(abilities);
	}

	@Override
	public final UnitTemplate addTriggeredAbilities(Collection<TriggeredEffectInstance> abilities) {
		triggeredAbilities.addAll(abilities);
		return this;
	}

	@Override
	public UnitTemplate removeTriggeredAbilities(TriggeredEffectInstance... abilities) {
		return (UnitTemplate) UnitInstance.super.removeTriggeredAbilities(abilities);
	}

	@Override
	public final UnitTemplate removeTriggeredAbilities(Collection<TriggeredEffectInstance> abilities) {
		triggeredAbilities.removeAll(abilities);
		return this;
	}

	@Override
	public final UnitStatInstance<Double> getHitPoints() {
		return stats.get("Hitpoints");
	}

	@Override
	public final Faction getFaction() {
		return faction;
	}

	public final UnitTemplate setFaction(Faction faction) {
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
	public Player getOwner() {
		return ownerPlayer;
	}

	public void setOwner(Player p) {
		ownerPlayer = p;
	}

	public final void setCurrentCell(CellInstance currentCell) {
		this.currentCell = currentCell;
	}

	public final Collection<? extends UnitStatInstance> getUnitStats() {
		return stats.getAll();
	}

	public final UnitTemplate addUnitStats(Collection<UnitStatInstance> unitStats) {
		stats.addAll(unitStats);
		return this;
	}

	public final UnitTemplate addUnitStats(UnitStatInstance... unitStats) {
		stats.addAll(unitStats);
		return this;
	}

	public final UnitTemplate removeUnitStats(Collection<UnitStatInstance> unitStat) {
		stats.removeAll(unitStat);
		return this;
	}

	public static Collection<UnitTemplate> getPredefinedUnitTemplates() {
		return getPredefined(UnitTemplate.class);
	}
}