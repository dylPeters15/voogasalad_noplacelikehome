package controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

import backend.cell.ModifiableTerrain;
import backend.cell.Terrain;
import backend.grid.GameBoard;
import backend.grid.ModifiableGameBoard;
import backend.player.ImmutablePlayer;
import backend.unit.ModifiableUnit;
import backend.unit.Unit;
import backend.util.AuthoringGameState;
import backend.util.GameplayState;
import backend.util.io.XMLSerializer;
import backend.util.ReadonlyGameplayState;
import frontend.View;
import frontend.util.Updatable;
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
	private Collection<Updatable> thingsToUpdate;
	private ObservableClient<? extends ReadonlyGameplayState> mClient;

	public CommunicationController(AuthoringGameState gameState, Collection<Updatable> thingsToUpdate) {
		this.thingsToUpdate = new ArrayList<Updatable>();
		if (thingsToUpdate != null){
			this.thingsToUpdate.addAll(thingsToUpdate);
		}
		this.mGameState = gameState;
		try{
			mClient = new ObservableClient<AuthoringGameState>("127.0.0.1", 10023, new XMLSerializer<AuthoringGameState>(), new XMLSerializer<AuthoringGameState>(), Duration.ofSeconds(60));
			mClient.addListener(e -> updateGameState(e));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public GameBoard getGrid() {
		return mGameState.getGrid();
	}
	
	public void updateGameState(ReadonlyGameplayState newGameState)
	{
		mGameState = newGameState;
		updateAll();
	}
	
	public void setClient(ObservableClient<? extends ReadonlyGameplayState> client) {
		this.mClient = client;
		updateAll();
	}

	public ObservableClient<? extends ReadonlyGameplayState> getClient() {
		return mClient;
	}

	public void setGameState(ReadonlyGameplayState gameState) {
		gameStateHistory.addToBuffer(gameState);
		this.mGameState = (AuthoringGameState) gameState;
		updateAll();
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
	public Collection<? extends Unit> getUnitTemplates() {
		return ModifiableUnit.getPredefinedUnits();
	}

	@Override
	public Collection<? extends Terrain> getTerrainTemplates() {
		return Terrain.getPredefinedTerrain();
	}
	
	public void addToUpdated(Updatable updatable){
		if (!thingsToUpdate.contains(updatable)){
			thingsToUpdate.add(updatable);
		}
	}
	
	public void removeFromUpdated(Updatable updatable){
		if (thingsToUpdate.contains(updatable)){
			thingsToUpdate.remove(updatable);
		}
	}
	
	private void updateAll(){
		thingsToUpdate.stream().forEach(updatable -> updatable.update());
	}

}