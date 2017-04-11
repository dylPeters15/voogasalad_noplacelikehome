package controller;

import backend.grid.GameBoard;
import backend.util.AuthoringGameState;
import frontend.View;
import util.net.ObservableClient;

/**
 * @author Created by ncp14
 *         This class is the communication controller which communicates between the frontend and backend.
 *         The primary purpose of my controller is to hide implementation of backend structure, specifically how
 *         our networking works and how the GameState is structured.
 */
public class CommunicationController implements Controller {
	private MyBuffer<AuthoringGameState> gameStateHistory;
	private AuthoringGameState mGameState;
	private View mView;
	private ObservableClient mClient;

	public CommunicationController(AuthoringGameState gameState, View view) {
		this.mGameState = gameState;
		this.mView = view;
	}

	@Override
	public GameBoard getGrid() {
		return mGameState.getGrid();
	}

	@Override
	public Object getUnitTemplates() {
		return mGameState.getTemplateByCategory("unit");
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

	public void setGameState(AuthoringGameState gameState) {
		gameStateHistory.addToBuffer(gameState);
		this.mGameState = gameState;
		mView.update();
	}

	public AuthoringGameState getGameState() {
		return mGameState;
	}

	public AuthoringGameState getMostRecentGameState() {
		return gameStateHistory.getBufferHead();
	}

}