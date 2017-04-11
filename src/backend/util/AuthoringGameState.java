package backend.util;

import backend.cell.ModifiableCell;
import backend.cell.ModifiableTerrain;
import backend.game_engine.ResultQuadPredicate;
import backend.grid.BoundsHandler;
import backend.grid.GameBoard;
import backend.grid.GridPattern;
import backend.grid.ModifiableGameBoard;
import backend.player.Player;
import backend.player.Team;
import backend.unit.ModifiableUnit;
import backend.unit.properties.ActiveAbility;
import backend.unit.properties.InteractionModifier;
import backend.unit.properties.ModifiableUnitStat;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class AuthoringGameState extends GameplayState implements VoogaEntity {
	private Map<String, ModifiableVoogaCollection> templates;

	public AuthoringGameState(String name) {
		super(name, null, "", "");
		templates = new HashMap<>();
		templates.put("gameboard", new ModifiableVoogaCollection<>("GameBoards", "", "", ModifiableGameBoard.getPredefinedGameBoards()));
		templates.put("terrain", new ModifiableVoogaCollection<>("Terrain", "", "", ModifiableTerrain.getPredefinedTerrain()));
		templates.put("unit", new ModifiableVoogaCollection<>("Units", "", "", ModifiableUnit.getPredefinedUnits()));
		templates.put("unittriggeredeffect", new ModifiableVoogaCollection<>("Unit Passive/Triggered Abilities", "", "", ModifiableTriggeredEffect.getPredefinedUnitPassives()));
		templates.put("celltriggeredeffect", new ModifiableVoogaCollection<>("Cell Passive/Triggered Abilities", "", "", ModifiableTriggeredEffect.getPredefinedCellPassives()));
		templates.put("unitstat", new ModifiableVoogaCollection<>("Unit Stats", "", "", ModifiableUnitStat.getPredefinedUnitStats()));
		templates.put("gridpattern", new ModifiableVoogaCollection<>("Grid Patterns", "", "", GridPattern.getPredefinedGridPatterns()));
		templates.put("boundshandler", new ModifiableVoogaCollection<>("Bounds Handlers", "", "", BoundsHandler.getPredefinedBoundsHandlers()));
		templates.put("activeabilities", new ModifiableVoogaCollection<>("Active Abilities", "", "", ActiveAbility.getPredefinedActiveAbilities()));
		templates.put("cell", new ModifiableVoogaCollection<>("Cells", "", "", ModifiableCell.getPredefinedCells()));
		templates.put("offensivemodifier", new ModifiableVoogaCollection<>("Offensive Modifiers", "", "", InteractionModifier.getPredefinedOffensiveModifiers()));
		templates.put("defensivemodifier", new ModifiableVoogaCollection<>("Defensive Modifiers", "", "", InteractionModifier.getPredefinedDefensiveModifiers()));
	}

	@Override
	public AuthoringGameState addPlayer(Player newPlayer) {
		return (AuthoringGameState) super.addPlayer(newPlayer);
	}

	@Override
	public AuthoringGameState addPlayer(Player newPlayer, Team team) {
		return (AuthoringGameState) super.addPlayer(newPlayer, team);
	}

	@Override
	public AuthoringGameState setDescription(String description) {
		return (AuthoringGameState) super.setDescription(description);
	}

	@Override
	public AuthoringGameState setImgPath(String imgPath) {
		return (AuthoringGameState) super.setImgPath(imgPath);
	}

	@Override
	public AuthoringGameState setName(String name) {
		return (AuthoringGameState) super.setName(name);
	}

	@Override
	public ModifiableGameBoard getGrid() {
		return (ModifiableGameBoard) super.getGrid();
	}

	@Override
	public AuthoringGameState setGrid(GameBoard grid) {
		return (AuthoringGameState) super.setGrid(grid);
	}

	@Override
	public AuthoringGameState addObjectives(ResultQuadPredicate... objectives) {
		return (AuthoringGameState) super.addObjectives(objectives);
	}

	@Override
	public AuthoringGameState addObjectives(Collection<ResultQuadPredicate> objectives) {
		return (AuthoringGameState) super.addObjectives(objectives);
	}

	@Override
	public AuthoringGameState addTeam(Team team) {
		return (AuthoringGameState) super.addTeam(team);
	}

	@Override
	public AuthoringGameState addTurnActions(Event event, Collection<BiConsumer<Player, GameplayState>> actions) {
		return (AuthoringGameState) super.addTurnActions(event, actions);
	}

	@Override
	public AuthoringGameState addTurnActions(Event event, BiConsumer<Player, GameplayState>... actions) {
		return (AuthoringGameState) super.addTurnActions(event, actions);
	}

	@Override
	public AuthoringGameState addTurnRequirements(Collection<BiPredicate<Player, GameplayState>> turnRequirements) {
		return (AuthoringGameState) super.addTurnRequirements(turnRequirements);
	}

	@Override
	public AuthoringGameState addTurnRequirements(BiPredicate<Player, GameplayState>... turnRequirements) {
		return (AuthoringGameState) super.addTurnRequirements(turnRequirements);
	}

	@Override
	public AuthoringGameState removeObjectives(ResultQuadPredicate... objectives) {
		return (AuthoringGameState) super.removeObjectives(objectives);
	}

	@Override
	public AuthoringGameState removeObjectives(Collection<ResultQuadPredicate> objectives) {
		return (AuthoringGameState) super.removeObjectives(objectives);
	}

	@Override
	public AuthoringGameState removeTeamByName(String name) {
		return (AuthoringGameState) super.removeTeamByName(name);
	}

	@Override
	public AuthoringGameState removeTurnActions(Event event, Collection<BiConsumer<Player, GameplayState>> actions) {
		return (AuthoringGameState) super.removeTurnActions(event, actions);
	}

	@Override
	public AuthoringGameState removeTurnActions(Event event, BiConsumer<Player, GameplayState>[] actions) {
		return (AuthoringGameState) super.removeTurnActions(event, actions);
	}

	@Override
	public AuthoringGameState removeTurnRequirements(Collection<BiPredicate<Player, GameplayState>> turnRequirements) {
		return (AuthoringGameState) super.removeTurnRequirements(turnRequirements);
	}

	@Override
	public AuthoringGameState removeTurnRequirements(BiPredicate<Player, GameplayState>... turnRequirements) {
		return (AuthoringGameState) super.removeTurnRequirements(turnRequirements);
	}

	//Name can be Terrain, OffensiveModifier, DefensiveModifier, Cell, CellTriggeredEffect, UnitTriggeredEffect, ActiveAbility, Unit, UnitStat, GridPattern, GameBoard
	//Case and space character insensitive
	public ModifiableVoogaCollection<VoogaEntity,?> getTemplateByCategory(String categoryName) {
		return templates.get(categoryName.replaceAll(" ", "").replaceAll("s$", "").toLowerCase());
	}
}
