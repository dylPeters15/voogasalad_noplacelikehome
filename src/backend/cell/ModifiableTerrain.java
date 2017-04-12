package backend.cell;

import backend.unit.properties.DefensiveModifierSet;
import backend.unit.properties.InteractionModifier;
import backend.unit.properties.OffensiveModifierSet;
import backend.unit.properties.TriggeredAbilitySet;
import backend.util.ModifiableVoogaObject;
import backend.util.TriggeredEffect;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 4/11/2017.
 */
public class ModifiableTerrain extends ModifiableVoogaObject<ModifiableTerrain> implements Terrain {

	public static final int DEFAULT_DEFAULT_MOVE_COST = 1;
	private int defaultMoveCost;
	private final TriggeredAbilitySet triggeredAbilities;
	private final OffensiveModifierSet offensiveModifiers;
	private final DefensiveModifierSet defensiveModifiers;

	public ModifiableTerrain(String name) {
		this(name, DEFAULT_DEFAULT_MOVE_COST, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "", "");
	}

	public ModifiableTerrain(String name, int defaultMoveCost, Collection<? extends TriggeredEffect> triggeredAbilities, Collection<? extends InteractionModifier<Double>> offensiveModifiers, Collection<? extends InteractionModifier<Double>> defensiveModifiers, String description, String imgPath) {
		super(name, description, imgPath);
		this.defaultMoveCost = defaultMoveCost;
		this.triggeredAbilities = new TriggeredAbilitySet(triggeredAbilities);
		this.offensiveModifiers = new OffensiveModifierSet(offensiveModifiers);
		this.defensiveModifiers = new DefensiveModifierSet(defensiveModifiers);
	}

	@Override
	public Collection<? extends TriggeredEffect> getTriggeredAbilities() {
		return triggeredAbilities.getAll();
	}

	@Override
	public ModifiableTerrain addTriggeredAbilities(TriggeredEffect... triggeredAbilities) {
		this.triggeredAbilities.addAll(triggeredAbilities);
		return this;
	}

	@Override
	public ModifiableTerrain removeTriggeredAbilities(TriggeredEffect... triggeredAbilities) {
		this.triggeredAbilities.removeAll(triggeredAbilities);
		return this;
	}

	@Override
	public ModifiableTerrain removeTriggeredAbilitiesIf(Predicate<TriggeredEffect> predicate) {
		triggeredAbilities.removeIf(predicate);
		return this;
	}

	@Override
	public List<? extends InteractionModifier<Double>> getOffensiveModifiers() {
		return offensiveModifiers.getAll();
	}

	@Override
	@SafeVarargs
	public final ModifiableTerrain addOffensiveModifiers(InteractionModifier<Double>... offensiveModifiers) {
		this.offensiveModifiers.addAll(offensiveModifiers);
		return this;
	}

	@Override
	@SafeVarargs
	public final ModifiableTerrain removeOffensiveModifiers(InteractionModifier<Double>... offensiveModifiers) {
		this.offensiveModifiers.removeAll(offensiveModifiers);
		return this;
	}

	@Override
	public List<? extends InteractionModifier<Double>> getDefensiveModifiers() {
		return defensiveModifiers.getAll();
	}

	@Override
	@SafeVarargs
	public final ModifiableTerrain addDefensiveModifiers(InteractionModifier<Double>... defensiveModifiers) {
		this.defensiveModifiers.addAll(defensiveModifiers);
		return this;
	}

	@Override
	@SafeVarargs
	public final ModifiableTerrain removeDefensiveModifiers(InteractionModifier<Double>... defensiveModifiers) {
		this.defensiveModifiers.removeAll(defensiveModifiers);
		return this;
	}

	@Override
	public int getDefaultMoveCost() {
		return defaultMoveCost;
	}

	public ModifiableTerrain setDefaultMoveCost(int cost) {
		defaultMoveCost = cost;
		return this;
	}

	@Override
	public ModifiableTerrain copy() {
		return new ModifiableTerrain(
				getName(),
				getDefaultMoveCost(),
				getTriggeredAbilities().stream().map(TriggeredEffect::copy).collect(Collectors.toList()),
				getOffensiveModifiers().stream().map(InteractionModifier::copy).collect(Collectors.toList()),
				getDefensiveModifiers().stream().map(InteractionModifier::copy).collect(Collectors.toList()),
				getDescription(),
				getImgPath()
		);
	}

	@Deprecated
	public static Collection<? extends Terrain> getPredefinedTerrain() {
		return getPredefined(ModifiableTerrain.class);
	}
}
