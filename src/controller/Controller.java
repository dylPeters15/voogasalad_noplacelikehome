package controller;

import java.util.Collection;

import backend.cell.Terrain;
import backend.grid.GameBoard;
import backend.grid.ModifiableGameBoard;
import backend.player.ImmutablePlayer;
import backend.unit.Unit;
import backend.util.AuthoringGameState;
import frontend.View;
import backend.util.GameplayState;
import backend.util.ReadonlyGameplayState;
import util.net.Modifier;

/**
 * @author Created by ncp14 on 4/3/2017.
 */
public interface Controller<T extends ReadonlyGameplayState> {

	GameBoard getGrid();
	
	ReadonlyGameplayState getReadOnlyGameState();
	
	AuthoringGameState getAuthoringGameState();

	T getGameState();

	ImmutablePlayer getPlayer(String name);

	void setView(View view);

	void setGameState(T newGameState);
	
	ModifiableGameBoard getModifiableCells();
	
	void sendModifier(Modifier<T> modifier);

	Collection<? extends Unit> getUnits();

	Collection<? extends Terrain> getTerrains();
	
	Collection<? extends Unit> getUnitTemplate();

	Collection<? extends Terrain> getTerrainTemplate();
	

	
	
	
	
	
	
	
}
