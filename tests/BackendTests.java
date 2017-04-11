import backend.cell.Cell;
import backend.cell.ModifiableCell;
import backend.grid.*;
import backend.unit.ModifiableUnit;
import backend.unit.Unit;
import backend.unit.properties.ActiveAbility;
import backend.unit.properties.InteractionModifier;
import backend.util.GameState;
import backend.util.io.XMLSerializer;
import org.junit.Test;
import util.scripting.VoogaScriptEngine;
import util.scripting.VoogaScriptEngineManager;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Created by th174 on 4/10/17.
 */
public class BackendTests {

	@org.junit.Test
	public void testUnits() {
		assertEquals("name", new ModifiableUnit("name").getName());
	}

	@Test
	public void testAttack() {
		GameState gameState = new GameState();
		Unit unit1 = ModifiableUnit.SKELETON_ARCHER.copy();
		Unit unit2 = ModifiableUnit.SKELETON_WARRIOR.copy();
		double startingHP = unit2.getHitPoints().getCurrentValue();
		unit1.useActiveAbility("Bow", unit2, gameState);
		double endingHP = unit2.getHitPoints().getCurrentValue();
		assertEquals(startingHP - endingHP, 14, .00001);
	}

	@Test
	public void testScriptingAbiity() {
		String script = "" +
				"user:takeDamage(-5)\n" +
				"target:takeDamage(5)";
		VoogaScriptEngine engine = VoogaScriptEngineManager.read("Lua", script);
		GameState gameState = new GameState();
		Unit unit1 = ModifiableUnit.SKELETON_ARCHER.copy();
		Unit unit2 = ModifiableUnit.SKELETON_WARRIOR.copy();
		double startingHP1 = unit1.getHitPoints().getCurrentValue();
		double startingHP2 = unit2.getHitPoints().getCurrentValue();
		unit1.addActiveAbilities(new ActiveAbility<Unit>("Drain", engine, GridPattern.HEXAGONAL_ADJACENT, "", ""));
		unit1.useActiveAbility("Drain", unit2, gameState);
		double endingHP1 = unit1.getHitPoints().getCurrentValue();
		double endingHP2 = unit2.getHitPoints().getCurrentValue();
		assertEquals(5, startingHP2 - endingHP2, .0001);
		assertEquals(-5, startingHP1 - endingHP1, .0001);
	}

	@Test
	public void testXML() {
		Unit unit1 = ModifiableUnit.SKELETON_WARRIOR.copy();
		XMLSerializer<Unit> serializer = new XMLSerializer<>();
		unit1.addDefensiveModifiers(InteractionModifier.EVASIVE);
		String xmlUnit1 = (String) serializer.serialize(unit1);
		Unit unit2 = serializer.unserialize(xmlUnit1);
		assertEquals(unit1.getName() + unit1.getDescription(), unit2.getName() + unit2.getDescription());
	}

	@Test
	public void testCoordinateNeighbors() {
		Collection<CoordinateTuple> hexagonalAdjacent1 = Shape.HEXAGONAL.getNeighborPattern().getCoordinates();
		Collection<CoordinateTuple> squareAdjacent1 = Shape.SQUARE.getNeighborPattern().getCoordinates();
		Collection<CoordinateTuple> hexagonalAdjacent2 = CoordinateTuple.getOrigin(3).getRays(1, 2);
		Collection<CoordinateTuple> squareAdjacent2 = CoordinateTuple.getOrigin(2).getRays(1, 2);
		squareAdjacent1.forEach(e -> assertTrue(squareAdjacent2.contains(e)));
		hexagonalAdjacent1.forEach(e -> assertTrue(hexagonalAdjacent2.contains(e)));
	}

	@Test
	public void createGrid() {
		Cell template = ModifiableCell.BASIC_SQUARE_FLAT;
		ModifiableGameBoard board = new ModifiableGameBoard("testBoard", template, 5, 5, BoundsHandler.TOROIDAL_BOUNDS, "", "").copy();
		GameState gameState = new GameState(board);
		new CoordinateTuple(0, 0).getNeighbors().forEach(e -> board.get(e).arrive(ModifiableUnit.SKELETON_ARCHER.copy(), gameState));
		assertEquals(25, board.getCells().values().stream().filter(e -> e.getTerrain().equals(template.getTerrain())).count());
		assertEquals(4, board.getUnits().size());
		assertTrue(!board.get(new CoordinateTuple(0, 4)).getOccupants().isEmpty());
		assertTrue(!board.get(new CoordinateTuple(4, 0)).getOccupants().isEmpty());
		assertTrue(!board.get(new CoordinateTuple(1, 0)).getOccupants().isEmpty());
		assertTrue(!board.get(new CoordinateTuple(0, 1)).getOccupants().isEmpty());
	}
}
