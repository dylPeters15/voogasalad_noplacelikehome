package controller;

import java.util.Collection;

import backend.cell.Terrain;
import backend.grid.GameBoard;
import backend.grid.ModifiableGameBoard;
import backend.player.ImmutablePlayer;
import backend.unit.ModifiableUnit;
import backend.unit.Unit;
import backend.util.AuthoringGameState;
import backend.util.GameplayState;
import backend.util.ReadonlyGameplayState;
import frontend.View;
import util.net.Modifier;
import util.net.ObservableClient;

/**
 * @author Created by ncp14
 *         This class is the communication controller which communicates between the frontend and backend.
 *         The primary purpose of my controller is to hide implementation of backend structure, specifically how
 *         our networking works and how the GameState is structured.
 */
public class CommunicationController<T extends ReadonlyGameplayState> implements Controller<T> {
	private MyBuffer<AuthoringGameState> gameStateHistory;
	private T mGameState;
	private View mView;
	private ObservableClient<T> mClient;

	public CommunicationController(T gameState, View view) {
		this.mGameState = gameState;
		this.mView = view;
		mClient.addListener(this::updateGameState);
	}

	@Override
	public GameBoard getGrid() {
		return mGameState.getGrid();
	}
	
	public void updateGameState(T newGameState)
	{
		mGameState = newGameState;
		mView.update();
	}

	public void setView(View view) {
		this.mView = view;
	}

	public void setClient(ObservableClient<T> client) {
		this.mClient = client;
		mView.update();
	}

	public ObservableClient getClient() {
		return mClient;
	}

	public void setGameState(T gameState) {
		gameStateHistory.addToBuffer(gameState);
		this.mGameState = gameState;
		mView.update();
	}

	@Override
	public ReadonlyGameplayState getReadOnlyGameState() {
		return mGameState;
	}

	public ReadonlyGameplayState getMostRecentGameState() {
		return gameStateHistory.getBufferHead();
	}

	@Override
	public AuthoringGameState getAuthoringGameState() {
		return (AuthoringGameState) mGameState;
	}

	@Override
	public T getGameState() {
		return mGameState;
	}

	@Override
	public ImmutablePlayer getPlayer(String name) {
		return mGameState.getPlayerByName(name);
	}

	@Override
	public ModifiableGameBoard getModifiableCells() {
		return (ModifiableGameBoard) mGameState.getGrid();
	}

	@Override
	public void sendModifier(Modifier<T> modifier) {
		mClient.addToOutbox(modifier);
	}

	@Override
	public Collection<? extends Unit> getUnits() {
		return mGameState.getGrid().getUnits();
	}

	@Override
	public Collection<? extends Terrain> getTerrains() {
		//Todo
		return null;
	}

	@Override
	public Collection<? extends Unit> getUnitTemplate() {
		return ModifiableUnit.getPredefinedUnits();
	}

	@Override
	public Collection<? extends Terrain> getTerrainTemplate() {
		//return ModifiableUnit.getPredefinedTerrain(); TOTO
		return null;
	}

}