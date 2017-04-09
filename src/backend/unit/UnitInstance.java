package backend.unit;

import backend.cell.CellInstance;
import backend.cell.TerrainInstance;
import backend.grid.CoordinateTuple;
import backend.grid.GridPattern;
import backend.grid.ModifiableGameBoard;
import backend.player.Player;
import backend.player.Team;
import backend.unit.properties.ActiveAbility;
import backend.unit.properties.Faction;
import backend.unit.properties.InteractionModifier;
import backend.unit.properties.UnitStatInstance;
import backend.util.GameState;
import backend.util.ImmutableGameState;
import backend.util.TriggeredEffectInstance;
import backend.util.VoogaObject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Timmy
 *
 * @author Created by th174 on 3/27/2017.
 */
public interface UnitInstance extends VoogaObject {
	void moveTo(CellInstance destinationCell, ImmutableGameState gameState);

	void startTurn(GameState gameState);

	void endTurn(GameState gameState);

	void takeDamage(double damage);

	default void useActiveAbility(String activeAbilityName, VoogaObject target, ImmutableGameState gameState) {
		useActiveAbility(getActiveAbilityByName(activeAbilityName), target, gameState);
	}

	void useActiveAbility(ActiveAbility activeAbility, VoogaObject target, ImmutableGameState gameState);

	ActiveAbility getActiveAbilityByName(String name);

	default Collection<CellInstance> getLegalMoves(ModifiableGameBoard grid) {
		return getMovePattern().getCoordinates().parallelStream()
				.map(e -> grid.get(e.sum(this.getLocation())))
				.filter(Objects::nonNull)
				.filter(e -> getTerrainMoveCosts().get(e.getTerrain()) < getMovePoints().getCurrentValue()).collect(Collectors.toSet());
	}

	GridPattern getMovePattern();

	default CoordinateTuple getLocation() {
		return getCurrentCell().getLocation();
	}

	CellInstance getCurrentCell();

	Map<TerrainInstance, Integer> getTerrainMoveCosts();

	UnitStatInstance<Integer> getMovePoints();

	default Collection<UnitInstance> getAllNeighboringUnits(ModifiableGameBoard grid) {
		return getNeighboringUnits(grid).values().parallelStream().flatMap(Collection::stream).parallel().collect(Collectors.toSet());
	}

	default Map<CoordinateTuple, Collection<? extends UnitInstance>> getNeighboringUnits(ModifiableGameBoard grid) {
		Map<CoordinateTuple, Collection<? extends UnitInstance>> neighbors = getCurrentCell().getNeighbors(grid).entrySet().parallelStream()
				.filter(e -> !e.getValue().getOccupants().isEmpty())
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getOccupants()));
		neighbors.put(CoordinateTuple.getOrigin(getCurrentCell().dimension()), getCurrentCell().getOccupants().parallelStream().filter(e -> !equals(e)).collect(Collectors.toSet()));
		return neighbors;
	}

	default Map<CoordinateTuple, CellInstance> getNeighboringCells(ModifiableGameBoard grid) {
		return getCurrentCell().getNeighbors(grid);
	}

	default UnitInstance addOffensiveModifiers(InteractionModifier<Double>... modifiers) {
		return addOffensiveModifiers(Arrays.asList(modifiers));
	}

	UnitInstance addOffensiveModifiers(Collection<InteractionModifier<Double>> modifiers);

	default UnitInstance removeOffensiveModifiers(InteractionModifier<Double>... modifiers) {
		return removeOffensiveModifiers(Arrays.asList(modifiers));
	}

	UnitInstance removeOffensiveModifiers(Collection<InteractionModifier<Double>> modifiers);

	default double applyAllOffensiveModifiers(Double originalValue, UnitInstance target, ImmutableGameState gameState) {
		return InteractionModifier.modifyAll(getOffensiveModifiers(), originalValue, this, target, gameState);
	}

	List<InteractionModifier<Double>> getOffensiveModifiers();

	default UnitInstance addDefensiveModifiers(InteractionModifier<Double>... modifiers) {
		return addDefensiveModifiers(Arrays.asList(modifiers));
	}

	UnitInstance addDefensiveModifiers(Collection<InteractionModifier<Double>> modifiers);

	default UnitInstance removeDefensiveModifiers(InteractionModifier<Double>... modifiers) {
		return removeDefensiveModifiers(Arrays.asList(modifiers));
	}

	UnitInstance removeDefensiveModifiers(Collection<InteractionModifier<Double>> modifiers);

	default double applyAllDefensiveModifiers(Double originalValue, UnitInstance agent, ImmutableGameState gameState) {
		return InteractionModifier.modifyAll(getDefensiveModifiers(), originalValue, agent, this, gameState);
	}

	List<InteractionModifier<Double>> getDefensiveModifiers();

	Collection<? extends ActiveAbility> getActiveAbilities();

	default UnitInstance addActiveAbilities(ActiveAbility... abilities) {
		return addActiveAbilities(Arrays.asList(abilities));
	}

	UnitInstance addActiveAbilities(Collection<ActiveAbility> abilities);

	default UnitInstance removeActiveAbilities(ActiveAbility... abilities) {
		return removeActiveAbilities(Arrays.asList(abilities));
	}

	UnitInstance removeActiveAbilities(Collection<ActiveAbility> abilities);

	Collection<? extends TriggeredEffectInstance> getTriggeredAbilities();

	default UnitInstance addTriggeredAbilities(TriggeredEffectInstance... abilities) {
		return addTriggeredAbilities(Arrays.asList(abilities));
	}

	UnitInstance addTriggeredAbilities(Collection<TriggeredEffectInstance> abilities);

	default UnitInstance removeTriggeredAbilities(TriggeredEffectInstance... abilities) {
		return removeTriggeredAbilities(Arrays.asList(abilities));
	}

	UnitInstance removeTriggeredAbilities(Collection<TriggeredEffectInstance> abilities);

	UnitStatInstance<Double> getHitPoints();

	Faction getFaction();

	boolean isVisible();

	void setVisible(boolean isVisible);

	default int movePointsTo(CoordinateTuple other) {
		throw new RuntimeException("Not Implemented Yet");
	}

	default Team getTeam() {
		return getOwner().getTeam();
	}

	Player getOwner();
}