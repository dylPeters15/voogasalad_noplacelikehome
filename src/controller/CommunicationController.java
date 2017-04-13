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
public class CommunicationController implements Controller {
	private MyBuffer<AuthoringGameState> gameStateHistory;
	private ReadonlyGameplayState mGameState;
	private View mView;
	private ObservableClient<? extends ReadonlyGameplayState> mClient;

	public CommunicationController(AuthoringGameState gameState, View view) {
		this.mGameState = gameState;
		this.mView = view;
		mClient.addListener(e -> updateGameState(e));
	}

	@Override
	public GameBoard getGrid() {
		return mGameState.getGrid();
	}
	
	public void updateGameState(ReadonlyGameplayState newGameState)
	{
		mGameState = newGameState;
		mView.update();
	}

	public void setView(View view) {
		this.mView = view;
	}

	public void setClient(ObservableClient client) {
		this.mClient = client;
		mView.update();
	}

	public ObservableClient getClient() {
		return mClient;
	}

	public void setGameState(ReadonlyGameplayState gameState) {
		gameStateHistory.addToBuffer(gameState);
		this.mGameState = (AuthoringGameState) gameState;
		mView.update();
	}

	public ReadonlyGameplayState getGameState() {
		return (AuthoringGameState) mGameState;
	}

	public ReadonlyGameplayState getMostRecentGameState() {
		return gameStateHistory.getBufferHead();
	}

	@Override
	public AuthoringGameState getAuthoringGameState() {
		return (AuthoringGameState) mGameState;
	}

	@Override
	public GameplayState getGameplayState() {
		return (GameplayState) mGameState;
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
	public void sendModifier(Modifier modifier) {
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