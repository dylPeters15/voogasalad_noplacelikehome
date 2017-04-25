package controller;

import backend.cell.Cell;
import backend.cell.Terrain;
import backend.game_engine.ResultQuadPredicate;
import backend.game_engine.ResultQuadPredicate.Result;
import backend.grid.CoordinateTuple;
import backend.grid.GameBoard;
import backend.player.ImmutablePlayer;
import backend.player.Team;
import backend.unit.Unit;
import backend.util.AuthoringGameState;
import backend.util.Event;
import backend.util.GameplayState;
import backend.util.ReadonlyGameplayState;
import backend.util.VoogaEntity;
import backend.util.Actionable.SerializableBiConsumer;
import backend.util.Requirement.SerializableBiPredicate;
import frontend.util.UIComponentListener;
import util.net.Modifier;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collection;

/**
 * @author Created by ncp14 on 4/3/2017.
 */
public interface Controller {

	ReadonlyGameplayState loadFile(Path path) throws IOException;

	void saveFile(Path path) throws IOException;

	GameBoard getGrid();

	void startClient(String host, int port, Duration timeout);

	void startServer(ReadonlyGameplayState gameState, int port, Duration timeout);

	Cell getCell(CoordinateTuple tuple);

	ReadonlyGameplayState getReadOnlyGameState();

	AuthoringGameState getAuthoringGameState();

	GameplayState getGameState();

	ImmutablePlayer getPlayer(String name);

	<U extends ReadonlyGameplayState> void setGameState(U newGameState);

	<U extends ReadonlyGameplayState> void sendModifier(Modifier<U> modifier);

	Collection<? extends Unit> getUnits();

	Collection<? extends VoogaEntity> getTemplatesByCategory(String category);

	default Collection<? extends Unit> getUnitTemplates() {
		return (Collection<? extends Unit>) getTemplatesByCategory("unit");
	}

	void addTemplatesByCategory(String category, VoogaEntity... templates);

	void removeTemplatesByCategory(String category, String... templateNames);

	default void addUnitTemplates(Unit... unitTemplates) {
		addTemplatesByCategory("unit", unitTemplates);
	}

	default void removeUnitTemplates(String... unitTemplates) {
		removeTemplatesByCategory("unit", unitTemplates);
	}

	default Collection<? extends Terrain> getTerrainTemplates() {
		return (Collection<? extends Terrain>) getTemplatesByCategory("terrain");
	}

	default void addTerrainTemplates(Terrain... terrainTemplates) {
		addTemplatesByCategory("terrain", terrainTemplates);
	}

	default void removeTerrainTemplates(String... terrainTemplates) {
		removeTemplatesByCategory("terrain", terrainTemplates);
	}

	void addListener(UIComponentListener objectToUpdate);

	void removeListener(UIComponentListener objectToUpdate);
	
	void enterAuthoringMode();
	
	void enterGamePlayMode();
	
	boolean isAuthoringMode();

	String getPlayerName();
	
	void addTurnRequirement(String name, String description, String imgPath, SerializableBiPredicate biPredicate);
	
	void removeTurnRequirement(String name);
	
	void activateTurnRequirement(String name);
	
	void deactivateTurnRequirement(String name);
	
	void addTurnAction(Event event, String name, String description, String imgPath, SerializableBiConsumer biConsumer);
	
	void removeTurnAction(Event event, String name);
	
	void activateTurnAction(Event event, String name);
	
	void deactivateTurnAction(Event event, String name);
	
	void addEndCondition(String name, String description, String imgPath, ResultQuadPredicate resultQuadPredicate);
	
	void removeEndCondition(String name);
	
	void activateEndCondition(String name);
	
	void deactivateEndCondition(String name);

	default Collection<? extends Team> getTeamTemplates(){
		return (Collection<? extends Team>) getTemplatesByCategory("team");
	}

	default void addTeamTemplates(Team... teamTemplates) {
		addTemplatesByCategory("team", teamTemplates);
	}

	default void removeTeamTemplates(String... teamTemplates) {
		removeTemplatesByCategory("team", teamTemplates);
	}

	void addPlayer(String playerName);

	void undo();
}
