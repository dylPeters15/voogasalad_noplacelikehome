import backend.cell.Cell;
import backend.cell.ModifiableCell;
import backend.cell.ModifiableTerrain;
import backend.grid.*;
import backend.player.ImmutablePlayer;
import backend.player.Player;
import backend.player.Team;
import backend.unit.ModifiableUnit;
import backend.unit.Unit;
import backend.unit.properties.ActiveAbility;
import backend.unit.properties.InteractionModifier;
import backend.util.AuthoringGameState;
import backend.util.GameplayState;
import backend.util.io.XMLSerializer;
import org.junit.Test;
import util.scripting.VoogaScriptEngine;
import util.scripting.VoogaScriptEngineManager;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.function.BiPredicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Created by th174 on 4/10/17.
 */
public class BackendTests {

	@Test
	public void testUnits() {
		assertEquals("name", new ModifiableUnit("name").getName());
	}

	@Test
	public void testAttack() {
		AuthoringGameState authoringGameState = new AuthoringGameState("test");
		Unit unit1 = ModifiableUnit.SKELETON_ARCHER.copy();
		Unit unit2 = ModifiableUnit.SKELETON_WARRIOR.copy();
		double startingHP = unit2.getHitPoints().getCurrentValue();
		unit1.useActiveAbility("Bow", unit2, authoringGameState);
		double endingHP = unit2.getHitPoints().getCurrentValue();
		assertEquals(startingHP - endingHP, 14, .00001);
	}

	@Test
	public void testScriptingAbiity() {
		String script = "" +
				"user.takeDamage(-5)\n" +
				"target.takeDamage(5)";
		VoogaScriptEngine engine = VoogaScriptEngineManager.read("Javascript", script);
		AuthoringGameState authoringGameState = new AuthoringGameState("test");
		Unit unit1 = ModifiableUnit.SKELETON_ARCHER.copy();
		Unit unit2 = ModifiableUnit.SKELETON_WARRIOR.copy();
		double startingHP1 = unit1.getHitPoints().getCurrentValue();
		double startingHP2 = unit2.getHitPoints().getCurrentValue();
		unit1.addActiveAbilities(new ActiveAbility<Unit>("Drain", engine, GridPattern.HEXAGONAL_ADJACENT, "", ""));
		unit1.useActiveAbility("Drain", unit2, authoringGameState);
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
	public void createAndSaveGrid() throws IOException {
		GameBoard board = new ModifiableGameBoard("testBoard")
				.setTemplateCell(ModifiableCell.BASIC_SQUARE_FLAT).setRows(5).setColumns(5).setBoundsHandler(BoundsHandler.TOROIDAL_BOUNDS).build();
		AuthoringGameState authoringGameState = new AuthoringGameState("test").setGrid(board);
		new CoordinateTuple(0, 0).getNeighbors().forEach(e -> board.get(e).arrive(ModifiableUnit.SKELETON_ARCHER.copy(), authoringGameState));
		XMLSerializer<AuthoringGameState> serializer = new XMLSerializer<>();
		authoringGameState.addTurnRequirements((BiPredicate<ImmutablePlayer, GameplayState> & Serializable) (player, immutableAuthoringGameState) -> 3 < 5);
		Files.write(Paths.get("data/saved_game_data/basic_grid_5x5.xml"), ((String) serializer.serialize(authoringGameState)).getBytes());
		assertEquals(25, board.getCells().values().stream().filter(e -> e.getTerrain().equals(ModifiableTerrain.FLAT)).count());
		assertEquals(4, board.getUnits().size());
		assertTrue(!board.get(new CoordinateTuple(0, 4)).getOccupants().isEmpty());
		assertTrue(!board.get(new CoordinateTuple(4, 0)).getOccupants().isEmpty());
		assertTrue(!board.get(new CoordinateTuple(1, 0)).getOccupants().isEmpty());
		assertTrue(!board.get(new CoordinateTuple(0, 1)).getOccupants().isEmpty());
	}

	@Test
	public void testRectangularBounds(){
		GridPattern pattern = GridPattern.HEXAGONAL_ADJACENT;
		ModifiableCell template = ModifiableCell.BASIC_SQUARE_FLAT;
		GameBoard board = new ModifiableGameBoard("testBoard")
				.setTemplateCell(ModifiableCell.BASIC_SQUARE_FLAT).setRows(5).setColumns(5).setBoundsHandler(BoundsHandler.TOROIDAL_BOUNDS).build();
		assertEquals(4,board.getRectangularBounds().getMax(0));
		assertEquals(4,board.getRectangularBounds().getMax(1));
		assertEquals(4,board.getBounds().getMax(0));
		assertEquals(4,board.getBounds().getMax(1));
		assertEquals(0,board.getRectangularBounds().getMin(0));
		assertEquals(0,board.getRectangularBounds().getMin(1));
		assertEquals(0,board.getBounds().getMin(0));
		assertEquals(0,board.getBounds().getMin(1));

	}

	@Test
	public void createAndSaveGameState() {
		GridPattern gridPattern = GridPattern.HEXAGONAL_ADJACENT;
		ModifiableGameBoard DEFAULT_GAME_BOARD = (ModifiableGameBoard) new ModifiableGameBoard("Plain Rectangular Flat Board - 5x5")
				.setTemplateCell(ModifiableCell.BASIC_SQUARE_FLAT)
				.setRows(10)
				.setColumns(10).setBoundsHandler(BoundsHandler.FINITE_BOUNDS)
				.setDescription("Default base game board for testing purposes")
				.setImgPath("Duvall_lettuce.png")
				.build();
		AuthoringGameState authoringGameState = new AuthoringGameState("test").setGrid(DEFAULT_GAME_BOARD)
				.setDescription("This is just a test gamestate").setImgPath("").addTeam(new Team("Team 1", "Test team 1", ""))
				.addPlayer(new Player("Player 1", "Test player 1", ""));
		XMLSerializer<AuthoringGameState> serializer = new XMLSerializer<>();
		try {
			Files.write(Paths.get("data/saved_game_data/test_base_gamestate.xml"), ((String) serializer.serialize(authoringGameState)).getBytes());
			AuthoringGameState loadedGameState = serializer.unserialize(new String(Files.readAllBytes(Paths.get("data/saved_game_data/test_base_gamestate.xml"))));
			assertTrue(loadedGameState.getGrid().getTemplateCell().getTerrain().equals(ModifiableTerrain.FLAT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
